package gongdan.com.three;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
//import org.junit.Test;
import org.junit.Test;

/**
 * 山东手机模板
 * 
 * @author Administrator
 *
 */
public class CreateXMLForSDPHONE {

	// 1 、 电影Program
	public void REGISTDocument(List<Map<String,String>> list,String ip) throws Exception {
		try {
			for (Map<String, String> map : list) {
				String Code = map.get("Code").replaceAll("\n", "");
				String name = map.get("Name");
				String SeriesFlag = map.get("SeriesFlag");
				String Director = map.get("Director");
				String SearchName = map.get("SearchName");
				String ActorDisplay = map.get("ActorDisplay");
				String OriginalCountry = map.get("OriginalCountry");
				String ReleaseYear = map.get("ReleaseYear");
				String OrgAirDate = map.get("OrgAirDate");
				String Description = map.get("Description").replaceAll("\n", "");
				if (Description != null && !"".equals(Description)) {
					if (Description.length() > 200) {
						Description = Description.substring(0, 200) + "...";
					}
				}
				String MovieType = map.get("MovieType");
				String MovieFileURL = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/ts/movies/"+ map.get("MovieFileURL");
				String PfileURL1 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL1");
				String PfileURL2 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL2");
				String PfileURL3 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL3");
				String typeId = map.get("typeId");
				String Duration = map.get("Duration");
				File file1 = new File("E:/SHANS/Program/" + name);
				if (file1.mkdirs()) {
					System.out.println("多级层文件夹创建成功！创建后的文件目录为：" + file1.getPath() + ",上级文件为:" + file1.getParent());
				}
				FileWriter out = new FileWriter(file1.getPath() + "/" + Code + ".xml"); // 写入文件
				OutputFormat formater = new OutputFormat();
				formater.setIndent(true);
				formater.setNewlines(true);
				formater.setEncoding("UTF-8");
				XMLWriter xmlWriter = new XMLWriter(out, formater);
				Document dateDocument = dateDocument(Code, name, SeriesFlag, Director, SearchName, ActorDisplay,
						OriginalCountry, ReleaseYear, OrgAirDate, Description, MovieType, MovieFileURL, PfileURL1,
						PfileURL2, PfileURL3, typeId, Duration);
				xmlWriter.write(dateDocument);
				xmlWriter.close();
			}
		} catch (Exception e) {
			System.out.println("生成 phone 电影Programs 工单失败");
			e.printStackTrace();
		} 
	}

	//2、电视剧Series
	public void addDocument(List<Map<String,String>> list,String ip) throws Exception {
		try {
			for (Map<String, String> map : list) {
				String parentCode = map.get("parentCode").replaceAll("\n", "");// 获取数据库person表中name字段的值
				String name = map.get("Name");
				String SearchName = map.get("SearchName");
				String volumnCount = map.get("VolumnCount");
				String Director = map.get("Director");
				String ActorDisplay = map.get("ActorDisplay");
				String OriginalCountry = map.get("OriginalCountry");
				String ReleaseYear = map.get("ReleaseYear");
				String OrgAirDate = map.get("OrgAirDate");
				String Description = map.get("Description").replaceAll("\n", "");
				if (Description != null && !"".equals(Description)) {
					if (Description.length() > 200) {
						Description = Description.substring(0, 200) + "...";
					}
				}
				String PfileURL1 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL1");
				String PfileURL2 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL2");
				String PfileURL3 = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/picture"+ map.get("PictureFileURL3");
				String typeId = map.get("typeId");
				File file1 = new File("E:/SHANS/Series/" + name);
				if (file1.mkdirs()) {
					System.out.println("多级层文件夹创建成功！创建后的文件目录为：" + file1.getPath() + ",上级文件为:" + file1.getParent());
				}
				FileWriter out = new FileWriter(file1.getPath() + "/" + parentCode + ".xml"); // 写入文件
				OutputFormat formater = new OutputFormat();
				formater.setIndent(true);
				formater.setNewlines(true);// 设置是否换行
				formater.setEncoding("UTF-8");
				XMLWriter xmlWriter = new XMLWriter(out, formater);
				Document dateDocument = createDocument(parentCode, name, SearchName, volumnCount, Director,
						ActorDisplay, OriginalCountry, ReleaseYear, OrgAirDate, Description, PfileURL1, PfileURL2,
						PfileURL3, typeId);
				xmlWriter.write(dateDocument);
				xmlWriter.close();
			}
		} catch (Exception e) {
			System.out.println("生成 phone 电视剧 Series 工单失败");
			e.printStackTrace();
		}
	}

	//3、 电视剧Program
	public void addDocument1122(List<Map<String,String>> list,String ip) throws Exception {
		try {
			for (Map<String, String> map : list) {
				String parentCode = map.get("parentCode");// 获取数据库person表中name字段的值
				String name = map.get("Name");
				String Code = map.get("Code").replaceAll("\n", "");
				String ProgramName = map.get("ProgramName");
				String SeriesFlag = map.get("SeriesFlag");
				String MovieType = map.get("MovieType");
				String MovieFileURL = "ftp://huashi2018:pingtai123@"+ip+":21/shandong/ts/movies"
						+ map.get("MovieFileURL");
				String Sequence = map.get("Sequence");
				String Duration = map.get("Duration");
				File file1 = new File("E:/SHANS/SProgram/" + name);
				if (file1.mkdirs()) {
					System.out.println("多级层文件夹创建成功！创建后的文件目录为：" + file1.getPath() + ",上级文件为:" + file1.getParent());
				}
				FileWriter out = new FileWriter(file1.getPath() + "/" + Code + ".xml"); // 写入文件
				OutputFormat formater = new OutputFormat();
				formater.setIndent(true);
				formater.setNewlines(true);
				formater.setEncoding("UTF-8");
				XMLWriter xmlWriter = new XMLWriter(out, formater);
				Document dateDocument = createDocument1122(parentCode, Code, ProgramName, SeriesFlag, MovieType,
						MovieFileURL, Sequence, Duration);
				xmlWriter.write(dateDocument);
				xmlWriter.close();
			}
		} catch (Exception e) {
			System.out.println("生成 phone 电视剧 Program 工单失败");
			e.printStackTrace();
		} 
	}


	public Document createDocument(String parentCode, String name, String SearchName, String volumnCount,
			String Director, String ActorDisplay, String OriginalCountry, String ReleaseYear, String OrgAirDate,
			String Description, String PfileURL1, String PfileURL2, String PfileURL3, String typeId) {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("ADI").addAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");

		Element Objects = root.addElement("Objects");
		// 连续剧元数据
		Element Object = Objects.addElement("Object").addAttribute("ElementType", "Series")
				.addAttribute("ID", parentCode).addAttribute("Action", "REGIST").addAttribute("Code", parentCode);
		Object.addElement("Property").addAttribute("Name", "LicensingWindowStart").addCDATA("20120101010101");
		Object.addElement("Property").addAttribute("Name", "LicensingWindowEnd").addCDATA("29990101010101");
		Object.addElement("Property").addAttribute("Name", "ContentProvider").addCDATA("HSWJ");
		Object.addElement("Property").addAttribute("Name", "Name").addCDATA(name);
		Object.addElement("Property").addAttribute("Name", "SearchName").addCDATA(SearchName);
		Object.addElement("Property").addAttribute("Name", "ReleaseYear").addCDATA(ReleaseYear);
		Object.addElement("Property").addAttribute("Name", "OrgAirDate").addCDATA(OrgAirDate);
		Object.addElement("Property").addAttribute("Name", "VolumnCount").addCDATA(volumnCount);
		Object.addElement("Property").addAttribute("Name", "OriginalCountry").addCDATA(OriginalCountry);
		Object.addElement("Property").addAttribute("Name", "Status").addCDATA("1");
		Object.addElement("Property").addAttribute("Name", "Duration").addCDATA("0");
		Object.addElement("Property").addAttribute("Name", "Director").addCDATA(Director.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "ActorDisplay").addCDATA(ActorDisplay.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "Kpeople").addCDATA(ActorDisplay.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "Description").addCDATA(Description);

		// 剧照图片
		Element Object2 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", parentCode.replaceAll("000000", "100000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", parentCode.replaceAll("000000", "100000"));
		Object2.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL2);
		Object2.addElement("Property").addAttribute("Name", "Type").addCDATA("1");

		// 海报图片
		Element Object3 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", parentCode.replaceAll("000000", "200000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", parentCode.replaceAll("000000", "200000"));
		Object3.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL1);
		Object3.addElement("Property").addAttribute("Name", "Type").addCDATA("3");

		// 广告图片
		Element Object4 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", parentCode.replaceAll("000000", "500000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", parentCode.replaceAll("000000", "500000"));
		Object4.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL3);
		Object4.addElement("Property").addAttribute("Name", "Type").addCDATA("5");

		// 映射
		Element Mappings = root.addElement("Mappings");

		// 映射剧照
		Element Mapping2 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", parentCode.replaceAll("000000", "100000"))
				.addAttribute("ElementType", "Series").addAttribute("ElementID", parentCode)
				.addAttribute("ParentCode", parentCode.replaceAll("000000", "100000"))
				.addAttribute("ElementCode", parentCode).addAttribute("Action", "REGIST");
		Mapping2.addElement("Property").addAttribute("Name", "Type").addCDATA("1");
		Mapping2.addElement("Property").addAttribute("Name", "Sequence").addCDATA("1");

		// 映射海报
		Element Mapping3 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", parentCode.replaceAll("000000", "200000"))
				.addAttribute("ElementType", "Series").addAttribute("ElementID", parentCode)
				.addAttribute("ParentCode", parentCode.replaceAll("000000", "200000"))
				.addAttribute("ElementCode", parentCode).addAttribute("Action", "REGIST");
		Mapping3.addElement("Property").addAttribute("Name", "Type").addCDATA("3");
		Mapping3.addElement("Property").addAttribute("Name", "Sequence").addCDATA("3");

		// 映射广告
		Element Mapping4 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", parentCode.replaceAll("000000", "500000"))
				.addAttribute("ElementType", "Series").addAttribute("ElementID", parentCode)
				.addAttribute("ParentCode", parentCode.replaceAll("000000", "500000"))
				.addAttribute("ElementCode", parentCode).addAttribute("Action", "REGIST");
		Mapping4.addElement("Property").addAttribute("Name", "Type").addCDATA("5");
		Mapping4.addElement("Property").addAttribute("Name", "Sequence").addCDATA("5");

		// 栏目映射
		Element Mapping5 = Mappings.addElement("Mapping").addAttribute("ParentType", "Category")
				.addAttribute("ParentID", typeId).addAttribute("ElementType", "Series")
				.addAttribute("ElementID", parentCode).addAttribute("ParentCode", typeId)
				.addAttribute("ElementCode", parentCode).addAttribute("Action", "REGIST");

		return document;
	}



	public Document createDocument1122(String parentCode, String Code, String ProgramName, String SeriesFlag,
			String MovieType, String MovieFileURL, String Sequence, String Duration) {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("ADI").addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");

		Element Objects = root.addElement("Objects");
		// 连续剧元数据
		Element Object = Objects.addElement("Object").addAttribute("ElementType", "Program").addAttribute("ID", Code)
				.addAttribute("Action", "REGIST").addAttribute("Code", Code);
		Object.addElement("Property").addAttribute("Name", "LicensingWindowStart").addCDATA("20120101010101");
		Object.addElement("Property").addAttribute("Name", "LicensingWindowEnd").addCDATA("29990101010101");
		Object.addElement("Property").addAttribute("Name", "ContentProvider").addCDATA("HUASHIWANGJU");
		Object.addElement("Property").addAttribute("Name", "Name").addCDATA(ProgramName);
		Object.addElement("Property").addAttribute("Name", "SearchName").addCDATA("");
		Object.addElement("Property").addAttribute("Name", "Status").addCDATA("1");
		Object.addElement("Property").addAttribute("Name", "SeriesFlag").addCDATA(SeriesFlag);
		Object.addElement("Property").addAttribute("Name", "SourceType").addCDATA("1");
		Object.addElement("Property").addAttribute("Name", "Duration").addCDATA(Duration);

		// 内容媒体数据
		/*Element Object2 = Objects.addElement("Object").addAttribute("ElementType", "Movie")
				.addAttribute("ID", Code.replace("000000", "300000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", Code.replace("000000", "300000"));
		Object2.addElement("Property").addAttribute("Name", "Type").addCDATA("1");// 正片
		Object2.addElement("Property").addAttribute("Name", "FileURL").addCDATA(MovieFileURL);*/

		// 映射
		Element Mappings = root.addElement("Mappings");

		// 映射连续剧
		/*Element Mapping = Mappings.addElement("Mapping").addAttribute("ParentType", "Program")
				.addAttribute("ParentID", Code).addAttribute("ElementType", "Movie")
				.addAttribute("ElementID", Code.replace("000000", "300000")).addAttribute("ParentCode", Code)
				.addAttribute("ElementCode", Code.replace("000000", "300000")).addAttribute("Action", "REGIST");*/

		// 映射连续剧
		Element Mapping2 = Mappings.addElement("Mapping").addAttribute("ParentType", "Series")
				.addAttribute("ParentID", parentCode).addAttribute("ElementType", "Program")
				.addAttribute("ElementID", Code).addAttribute("ParentCode", parentCode)
				.addAttribute("ElementCode", Code).addAttribute("Action", "REGIST");
		Mapping2.addElement("Property").addAttribute("Name", "Sequence").addCDATA(Sequence);

		return document;
	}


	public Document dateDocument(String Code, String name, String SeriesFlag, String Director, String SearchName,
			String ActorDisplay, String OriginalCountry, String ReleaseYear, String OrgAirDate, String Description,
			String MovieType, String MovieFileURL, String PfileURL1, String PfileURL2, String PfileURL3, String typeId,
			String Duration) {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("ADI").addAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");

		Element Objects = root.addElement("Objects");
		// 连续剧元数据
		Element Object = Objects.addElement("Object").addAttribute("ElementType", "Program").addAttribute("ID", Code)
				.addAttribute("Action", "REGIST").addAttribute("Code", Code);
		Object.addElement("Property").addAttribute("Name", "LicensingWindowStart").addCDATA("20120101010101");
		Object.addElement("Property").addAttribute("Name", "LicensingWindowEnd").addCDATA("29990101010101");
		Object.addElement("Property").addAttribute("Name", "ContentProvider").addCDATA("HSWJ");
		Object.addElement("Property").addAttribute("Name", "Name").addCDATA(name);
		Object.addElement("Property").addAttribute("Name", "SearchName").addCDATA(SearchName);
		Object.addElement("Property").addAttribute("Name", "ReleaseYear").addCDATA(ReleaseYear);
		Object.addElement("Property").addAttribute("Name", "OrgAirDate").addCDATA(OrgAirDate);
		Object.addElement("Property").addAttribute("Name", "VolumnCount").addCDATA("");
		Object.addElement("Property").addAttribute("Name", "Status").addCDATA("1");
		Object.addElement("Property").addAttribute("Name", "SeriesFlag").addCDATA("0");
		Object.addElement("Property").addAttribute("Name", "Duration").addCDATA(Duration);
		Object.addElement("Property").addAttribute("Name", "Director").addCDATA(Director.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "ActorDisplay").addCDATA(ActorDisplay.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "Kpeople").addCDATA(ActorDisplay.replaceAll(",", " "));
		Object.addElement("Property").addAttribute("Name", "OriginalCountry").addCDATA(OriginalCountry);
		Object.addElement("Property").addAttribute("Name", "Description").addCDATA(Description);

		// 剧照图片
		Element Object2 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", Code.replaceAll("000000", "100000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", Code.replaceAll("000000", "100000"));
		Object2.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL2);
		Object2.addElement("Property").addAttribute("Name", "Type").addCDATA("1");

		// 海报图片
		Element Object3 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", Code.replaceAll("000000", "200000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", Code.replaceAll("000000", "200000"));
		Object3.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL1);
		Object3.addElement("Property").addAttribute("Name", "Type").addCDATA("3");

		// 广告图片
		Element Object4 = Objects.addElement("Object").addAttribute("ElementType", "Picture")
				.addAttribute("ID", Code.replaceAll("000000", "500000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", Code.replaceAll("000000", "500000"));
		Object4.addElement("Property").addAttribute("Name", "FileURL").addCDATA(PfileURL3);
		Object4.addElement("Property").addAttribute("Name", "Type").addCDATA("5");

		// 内容媒体数据
		/*Element Object5 = Objects.addElement("Object").addAttribute("ElementType", "Movie")
				.addAttribute("ID", Code.replaceAll("000000", "300000")).addAttribute("Action", "REGIST")
				.addAttribute("Code", Code.replaceAll("000000", "300000"));
		Object5.addElement("Property").addAttribute("Name", "Type").addCDATA("1");// 正片
		Object5.addElement("Property").addAttribute("Name", "FileURL").addCDATA(MovieFileURL);*/
		// 映射
		Element Mappings = root.addElement("Mappings");

		// 映射连续剧
		/*Element Mapping = Mappings.addElement("Mapping").addAttribute("ParentType", "Program")
				.addAttribute("ParentID", Code).addAttribute("ElementType", "Movie")
				.addAttribute("ElementID", Code.replaceAll("000000", "300000")).addAttribute("ParentCode", Code)
				.addAttribute("ElementCode", Code.replaceAll("000000", "300000")).addAttribute("Action", "REGIST");*/
		// 映射剧照
		Element Mapping2 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", Code.replaceAll("000000", "100000")).addAttribute("ElementType", "Program")
				.addAttribute("ElementID", Code).addAttribute("ParentCode", Code.replaceAll("000000", "100000"))
				.addAttribute("ElementCode", Code).addAttribute("Action", "REGIST");
		Mapping2.addElement("Property").addAttribute("Name", "Type").addCDATA("1");
		Mapping2.addElement("Property").addAttribute("Name", "Sequence").addCDATA("1");

		// 映射海报
		Element Mapping3 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", Code.replaceAll("000000", "200000")).addAttribute("ElementType", "Program")
				.addAttribute("ElementID", Code).addAttribute("ParentCode", Code.replaceAll("000000", "200000"))
				.addAttribute("ElementCode", Code).addAttribute("Action", "REGIST");
		Mapping3.addElement("Property").addAttribute("Name", "Type").addCDATA("3");
		Mapping3.addElement("Property").addAttribute("Name", "Sequence").addCDATA("3");

		// 映射广告
		Element Mapping4 = Mappings.addElement("Mapping").addAttribute("ParentType", "Picture")
				.addAttribute("ParentID", Code.replaceAll("000000", "500000"))
				.addAttribute("ElementType", "Program").addAttribute("ElementID", Code)
				.addAttribute("ParentCode", Code.replaceAll("000000", "500000"))
				.addAttribute("ElementCode", Code).addAttribute("Action", "REGIST");
		Mapping4.addElement("Property").addAttribute("Name", "Type").addCDATA("5");
		Mapping4.addElement("Property").addAttribute("Name", "Sequence").addCDATA("5");

		// 栏目映射
		Element Mapping5 = Mappings.addElement("Mapping").addAttribute("ParentType", "Category")
				.addAttribute("ParentID", typeId).addAttribute("ElementType", "Program").addAttribute("ElementID", Code)
				.addAttribute("ParentCode", typeId).addAttribute("ElementCode", Code).addAttribute("Action", "REGIST");

		return document;
	}

}
