package com.playground.threading.baeldung;

public class ThreadLocalSample implements Runnable {

    private static final ThreadLocal<Context> userContextThreadLocal1 = new ThreadLocal<>();
    private final ThreadLocal<Context> userContextThreadLocal2 = new ThreadLocal<>(){
        @Override
        protected Context initialValue(){
            return new Context();
        }

    };
    private final ThreadLocal<Context> userContextThreadLocal3 = ThreadLocal.withInitial(Context::new);

    private final Integer userId;

    public ThreadLocalSample(final Integer userId){
        this.userId= userId;
    }

    public static class Context {
        private String userName;
        public Context() {

        }
        public Context(String userName) {
            this.userName = userName;
        }
        public String getUserName(){
            return this.userName;
        }
        public void setUserName(String userName){
            this.userName = userName;
        }
    }

    @Override
    public void run(){
        String userName = this.userId== 1? "Tom" : "Harry";
        userContextThreadLocal1.set(new Context(userName));
        this.userContextThreadLocal2.get().userName = userName;

        System.out.println("thread context 1 for given userId: "+ this.userId + " is: " + userContextThreadLocal1.get().getUserName());
        System.out.println("thread context 2 for given userId: "+ this.userId + " is: " + this.userContextThreadLocal2.get().getUserName());
    }

    public static void main(final String[] args){
        ThreadLocalSample threadLocalExample1 = new ThreadLocalSample(1);
        ThreadLocalSample threadLocalExample2 = new ThreadLocalSample(2);

        new Thread(threadLocalExample1).start();
        new Thread(threadLocalExample2).start();

    }
}
