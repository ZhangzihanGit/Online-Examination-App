package auth;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import servlet.LoginServlet;

public class testHello {
    private final static Logger logger = LogManager.getLogger(LoginServlet.class);

    public testHello() {}

    public void testShiroInit() {
        logger.info("My First Apache Shiro Application");

        //1.
        Environment env = new BasicIniEnvironment("classpath:shiro.ini");
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.
//        SecurityManager securityManager = factory.getInstance();
        SecurityManager securityManager = env.getSecurityManager();

        //3.
        SecurityUtils.setSecurityManager(securityManager);

        logger.info("Init Shiro Application");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "111");

        try {
            //4、登录，即身份验证
            subject.login(token);
            logger.info("authenticate success");
        } catch (AuthenticationException e) {
            //5、身份验证失败
            logger.error("authenticate fail");
        }


        //6、退出
        subject.logout();
    }
}
