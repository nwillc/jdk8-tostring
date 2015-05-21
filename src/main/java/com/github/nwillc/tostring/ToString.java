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
import java.util.stream.Stream;

public final class ToString {
    private ToString() {
    }

    static String toString(Object instance) {
        return wrap(instance, commaAppend(FieldAccessor.get(instance)
                .map(ToString::toString)));
    }

    static String toString(Object instance, String ... fields) {
        return wrap(instance, commaAppend(FieldAccessor.get(instance)
                .filter(entry -> Stream.of(fields).anyMatch(s -> entry.getKey().equals(s)))
                .map(ToString::toString)));
    }

    static String toStringExcluding(Object instance, String ... fields) {
        return wrap(instance, commaAppend(FieldAccessor.get(instance)
                .filter(entry -> Stream.of(fields).noneMatch(s -> entry.getKey().equals(s)))
                .map(ToString::toString)));
    }

    private static String wrap(Object instance, String description) {
        return instance.getClass().getSimpleName() + "{ " + description + " }";
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
