package com.pbtd.playclick.util;

import java.io.File;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
/**
 * 图片压缩
 * @author zr
 *
 */

@Controller("vod.ImgCompressUtil")
@RequestMapping("/zr")
public class ImgCompressUtil {

	/**
	 * log4j
	 */
	private final static Logger logger = Logger.getLogger(ImgCompressUtil.class
			.getName());

	/**
	 * 图片压缩测试
	 * 
	 */
	/*@Test*/
	public   void tes( ) {
		// 源图片url
		String url = "d:/0a2e174456552e8a09621fc2a34081eec2cdec89.jpg";
		//压缩后的宽和高
		int w = 260;
		int h = 360;
	    	//压缩后的图片路径
			String newName = "bak";
			String newDir=System.getProperty("user.dir")+"/images/" ;
			String filePath = newDir + newName + "_"+ w + "_" + h + url.substring(url.indexOf("."));
		//压缩
		 String n=ImgCompress(url,filePath,w,h);
		 System.out.println(n);
	}
 
	public   String upload(String url ,String type) {
		// 源图片url 	本地图片存储路径
	//	String url = "0a2e174456552e8a09621fc2a34081eec2cdec89.jpg";
		//压缩后的宽和高
		int w=0;int h=0;
		if(type.equals("1")){//树图
			  w = 260;
			  h = 360;
		}else{//横图
			  w = 372;
			  h = 209;
		}
		
	    	//压缩后的图片路径
			String newName = "bak";
			String newDir=System.getProperty("user.dir")+"/images/" ;
			String name=url.substring(url.lastIndexOf('.'), url.length());
			String filePath = newDir + newName + "_"+ w + "_" + h + name;
		//压缩
		 String n=ImgCompress(url,filePath,w,h);
		 return n;
	}
	/**
	 * 外部调用方法
	 * @param url
	 * @param w
	 * @param h
	 */
	public static String ImgCompress(String url, String filePath, int w, int h) {
			// 压缩质量 百分比
			float JPEGcompression = 0.7f;
			// 本地图片存储路径
			//url = System.getProperty("user.dir")+"/images/" + url;
			String name = url.substring(url.lastIndexOf("/") + 1);
			// 截取url中最后一个“/”之后的字符串为name
			logger.info("url：===========" + url);
			logger.info("name：=========" + name);
			//压缩主方法
			 return ImgCompress(filePath, url, name, w, h, JPEGcompression);
		
	}
	
	/**
	 * 图片压缩主方法
	 * @param newDir	 *            图片所在的文件夹路径
	 * @param file	 *            图片路径
	 * @param name	 *            图片名
	 * @param w	 *            目标宽
	 * @param h	 *            目标高
	 * @param JPEGcompression	 *            压缩质量/百分比
	 * @author zr
	 */
	public static String ImgCompress(String filePath, String url, String name,
			int w, int h, float JPEGcompression) {
		File file = new File(url);
		if (!(file.exists() && file.canRead())) {
			filePath = "/var/upload/404.jpg";
		}else{
			try {
				BufferedImage bufferedImage =  ImageIO.read(file);
				   double wr=0,hr=0;
			        File srcFile = new File(url);
			        File destFile = new File(filePath);
			        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
			        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
			        
			        
			        wr=w*1.0/bufImg.getWidth();   //获取缩放比例
			        hr=h*1.0 / bufImg.getHeight();
			        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
			        Itemp = ato.filter(bufImg, null);
			        try {
			            ImageIO.write((BufferedImage) Itemp,filePath.substring(filePath.lastIndexOf(".")+1), destFile); //写入缩减后的图片
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
				/*
				 * 
				  int new_w = w;
			        int new_h = h; 
				  BufferedImage image_to_save = new BufferedImage(new_w, new_h,bufferedImage.getType());
				image_to_save.getGraphics().drawImage(
						bufferedImage.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
						0, null);
				FileOutputStream fos = new FileOutputStream(filePath); // 输出到文件流
				// 新的方法
				 int dpi = 300;//分辨率
			//	saveAsJPEG(dpi, image_to_save, JPEGcompression, fos);
				//关闭输出流
				fos.close();
				//返回压缩后的图片地址*/
			} catch (IOException ex) {
				logger.log(Level.SEVERE, null, ex);
				filePath = "404.jpg";
			}
		}
		
		return filePath;
		
	}

	/**
	 * 以JPEG编码保存图片
	 * @param dpi	 *            分辨率
	 * @param image_to_save	 *            要处理的图像图片
	 * @param JPEGcompression	 *            压缩比
	 * @param fos	 *            文件输出流
	 * @throws IOException
	 
	public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save,
			float JPEGcompression, FileOutputStream fos) throws IOException {
		//JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
		ImageWriter imageWriter =   ImageIO.getImageWritersBySuffix("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
		imageWriter.setOutput(ios);
		IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
		if (JPEGcompression >= 0 && JPEGcompression <= 1f) {
			JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
			jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
			jpegParams.setCompressionQuality(JPEGcompression);
		}
		imageWriter.write(imageMetaData,new IIOImage(image_to_save, null, null), null);
		ios.close();
		imageWriter.dispose();

	}*/

}
