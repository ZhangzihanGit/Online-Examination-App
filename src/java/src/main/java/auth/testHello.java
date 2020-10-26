package auth;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;

public class testHello {
    private final static Logger logger = LogManager.getLogger(testHello.class);

    public testHello() {}

    public void login(String username, String password) throws
            UnknownAccountException, IncorrectCredentialsException, AuthenticationException  {
        logger.info("My First Apache Shiro Application");
        Environment env = new BasicIniEnvironment("classpath:shiro.ini");
        SecurityManager securityManager = env.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);
        logger.info("Init Shiro Application");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        if (!subject.isAuthenticated()) {
//            try {
                subject.login(token);
//            } catch (UnknownAccountException e) {
//                logger.error("Unknown username");
//            }
//
        }
    }
}
