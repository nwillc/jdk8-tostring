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


import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.Map.Entry;

/**
 * Utility class providing, via reflection, access to fields of an object.
 */
public final class FieldAccessor {
    private static final Logger LOG = Logger.getLogger(FieldAccessor.class.getName());

    private FieldAccessor() {}

    /**
     * Via reflection, return all the fields declared by the object provided.
     * @param instance the object to list the fields of
     * @return a stream of name value pairs of the fields and their values
     */
    public static Stream<Entry<String, ?>> getFields(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        return Stream.of(fields).map(f -> toEntry(instance, f));
    }

    /**
     * Retrieve a specified fields value.
     * @param instance the object to retrieve the field from
     * @param fieldName the name of the field to retrieve
     * @return a name value pair of the field and its value
     * @throws NoSuchFieldException if field is not declared by the object
     */
    public static Entry<String, ?> getField(Object instance, String fieldName) throws NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        return toEntry(instance, field);
    }

    static Entry<String, ?> toEntry(Object instance, Field field) {
        field.setAccessible(true);
        try {
            return new AbstractMap.SimpleEntry<>(field.getName(), field.get(instance));
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "Unable to access field " + field.getName(), e);
        }
        return null;
    }

}
