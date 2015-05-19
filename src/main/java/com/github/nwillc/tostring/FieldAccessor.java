package com.github.nwillc.tostring;


import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Spliterators;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Map.Entry;

public final class FieldAccessor {
    private static Logger LOG = Logger.getLogger(FieldAccessor.class.getName());

    private FieldAccessor() {
    }

    public static Stream<Entry<String, Object>> get(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        return Stream.of(fields).map(f -> toEntry(instance, f));
    }

    public static Entry<String, Object> get(Object instance, String fieldName) throws NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);

        return toEntry(instance, field);
    }

    public static Entry<String, Object> toEntry(Object instance, Field field) {
        field.setAccessible(true);
        try {
            return new AbstractMap.SimpleEntry<>(field.getName(), field.get(instance));
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "Unable to access field " + field.getName(), e);
        }
        return null;
    }

}
