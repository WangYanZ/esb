package io.transwarp.esb.a.sort;

import io.transwarp.esb.a.BaseUtil;
/*
 *插入排序
 * 最佳O(n)  最差O(n平方)
 * 稳定的
 */
public class InsertionSort {
    public static int[] insertionSortTest(int[] data){
        if (data.length == 0){
            return data;
        }
        if (data.length == 1){
            return data;
        }
        for (int i=1;i<data.length;i++){
            int temp = data[i];
            int lefti = i-1;
            while (lefti>=0 && data[lefti]>temp){
                data[lefti+1] = data[lefti];
                lefti--;
            }
            data[lefti+1] = temp;
            BaseUtil.printData(data,i);
        }
        return data;

    }
    public static void main(String[] args){
        int[] data = {1,5,8,3,4,2,8};
        int[] dataAfterSort = insertionSortTest(data);
    }
}
