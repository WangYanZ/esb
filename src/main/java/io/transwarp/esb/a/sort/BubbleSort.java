package io.transwarp.esb.a.sort;

import io.transwarp.esb.a.BaseUtil;

public class BubbleSort {
    /*
    *冒泡排序
    * 最佳O(n)  最差O(n平方)
    * 稳定的
    */

    public static int[] bubbleSortTest(int[] data){
        if (data.length == 0){
            return data;
        }
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data.length-1-i;j++){
                if (data[j]>data[j+1]){
                    BaseUtil.swap(data,j,j+1);
                }
            }
            BaseUtil.printData(data,i);
        }
        return data;
    }
    public static int[] bubbleSortTestBest(int[] data){
        if (data.length == 0){
            return data;
        }
        boolean flag;
        for (int i=0;i<data.length;i++){
            flag = false;
            for (int j=0;j<data.length-1-i;j++){
                if (data[j]>data[j+1]){
                    BaseUtil.swap(data,j,j+1);
                    flag = true;
                }
            }
            if (flag == false){
                BaseUtil.printData(data,i);
                return data;
            }
            BaseUtil.printData(data,i);
        }
        return data;
    }

    public static void main(String[] args){
        int[] data = {1,2,4,6,7,8,8};
        int[] dataAfterSort = bubbleSortTestBest(data);
    }
}
