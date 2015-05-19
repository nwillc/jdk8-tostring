package com.github.nwillc.tostring;

import com.github.nwillc.contracts.UtilityClassContract;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldAccessorTest extends UtilityClassContract {
    private Sample instance;

    @Override
    protected Class<?> getClassToTest() {
        return FieldAccessor.class;
    }

    @Before
    public void setUp() throws Exception {
        instance = new Sample("foo", 5);
    }

    @Test
    public void testGetObject() throws Exception {
        Map.Entry entry = FieldAccessor.get(instance, "str");
        assertThat(entry).isNotNull();
        assertThat(entry.getKey()).isEqualTo("str");
        assertThat(entry.getValue()).isEqualTo("foo");
    }

    @Test
    public void testPrimitive() throws Exception {
        Map.Entry entry = FieldAccessor.get(instance, "x");
        assertThat(entry).isNotNull();
        assertThat(entry.getKey()).isEqualTo("x");
        assertThat(entry.getValue()).isEqualTo(5);
    }

    @Test
    public void testFieldStream() throws Exception {
        Stream fields = FieldAccessor.get(instance);
        assertThat(fields).isNotNull();
        Object[] entries = fields.toArray();
        assertThat(entries.length).isEqualTo(2);
        assertThat(entries).haveAtLeastOne(new EntryMapCondition("str", "foo"));
        assertThat(entries).haveAtLeastOne(new EntryMapCondition("x", 5));
    }

    private static class EntryMapCondition extends Condition<Object> {
        final String key;
        final Object value;

        public EntryMapCondition(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean matches(Object o) {
            Map.Entry<String,Object> entry = (Map.Entry<String, Object>) o;
            return entry.getKey().equals(key) && entry.getValue().equals(value);
        }
    }
    private static class Sample {
        private final String str;
        private final int x;

        public Sample(String str, int x) {
            this.str = str;
            this.x = x;
        }
    }
}