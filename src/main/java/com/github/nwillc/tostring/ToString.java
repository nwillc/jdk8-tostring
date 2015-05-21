/*
 * Copyright (c) 2015, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.tostring;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.github.nwillc.tostring.FieldAccessor.getFields;

public final class ToString {
    private ToString() {
    }

    static String toString(Object instance) {
        return wrap(instance, getFields(instance));
    }

    static String toString(Object instance, String ... fields) {
        return wrap(instance, getFields(instance)
                .filter(entry -> Stream.of(fields).anyMatch(field -> entry.getKey().equals(field))));
    }

    static String toStringExcluding(Object instance, String ... fields) {
        return wrap(instance, getFields(instance)
                .filter(entry -> Stream.of(fields).noneMatch(field -> entry.getKey().equals(field))));
    }

    private static String wrap(Object instance, Stream<Map.Entry<String,?>> entries) {
        return instance.getClass().getSimpleName() + "{ " + entries.map(ToString::toString).collect(Collectors.joining(", ")) + " }";
    }

    private static String toString(Map.Entry<String,?> entry) {
        String value;

        if (entry.getValue() != null && entry.getValue().getClass().isAssignableFrom(String.class)) {
            value= "'" + entry.getValue() + "'";
        } else {
            value = String.valueOf(entry.getValue());
        }

        return entry.getKey() + "=" + value;
    }
}
