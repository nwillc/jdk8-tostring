package com.github.nwillc.tostring;

import java.util.Map;
import java.util.stream.Stream;

public final class ToString {
    private ToString() {
    }

    static String fields(Object instance) {
        return commaReduce(FieldAccessor.get(instance).map(ToString::toString));
    }

    static String nonNullFields(Object instance) {
        return commaReduce(FieldAccessor.get(instance).filter(entry -> entry.getValue() != null).map(ToString::toString));
    }

    static String commaReduce(Stream<String> strings) {
        return strings.reduce(null, (l, r) -> {
            if (l == null) {
                return r;
            }

            if (r == null) {
                return l;
            }

            return l + ", " + r;
        } );
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
