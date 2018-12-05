/**
 * 
 */
package com.zckj.files.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.zckj.tool.utils.ConstUtils;
import com.zckj.tool.utils.FileUtils;

/**
 * 解压缩
 * 
 * @author liuxiaomeng
 * @datetime 2015-7-31_下午4:08:08
 */
public class FlodUnCompress {
	private static FlodUnCompress flodUnCompress = new FlodUnCompress();

	private FlodUnCompress() {
	}

	public static FlodUnCompress getInstance() {
		if (FlodUnCompress.flodUnCompress == null) {
			FlodUnCompress.flodUnCompress = new FlodUnCompress();
		}
		return FlodUnCompress.flodUnCompress;
	}

	/**
	 * 解压到指定目录
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:08:28
	 * @param zipPath
	 * @param descDir
	 * @throws IOException
	 */
	public boolean unZipFiles(final String zipPath, final String descDir) throws IOException {
		return this.unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:08:33
	 * @param zipFile
	 * @param descDir
	 * @throws IOException
	 */
	public boolean unZipFiles(final File zipFile, final String descDir) throws IOException {
		final File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		final ZipFile zip = new ZipFile(zipFile);
		String zipName = zipFile.getName().replace(".zip", "/");
		final Enumeration<?> entries = zip.entries();
		int index = 0;
		while (entries.hasMoreElements()) {
			final ZipEntry entry = (ZipEntry) entries.nextElement();
			final String zipEntryName = entry.getName();
			if(index == 0 && !zipName.equals(zipEntryName)) {
				zip.close();
				return false;
			}
			index ++;
			final InputStream in = zip.getInputStream(entry);
			final String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file;// = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (outPath.contains("/")) {
				file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			} else {
				file = new File(outPath);
			}
			if (!file.exists()) {
				file.mkdirs();
			}
			final File outFile = new File(outPath);
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (outFile.isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			//System.out.println(outPath);
			final OutputStream out = new FileOutputStream(outPath);
			final byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
			if (zipEntryName.contains(ConstUtils.deleteFileName)) {
				if (null != ConstUtils.deleteFileList) {
					ConstUtils.deleteFileList.clear();
				} else {
					ConstUtils.deleteFileList = new ArrayList<String>();
				}
				final List<String> tmpList = FileUtils.readTxtFile(outFile);
				for (final String tmp : tmpList) {
					final String[] sps = tmp.split("/");
					ConstUtils.deleteFileList.add(sps[sps.length - 1]);
				}
				outFile.delete();
				//System.out.println("更新清除列表： " + ConstUtils.deleteFileList);
			}
		}
		zip.close();
		//System.out.println(zipFile.getAbsolutePath() + "  ******************解压完毕********************");
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		final String path = "d:/zipfile/";
		final String zipFile = "d:/1.4.zip";
		try {
			FlodUnCompress.getInstance().unZipFiles(zipFile, path);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
