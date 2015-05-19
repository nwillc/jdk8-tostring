package com.github.nwillc.tostring;

import com.github.nwillc.contracts.UtilityClassContract;
import org.junit.Before;
import org.junit.Test;

public class ToStringTest extends UtilityClassContract {
    Sample sample;

    @Override
    protected Class<?> getClassToTest() {
        return ToString.class;
    }

    @Before
    public void setUp() throws Exception {
        sample = new Sample(null, 0, 1.2);

    }

    @Test
    public void testFields() throws Exception {
        ToString.fields(sample).forEach(System.out::println);
        ToString.nonNullFields(sample).forEach(System.out::println);
    }

    static class Sample {
        final String name;
        final int count;
        final double weight;
        final Boolean flag = Boolean.FALSE;

        public Sample(String name, int count, double weight) {
            this.name = name;
            this.count = count;
            this.weight = weight;
        }
    }
}