package com.pbtd.manager.test;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pbtd.manager.util.pushUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class PushTest {
    public static final String ADD_URL = "http://192.168.0.199:8080/*.controller/*.action";
    public static void appadd() {
        try {
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            String message = java.net.URLEncoder.encode("哈哈哈","utf-8");
            obj.element("detail", "df");
            obj.element("TEXT1", "asd");
            obj.element("TEXT2", message);

            out.writeBytes("data="+obj.toString());
            System.out.println("data="+obj.toString());
            out.flush();
            out.close();
            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void receivePost(HttpServletRequest request,HttpServletResponse response){
    	pushUtils push = new pushUtils();
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		if ("1".equals(request.getParameter("queryString"))) {
			json1.accumulate("is_live", "1");
			json1.accumulate("device_type", "1");
			json1.accumulate("channel_backTime", "0");
			json1.accumulate("channel_id", "CCTV-1-HD");
			json1.accumulate("proj_id", "HE_CMCC_OTT_ZTE");
			json1.accumulate("album_id", "2100140012981272561740004");
			json1.accumulate("token", "cba1fc03-9b13-4f41-9f74-8e2622bbc348");
			json1.accumulate("des_token", "Agd16HbuEmAq8qibPGeZSqJhScgmq_mFShpqzp3nMP_e");
			json1.accumulate("src_token", "458fa31c88afb18d6a29b0e932930296e45a9f1cea9f3243b1ecf94e319be73a");
			try {
				push.sendTvUnicast("AiJan9ixNwBirD_Y3lVUQiluhaB861QfcG-ogz_kuEH2","安卓单播",json1.toString(),"2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("安卓单播");
		}
    }
  

    public static void main(String[] args) {
        appadd();
    }

}
