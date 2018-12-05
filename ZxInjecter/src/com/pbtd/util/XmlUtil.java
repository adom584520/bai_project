package com.pbtd.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.entity.SeriesDetail;


public class XmlUtil {

	private static final Logger logger=Logger.getLogger(XmlUtil.class);
	private static SeriesDetailMapper seriesDetailMapper=(SeriesDetailMapper)BeanFactory.getBean(SeriesDetailMapper.class); 
	
	/**
	 * 根据id生成xml文件
	 * @param id
	 * @return 
	 */
	public static Map<String,String> createXMLFile(int id,String xmllocationdir) {
		
		Map<String,String> hashMap = new HashMap<String, String>();
		Document doc;
		//------
		Element ADI = null;
		Element Objects = null;
		Element Mappings = null ;
		
		Element ProgramObject;
		Element MovieObject;
		
		Element LicensingWindowEndProperty;
		Element LicensingWindowStartProperty;
		Element NameProperty;
		Element NameMovie;
		Element FileURLProperty;
		Element TypeProperty;
		
		Element Mapping;
		String movieId = null;
		String programId= null;
		SeriesDetail seriesDetail=new SeriesDetail();
		seriesDetail.setId(id);
		seriesDetail = seriesDetailMapper.getInfoById(seriesDetail);
		logger.info("===========>进入生成xml文件方法===========");
		try {
			// 得到DOM解析器的工厂实例
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			// 从DOM工厂中获得DOM解析器
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			// 创建文档树模型对象
			doc = dbBuilder.newDocument();
			if (doc != null) {
				ADI = doc.createElement("ADI");
				ADI.setAttribute("StaffID", "1"); 
				ADI.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
				ADI.setAttribute("BizDomain", "2");
				Objects =doc.createElement("Objects");
				Mappings =doc.createElement("Mappings");
				
			
				programId = UUID.randomUUID().toString().replace("-", "");
				while("null".equals(programId)){
					programId = UUID.randomUUID().toString().replace("-", "");
					logger.info("programId——UUID.randomUUID()为null，正重新生成");
				}
				movieId = UUID.randomUUID().toString().replace("-", "");
				//movieId=programId; 
					
					
					ProgramObject = doc.createElement("Object");
					//设置元素Object的属性值
					ProgramObject.setAttribute("Action", "REGIST"/*books.get(i).getCategory()*/);
					ProgramObject.setAttribute("Code", programId/**这里要生成uuid*/);
					ProgramObject.setAttribute("ElementType", "Program");
					ProgramObject.setAttribute("ID", programId/**这里要生成uuid,并且和CODE字段一致*/);
					
					//创建名称为LicensingWindowEndProperty的元素
					LicensingWindowEndProperty = doc.createElement("Property");
					//设置元素LicensingWindowEndProperty的属性值
					LicensingWindowEndProperty.setAttribute("Name", "LicensingWindowEnd");
					//创建文本节点并作为子节点添加到LicensingWindowEndProperty元素中
					LicensingWindowEndProperty.appendChild(doc.createTextNode("29990101000000")/*doc.createTextNode(books.get(i).getTitle())*/);
				
					
					
					//创建名称为LicensingWindowStart的元素
					LicensingWindowStartProperty = doc.createElement("Property");
					//设置元素LicensingWindowStart的属性值
					LicensingWindowStartProperty.setAttribute("Name", "LicensingWindowStart");
					//创建文本节点并作为子节点添加到LicensingWindowStart元素中
					LicensingWindowStartProperty.appendChild(doc.createTextNode("20100101000000")/*doc.createTextNode(books.get(i).getTitle())*/);
					
					
					
					//创建名称为NameProperty的元素
					NameProperty = doc.createElement("Property");
					//设置元素NameProperty的属性值
					NameProperty.setAttribute("Name", "Name");
					//创建文本节点并作为子节点添加到title元素中
					NameProperty.appendChild(doc.createTextNode(seriesDetail.getProgramName()));
	
					//将property子元素添加到ProgramObject中
					ProgramObject.appendChild(LicensingWindowStartProperty);
					ProgramObject.appendChild(LicensingWindowEndProperty);
					ProgramObject.appendChild(NameProperty);
					
					//MovieObject
					MovieObject = doc.createElement("Object");
					//设置元素Object的属性值
					MovieObject.setAttribute("Action", "REGIST"/*books.get(i).getCategory()*/);
					MovieObject.setAttribute("Code", movieId/**uuid*/);
					MovieObject.setAttribute("ElementType", "Movie");
					MovieObject.setAttribute("ID", movieId/**uuid,并且和CODE字段一致*/);
					
					//创建名称为NameProperty的元素
					NameMovie = doc.createElement("Property");
					//设置元素NameProperty的属性值
					NameMovie.setAttribute("Name", "Name");
					//创建文本节点并作为子节点添加到title元素中
					NameMovie.appendChild(doc.createTextNode(seriesDetail.getProgramName()));
					
					//创建名称为FileURLProperty的元素
					FileURLProperty = doc.createElement("Property");
					//设置元素FileURLProperty的属性值
					FileURLProperty.setAttribute("Name", "FileURL");
					//创建文本节点并作为子节点添加到FileURLProperty元素中
				//	String fileurl = "http://hls01.ott.disp.guttv.cibntv.net/2017/06/05/3b28596ff99f4b9da3ceae692db2ec6d/190468d7d44a357e35f1ac579e86ab2f.m3u8";
					FileURLProperty.appendChild(doc.createTextNode(seriesDetail.getSourceUrl())/*doc.createTextNode(books.get(i).getTitle())*/);
					
					
					//创建名称为TypeProperty的元素
					TypeProperty = doc.createElement("Property");
					//设置元素TypeProperty的属性值
					TypeProperty.setAttribute("Name", "Type");
					//创建文本节点并作为子节点添加到TypeProperty元素中
					TypeProperty.appendChild(doc.createTextNode("1")/*doc.createTextNode(books.get(i).getTitle())*/);
					
					//将property子元素添加到MovieObject中
					MovieObject.appendChild(FileURLProperty);
					MovieObject.appendChild(TypeProperty);
					MovieObject.appendChild(NameMovie);
					
					//将MovieObject 和 ProgramObject 添加到文档树Objects 中
					Objects.appendChild(ProgramObject);
					Objects.appendChild(MovieObject);
					
					//----Mapping标签
					Mapping = doc.createElement("Mapping");
					//设置元素Object的属性值
					Mapping.setAttribute("ParentType", "Program"/*books.get(i).getCategory()*/);
					Mapping.setAttribute("ParentID", programId/**uuid*/);
					Mapping.setAttribute("ParentCode", programId);
					Mapping.setAttribute("ElementType", "Movie");
					Mapping.setAttribute("ElementID", movieId/**与movie标签对应*/);
					Mapping.setAttribute("ElementCode", movieId/**与movie标签对应*/);
					Mapping.setAttribute("Action", "REGIST");
					
					Mappings.appendChild(Mapping);
				
				}
				ADI.appendChild(Objects);
				ADI.appendChild(Mappings);
				doc.appendChild(ADI);//添加到文档树中
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				
				//StringBuilder sbu =new StringBuilder("F:/creatXML/"); //windows本机测试目录
				//StringBuilder sbu =new StringBuilder("/data/pbtd/newxml/");//linux地址
				
				StringBuilder sbu = new StringBuilder(xmllocationdir);
				sbu.append(id);
				sbu.append(".xml");
				String xmlLocation = sbu+"";
				StreamResult result = new StreamResult(new File(xmlLocation));
				logger.info("=========>生成xml位置为"+xmlLocation);
				transformer.transform(source, result);
				logger.info("============>生成完成");
				hashMap.put("programId", programId);
				hashMap.put("movieId", movieId);
			}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return hashMap;
	}
	
		
	/**
	 * 根据SeriesDetail,programId,bunchCode生成xml
	 * @param seriesDetail
	 * @param programId
	 * @param bunchCode
	 * @param xmlLocation
	 * @return
	 */
	public static void createXMLFileByEntity(SeriesDetail sd,String programId,String bunchCode,String xmlLocation){
		//注：bunchCode即movieId
		
		Document doc;
		//------
		Element ADI = null;
		Element Objects = null;
		Element Mappings = null ;
		
		Element ProgramObject;
		Element MovieObject;
		
		Element LicensingWindowEndProperty;
		Element LicensingWindowStartProperty;
		Element NameProperty;
		Element NameMovie;
		Element FileURLProperty;
		Element TypeProperty;
		
		Element Mapping;
		logger.info("===========>进入生成xml文件方法===========");
		try {
			// 得到DOM解析器的工厂实例
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			// 从DOM工厂中获得DOM解析器
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			// 创建文档树模型对象
			doc = dbBuilder.newDocument();
			if (doc != null) {
				ADI = doc.createElement("ADI");
				ADI.setAttribute("StaffID", "1"); 
				ADI.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
				ADI.setAttribute("BizDomain", "2");
				Objects =doc.createElement("Objects");
				Mappings =doc.createElement("Mappings");
				
					
				ProgramObject = doc.createElement("Object");
				//设置元素Object的属性值
				ProgramObject.setAttribute("Action", "REGIST"/*books.get(i).getCategory()*/);
				ProgramObject.setAttribute("Code", programId/**这里要生成uuid*/);
				ProgramObject.setAttribute("ElementType", "Program");
				ProgramObject.setAttribute("ID", programId/**这里要生成uuid,并且和CODE字段一致*/);
				
				//创建名称为LicensingWindowEndProperty的元素
				LicensingWindowEndProperty = doc.createElement("Property");
				//设置元素LicensingWindowEndProperty的属性值
				LicensingWindowEndProperty.setAttribute("Name", "LicensingWindowEnd");
				//创建文本节点并作为子节点添加到LicensingWindowEndProperty元素中
				LicensingWindowEndProperty.appendChild(doc.createTextNode("29990101000000")/*doc.createTextNode(books.get(i).getTitle())*/);
			
				
				
				//创建名称为LicensingWindowStart的元素
				LicensingWindowStartProperty = doc.createElement("Property");
				//设置元素LicensingWindowStart的属性值
				LicensingWindowStartProperty.setAttribute("Name", "LicensingWindowStart");
				//创建文本节点并作为子节点添加到LicensingWindowStart元素中
				LicensingWindowStartProperty.appendChild(doc.createTextNode("20100101000000")/*doc.createTextNode(books.get(i).getTitle())*/);
				
				
				
				//创建名称为NameProperty的元素
				NameProperty = doc.createElement("Property");
				//设置元素NameProperty的属性值
				NameProperty.setAttribute("Name", "Name");
				//创建文本节点并作为子节点添加到title元素中
				NameProperty.appendChild(doc.createTextNode(sd.getProgramName()));

				//将property子元素添加到ProgramObject中
				ProgramObject.appendChild(LicensingWindowStartProperty);
				ProgramObject.appendChild(LicensingWindowEndProperty);
				ProgramObject.appendChild(NameProperty);
				
				//MovieObject
				MovieObject = doc.createElement("Object");
				//设置元素Object的属性值
				MovieObject.setAttribute("Action", "REGIST"/*books.get(i).getCategory()*/);
				MovieObject.setAttribute("Code", bunchCode/**uuid*/);
				MovieObject.setAttribute("ElementType", "Movie");
				MovieObject.setAttribute("ID", bunchCode/**uuid,并且和CODE字段一致*/);
				
				//创建名称为NameProperty的元素
				NameMovie = doc.createElement("Property");
				//设置元素NameProperty的属性值
				NameMovie.setAttribute("Name", "Name");
				//创建文本节点并作为子节点添加到title元素中
				NameMovie.appendChild(doc.createTextNode(sd.getProgramName()));
				
				//创建名称为FileURLProperty的元素
				FileURLProperty = doc.createElement("Property");
				//设置元素FileURLProperty的属性值
				FileURLProperty.setAttribute("Name", "FileURL");
				//创建文本节点并作为子节点添加到FileURLProperty元素中
			//	String fileurl = "http://hls01.ott.disp.guttv.cibntv.net/2017/06/05/3b28596ff99f4b9da3ceae692db2ec6d/190468d7d44a357e35f1ac579e86ab2f.m3u8";
				FileURLProperty.appendChild(doc.createTextNode(sd.getSourceUrl())/*doc.createTextNode(books.get(i).getTitle())*/);
				
				
				//创建名称为TypeProperty的元素
				TypeProperty = doc.createElement("Property");
				//设置元素TypeProperty的属性值
				TypeProperty.setAttribute("Name", "Type");
				//创建文本节点并作为子节点添加到TypeProperty元素中
				TypeProperty.appendChild(doc.createTextNode("1")/*doc.createTextNode(books.get(i).getTitle())*/);
				
				//将property子元素添加到MovieObject中
				MovieObject.appendChild(FileURLProperty);
				MovieObject.appendChild(TypeProperty);
				MovieObject.appendChild(NameMovie);
				
				//将MovieObject 和 ProgramObject 添加到文档树Objects 中
				Objects.appendChild(ProgramObject);
				Objects.appendChild(MovieObject);
				
				//----Mapping标签
				Mapping = doc.createElement("Mapping");
				//设置元素Object的属性值
				Mapping.setAttribute("ParentType", "Program"/*books.get(i).getCategory()*/);
				Mapping.setAttribute("ParentID", programId/**uuid*/);
				Mapping.setAttribute("ParentCode", programId);
				Mapping.setAttribute("ElementType", "Movie");
				Mapping.setAttribute("ElementID", bunchCode/**与movie标签对应*/);
				Mapping.setAttribute("ElementCode", bunchCode/**与movie标签对应*/);
				Mapping.setAttribute("Action", "REGIST");
				
				Mappings.appendChild(Mapping);
			
			}
			ADI.appendChild(Objects);
			ADI.appendChild(Mappings);
			doc.appendChild(ADI);//添加到文档树中
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			//StringBuilder sbu =new StringBuilder("F:/creatXML/"); //windows本机测试目录
			//StringBuilder sbu =new StringBuilder("/data/pbtd/newxml/");//linux地址
			
			
			String childXMLLocation = xmlLocation+sd.getId()+".xml";  //根据目录生成工单位置
			StreamResult result = new StreamResult(new File(childXMLLocation));
			logger.info("=========>生成xml位置为"+childXMLLocation);
			transformer.transform(source, result);
			logger.info("============>生成完成");
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
