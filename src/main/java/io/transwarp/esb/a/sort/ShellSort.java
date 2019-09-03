package io.transwarp.esb.a.sort;
/*
 *希尔排序
 * 最佳O(nlogn平方)  最差O(nlogn平方)
 * 不稳定的
 */
public class ShellSort {
    public static int[] shellSortTest(int[] data){
        if (data.length == 0){
            return data;
        }
        if (data.length == 1){
            return data;
        }

        return data;

    }
    public static void main(String[] args){
        int[] data = {1,5,8,3,4,2,8};
        int[] dataAfterSort = shellSortTest(data);
    }
}
