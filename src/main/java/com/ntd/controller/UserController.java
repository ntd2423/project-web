package com.ntd.controller;

import com.google.common.collect.Maps;
import com.ntd.entity.User;
import com.ntd.service.UserService;
import com.ntd.utils.JSONResponseBuilder;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Log logger = LogFactory.getLog(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 用户信息
     */
    @RequestMapping("/userInfo")
    public void userInfo(@RequestParam(value="var", required=false) String var,
                         @RequestParam(value="userId", required = true) long userId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {

        Map<String, Object> result = Maps.newHashMap();
        try {

            result.put("userInfo", userService.selectUserById(userId));
            result.put("success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(StringUtils.isNotBlank(var)) {
                JSONResponseBuilder.buildRespVar(response, result, var);
            } else {
                JSONResponseBuilder.buildResp(response, result);
            }
        }
    }

    /**
     * 用户信息
     */
    @RequestMapping("/userInfo2")
    public ModelAndView userInfo2(@RequestParam(value="userId", required = true) long userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        logger.info("===访问用户信息页面2===");
        User user = userService.selectUserById(userId);

        //return new ModelAndView("userInfo").addObject("user", user);
        return new ModelAndView("userInfo", "user", user);
    }

    /**
     * 用户信息
     */
    @RequestMapping("/userInfo3")
    public ModelAndView userInfo3(@RequestParam(value="userId", required = true) long userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        logger.info("===访问用户信息页面3===");
        User user = userService.selectUserBySlave(userId);

        return new ModelAndView("userInfo", "user", user);
    }

    /**
     * 用户信息
     */
    @RequestMapping("/userInfo4")
    public ModelAndView userInfo4(@RequestParam(value="userId", required = true) long userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        logger.info("===访问用户信息页面4===");
        User user = userService.selectUserByCache(userId);

        return new ModelAndView("userInfo", "user", user);
    }

}
