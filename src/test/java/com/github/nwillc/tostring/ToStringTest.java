package com.github.nwillc.tostring;

import com.github.nwillc.contracts.UtilityClassContract;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ToStringTest extends UtilityClassContract {
    Sample sample1;
    Sample sample2;

    @Override
    protected Class<?> getClassToTest() {
        return ToString.class;
    }

    @Before
    public void setUp() throws Exception {
        sample1 = new Sample("fred", 5, 1.3, false);
        sample2 = new Sample(null, 0, 1.2, true);

    }

    @Test
    public void testFields() throws Exception {
        assertThat(ToString.toString(sample1)).isEqualTo("Sample{ name='fred', count=5, weight=1.3, flag=false }");
        assertThat(ToString.toString(sample2)).isEqualTo("Sample{ name=null, count=0, weight=1.2, flag=true }");
    }

    @Test
    public void testNonNullFields() throws Exception {
        assertThat(ToString.toStringNoNulls(sample2)).isEqualTo("Sample{ count=0, weight=1.2, flag=true }");
    }
    
    static class Sample {
        final String name;
        final int count;
        final double weight;
        final Boolean flag;

        public Sample(String name, int count, double weight, boolean flag) {
            this.name = name;
            this.count = count;
            this.weight = weight;
            this.flag = flag;
        }
    }
}