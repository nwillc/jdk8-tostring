package com.github.nwillc.tostring;


import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FieldAccessor {
    private static Logger LOG = Logger.getLogger(FieldAccessor.class.getName());

    private FieldAccessor() {
    }

    public static Object get(Object instance, String fieldName) throws NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
           LOG.log(Level.SEVERE,"Unable to access field " + fieldName, e);
        }
        return null;
    }
}
