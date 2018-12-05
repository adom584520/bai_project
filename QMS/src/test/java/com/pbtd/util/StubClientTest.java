package com.pbtd.util;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public class StubClientTest {

	
	public static void main(String[] args) throws MalformedURLException, RemoteException {
		
		List<String> list=ListProvider.getList();
		
		String contentName="";
		int contentType=0;
		long beforeTime=Calendar.getInstance().getTimeInMillis();
		for(int i=0;i<list.size();i++){
			contentName=list.get(i);
			StubClient client=new StubClient();
			client.queryContent(contentName, contentType);
		}
		long afterTime=Calendar.getInstance().getTimeInMillis();
		System.out.println("用时："+(afterTime-beforeTime)/1000+"s");

	}
}
