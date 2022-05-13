package edu.hfu.train.util;

import java.util.Properties;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastFDSTest {

	public static void main(String[] args) {
		try {
			Properties p=new Properties();
			p.setProperty("tracker_server", "192.168.0.107:22122");
			p.setProperty("fastdfs.tracker_servers", "192.168.0.107:22122");
			
			ClientGlobal.initByProperties(p);
			TrackerClient c=new  TrackerClient();
            TrackerServer s=c.getConnection();
            StorageClient storageClient = new StorageClient(s, null);
            String filePath="D:\\temp\\操作手册.docx";
            String[] arr = storageClient.upload_file(filePath, null, null);
            if (arr == null || arr.length != 2) {
            	System.err.println("上传失败");
            } else {
            	System.out.println(arr[0]);
            	System.out.println(arr[1]);
            } 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
       


	}

}
