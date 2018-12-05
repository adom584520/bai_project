
package com.pbtd.playclick.base.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

public class FileUtils
{
    private static final Log logger = LogFactory.getLog(FileUtils.class);

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFilePath 源文件File
     * @param destDirPath 目标目录File
     * @param newFileName 新文件名
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    @SuppressWarnings("resource")
    public static boolean copyFile(String srcFilePath , String destDirPath , String newFileName)
    {
        boolean copySizes = false;
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists())
        {
            logger.info("源文件" + srcFilePath + "不存在");
            return false;
        }
        else if (StringUtils.isBlank(newFileName))
        {
            logger.info(newFileName + "文件名为null");
            return false;
        }
        File destDir = new File(destDirPath);
        if (!(destDir.exists()) && !(destDir.isDirectory()))
        {
            destDir.mkdirs();
            destDir = new File(destDirPath);
        }
        try
        {
            FileChannel fcin = new FileInputStream(srcFile).getChannel();
            FileChannel fcout = new FileOutputStream(new File(destDir, newFileName)).getChannel();
            fcin.transferTo(0, fcin.size(), fcout);
            fcin.close();
            fcout.close();
            copySizes = true;
        }
        catch (Exception e)
        {
            logger.error("文件复制失败，错误信息为： " + ExceptionPrint.getInstance().getStackTrace(e));
            return false;
        }
        return copySizes;
    }

    /**
     * 根据文件夹路径删除整个文件夹
     *
     * @param delPath 文件路径
     */
    public static void deleteFile(String delPath)
    {
        try
        {
            File file = new File(delPath);
            if (!file.isDirectory())
            {
                file.delete();
            }
            else if (file.isDirectory())
            {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++)
                {
                    File delfile = new File(delPath + "\\" + filelist[i]);
                    if (!delfile.isDirectory())
                        delfile.delete();
                    else if (delfile.isDirectory())
                        deleteFile(delPath + "\\" + filelist[i]);
                }
                file.delete();
            }
        }
        catch (Exception e)
        {
            logger.error("删除文件失败，错误信息为： " + ExceptionPrint.getInstance().getStackTrace(e));
        }
    }

    /**
     * 根据文件夹路径创建文件
     *
     * @param folderPath
     */
    public static void creatFolder(String folderPath)
    {
        File folderFile = new File(folderPath);
        //判断文件夹是否存在，不存在则创建
        if (!(folderFile.exists()) && !(folderFile.isDirectory()))
        {
            folderFile.mkdirs();
        }
    }

    /**
     * 保存文件到服务器上
     *
     * @param is              输入流
     * @param filePathAndName 文件路径和文件名
     * @throws java.io.IOException
     */
    public static void writeFile(InputStream is , String filePathAndName)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(filePathAndName);
            byte[] buffer = new byte[1024 * 4];
            int byteread = 0;
            while ((byteread = is.read(buffer)) != -1)
            {
                fos.write(buffer, 0, byteread);
                fos.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (ParamTools.isObjectNotNull(fos))
                {
                    fos.close();
                }
                if (ParamTools.isObjectNotNull(is))
                {
                    is.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }

    /***
     * 获取系统路径
     * @return  系统路径
     */
    public static String getSystemPath()
    {
        String OS = System.getProperty("os.name").toLowerCase();
        String systemPath = FileUtils.class.getClassLoader().getResource("/").getPath().replace("%20", " ");
        systemPath = systemPath.replace("/WEB-INF/classes", "");
        if (OS.indexOf("windows") >= 0)
        {
            systemPath = systemPath.substring(1, systemPath.length());
        }
        return systemPath;
    }
}
