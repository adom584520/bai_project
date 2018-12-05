package com.yh.search.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.GroupParams;
import org.junit.Test;

import com.yh.search.utils.PropertyUtil;
import com.yh.search.utils.pingYinUtil;

public class TestSearch {
	// 添加数据到索引库
	@Test
	public void addDocument() throws Exception {
		// 第一步：把solrJ的jar包添加到工程中。
		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
		// SolrServer solrServer = new
		// HttpSolrServer("http://192.168.0.197:8080/solr");
		String url = "http://192.168.0.215:8080/solr";
		HttpSolrServer solrServer = new HttpSolrServer(url);
		// socket 读取超时
		solrServer.setSoTimeout(1000);
		solrServer.setConnectionTimeout(100);
		solrServer.setDefaultMaxConnectionsPerHost(100);
		solrServer.setMaxTotalConnections(100);
		// 默认值为false
		solrServer.setFollowRedirects(false);
		// allowCompression默认为 false.
		// 服务器端必须支持gzip或缩小产生任何影响
		solrServer.setAllowCompression(true);
		//默认值为0。> 1不推荐。
		solrServer.setMaxRetries(1); 

		// 第三步：创建一个文档对象SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
		document.addField("id", "12344566007");
		document.addField("play_name", "秦时明天下月之君临天下123");
		document.addField("play_names", "秦时明天下月之君临天下");
		document.addField("play_actor", "荆天明|高月|项少羽|石兰");
		document.addField("play_year", "1999");
		document.addField("play_zt", "1");
		document.addField("play_vip", "1");
		document.addField("play_score", "8.0");
		// 第五步：把文档添加到索引库中。
		solrServer.add(document);
		// 第六步：提交。
		solrServer.commit();
	}

	// 从索引库查询数据
	@SuppressWarnings("all")
	@Test
	public void queryDocument() throws Exception {
		// 第一步：创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.215:8080/solr");
		// 第二步：创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 第三步：向SolrQuery中添加查询条件、过滤条件。。。
		Integer page = 1;
		Integer rows = 30;
		query.setQuery("长征");
		query.setStart(page);
		query.setRows(rows);
		// 4、需要指定默认搜索域。
		query.set("df", "play_keywords");
		// 5、设置高亮
		query.setHighlight(true);
		query.addHighlightField("play_name");
		query.setHighlightSimplePre("<span style=\"color:blue\">");
		query.setHighlightSimplePost("</span>");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
		// 第六步：遍历结果并打印。
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("play_epis"));
			System.out.println(solrDocument.get("play_sup_id"));
			System.out.println(solrDocument.get("play_categoryName"));
			System.out.println(solrDocument.get("play_actor"));
			System.out.println(solrDocument.get("play_writer"));
			System.out.println(solrDocument.get("play_image_v"));
			System.out.println(solrDocument.get("play_image_h"));
			System.out.println(solrDocument.get("play_updata_no"));
			System.out.println(solrDocument.get("play_imgurl"));
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("play_name");
			String play_name = null;
			// 有高亮显示的内容时。
			if (list != null && list.size() > 0) {
				play_name = list.get(0);
				System.out.println(play_name);
			} else {
				System.out.println((String) solrDocument.get("play_name"));
			}

			System.out.println("---------------------------------------------------");
		}
	}

	// 从索引库查询数据
	@SuppressWarnings("all")
	@Test
	public void queryStatDocument() throws Exception {
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.197:8080/solr");
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("测");
		// 过滤条件
		/*
		 * String s = "动漫"; query.setFilterQueries("play_name" + ":" + s);
		 */
		// 结果中域的列表
		// query.setFields("play_categoryName");
		// 设置默认搜索域
		query.set("df", "play_keywords");
		// 执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());

	}

	// 根据查询条件删除文档
	@Test
	public void deleteDocumentByQuery() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.215:8080/solr/collection1");
		// 根据查询条件删除文档
		solrServer.deleteByQuery("id:12344566007");
		// 提交修改
		solrServer.commit();
	}

	@Test
	public void addDocument2() throws Exception {
		// 第一步：把solrJ的jar包添加到工程中。
		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.215:8080/solr/collection2");
		// 第三步：创建一个文档对象SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
		document.addField("id", "秦时明天下月之君临天下");
		document.addField("play_name", "秦时明天下月之君临天下123");
		document.addField("play_pingyin", "1999");
		document.addField("play_pingyinsx", "1999");
		document.addField("play_zt", "1");
		// 第五步：把文档添加到索引库中。
		solrServer.add(document);
		// 第六步：提交。
		solrServer.commit();
	}

	/**
	 * <b>function:</b> query 基本用法测试
	 *
	 */

	@Test
	public void GroupFieldQuery() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.163:8080/solr/collection1");
		// SolrQuery query = new SolrQuery("play_name:高清");
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("2016");

		// 设置默认搜索域
		query.set("df", "play_keywords");
		// 设置通过facet查询为true，表示查询时使用facet机制
		query.setParam(GroupParams.GROUP, true);
		query.setParam(GroupParams.GROUP_FIELD, "play_categoryName");// 分组
		// 设置每个quality对应的
		// query.setParam(GroupParams.GROUP_SORT, "play_categoryName asc");
		// 设置返回doc文档数据，因只需要数量，故设置为0
		query.setParam(GroupParams.GROUP_LIMIT, "0");
		// 显示结果数
		query.setRows(10);
		QueryResponse response = solrServer.query(query);
		if (response != null) {
			GroupResponse groupResponse = response.getGroupResponse();
			if (groupResponse != null) {
				List<GroupCommand> groupList = groupResponse.getValues();
				for (GroupCommand groupCommand : groupList) {
					List<Group> groups = groupCommand.getValues();
					for (Group group : groups) {
						// SolrDocumentList solrDocumentList =
						// group.getResult();
						/*
						 * for (SolrDocument solrDocument : solrDocumentList) {
						 * System.out.println(solrDocument.get("id").toString()
						 * + "..." + solrDocument.get("play_name") + "..." +
						 * solrDocument.get("play_epis") + "..." +
						 * solrDocument.get("play_actor") + "..." +
						 * solrDocument.get("play_writer") + "..." +
						 * solrDocument.get("play_supid") + "..." +
						 * solrDocument.get("play_categoryName"));
						 * System.out.println(
						 * "-----------------------------------------------------------------"
						 * ); }
						 */
						System.out.println(
								"group查询..." + group.getGroupValue() + "...数量为：" + group.getResult().getNumFound());
					}
				}
			}
		}

	}

	// 更新
	@Test
	public void REGISTDocument() throws Exception {
		Connection conn = null;
		try {
			// 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
			// 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			String url = "jdbc:mysql://192.168.0.46:3306/central?useUnicode=true&characterEncoding=utf-8";
			String user = "root";
			String password = "admin";
			System.out.println("成功加载MySQL驱动程序");
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url, user, password);
			// Statement里面带有很多方法，比如executeREGIST可以实现插入，更新和删除等
			Statement stmt = conn.createStatement();
			String sql = "SELECT seriesName from vod_albuminfovideo";
			ResultSet result = stmt.executeQuery(sql);// executeREGIST语句会返回一个受影响的行数，如果返回-1就没有成功
			// int i = 1;
			while (result.next()) {
				// i++;

				/*
				 * if (i > 1399) { break; }
				 */

				String id = result.getString("albumId");// 获取数据库person表中name字段的值
				String mid = id + "00";
				String name = result.getString("seriesName");// 获取数据库person表中name字段的值
				// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
				SolrServer solrServer = new HttpSolrServer("http://192.168.0.197:8080/solr/collection2");
				// 第三步：创建一个文档对象SolrInputDocument对象。
				SolrInputDocument document = new SolrInputDocument();
				// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
				document.addField("id", mid);
				// 清除掉所有特殊字符
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String pingYin = pingYinUtil.getPingYin(name).toLowerCase();
				String headChar = pingYinUtil.getPinYinHeadChar(name).toLowerCase();
				document.addField("play_name", name);
				document.addField("play_pingyin", pingYin);
				document.addField("play_pingyinsx", headChar);

				document.setDocumentBoost(10.0f);// 10.0f就是doc的boost
				// 第五步：把文档添加到索引库中。
				solrServer.add(document);
				// 第六步：提交。
				solrServer.commit();

			}
		} catch (Exception e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	@Test
	public void addDocum() throws Exception {
		// 第一步：把solrJ的jar包添加到工程中。
		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.197:8080/solr/collection2");
		// 第三步：创建一个文档对象SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
		document.addField("id", "test001");
		document.addField("play_name", "重启");
		document.addField("play_pingyin", "chongqing");
		document.addField("play_pingyinsx", "cq");

		// 第五步：把文档添加到索引库中。
		solrServer.add(document);
		// 第六步：提交。
		solrServer.commit();
	}

	@Test
	public void suggest() throws Exception {
		SolrServer server = new HttpSolrServer("http://192.168.0.197:8080/solr/collection2");
		SolrQuery query = new SolrQuery();
		query.set("qt", "/suggest");// 请求到suggest中
		query.set("q", "王一");// 查询的词

		// query.set("spellcheck.count", "");// 返回数量
		// QueryResponse rsp = server.query(query);

		try {
			SpellCheckResponse spellCheckResponse = server.query(query).getSpellCheckResponse();
			// solr 数字拼写提示没有返回
			if (spellCheckResponse == null) {
				System.out.println("1");
			}

			List<Suggestion> suggestions = spellCheckResponse.getSuggestions();

			if (suggestions.size() == 0) {
				System.out.println("2");
			} else {
				Suggestion suggestion = suggestions.get(suggestions.size() - 1);
				List<String> list = suggestion.getAlternatives();
				for (String spellWord : list) {
					System.out.println(spellWord);
				}
			}
		} catch (SolrServerException e) {
			System.out.println("获取推荐词时遇到错误:" + e);

		}
		/*
		 * // …上面取结果的代码 SpellCheckResponse re = rsp.getSpellCheckResponse(); if
		 * (re != null) { for (Suggestion s : re.getSuggestions()) {
		 * List<String> list = s.getAlternatives(); for (String spellWord :
		 * list) { System.out.println(spellWord); } }
		 * 
		 * }
		 */
	}

	@Test
	public void sush() throws Exception {
		String s = "あいみょん - 君はロックを聴かない";
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122)
					|| (chars[i] >= 65 && chars[i] <= 90) || (chars[i] >= 48 && chars[i] <= 57) || chars[i] == 32
					|| chars[i] == 40 || chars[i] == 41 || chars[i] == 45 || chars[i] == 58 || chars[i] == 183) {
				System.out.print(chars[i]);
			}
		}
	}

	@Test
	public void sushasd() throws Exception {
		// 只允许字母和数字
		String str = "杨幂获\"奇葩\"奖项";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].\"\"<>/?~！@#￥%……&*（）——+|{} 【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		System.out.println(m.replaceAll("").trim());
	}

	@Test
	public void sushasdsdfg() throws Exception {
		String str = "《猩球崛起3:终极之战》“凯撒变脸》”特效视频";
		String str1 = str.substring(1, str.length());
		System.out.println(str1.replaceFirst("》", " "));

		String regEx = "[》】]";
		Pattern p = Pattern.compile(regEx);
		String instring = "-Jacob视频Chase";
		String str2 = instring.substring(1, instring.length());
		Matcher m = p.matcher(str2);
		String tmp = m.replaceFirst(" ");
		String pingYin = pingYinUtil.getPingYin(tmp).toLowerCase();
		String headChar = pingYinUtil.getPinYinHeadChar(tmp).toLowerCase();
		String lower = "aAbaasd AcD".toLowerCase();
		System.out.println("小写:" + lower);
		System.out.println("全拼:" + pingYin);
		System.out.println("首字母:" + headChar);
		System.out.println("过滤:" + tmp.toLowerCase());
	}

	@Test
	public void sushas() throws Exception {
		// 第一步：把solrJ的jar包添加到工程中。
		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
		SolrServer solrServer = new HttpSolrServer("http://192.168.0.197:8080/solr/collection2");
		// 第三步：创建一个文档对象SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
		String name = "《猩球崛起3:终极之战》“凯撒变脸》”特效视频";
		document.addField("id", "test03");
		String str1 = name.substring(0, 1);
		if ("【".equals(str1) || "《".equals(str1)) {
			String regEx = "[》】]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(name.substring(1, name.length()));
			name = m.replaceFirst(" ");
		}
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{} 《》【】‘；：”“’。，、？·]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);
		String py = m.replaceAll("").trim();
		String pingYin = pingYinUtil.getPingYin(py).toLowerCase();
		String headChar = pingYinUtil.getPinYinHeadChar(py).toLowerCase();
		document.addField("play_name", name);
		document.addField("play_pingyin", pingYin);
		document.addField("play_pingyinsx", headChar);

		// 第五步：把文档添加到索引库中。
		solrServer.add(document);
		// 第六步：提交。
		solrServer.commit();
	}

	@Test
	public void sushaswer() throws Exception {
		Properties prop = new Properties();
		File f = new File(this.getClass().getResource("/").getPath());
		String file = f.getPath().replaceAll("classes", "");
		String path = "app-config/solr.properties";
		f = new File(file + System.getProperty("file.separator") + path);
		prop.load(new FileInputStream(f));
		System.out.println(prop.getProperty("solrURL"));
	}

	@Test
	public void sushaswedsr() throws Exception {
		PropertyUtil pro = new PropertyUtil("app-config/solr.properties");
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer(pro.getProperty("solrURL"));
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("王者");
		// 结果中域的列表
		query.setFields("play_name");
		// 设置默认搜索域
		query.set("df", "suggest");
		// 执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		List<String> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			String str = solrDocument.get("play_name").toString();
			// 添加到专辑列表
			System.out.println(str);
			itemList.add(str);
		}

	}

	@Test
	public void REGISTDocuments() throws Exception {
		Connection conn = null;
		try {
			// 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
			// 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			String url = "jdbc:mysql://192.168.0.46:3306/pbtdvod-hebei?useUnicode=true&characterEncoding=utf-8";
			String user = "root";
			String password = "admin";
			System.out.println("成功加载MySQL驱动程序");
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url, user, password);
			// Statement里面带有很多方法，比如executeREGIST可以实现插入，更新和删除等
			Statement stmt = conn.createStatement();
			Statement stmt1 = conn.createStatement();
			String sql = "SELECT seriesCode,seriesName from vod_tv_albuminfo";
			ResultSet result = stmt.executeQuery(sql);// executeREGIST语句会返回一个受影响的行数，如果返回-1就没有成功
			while (result.next()) {
				String id = result.getString("seriesCode");// 获取数据库person表中name字段的值
				String name = result.getString("seriesName");// 获取数据库表中seriesName字段的值
				// 清除掉所有特殊字符
				String regEx = "[`~!@#$%^&*()+=|{}': ;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{}《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String pingYin = pingYinUtil.getPingYin(name).toLowerCase();//全拼
				String headChar = pingYinUtil.getPinYinHeadChar(name).toLowerCase();//简拼

				sql = "UPDATE vod_tv_albuminfo SET pinyin = '" + pingYin + "' ," + "pinyinsuoxie= '" + headChar
						+ "' WHERE seriesCode ='" + id + "'";

				stmt1.executeUpdate(sql);
			}
		} catch (

		Exception e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	@Test
	public void REGISTDocus() throws Exception {
		String name = "顾贇贇";
		String pingYin = pingYinUtil.getPingYin(name).toLowerCase();
		String headChar = pingYinUtil.getPinYinHeadChar(name).toLowerCase();
		System.out.println("全拼: " + pingYin);
		System.out.println("简拼: " + headChar);

	}
	
	@Test
	public void isChinese() {
		String str = "都是该死的1aszgvd";
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		System.out.println(flg+".......");
	}
	
	@Test
	public void REGISTDocus111() throws Exception {
		String name = "1,2,3,4,";
		String[] split = name.split(",");
		System.out.println("转换后:  " + StringUtils.join(split, ","));


	}
	@Test
	public void REGISTDocus11333() throws Exception {
		String name = null;
		String[] split = StringUtils.splitPreserveAllTokens(name);
		System.out.println(split==null);
		
	}
	
	@Test
	public void REGISTDocus113366() throws Exception {
		List<String> list = new ArrayList<>();
		//list.add("1");
		Iterator<String> iteratorTwo = list.iterator();
		while (iteratorTwo.hasNext()) {
			System.out.println(iteratorTwo.next());
			}
		/*list.add("1");
		list.add("2");
		list.add("3");
		list.add("5");
		System.out.println(list.subList(0, 4));*/
	}
}
