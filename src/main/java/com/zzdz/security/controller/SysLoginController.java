package com.zzdz.security.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;
	
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Result login(String username, String password/*, String captcha*/) {
//		String kaptcha = getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//		if(!captcha.equalsIgnoreCase(kaptcha)) {
//
//			return new Result(ResultCode.KAPTCHA_ERROR);
//		}

		try {
			//1.构造登录令牌 UsernamePasswordToken
			//加密密码
			password = new Md5Hash(password,username,3).toString();  //1.密码，盐，加密次数
			UsernamePasswordToken upToken = new UsernamePasswordToken(username,password);
			//2.获取subject
			Subject subject = SecurityUtils.getSubject();
			//3.调用login方法，进入realm完成认证
			subject.login(upToken);
			//4.获取sessionId
			String sessionId = (String)subject.getSession().getId();
			//5.构造返回结果
			return new Result(ResultCode.SUCCESS,sessionId);
		}catch (Exception e) {
			return new Result(ResultCode.FAIL, e.getMessage());
		}
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:login.html";
	}

	public static void main(String[] args) {
		String password = new Md5Hash("123456","admin",3).toString();
		System.out.println(password);
	}


	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RuntimeException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}
	
}
