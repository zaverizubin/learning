package com.playground.core_java.streams.baeldung;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BreakFromJavaStream {

    public void takeWhile(){
        Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck").takeWhile(s -> s.length() % 2 !=0).forEach(System.out::println);

    }

    public static <T> Stream<T> customTakeWhile(Stream<T> stream, Predicate<T> predicate) {
        CustomSpliterator<T> customSpliterator = new CustomSpliterator<>(stream.spliterator(), predicate);
        return StreamSupport.stream(customSpliterator, false);
    }

    public static class CustomSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

        private Spliterator<T> splitr;
        private Predicate<T> predicate;
        private boolean isMatched = true;

        public CustomSpliterator(Spliterator<T> splitr, Predicate<T> predicate) {
            super(splitr.estimateSize(), 0);
            this.splitr = splitr;
            this.predicate = predicate;
        }

        @Override
        public synchronized boolean tryAdvance(Consumer<? super T> consumer) {
            boolean hadNext = this.splitr.tryAdvance(elem -> {
                if (this.predicate.test(elem) && this.isMatched) {
                    consumer.accept(elem);
                } else {
                    this.isMatched = false;
                }
            });
            return hadNext && this.isMatched;
        }
    }

}
