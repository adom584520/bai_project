/**
 * 
 */
package com.zckj.tool.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 文件处理类
 * 
 * @author liuxiaomeng
 * @datetime 2015-7-31_下午4:09:38
 */
public class FileUtils {
	/**
	 * 复制文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:09:49
	 * @param sourcefile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(final File sourcefile, final File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		final FileInputStream input = new FileInputStream(sourcefile);
		final BufferedInputStream inbuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		final FileOutputStream out = new FileOutputStream(targetFile);
		final BufferedOutputStream outbuff = new BufferedOutputStream(out);
		// 缓冲数组
		final byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outbuff.flush();
		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
	}

	/**
	 * 复制文件夹
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:09:56
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(final String sourceDir, final String targetDir) throws IOException {
		// 新建目标目录
		final File td = new File(targetDir);
		if (!td.exists()) {
			td.mkdirs();
		}
		// 获取源文件夹当下的文件或目录
		final File[] file = (new File(sourceDir)).listFiles();
		if (null == file) {
			return;
		}
		for (final File sourceFile : file) {
			if (sourceFile.isFile()) {
				// 目标文件
				final File targetFile = new File(new File(targetDir).getAbsolutePath() + ConstUtils.f_separator + sourceFile.getName());
				FileUtils.copyFile(sourceFile, targetFile);
			}
			if (sourceFile.isDirectory()) {
				// 准备复制的源文件夹
				final String dir1 = sourceDir + "/" + sourceFile.getName();
				// 准备复制的目标文件夹
				final String dir2 = targetDir + "/" + sourceFile.getName();
				FileUtils.copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:10:06
	 * @param myFilePath 文件夹路径
	 */
	public static void delFolder(final File myFilePath) {
		try {
			FileUtils.delAllFile(myFilePath); // 删除完里面所有内容
			myFilePath.delete(); // 删除空文件夹
		} catch (final Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:10:21
	 * @param folderPath 文件夹路径
	 */
	public static void delFolder(final String folderPath) {
		try {
			FileUtils.delAllFile(folderPath); // 删除完里面所有内容
			final String filePath = folderPath.toString();
			final java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (final Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:10:31
	 * @param path String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(final String path) {
		final File file = new File(path);
		FileUtils.delAllFile(file);
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:10:43
	 * @param file File文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(final File file) {
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		final String[] tempList = file.list();
		File temp = null;
		final String path = file.getAbsolutePath();
		for (final String element : tempList) {
			if (path.endsWith(File.separator) || path.endsWith(ConstUtils.f_separator)) {
				temp = new File(path + element);
			} else {
				temp = new File(path + ConstUtils.f_separator + element);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				FileUtils.delAllFile(path + "/" + element);// 先删除文件夹里面的文件
				FileUtils.delFolder(path + "/" + element);// 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:11:13
	 * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
	 */
	public static void delFile(final String filePathAndName) {
		try {
			final String filePath = filePathAndName.trim();
			final File myDelFile = new File(filePath);
			myDelFile.delete();
		} catch (final Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 遍历文件夹删除文件夹里面的指定文件列表
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:11:25
	 * @param path String 文件夹路径 如 c:/fqf
	 * @param deleteList 指定文件列表
	 */
	public static void findFilesToDelete(final String path, final List<String> deleteList) {
		if ((null == deleteList) || deleteList.isEmpty()) {
			return;
		}
		final File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		final String[] tempList = file.list();
		for (final String temp : tempList) {
			final File tempfile = new File(path + "/" + temp);
			if (deleteList.contains(temp)) {
				if (tempfile.isDirectory()) {
					FileUtils.findFilesToDelete(path + "/" + temp, deleteList);// 先删除文件夹里面的文件
				}
				tempfile.delete();
			}
			if (tempfile.isDirectory()) {
				FileUtils.findFilesToDelete(path + "/" + temp, deleteList);// 先删除文件夹里面的文件
			}
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:11:41
	 * @param oldPath String 如：c:/fqf.txt
	 * @param newPath String 如：d:/fqf.txt
	 */
	public static void moveFile(final String oldPath, final String newPath) {
		try {
			final File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				FileUtils.copyFile(oldfile, new File(newPath));
				FileUtils.delFile(oldPath);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:11:50
	 * @param oldPath String 如：c:/fqf.txt
	 * @param newPath String 如：d:/fqf.txt
	 */
	public static void moveFolder(final String oldPath, final String newPath) {
		try {
			FileUtils.copyDirectiory(oldPath, newPath);
			FileUtils.delFolder(oldPath);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:12:05
	 * @param file
	 * @return 文件内容列表
	 */
	public static List<String> readTxtFile(final File file) {
		try {
			List<String> contentList = null;
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				contentList = new ArrayList<String>();
				final InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
				final BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					contentList.add(lineTxt.trim());
				}
				read.close();
			} else {
				System.out.println("not find file");
			}
			return contentList;
		} catch (final Exception e) {
			System.out.println("read file error");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:12:05
	 * @param file
	 * @return 文件内容列表
	 */
	public static List<String> readTxtFile(final String filePath) {
		try {
			final File file = new File(filePath);
			return FileUtils.readTxtFile(file);
		} catch (final Exception e) {
			System.out.println("read file error");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:12:27
	 * @param filePath 文件路径
	 * @param content 写入内容
	 */
	public static void writerTxt(final String filePath, final String content) {
		BufferedWriter fw = null;
		try {
			final File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			//final FileOutputStream out = new FileOutputStream(targetFile);
			//final BufferedOutputStream outbuff = new BufferedOutputStream(out);
			file.createNewFile();
			fw = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file, true)), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
			//fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
			fw.append(content);
			fw.flush(); // 全部写入缓存中的内容
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取属性文件
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:12:45
	 * @return
	 */
	public static Properties getSetConfigPors() {
		final Properties prop = new Properties();
		// 读属性文件
		try {
			final InputStream in = new FileUtils().getClass().getResourceAsStream("/set.properties");
			prop.load(in);
			// Set keyValue = prop.keySet();
			// for (Iterator it = keyValue.iterator(); it.hasNext();) {
			// String key = (String) it.next();
			// System.out.println(key + "-- " );
			// }
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * 文件MD5
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午12:49:48
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getMd5ByFile(final String fileName) throws FileNotFoundException {
		final File file = new File(fileName);
		return FileUtils.getMd5ByFile(file);
	}

	/**
	 * 文件MD5
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-6_下午12:49:48
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getMd5ByFile(final File file) throws FileNotFoundException {
		String value = null;
		final FileInputStream in = new FileInputStream(file);
		//BufferedInputStream in = new BufferedInputStream(fileIn);
		try {
			final MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			final BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(final String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//FileUtils.getSetConfigPors();
		final long t = System.currentTimeMillis();
		//		book.zip("D:/test/1.3/update", "D:/test/1.3/doc.zip");// 你要压缩的文件夹
		//System.out.println(getMd5ByFile(new File("D:/soft/doc.zip")));
		for (int i = 0; i < 10000; i++) {
			FileUtils.writerTxt("D:/test/1.txt", "dfgsdfgdfsgdfagnjdfoghndfolgjdoigjdsfgjdfp对方国家都是国家的实力开发工具对方第三方祭扫法搜面粉加萨是的法规及时判断房价水平是大家发撒旦法就是怕大家都发给对方是个好都是第三方合法施工和法国");
		}
		System.out.println(" --------------------------------------- " + (System.currentTimeMillis() - t));
	}
}
