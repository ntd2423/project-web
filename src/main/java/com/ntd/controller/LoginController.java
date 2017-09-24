package com.ntd.controller;

import com.ntd.service.UserService;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Log logger = LogFactory.getLog(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView getIndex(){
        logger.info("===访问首页===");

        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping(value = "/error-502.do")
    public ModelAndView error502(ModelMap mm) {
        mm.put("errorMsg", "502");

        return new ModelAndView("errorpage/error502", "mm", mm);
    }

}
