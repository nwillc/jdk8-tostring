package com.github.nwillc.tostring;

import com.github.nwillc.contracts.UtilityClassContract;
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
        assertThat(fields.count()).isEqualTo(2);
    }

    private final static class Sample {
        private final String str;
        private final int x;

        public Sample(String str, int x) {
            this.str = str;
            this.x = x;
        }
    }
}