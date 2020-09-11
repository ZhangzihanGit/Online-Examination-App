package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretLoader {
    private static final Logger logger = LogManager.getLogger(SecretLoader.class);
    private static final String file = "secreturi.properties";
    public static String getDBUri() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties p = new Properties();
            InputStream resourceStream = loader.getResourceAsStream(file);
            p.load(resourceStream);
            logger.info(p.getProperty("uri"));
            return p.getProperty("uri");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
