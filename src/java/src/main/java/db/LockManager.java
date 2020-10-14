package db;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockManager {
    private static final Logger logger = Logger.getLogger(LockManager.class);
    private Map<Integer, String> lockMap = new ConcurrentHashMap<>();
    private static LockManager lockManager;

    public static final LockManager getInstance() {
        if (lockManager == null) {
            try {
                lockManager = new LockManager();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return lockManager;
    }

    public boolean hasLock(Integer lockable, String host) {
        if (lockManager.lockMap.containsKey(lockable)) {
            return lockManager.lockMap.get(lockable).equals(host);
        }
        return false;
    }

    private boolean put(Integer lockable, String host) {
        if (!lockManager.lockMap.containsKey(lockable)) {
            lockManager.lockMap.put(lockable,host);
            return true;
        }
        return false;
    }

    public boolean acquireLock(Integer lockable, String host) {
        boolean lockResult = true;
        if (!hasLock(lockable,host)) {
            lockResult = put(lockable,host);
        }
        return lockResult;
    }

    public boolean releaseLock(Integer lockable) {
        lockManager.lockMap.remove(lockable);
    }
}
