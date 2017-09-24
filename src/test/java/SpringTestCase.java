import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by nongtiedan on 2016/8/2.
 */
//指定bean注入的配置文件。该设置还有一个inheritLocations的属性，默认为true,表示子类可以继承该设置。
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTestCase {

}
