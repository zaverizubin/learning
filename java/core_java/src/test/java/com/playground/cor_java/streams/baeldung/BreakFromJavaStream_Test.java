package com.playground.cor_java.streams.baeldung;

import com.playground.core_java.streams.baeldung.BreakFromJavaStream;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BreakFromJavaStream_Test {

    @Test
    public void whenCustomTakeWhileIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream =
                Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");

        List<String> result =
                BreakFromJavaStream.customTakeWhile(initialStream, x -> x.length() % 2 != 0)
                        .collect(Collectors.toList());

        assertEquals(Arrays.asList("cat", "dog"), result);
    }
}
