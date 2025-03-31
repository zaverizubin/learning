package com.playground.core_java.streams;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class JavaStreamGroupingCollectors {
    class BlogPost {
        String title;
        String author;
        BlogPostType type;
        int likes;

        public BlogPost(final String title, final String author, final BlogPostType type, final int likes) {
            this.title = title;
            this.author = author;
            this.type = type;
            this.likes = likes;
        }

        public String getTitle() {
            return this.title;
        }

        public String getAuthor() {
            return this.author;
        }

        public BlogPostType getType() {
            return this.type;
        }

        public int getLikes() {
            return this.likes;
        }
    }

    enum BlogPostType {
        NEWS,
        REVIEW,
        GUIDE
    }


    private final BlogPost[] blogPosts = {new BlogPost("Blog Post 1", "Author 1", BlogPostType.NEWS,5),
            new BlogPost("Blog Post 1", "Author 1", BlogPostType.NEWS,10),
            new BlogPost("Blog Post 2", "Author 2", BlogPostType.NEWS,5),
            new BlogPost("Blog Post 2", "Author 3", BlogPostType.REVIEW,8),
            new BlogPost("Blog Post 3", "Author 4", BlogPostType.REVIEW,5),
            new BlogPost("Blog Post 4", "Author 5", BlogPostType.GUIDE,16)};


    public Map<BlogPostType, List<BlogPost>> groupBy(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType));
    }

    public Map<BlogPostType, Set<BlogPost>> groupingByReturnValueAsSet(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, toSet()));
    }

    public Map<Pair<BlogPostType, String>, List<BlogPost>> groupByComplexKey(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(blogPost -> new ImmutablePair<>(blogPost.getType(), blogPost.getAuthor())));
    }

    public Map<String, Map<BlogPostType, List<BlogPost>>> multipleGroupBy(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getAuthor, Collectors.groupingBy(BlogPost::getType)));
    }

    public Map<BlogPostType, Integer> groupByWithSum(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, summingInt(BlogPost::getLikes)));
    }

    public Map<BlogPostType, Double> groupByWithAverage(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, averagingInt(BlogPost::getLikes)));
    }

    public Map<BlogPostType, Optional<BlogPost>> groupByWithMax(){
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, Collectors.maxBy(Comparator.comparingInt(BlogPost::getLikes))));
    }

    public Map<BlogPostType, IntSummaryStatistics> groupingWithSummaryAttributes() {
         return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, summarizingInt(BlogPost::getLikes)));
    }

    public Map<BlogPostType, String> groupingWithValueAsString() {
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType,
                Collectors.mapping(BlogPost::getTitle, Collectors.joining(", ", "Post title(", ")"))));
    }

    public Map<BlogPostType, List<BlogPost>> groupingToReturnEnumMap() {
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getType, () -> new EnumMap<>(BlogPostType.class), toList()));
    }

    public Map<String, Set<BlogPost>> groupByWithFiltering() {
        return Stream.of(this.blogPosts).collect(Collectors.groupingBy(BlogPost::getAuthor, Collectors.filtering(e -> e.getLikes() > 100, Collectors.toSet())));
    }

    public Map<Boolean, List<BlogPost>> partitionBy() {
        return Stream.of(this.blogPosts).collect(Collectors.partitioningBy(blogPost -> blogPost.getLikes()> 10));
    }

    public Collector<BlogPost, ?, LinkedList<BlogPost>> toLinkedList = Collector.of(LinkedList::new, LinkedList::add, (first, second) -> {
                                                                                    first.addAll(second);
                                                                                    return first;
                                                                                });
    LinkedList<BlogPost> linkedListOfBlogPost =  Stream.of(this.blogPosts).collect(this.toLinkedList);
}
