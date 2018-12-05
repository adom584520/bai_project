/**
 * 
 */
package com.zckj.files.compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件夹压缩处理
 * 
 * @author liuxiaomeng
 * @datetime 2015-7-31_下午4:05:30
 */
public class FlodCompress {
	private static FlodCompress flodCompress = new FlodCompress();

	public static FlodCompress getInstance() {
		if (FlodCompress.flodCompress == null) {
			FlodCompress.flodCompress = new FlodCompress();
		}
		return FlodCompress.flodCompress;
	}

	public FlodCompress() {
	}

	/**
	 * inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:06:08
	 * @param inputFileName
	 * @param zipFileName
	 * @throws Exception
	 */
	public void zip(final String inputFileName, final String zipFileName) throws Exception {
		// String zipFileName = "c:\\test.zip"; // 打包后文件名字
		//System.out.println("zip name ： " + zipFileName);
		this.zip(zipFileName, new File(inputFileName));
	}

	/**
	 * inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:06:18
	 * @param zipFileName
	 * @param inputFile
	 * @throws Exception
	 */
	private void zip(final String zipFileName, final File inputFile) throws Exception {
		//final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
		this.zip(out, inputFile, "");
		//System.out.println("--------zip done--------------");
		out.close();
	}
	
	
	/**
	 * 解压 zip文件
	 * 
	 * @param zipFilePath
	 * @param targetPath 目标目录
	 */
	public void unzip(String zipFilePath,String targetPath){
		OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFilePath);
            String directoryPath = "";
            if (null == targetPath || "".equals(targetPath)) {
                directoryPath = zipFilePath.substring(0, zipFilePath
                        .lastIndexOf("."));
            } else {
                directoryPath = targetPath;
            }
            Enumeration<? extends ZipEntry> entryEnum = zipFile.entries();
            if (null != entryEnum) {
                ZipEntry zipEntry = null;
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipEntry) entryEnum.nextElement();
//                    if (zipEntry.isDirectory()) {
//                        directoryPath = directoryPath + File.separator
//                                + zipEntry.getName();
//                        System.out.println(directoryPath);
//                        continue;
//                    }
                    if (zipEntry.getSize() > 0) {
                        // 文件
                        File targetFile = buildFile(directoryPath
                                + File.separator + zipEntry.getName(), false);
                        os = new BufferedOutputStream(new FileOutputStream(
                                targetFile));
                        is = zipFile.getInputStream(zipEntry);
                        byte[] buffer = new byte[4096];
                        int readLen = 0;
                        while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
                            os.write(buffer, 0, readLen);
                        }

                        os.flush();
                        os.close();
                    } else {
                        // 空目录
                        buildFile(directoryPath + File.separator
                                + zipEntry.getName(), true);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(null != zipFile){
                zipFile = null;
            }
            if (null != is) {
                try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (null != os) {
                try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	
	/**
	 * 生产文件 如果文件所在路径不存在则生成路径
	 * 
	 * @param fileName
	 * @param isDirectory
	 * @return
	 */
	private File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		}else{
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		
		return target;
	}

	/**
	 * 压缩处理
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-7-31_下午4:06:30
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	private void zip(final ZipOutputStream out, final File f, String base) throws Exception {
		if (f.isDirectory()) {
			final File[] fl = f.listFiles();
			if (base.length() != 0) {
				out.putNextEntry(new ZipEntry(base + "/"));
			}
			base = base.length() == 0 ? "" : base + "/";
			for (final File element : fl) {
				this.zip(out, element, base + element.getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			//	final FileInputStream in = new FileInputStream(f);
			final FileInputStream fileIn = new FileInputStream(f);
			final BufferedInputStream in = new BufferedInputStream(fileIn);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	public static void main(final String[] temp) {
		//		final FlodCompress book = new FlodCompress();
		try {
			//			long t = System.currentTimeMillis();
			//			book.zip("D:/test/1.3/update", "D:/test/1.3/doc.zip");// 你要压缩的文件夹
			//			System.out.println(" --------------------------------------- " + (System.currentTimeMillis() - t));
			System.out.println(new File("D:/soft/doc.zip").getAbsoluteFile());
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}
}
