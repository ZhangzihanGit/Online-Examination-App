package auth;

import exceptions.AlreadyLoggedInException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.Collection;

public class AuthenticationCenter {
    DefaultSecurityManager securityManager;
    DefaultSessionManager sessionManager;
    Collection<Session> currentSessions;
    private final static Logger logger = LogManager.getLogger(AuthenticationCenter.class);

    public AuthenticationCenter() {
        this.securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        this.sessionManager = (DefaultSessionManager) this.securityManager.getSessionManager();
        this.currentSessions = this.sessionManager.getSessionDAO().getActiveSessions();
    }

    public Serializable login(String username, String password)
            throws AlreadyLoggedInException, IncorrectCredentialsException,
            UnknownAccountException, AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // let user log in and create a session
        if (!hasLoggedIn(token)) {
            try {
                subject.login(token);
                logger.info("User has authenticated");

                Session session = subject.getSession();
                session.setAttribute("principal" , subject.getPrincipal());
                logger.info("Principal: " + subject.getPrincipal().toString());
                logger.info("Sessionid: " + session.getId());

                if (subject.hasRole("instructor")) {
                    logger.info("User has role: instructor");
                } else {
                    logger.error("User NOT has role: instructor");
                }

                return session.getId();
            } catch (UnknownAccountException e) {
                logger.error("User has NOT authenticated");
                throw new UnknownAccountException("Unknown username!");
            } catch (IncorrectCredentialsException e) {
                logger.error("User has NOT authenticated");
                throw new IncorrectCredentialsException("Incorrect password!");
            } catch (AuthenticationException e) {
                logger.error("User has NOT authenticated");
                throw new AuthenticationException("Login fail!");
            }
        } else {
            throw new AlreadyLoggedInException("You have already logged in somewhere else, please logout first!");
        }
    }

    /**
     * Check if the token has already logged in, if already logged in,
     * then its username must be in an existing session
     * @param token
     * @return
     */
    public boolean hasLoggedIn(UsernamePasswordToken token) {
        Object username = token.getPrincipal();
        for (Session s : currentSessions) {
            Object existUsername = s.getAttribute("principal");
            if (username != null && existUsername.equals(username)) {
                logger.info("You have already logged in!");
                return true;
            }
        }

        return false;
    }
}
