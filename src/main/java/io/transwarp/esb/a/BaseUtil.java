package io.transwarp.esb.a;

public class BaseUtil {
    public static void printData(int[] data,int i){
        System.out.print("step"+i+":  ");
        for (int k=0;k<data.length;k++){
            System.out.print(data[k]+"  ");
        }
        System.out.println();
    }
    public static void swap(int[] data,int k,int j){
        int temp;
        temp = data[k];
        data[k] = data[j];
        data[j] = temp;
    }
}
