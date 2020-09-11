package util;

import java.util.ArrayList;

public class UnitOfWork {
    private static UnitOfWork instance;
    private ArrayList<Object> newObjectList = new ArrayList<Object>();
    private ArrayList<Object> dirtyObjectList = new ArrayList<Object>();
    private ArrayList<Object> deletedObjectList = new ArrayList<Object>();

    /**
     *
     * @return
     */
    public static UnitOfWork getInstance() {
        if (instance == null) {
            try {
                instance = new UnitOfWork();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     *
     * @param object
     */
    public void registerDirtyObject(Object object) {
        if (newObjectList.contains(object) || dirtyObjectList.contains(object)
        || deletedObjectList.contains(object)) {
            return;
        }
        dirtyObjectList.add(object);
    }

    /**
     *
     * @param object
     */
    public void registerNewObject(Object object) {
        if (newObjectList.contains(object) || dirtyObjectList.contains(object)
                || deletedObjectList.contains(object)) {
            return;
        }
        newObjectList.add(object);
    }

    /**
     *
     * @param object
     */
    public void registerDeletedObject(Object object) {
        if(newObjectList.remove(object)) {
            return;
        }
        dirtyObjectList.remove(object);
        if(!deletedObjectList.contains(object)) {
            deletedObjectList.add(object);
        }
    }

    public void commit() {
    }
}
