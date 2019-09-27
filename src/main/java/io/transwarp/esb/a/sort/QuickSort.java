package io.transwarp.esb.a.sort;

import io.transwarp.esb.a.BaseUtil;

/*
 * 快速排序
 * 最佳O(nlogn)  最差O(n平方)
 * 稳定的
 */
public class QuickSort {
    public static int[] quickSortTest(int[] data) {
        if (data.length == 0) {
            return data;
        }
        if (data.length == 1) {
            return data;
        }
        int start = 0;
        int end = data.length-1;
        if (start<end){
            int smallIndex = getIndex(data,start,end);
            getIndex(data,start,smallIndex-1);
            getIndex(data,smallIndex+1,end);
        }
        return data;
    }
    public static int getIndex(int[] data,int start,int end){
        int temp = data[start];
        while (start<end){
            while (start<end && data[end]>= temp){
                end--;
            }
            data[start] = data[end];
            while (start<end && data[start]<=temp){
                start++;
            }
            data[end] = data[start];
            BaseUtil.printData(data,start);
        }
        data[start] = temp;
        return start;
    }

    public static void main(String[] args) {
        int[] data = {1, 5, 8, 3, 4, 2, 8};
        int[] dataAfterSort = quickSortTest(data);
        for (int i=0;i<dataAfterSort.length;i++){
            System.out.print(dataAfterSort[i]+"  ");
        }
    }
}
