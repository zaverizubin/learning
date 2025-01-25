package com.playground.threading.baeldung;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompletableFutures {

    public void calculateAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        System.out.println(completableFuture.get());
    }

    private void runAsync() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });
        Thread.sleep(5000);
        System.out.println("I'm done");
    }

    private void supplyAsync(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "I'll run in a separate thread than the main thread.";
        });
        System.out.println(future.getNow(""));
    }

    private void supplyAsyncWithThreadPool() throws ExecutionException, InterruptedException {
        Executor executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "I'll run in a separate thread than the main thread.";
        }, executorService);
        System.out.println(future.get());
    }

    private void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hello";
        }).thenApply(name -> name + " World").thenApply(name -> name + " is here");
        System.out.println(future.get());
    }

    private void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hello";
        });
        future.thenAccept(name -> System.out.println(name + "World"));
        future.join();
    }

    private void thenApplyAsync() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Some result";
        }).thenApplyAsync(result -> {
            // Executed in a thread obtained from the executor
            return "Processed Result";
        }, executor);
        future.join();

        System.out.println(future.get());
    }

    private CompletableFuture<String> getUserDetail() {
        return CompletableFuture.supplyAsync(() -> "User Details ");
    }

    private CompletableFuture<String> getCreditRating(String user) {
        return CompletableFuture.supplyAsync(() -> user + 5d);
    }

    private void thenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<CompletableFuture<String>> result = getUserDetail().thenApply(this::getCreditRating);
        CompletableFuture<CompletableFuture<String>> future = result.whenComplete((res, exp) -> {
            try {
                System.out.println(res.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        future.join();
    }

    private CompletableFuture<String> downloadWebPage(String url){
        return  CompletableFuture.supplyAsync(() -> url);
    }

    private void allOff() throws ExecutionException, InterruptedException {
        List<String> webPageLinks = Arrays.asList("www.google.com","www.bing.com");

        List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
                .map(this::downloadWebPage).collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                pageContentFutures.toArray(new CompletableFuture[0])
        );

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
        CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v ->
             pageContentFutures.stream().map(CompletableFuture::join).collect(Collectors.toList())
        );

        // Count the number of web pages having the "CompletableFuture" keyword.
        CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents ->
             pageContents.stream().filter(pageContent -> pageContent.contains("CompletableFuture")).count()
        );

        System.out.println("Number of Web Pages having CompletableFuture keyword - " + countFuture.get());
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutures completableFutures = new CompletableFutures();
        completableFutures.runAsync();

    }
}
