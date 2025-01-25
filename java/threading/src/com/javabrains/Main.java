package com.javabrains;

public class Main {



    static A_PrimeNumberWithNoThreading a_PrimeNumberWithNoThreading = new A_PrimeNumberWithNoThreading();
    static B_PrimeNumberWithThreading b_PrimeNumberWithThreading = new B_PrimeNumberWithThreading();
    static C_PrimeNumberWithDaemonThreading c_PrimeNumberWithDaemonThreading = new C_PrimeNumberWithDaemonThreading();
    static D_ThreadStates d_ThreadStates = new D_ThreadStates();
    static E_ThreadJoin d_ThreadJoin = new E_ThreadJoin();
    static F_Synchronized f_Synchronized = new F_Synchronized();
    static H_PrimeNumberWithThreadPool h_PrimeNumberWithThreadPool = new H_PrimeNumberWithThreadPool();
    static J_PrimeNumberWithCallable j_PrimeNumberWithCallable = new J_PrimeNumberWithCallable();
    static K_PrimeNumberWithCompleteableFuture k_PrimeNumberWithCompleteableFuture = new K_PrimeNumberWithCompleteableFuture();
    static L_PrimeNumberWithForkJoin l_PrimeNumberWithForkJoin = new L_PrimeNumberWithForkJoin();

    public static void main(String[] args) {
        //a_PrimeNumberWithNoThreading.doCalculate();
        //b_PrimeNumberWithThreading.doCalculate();
        //c_PrimeNumberWithDaemonThreading.doCalculate();
        //d_ThreadStates.doCalculate();
        //f_Synchronized.doCalculate();
        //h_PrimeNumberWithThreadPool.doCalculate();
        //j_PrimeNumberWithCallable.doCalculate();
        //k_PrimeNumberWithCompleteableFuture.doCalculate();
        l_PrimeNumberWithForkJoin.doCalculate();
    }


}
