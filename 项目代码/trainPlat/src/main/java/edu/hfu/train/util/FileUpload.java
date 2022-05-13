package edu.hfu.train.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	/**
	 * 保存申请你表中的附件，应检查附件是否为空
	 * @param file 接受的文件
	 * @param basePath 基础路径
	 * @param savePath 文件要保存的路径
	 *                 例："\\static\\advert\\"
	 * @param fileName 文件保存时的文件名
	 * @return 完整的文件路径
	 * @throws IOException
	 */
	public  String  saveFileForApplication(MultipartFile file,String basePath,String savePath,String fileName) throws IOException
	{
		String filePath=basePath+savePath;
		File upload = new File(basePath,savePath);
		if (!upload.exists()) {
			if(!upload.mkdirs())
				throw new IOException("上传失败：无法创建路径！");
		}
		//获取文件的后缀
		//String extendName=fileName.substring(fileName.lastIndexOf('.'));
		FileOutputStream out = new FileOutputStream(filePath + "\\" + fileName);
		out.write(file.getBytes());
		out.flush();
		out.close();
		//拼接完整路径
		String fulPath=(savePath+fileName).replace('\\', '/');
		return fulPath;
	}
}
