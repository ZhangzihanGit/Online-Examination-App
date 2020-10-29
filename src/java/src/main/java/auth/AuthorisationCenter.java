package auth;

import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AuthorisationCenter {
    private static AuthorisationCenter instance;
    private final static Logger logger = LogManager.getLogger(AuthorisationCenter.class);

    public AuthorisationCenter() {}

    public static AuthorisationCenter getInstance() {
        if (instance == null) {
            instance = new AuthorisationCenter();
        }
        return instance;
    }

    public void checkPermission(String sessionId, String role) throws NoAuthorisationException {
//        Subject subject = SecurityUtils.getSubject();
        Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        logger.info("auth: " + subject.isAuthenticated());

        if (!subject.hasRole(role)) {
            logger.error("User NOT has role: " + role);
            throw new NoAuthorisationException("You do not have authorisation!");
        }
    }
}
