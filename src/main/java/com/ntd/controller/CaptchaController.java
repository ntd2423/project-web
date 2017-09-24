package com.ntd.controller;

import com.ntd.redis.JedisTemplate;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	
	private static final Log logger = LogFactory.getLog(CaptchaController.class);
	
	private static final int CAPTCHA_EXPIRE = 60;

	private JedisTemplate jedisTemplate;
	
	@RequestMapping(value="/getInfo")
	public void captcha(
			@RequestParam("captchaToken") String captchaToken,
			HttpServletResponse response) throws IOException {
		if(StringUtils.isEmpty(captchaToken)) {
			return;
		}
		response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "No-cache");  
        response.setDateHeader("Expires", 0);
        
    	//Captcha captcha = CaptchaUtil.drawImage(1, 6, 120, 50);
        //String code = captcha.getAnswer();
        
        //存入缓存
		//jedisTemplate.setex("cacheKey#" + captchaToken, CAPTCHA_EXPIRE, code);
        //ImageIO.write(captcha.getImage(), "JPEG", response.getOutputStream());
    }
}
