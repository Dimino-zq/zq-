package edu.hfu.train.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.service.sysset.FastDFSService;

@RestController
public class LoginIndexAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FastDFSService fastDFSService;
	
	/** auth系统地址 */
	@Value("${auth.server.url}")
	String authUrl;
	
	@RequestMapping(value = { "/", "/login" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoLoginPage(String mess) {
		LOG.debug("{}","进入登录页...");
		LOG.debug("进入登录页...");
		ModelAndView mod = new ModelAndView("/login.btl");
		if (null == mess) {
			mess = "";
		}
		mod.addObject("mess", mess);
		// 添加auth注册时返回的地址
		mod.addObject("authUrlReg", authUrl + "/userregistration");
		return mod;
	} 
	
	
	@RequestMapping(value = { "/goIndex" }, method = { RequestMethod.POST, RequestMethod.GET })
	@PreAuthorize("hasRole('ROLE_01')")
	public ModelAndView goIndex() {
		ModelAndView mod = new ModelAndView("/index.btl");
		return mod;
	}
	
	@RequestMapping(value = { "/invalidSession" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView invalidSession() {
		ModelAndView mod = new ModelAndView("/invalidSession.btl");
		return mod;
	}
	
	@RequestMapping(value = { "/goBlank" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView goBlank() {
		ModelAndView mod = new ModelAndView("/blank.btl");
		return mod;
	}
	@RequestMapping(value={"/loginOut"}, method= {RequestMethod.POST,RequestMethod.GET})
	public String loginOut(HttpSession session) {
		try {
			String person_name = String.valueOf(session.getAttribute("userName"));
			LOG.debug("用户 "+person_name+" 退出登录");
			session.invalidate();
		} catch (Exception e) {
		}
		return "success";		
	}
	public String testupfile() {
		try {
			System.out.println("存入：");
			String filePath="D:\\temp\\操作手册.docx";
			FileInputStream  fis = new FileInputStream(filePath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
	        int n;
	        while ((n = fis.read(b)) != -1) {
			     bos.write(b, 0, n);
			}
	        fis.close();
	        bos.close();
	        byte[] buffer = bos.toByteArray();
	        System.out.println("存入长度："+buffer.length);
			String filepath=fastDFSService.uploadFile(buffer);
			System.out.println(filepath);
			System.out.println("取出：");
			byte[] outBuffer=fastDFSService.downloadFile(filepath);
			System.out.println("取出长度："+outBuffer.length);
			System.out.println("删除：");
			boolean res=fastDFSService.deleteFile(filepath);
			System.out.println("删除"+res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping(value={"/getRemoteImgFile"}, method= {RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<byte[]> getRemoteImgFile(String filePath,HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        byte[] data=fastDFSService.downloadFile(filePath);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
	}
	@RequestMapping(value={"/getRemotePdfFile"}, method= {RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<byte[]> getRemotePdfFile(String filePath,HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        byte[] data=fastDFSService.downloadFile(filePath);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
	}
	
}
