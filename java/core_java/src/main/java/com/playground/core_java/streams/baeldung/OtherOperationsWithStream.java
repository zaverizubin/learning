package com.playground.core_java.streams.baeldung;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OtherOperationsWithStream {



    //Char Streams
    public void test(){
        String testString = "String";
        Stream<Object> objStream1 =  testString.chars().mapToObj(c -> (char) c);

        Stream<Object> objStream2 =  testString.codePoints().mapToObj(c -> (char) c);
    }

    //Optionals and streams
    List<Optional<String>> listOfOptionals = Arrays.asList(Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));

    public void filterEmptyOptionals(){
        this.listOfOptionals.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        this.listOfOptionals.stream().flatMap(Optional::stream).collect(Collectors.toList());
    }

    //Infinite streams
    public void infiniteStream(){
        Stream<UUID> infiniteStreamOfRandomUUID = Stream.generate(UUID::randomUUID);
        infiniteStreamOfRandomUUID.skip(10).limit(5).collect(Collectors.toList());

        Stream.iterate(0, i->i+1).limit(10).forEach(System.out::println);
    }

    //Iterable with streams
    public void iterableToStream(){
        Iterable<String> iterable =  Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");
        List<String> result = StreamSupport.stream(iterable.spliterator(), false).map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public void getLastElementFromStream(){
        List<String> valueList = new ArrayList<>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");

        valueList.stream().skip(valueList.size()-1).findFirst().get();
    }

    //merging streams
    public void merginStreams(){
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 13);
        Stream<Integer> stream4 = Stream.of(99);

        Stream<Integer> resultingStream1 =  Stream.concat(stream1, stream2);

        Stream<Integer> resultingStream2 = Stream.of(stream1, stream2, stream3, stream4).flatMap(i -> i);


    }

    //String operation with stream
    public List<String> getEvenIndexedStrings(String[] names) {
        return IntStream.range(0, names.length).filter(i -> i % 2 == 0).mapToObj( i -> names[i]).collect(Collectors.toList());
    }

    //Primitive streams
    int[] integers = new int[] {20, 98, 12, 7, 35};
    int min = Arrays.stream(this.integers).min().getAsInt();

    int max = IntStream.of(20, 98, 12, 7, 35).max().getAsInt();
    double avg = IntStream.of(20, 98, 12, 7, 35).average().getAsDouble();
    int sum1 = IntStream.rangeClosed(1, 10).sum();
    int sum2 = Arrays.asList(33,45).stream().mapToInt(i -> i).sum();
}
