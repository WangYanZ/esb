package io.transwarp.esb.youzan;


import java.util.Scanner;

public class Main2 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(fun(n));

    }
    public static int fun(int n){
        int max = (int)Math.pow(2,n-1);
        return max;

    }
}
