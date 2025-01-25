package com.playground.core_java.streams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.*;

public class StreamCreation {

    public void createStreams(){

        Stream<String> streamOfArray = Stream.of("a", "b", "c");

        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);

        Stream<String> streamBuilder = Stream.<String>builder().add("a").add("b").add("c").build();

        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
    }

    public void primitiveStreams(){
        IntStream.generate(()->1).limit(10);
        LongStream longStream = LongStream.rangeClosed(1, 3);

        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
    }

    public void fileStream() throws IOException {
        Path path = Paths.get("C:\\file.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        Stream<String> streamWithCharset =  Files.lines(path, Charset.forName("UTF-8"));
    }

    public List<String> getEvenIndexedStrings(String[] names) {
        return IntStream.range(0, names.length).filter(i -> i % 2 == 0).mapToObj(i -> names[i]).collect(Collectors.toList());
    }

    public void streamSupport(){
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");
        StreamSupport.stream(iterable.spliterator(), false);


    }


}
