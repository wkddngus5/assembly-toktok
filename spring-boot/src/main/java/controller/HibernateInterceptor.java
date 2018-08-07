package controller;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class HibernateInterceptor extends EmptyInterceptor {
    public static final String AWS_ADDRESS = "https://s3.ap-northeast-2.amazonaws.com/wagltoktok";
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        // do your checks here
        return false;
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        List<String> propertyNameList = Arrays.asList(propertyNames);
        if (propertyNameList.contains("image")) {
            String regex = "([a-z])([A-Z]+)";
            String replacement = "$1_$2";
            int imageIndex = propertyNameList.indexOf("image");
            state[imageIndex] = AWS_ADDRESS + "/uploads/" + entity.getClass().getSimpleName().replaceAll(regex, replacement).toLowerCase() + "/image/" + id + "/" + state[imageIndex];
//            state[imageIndex] = "/uploads/" + state[imageIndex];
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return super.findDirty(entity, id, currentState, previousState, propertyNames, types);
    }


}
