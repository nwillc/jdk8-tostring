package com.github.nwillc.tostring;

import com.github.nwillc.contracts.UtilityClassContract;
import org.junit.Before;
import org.junit.Test;

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
        assertThat(FieldAccessor.get(instance, "str")).isEqualTo("foo");
    }

    @Test
    public void testPrimitive() throws Exception {
        assertThat(FieldAccessor.get(instance, "x")).isEqualTo(5);
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