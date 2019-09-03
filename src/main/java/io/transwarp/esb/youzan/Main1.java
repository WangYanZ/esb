package io.transwarp.esb.youzan;

import java.util.Scanner;

public class Main1 {
    public static boolean isFlag(int n){
        if (n==2 || n==3){
            return true;
        }
        for (int i=2;i<Math.sqrt(n);i++){
            if (n%i == 0)
                return false;
        }
        return true;
    }
    public static void fun(int n){
        int count = 0;
        if (2 == n  || 3 == n){
            System.out.println(1);
            return;
        }
        int N = n;
        for (int i=2;i<=N&& n!=1;){
            if (isFlag(i)&& n%i == 0){
                count++;
                n=n/i;
            }else
                i++;
        }
        System.out.println(count);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int n=scanner.nextInt();
            fun(n);
        }

    }
}
