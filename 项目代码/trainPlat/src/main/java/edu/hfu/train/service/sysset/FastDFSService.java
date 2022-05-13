package edu.hfu.train.service.sysset;

import java.io.IOException;

import javax.annotation.Resource;

import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FastDFSService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private TrackerClient trackerClient;

	/**
	 * 按模块上传文件
	 * 
	 * @param moduleName
	 * @param fileStream
	 * @return
	 */
	public String uploadFile(byte[] fileStream) {
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, null);
//          这个参数可以指定，也可以不指定，如果指定了，可以根据 testGetFileMate()方法来获取到这里面的值
//          NameValuePair nvp [] = new NameValuePair[]{ 
//                  new NameValuePair("age", "18"), 
//                  new NameValuePair("sex", "male") 
//          }; 
			String[] arr = storageClient.upload_file(fileStream, null, null);
			if (arr == null || arr.length != 2) {
				log.error("向FastDFS上传文件失败");
			} else {
				log.info("向FastDFS上传文件成功");
				return arr[0]+"/"+arr[1];
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
		return null;
	}
	/**
	 * 获取远程文件
	 * @param groupName
	 * @param remoteFileId
	 * @return
	 */
	public byte[] downloadFile(String groupName, String remoteFileId) {
		TrackerServer trackerServer=null;
		try {
			trackerServer = trackerClient.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, null);
			byte[] content = storageClient.download_file(groupName, remoteFileId);
			if (content == null || content.length == 0) {
				log.error("文件大小为空！");
			}else {
				log.info("成功下载文件： ");
				return content;
			}
		}  catch (Exception e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
		return null;
	}
	public byte[] downloadFile(String remoteFilePath) {
		int pos= remoteFilePath.indexOf("/");
		String groupName=remoteFilePath.substring(0, pos);
		String remoteFileId=remoteFilePath.substring(pos+1);
		return downloadFile(groupName,remoteFileId);
	}
	/**
	 * 从FastDFS删除文件
	 *
	 * @param localFilename  本地文件名
	 * @param groupName      文件在FastDFS中的组名
	 * @param remoteFilename 文件在FastDFS中的名称
	 */
	public boolean deleteFile(String groupName, String remoteFileId) {
		TrackerServer trackerServer=null;
		try {
			trackerServer = trackerClient.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, null);
			int i = storageClient.delete_file(groupName, remoteFileId);
			if (i == 0) {
				log.info("FastDFS删除文件成功");
				return true;
			} else {
				log.info("FastDFS删除文件失败");
				return false;
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
		return false;
	}

	public boolean deleteFile(String remoteFilePath) {
		int pos= remoteFilePath.indexOf("/");
		String groupName=remoteFilePath.substring(0, pos);
		String remoteFileId=remoteFilePath.substring(pos+1);
		return deleteFile(groupName,remoteFileId);
	}

	private void closeTrackerServer(TrackerServer trackerServer) {
		try {
			if (trackerServer != null) {
				log.info("关闭trackerServer连接");
				trackerServer.close();
			}
		} catch (IOException e) {
			log.error("error", e);
		}
	}
}
