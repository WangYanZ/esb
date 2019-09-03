package io.transwarp.esb.a.sort;

import io.transwarp.esb.a.BaseUtil;

public class SelectionSort {
    /*
     *选择排序
     * 最佳O(n平方)  最差O(n平方)
     * 不稳定的
     */

    public static int[] selectionSortTest(int[] data){
        if (data.length == 0){
            return data;
        }
        if (data.length == 1){
            return data;
        }
        for (int i=0;i<data.length;i++){
            int minIndex = i;
            for (int j=i+1;j<data.length;j++){
                 if (data[j]<data[minIndex]){
                    minIndex = j;
                }
            }
            BaseUtil.swap(data,minIndex,i);
            BaseUtil.printData(data,i);
        }

        return data;

    }

    public static void main(String[] args){
        int[] data = {1,5,8,3,4,2,8};
        int[] dataAfterSort = selectionSortTest(data);
    }
}
