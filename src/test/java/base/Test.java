package base;

import com.ntd.entity.User;
import com.ntd.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nongtiedan on 2016/8/29.
 */
public class Test {

    public static void main(String[] args) {
        String[] locations = {"applicationContextBase.xml"};
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(locations);
        UserService userService = (UserService) context.getBean("userServiceImpl");
        User user = userService.selectUserById(1);
        System.out.println(user.getName() + ":" + user.getPhone());
    }

}
