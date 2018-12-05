
package com.pbtd.playclick.base.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream; 

/**
 * 使用ant下的包
 * zip的解压缩工具类
 */
public class ZipUtils
{
    private static final int BUFFER = 40 * 1024 * 1024;

    private static final Log logger = LogFactory.getLog(ZipUtils.class);

    /**
     * 用zip格式压缩文件
     * 将存放在sourceFilePath 目录下的源文件,打包成fileName名称的ZIP文件,并存放到zipFilePath。
     *
     * @param sourceFilePath 待压缩的文件路径  可以是文件或文件夹 如："c:\\test" 或 "c:\\test.doc"
     * @param zipFilePath    压缩后存放路径  如："c:\\test"
     * @param zipFileName    压缩后文件的名称  如："test" 无需后缀名
     * @return boolean false 压缩失败，true 压缩成功
     */
    public static boolean folderToZip(String sourceFilePath , String zipFilePath , String zipFileName)
    {

        File sourceFile = new File(sourceFilePath);
        ZipOutputStream zos = null;
        if (!sourceFile.exists())
        {
            logger.error(">>>>>> 待压缩的文件目录：" + sourceFilePath + " 不存在. <<<<<<");
            return false;
        }
        else
        {
            try
            {
                File zipFile = new File(zipFilePath + "/" + zipFileName + ".zip");
                if (zipFile.exists())
                {
                    zipFile.delete();
                }
                zos = new ZipOutputStream(new FileOutputStream(zipFile));
                //设置压缩编码
                zos.setEncoding("UTF-8");
                // 递归压缩方法  
                zip(zos, sourceFile, "");
            }
            catch (Exception e)
            {
                logger.error("文件压缩失败，错误信息为： " + ExceptionPrint.getInstance().getStackTrace(e));
                return false;
            }
            finally
            {
                // 关闭流
                try
                {
                    if (null != zos)
                        zos.close();
                }
                catch (IOException e)
                {
                    logger.error("文件压缩失败，错误信息为： " + ExceptionPrint.getInstance().getStackTrace(e));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 压缩文件
     *
     * @param out  压缩文件输出流
     * @param f    待压缩的文件或文件夹
     * @param base
     * @throws Exception
     */
    private static void zip(ZipOutputStream out , File f , String base) throws Exception
    {
        if (f.isDirectory())
        {
            // 如果是文件夹，则获取下面的所有文件
            File[] fl = f.listFiles();
            // 此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            if (fl != null && fl.length > 0)
            {
                for (File aFl : fl)
                {
                    zip(out, aFl, base + aFl.getName());
                }
            }

        }
        else
        {
            // 如果是文件，则压缩
            out.putNextEntry(new ZipEntry(base)); // 生成下一个压缩节点
            FileInputStream in = new FileInputStream(f); // 读取文件内容
            int len;
            byte[] buf = new byte[BUFFER];
            while ((len = in.read(buf, 0, BUFFER)) != -1)
            {
                out.write(buf, 0, len); // 写入到压缩包
            }
            in.close();
        }
    }
    /**
	 * 把N多文件或文件夹压缩成zip。
	 * 
	 * @param files
	 *            需要压缩的文件或文件夹。
	 * @param zipFilePath
	 *            压缩后的zip文件
	 * @throws IOException
	 *             压缩时IO异常。
	 * @author 周三龙
	 */
	public static void compress(File[] files, File zipFile) throws IOException {
		if (files==null||files.length==0) {
			return;
		}
		ZipArchiveOutputStream out = new ZipArchiveOutputStream(zipFile);
		out.setUseZip64(Zip64Mode.AsNeeded);
		// 将每个文件用ZipArchiveEntry封装
		for (File file : files) {
			if (file == null) {
				continue;
			}
			compressOneFile(file, out, "");
		}
		if (out != null) {
			out.close();
		}
	}
	/**
	 * 把N多文件或文件夹压缩成zip。
	 * @param files
	 * @param zipFile
	 * @throws IOException
	 */
	public static void compress(List<File> files, File zipFile) throws IOException {
		if (files==null||files.size()==0) {
			return;
		}
		ZipArchiveOutputStream out = new ZipArchiveOutputStream(zipFile);
		out.setUseZip64(Zip64Mode.AsNeeded);
		// 将每个文件用ZipArchiveEntry封装
		for (File file : files) {
			if (file == null) {
				continue;
			}
			compressOneFile(file, out, "");
		}
		if (out != null) {
			out.close();
		}
	}
	
	/**
	 * 功能：压缩文件或文件夹。
	 * 
	 * @author 周三龙
	 * @param srcFile
	 *            源文件。
	 * @param destFile
	 *            压缩后的文件
	 * @throws IOException
	 *             压缩时出现了异常。
	 */
	public static void compress(File srcFile, File destFile) throws IOException {
		ZipArchiveOutputStream out = null;
		try {
			out = new ZipArchiveOutputStream(new BufferedOutputStream(
					new FileOutputStream(destFile), 1024));
			compressOneFile(srcFile, out, "");
		} finally {
			out.close();
		}
	}

	/**
	 * 功能：压缩单个文件,非文件夹。私有，不对外开放。
	 * 
	 * @author 周三龙
	 * @param srcFile
	 *            源文件，不能是文件夹。
	 * @param out
	 *            压缩文件的输出流。
	 * @param destFile
	 *            压缩后的文件
	 * @param dir
	 *            在压缩包中的位置,根目录传入/。
	 * @throws IOException
	 *             压缩时出现了异常。
	 */
	private static void compressOneFile(File srcFile,
			ZipArchiveOutputStream out, String dir) throws IOException {
		if (srcFile.isDirectory()) {// 对文件夹进行处理。
			ZipArchiveEntry entry = new ZipArchiveEntry(dir + srcFile.getName()
					+ "/");
			out.putArchiveEntry(entry);
			out.closeArchiveEntry();
			// 循环文件夹中的所有文件进行压缩处理。
			String[] subFiles = srcFile.list();
			for (String subFile : subFiles) {
				compressOneFile(new File(srcFile.getPath() + "/" + subFile),
						out, (dir + srcFile.getName() + "/"));
			}
		} else { // 普通文件。
			InputStream is = null;
			try {
				is = new BufferedInputStream(new FileInputStream(srcFile));
				// 创建一个压缩包。
				ZipArchiveEntry entry = new ZipArchiveEntry(srcFile, dir
						+ srcFile.getName());
				out.putArchiveEntry(entry);
				IOUtils.copy(is, out);
				out.closeArchiveEntry();
			} finally {
				if (is != null)
					is.close();
			}
		}
	}

	/**
	 * 功能：解压缩zip压缩包下的所有文件。
	 * 
	 * @author 周三龙
	 * @param zipFile
	 *            zip压缩文件
	 * @param dir
	 *            解压缩到这个路径下
	 * @throws IOException
	 *             文件流异常
	 */
	public void decompressZip(File zipFile, String dir) throws IOException {
		ZipFile zf = new ZipFile(zipFile);
		try {
			for (Enumeration<ZipArchiveEntry> entries = zf.getEntries(); entries
					.hasMoreElements();) {
				ZipArchiveEntry ze = entries.nextElement();
				// 不存在则创建目标文件夹。
				File targetFile = new File(dir, ze.getName());
				// 遇到根目录时跳过。
				if (ze.getName().lastIndexOf("/") == (ze.getName().length() - 1)) {
					continue;
				}
				// 如果文件夹不存在，创建文件夹。
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}

				InputStream i = zf.getInputStream(ze);
				OutputStream o = null;
				try {
					o = new FileOutputStream(targetFile);
					IOUtils.copy(i, o);
				} finally {
					if (i != null) {
						i.close();
					}
					if (o != null) {
						o.close();
					}
				}
			}
		} finally {
			zf.close();
		}
	}

	/**
	 * 功能：解压缩zip压缩包下的某个文件信息。
	 * 
	 * @author 周三龙
	 * @param zipFile
	 *            zip压缩文件
	 * @param fileName
	 *            某个文件名,例如abc.zip下面的a.jpg，需要传入/abc/a.jpg。
	 * @param dir
	 *            解压缩到这个路径下
	 * @throws IOException
	 *             文件流异常
	 */
	public void decompressZip(File zipFile, String fileName, String dir)
			throws IOException {
		// 不存在则创建目标文件夹。
		File targetFile = new File(dir, fileName);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		ZipFile zf = new ZipFile(zipFile);
		Enumeration<ZipArchiveEntry> zips = zf.getEntries();
		ZipArchiveEntry zip = null;
		while (zips.hasMoreElements()) {
			zip = zips.nextElement();
			if (fileName.equals(zip.getName())) {
				OutputStream o = null;
				InputStream i = zf.getInputStream(zip);
				try {
					o = new FileOutputStream(targetFile);
					IOUtils.copy(i, o);
				} finally {
					if (i != null) {
						i.close();
					}
					if (o != null) {
						o.close();
					}
				}
			}
		}
		zf.close();
	}

	/**
	 * 功能：得到zip压缩包下的某个文件信息,只能在根目录下查找。
	 * 
	 * @author 周三龙
	 * @param zipFile
	 *            zip压缩文件
	 * @param fileName
	 *            某个文件名,例如abc.zip下面的a.jpg，需要传入/abc/a.jpg。
	 * @return ZipArchiveEntry 压缩文件中的这个文件,没有找到返回null。
	 * @throws IOException
	 *             文件流异常
	 */
	public ZipArchiveEntry readZip(File zipFile, String fileName)
			throws IOException {
		ZipFile zf = new ZipFile(zipFile);
		Enumeration<ZipArchiveEntry> zips = zf.getEntries();
		ZipArchiveEntry zip = null;
		try{
			while (zips.hasMoreElements()) {
				zip = zips.nextElement();
				if (fileName.equals(zip.getName())) {
					return zip;
				}
			}
		}finally{
			zf.close();
		}
		return null;
	}

	/**
	 * 功能：得到zip压缩包下的所有文件信息。
	 * 
	 * @author 周三龙
	 * @param zipFile
	 *            zip压缩文件
	 * @return Enumeration<ZipArchiveEntry> 压缩文件中的文件枚举。
	 * @throws IOException
	 *             文件流异常
	 */
	public Enumeration<ZipArchiveEntry> readZip(File zipFile)
			throws IOException {
		ZipFile zf = new ZipFile(zipFile);
		Enumeration<ZipArchiveEntry> zips = zf.getEntries();
		zf.close();
		return zips;
	}
}
