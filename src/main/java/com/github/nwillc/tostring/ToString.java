package com.github.nwillc.tostring;

import java.util.Map;
import java.util.stream.Stream;

public final class ToString {
    private ToString() {
    }

    static Stream<String> fields(Object instance) {
        return FieldAccessor.get(instance).map(ToString::toString);
    }

    static Stream<String> nonNullFields(Object instance) {
        return FieldAccessor.get(instance).filter(entry -> entry.getValue() != null).map(ToString::toString);
    }

    private static String toString(Map.Entry entry) {
        String value;

        if (entry.getValue() != null && entry.getValue().getClass().isAssignableFrom(String.class)) {
            value= "'" + entry.getValue() + "'";
        } else {
            value = String.valueOf(entry.getValue());
        }

        return entry.getKey() + "=" + value;
    }
}
