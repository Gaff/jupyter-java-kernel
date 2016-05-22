package net.steelcog.jupyter.kernel;

import info.sample.FoobarValue;
import info.sample.ImmutableFoobarValue;

import java.util.List;

/**
 * Created on 22-May-16.
 */
public class HelloWorld {
    public static void main(String argv[]) {
        System.out.println("Hello world\n");
        FoobarValue fbv;
        FoobarValue value = ImmutableFoobarValue.builder()
                .foo(2)
                .bar("Bar")
                .addBuz(1, 3, 4)
                .build(); // FoobarValue{foo=2, bar=Bar, buz=[1, 3, 4], crux={}}

        int foo = value.foo(); // 2

        List<Integer> buz = value.buz(); // ImmutableList.of(1, 3, 4)
    }
}
