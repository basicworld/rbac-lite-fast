/**
 * 
 */
package com.rbac.system.base;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试基类 . <br>
 * ActiveProfiles表示加载指定配置文件 <br>
 * SpringBootTest.WebEnvironment.RANDOM_POR 表示使用随机端口号 <br>
 * Ignore表示本类或本方法不执行测试
 * 
 * @author wlfei
 * @date 2021-04-16
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore
public class BaseTest {

}
