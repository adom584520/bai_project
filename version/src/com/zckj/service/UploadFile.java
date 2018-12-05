/**
 * @author liuxiaomeng
 * @datetime 2015-8-1_下午7:55:19
 */
package com.zckj.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zckj.files.compress.FlodUnCompress;
import com.zckj.tool.utils.ConstUtils;
import com.zckj.tool.utils.DataBasUtils;

/**
 * @author liuxiaomeng
 * @datetime 2015-8-1_下午7:55:19
 */
@SuppressWarnings("serial")
public class UploadFile extends ActionSupport {
	final String zipName = "update";
	/** 上传文件 */
	public File userfile;

	public File getUserfile() {
		return this.userfile;
	}

	public void setUserfile(final File userfile) {
		this.userfile = userfile;
	}

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
	 * 上传文件路径存储
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午4:34:51
	 * @param id
	 * @param filePath
	 */
	public void saveUploadFilePath(final String id, final String channel, final String filePath) {
		try {
			if (null != filePath) {
				final String fp = filePath.replace('\\', '/');
				final String sql = "UPDATE `versioninfo` SET `filePath` = '" + fp + "', `uploadState` = '1' WHERE `id` = '" + id + "'; ";
				DataBasUtils.getInstance().getPrepareStatement(sql).executeUpdate();
				final VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(Integer.parseInt(id));
				vib.setFilePath(filePath);
				vib.setUploadState(1);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String execute() {
		try {
			final HttpServletRequest request = ServletActionContext.getRequest();
			final HttpServletResponse response = ServletActionContext.getResponse();
			String uploadfile = request.getParameter("uploadfile");
			final String id = request.getParameter("id");
			final String versionId = request.getParameter("versionId");
			final String channel = request.getParameter("channel");
			
			uploadfile = uploadfile.replace('\\', '/');
			final String[] ufsp = uploadfile.split("/");
			uploadfile = ufsp[ufsp.length - 1];
			final VersionInfoBean vib = ConstUtils.ch2IndexId2VerMap.get(channel).get(Integer.parseInt(id));
			final String newVerDir = ConstUtils.uploadRootPath + vib.getChannel() + ConstUtils.f_separator + versionId;
			final File newVerDirFile = new File(newVerDir);
			if (!newVerDirFile.exists()) {
				newVerDirFile.mkdirs();
			}
			final File imgfile = new File(newVerDir + ConstUtils.f_separator + uploadfile);
			UploadFile.copyFile(this.userfile, imgfile);

//保存到tomcat路径下 并解压
			final File newVersionZip = new File(vib.getFilePath());
			String nvzName = newVersionZip.getAbsolutePath().replace(newVersionZip.getName(), "").replace('\\', '/');
			nvzName=nvzName+ConstUtils.f_separator + zipName;
			final File newVerTomcatFile = new File(nvzName);
			if (!newVerTomcatFile.exists()) {
				newVerTomcatFile.mkdirs();
			}
			final File imgtomcatfile = new File(newVerTomcatFile + ConstUtils.f_separator + uploadfile);
			UploadFile.copyFile(this.userfile, imgtomcatfile);
		    FlodUnCompress.getInstance().unZipFiles(imgtomcatfile, newVerTomcatFile + ConstUtils.f_separator);
		    imgtomcatfile.delete();
			
			
			//最新差异版本解压，便于与前差异版本合并
			boolean unZipResult = FlodUnCompress.getInstance().unZipFiles(imgfile, newVerDir + ConstUtils.f_separator);
			//上传
			final JSONObject jObject = new JSONObject();
			if(!unZipResult) {
				jObject.put("success", false);
				jObject.put("info", "上传压缩文件目录有误，请查证后重新上传！");
			} else {
				jObject.put("success", true);
				jObject.put("info", "成功上传附件，文件大小（KB）：" + ((imgfile.length() / 1024) + 1));
				this.saveUploadFilePath(id, channel, imgfile.getAbsolutePath());
				final String flodName = uploadfile.replace(".zip", "");
				new File(newVerDir + ConstUtils.f_separator + flodName).renameTo(new File(newVerDir + ConstUtils.f_separator + "update"));
			}
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF8");
			response.getWriter().print(jObject.toString());
			response.getWriter().flush();
			return Action.NONE;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
}
