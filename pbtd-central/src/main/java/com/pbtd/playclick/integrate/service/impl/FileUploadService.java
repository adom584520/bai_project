package com.pbtd.playclick.integrate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.util.ImportImg;
import com.pbtd.playclick.integrate.mapper.FileUploadMapper;
import com.pbtd.playclick.integrate.service.face.IFileUploadService;
@Service
public class FileUploadService implements IFileUploadService {

	@Autowired
	private FileUploadMapper fileupload;
	@Autowired
	private ImportImg  imporimgs;

	private final String path=System.getProperty("user.dir")+"/images" ;

	@Override
	public int importvod_album_strategy(Map<String, Object> params) {
		List<Map<String,Object>> list= fileupload.album_strategy(params);
		//iscompressuril 是否压缩 pictype 1 树图 其他横图
		int iscompressuril=0;
		String pictype="1";
		for (Map<String, Object> map : list) {
			String i=map.get("img1")==null?"":map.get("img1").toString();
			if(!i.equals("")){
				String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i,filenamenew,"1","vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
			String i2=map.get("img2")==null?"":map.get("img2").toString();
			if(!i2.equals("")){
				pictype="0";
				String imgname= i2.substring( i2.lastIndexOf("/")+1, i2.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i2,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i2,filenamenew,"2","vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}

			String i3=map.get("img3")==null?"":map.get("img3").toString();
			if(!i3.equals("")){
				iscompressuril=0;
				String imgname= i3.substring( i3.lastIndexOf("/")+1,  i3.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String  filenamenew=imporimgs.downLoadFromUrl(i3,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i3,filenamenew,"3","vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
			String i4=map.get("img4")==null?"":map.get("img4").toString();
			if(!i4.equals("")){
				iscompressuril=0;
				pictype="0";
				String imgname= i4.substring( i4.lastIndexOf("/")+1,  i4.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i4,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i4,filenamenew,"4","vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
		}

		/*爬取剧集图片
		 * */
		importalbumvideo_strategy( params,path);
		return 0;
	}
	//爬取剧集图片 国广银河合一 自动入库数据
	public void importalbumvideo_strategy(Map<String, Object> params,String path){
		//iscompressuril 是否压缩 pictype 1 树图 其他横图
		int iscompressuril=0;
		String pictype="0";
		List<Map<String,Object>> listvideo= fileupload.albumvideo_strategy(params);
		for (Map<String, Object> mapvideo : listvideo) {
			String i=mapvideo.get("pic")==null?"":mapvideo.get("pic").toString();
			if(!i.equals("")){
				String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {

					String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
							mapvideo.get("code").toString());
					saveimgvodalbum_strategy(i,filenamenew,"4","vod_albuminfovideo_strategy",mapvideo.get("code").toString(),mapvideo.get("cpcode").toString());
				} catch (Exception e) {
					continue;
				}  
			}
		}
	}
	//保存图片信息
	private void saveimgvodalbum_strategy(String oldimg,String newimg,String pictype,
			String tablename,String code,String cpcode){//更改国广银河合一自动汇聚专辑图片
		Map<String, Object> params =new HashMap<>();
		if(tablename.equals("vod_albuminfo_strategy")){
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("seriesCode",code);
			params.put("cpcode", cpcode);
			fileupload.updatealbum_strategy(params);
		}else if(tablename.equals("vod_albuminfovideo_strategy")){//更改国广银河合一自动汇聚剧集图片
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("dramacode",code);
			params.put("cpcode", cpcode);
			fileupload.updatealbumvideo_strategy(params); 
		}else if(tablename.equals("vod_albuminfo")){//更改汇聚后专辑图片
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("seriesCode",code);
			fileupload.updatealbum(params); 
		}else if(tablename.equals("vod_albuminfovideo")){//更改汇聚后剧集图片
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("dramacode",code);
			fileupload.updatealbumvideo(params); 
		}else if(tablename.equals("youku_vod_albuminfo_strategy")){//更改汇聚后专辑图片
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("seriesCode",code);
			fileupload.updateyouku_album_strategy(params); 
		}else if(tablename.equals("youku_vod_albuminfovideo_strategy")){//更改汇聚后剧集图片
			params.put("imgurl", oldimg);
			params.put("newimgurl", newimg);
			params.put("pictype", pictype);
			params.put("dramacode",code);
			fileupload.updateyouku_albumvideo_strategy(params); 
		}
		fileupload.insertvodcpimg(params);//新增图片上传记录 
	}

	@Override
	public int importyouku_album_strategy(Map<String, Object> params) {
		List<Map<String,Object>> list= fileupload.youku_album_strategy(params);
		//iscompressuril 是否压缩 pictype 1 树图 其他横图
		int iscompressuril=0;
		String pictype="1";
		for (Map<String, Object> map : list) {
			String i=map.get("img1")==null?"":map.get("img1").toString();
			if(!i.equals("")){
				String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i,filenamenew,"1","youku_vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
			String i2=map.get("img2")==null?"":map.get("img2").toString();
			if(!i2.equals("")){
				pictype="0";
				String imgname= i2.substring( i2.lastIndexOf("/")+1, i2.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i2,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i2,filenamenew,"2","youku_vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}

			String i3=map.get("img3")==null?"":map.get("img3").toString();
			if(!i3.equals("")){
				iscompressuril=0;
				String imgname= i3.substring( i3.lastIndexOf("/")+1,  i3.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String  filenamenew=imporimgs.downLoadFromUrl(i3,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i3,filenamenew,"3","youku_vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
			String i4=map.get("img4")==null?"":map.get("img4").toString();
			if(!i4.equals("")){
				iscompressuril=0;
				pictype="0";
				String imgname= i4.substring( i4.lastIndexOf("/")+1,  i4.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i4,imgname,path,iscompressuril,pictype,
							map.get("code").toString());
					saveimgvodalbum_strategy(i4,filenamenew,"4","youku_vod_albuminfo_strategy",map.get("code").toString(),map.get("cpcode").toString());
				} catch (Exception e) {
				}  
			}
		}

		/*爬取剧集图片
		 * */
		importyouku_albumvideo_strategy( params,path);
		return 0;
	}
	//爬取剧集图youku自动入库数据
	public void importyouku_albumvideo_strategy(Map<String, Object> params,String path){
		//iscompressuril 是否压缩 pictype 1 树图 其他横图
		int iscompressuril=0;
		String pictype="0";
		List<Map<String,Object>> listvideo= fileupload.youku_albumvideo_strategy(params);
		for (Map<String, Object> mapvideo : listvideo) {
			String i=mapvideo.get("pic")==null?"":mapvideo.get("pic").toString();
			if(!i.equals("")){
				String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {

					String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
							mapvideo.get("code").toString());
					saveimgvodalbum_strategy(i,filenamenew,"4","youku_vod_albuminfovideo_strategy",mapvideo.get("code").toString(),mapvideo.get("cpcode").toString());
				} catch (Exception e) {
					continue;
				}  
			}
		}
	}

	@Override
	public int importvod_albumid(Map<String, Object> params) {
		try{  
			List<Map<String,Object>> list= fileupload.album(params);
			//iscompressuril 是否压缩 pictype 1 树图 其他横图
			int iscompressuril=0;
			String pictype="1";
			for (Map<String, Object> map : list) {
				String i=map.get("img1")==null?"":map.get("img1").toString();
				if(!i.equals("")){
					String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
					if(imgname.lastIndexOf(".")<0){
						imgname+=".jpg";
					}
					try {
						String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
								map.get("code").toString());
						saveimgvodalbum_strategy(i,filenamenew,"1","vod_albuminfo",map.get("code").toString(),map.get("cpcode").toString());

					} catch (Exception e) {
						e.printStackTrace();
					}  
				}
				String i2=map.get("img2")==null?"":map.get("img2").toString();
				if(!i2.equals("")){
					pictype="0";
					String imgname= i2.substring( i2.lastIndexOf("/")+1, i2.length());
					if(imgname.lastIndexOf(".")<0){
						imgname+=".jpg";
					}
					try {
						String filenamenew=imporimgs.downLoadFromUrl(i2,imgname,path,iscompressuril,pictype,
								map.get("code").toString());
						saveimgvodalbum_strategy(i2,filenamenew,"2","vod_albuminfo",map.get("code").toString(),map.get("cpcode").toString());

					} catch (Exception e) {
					}  
				}

				String i3=map.get("img3")==null?"":map.get("img3").toString();
				if(!i3.equals("")){
					iscompressuril=0;
					String imgname= i3.substring( i3.lastIndexOf("/")+1,  i3.length());
					if(imgname.lastIndexOf(".")<0){
						imgname+=".jpg";
					}
					try {
						String filenamenew=imporimgs.downLoadFromUrl(i3,imgname,path,iscompressuril,pictype,
								map.get("code").toString());
						saveimgvodalbum_strategy(i3,filenamenew,"3","vod_albuminfo",map.get("code").toString(),map.get("cpcode").toString());

					} catch (Exception e) {
					}  
				}
				String i4=map.get("img4")==null?"":map.get("img4").toString();
				if(!i4.equals("")){
					pictype="0";
					iscompressuril=0;
					String imgname= i4.substring( i4.lastIndexOf("/")+1,  i4.length());
					if(imgname.lastIndexOf(".")<0){
						imgname+=".jpg";
					}
					try {
						String filenamenew=imporimgs.downLoadFromUrl(i4,imgname,path,iscompressuril,pictype,
								map.get("code").toString());
						saveimgvodalbum_strategy(i4,filenamenew,"4","vod_albuminfo",map.get("code").toString(),map.get("cpcode").toString());

					} catch (Exception e) {
					}  
				}
			}

			/*爬取剧集图片
			 * 
			 * */
		importalbumvideo( params,path);
		}catch (Exception e) { 
			e.printStackTrace();
		}  
		return 0;
	}

	//爬取入库后剧集图片
	@Override
	public void importalbumvideo(Map<String, Object> params,String path){
		List<Map<String,Object>> listvideo= fileupload.albumvideo(params);
		//iscompressuril 是否压缩 pictype 1 树图 其他横图
		int iscompressuril=0;String pictype="0";
		for (Map<String, Object> mapvideo : listvideo) {
			String i=mapvideo.get("pic")==null?"":mapvideo.get("pic").toString();
			if(!i.equals("")){
				String imgname= i.substring( i.lastIndexOf("/")+1,  i.length());
				if(imgname.lastIndexOf(".")<0){
					imgname+=".jpg";
				}
				try {
					String filenamenew=imporimgs.downLoadFromUrl(i,imgname,path,iscompressuril,pictype,
							mapvideo.get("code").toString());
					saveimgvodalbum_strategy(i,filenamenew,"4","vod_albuminfovideo",mapvideo.get("code").toString(),"1");

				} catch (Exception e) {
					continue;
				}  
			}
		}
	}
}
