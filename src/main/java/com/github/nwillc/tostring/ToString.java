package com.github.nwillc.tostring;

import java.util.Map;
import java.util.stream.Stream;

public final class ToString {
    private ToString() {
    }

    static String toString(Object instance) {
        return instance.getClass().getSimpleName()
                + "{ "
                + commaAppend(FieldAccessor.get(instance).map(ToString::toString))
                + " }";
    }

    static String toStringNoNulls(Object instance) {
        return instance.getClass().getSimpleName()
                + "{ "
                + commaAppend(FieldAccessor.get(instance).filter(entry -> entry.getValue() != null).map(ToString::toString))
                + " }";
    }

    private static String commaAppend(Stream<String> strings) {
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
