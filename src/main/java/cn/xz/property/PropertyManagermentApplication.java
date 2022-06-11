package cn.xz.property;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
/**
 * Servlet可以直接通过@WebServlet注解自动注册
 * Filter可以直接通过@WebFilter注解自动注册
 * Listener可以直接通过@WebListener 注解自动注册
 */
@EnableTransactionManagement  //开启事务管理，这样我们的事务才能生效
public class PropertyManagermentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyManagermentApplication.class, args);
    }

}
