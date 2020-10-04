package db;

import domain.Exam;
import domain.Question;
import domain.Subject;
import domain.User;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityMap<E>{
    private Map<Integer, E> map = new HashMap<Integer, E>();
    private static Map<Class, IdentityMap> instance = new HashMap<Class, IdentityMap>();

    public static synchronized <E> IdentityMap<E> getInstance(Class c) {
        IdentityMap<E> result = instance.get(c);
        if (result == null) {
            result = new IdentityMap<E>();
            instance.put(c,result);
        }
        return result;
    }

    public static synchronized <E> IdentityMap<E> getInstance(E e) {
        IdentityMap<E> result = instance.get(e.getClass());
        if (result == null) {
            result = new IdentityMap<E>();
            instance.put(e.getClass(),result);
        }
        return result;
    }

    public void put(Integer id, E object) {
        map.put(id, object);
    }

    public E get(Integer id) {
        return map.get(id);
    }

}
