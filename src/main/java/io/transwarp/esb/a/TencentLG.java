package io.transwarp.esb.a;

import java.util.Scanner;

/**
 * @author wy
 * @description
 * @date 2019/9/27 10:15
 */

// 八大排序算法
public class TencentLG {

    // 二分查找函数
    public static int searchBin(int arr[],int key){
        if(arr == null)         // 输入数组异常
            return -1;
        int low = 0;            // 高位
        int high = arr.length-1;// 低位
        while(low<=high){       // low 大于 high 才结束
            int mid = (high-low)/2 + low;   //  优化部分
            if(arr[mid] == key) // 找到
                return mid;
            else if(arr[mid] > key)  // 左边
                high = mid-1;
            else
                low = mid+1;   // 右边
        }
        return -1;    //  推出循环表示找不到改值
    }

    // 1、快排序的循环部分
    public static void quickSort(int arr[],int low, int high){
        if(low>=high || arr==null)           // 输入异常
            return ;
        int index = partition(arr,low,high); // 第一次划分
        quickSort(arr,low,index-1);          // 对划分的结果左边进行快排序
        quickSort(arr,index+1,high);         // 对划分的结果右边进行快排序
    }

    // 快排的每次划分部分
    public static int partition(int arr[], int low ,int high){
        int key = arr[low] ;      // 划分的关键字值，采用取最低位，也可以取low-high之间的随机数，或者中间值
        while(low<high){          // low < high 保证有两个及以上元素
            while(low<high && arr[high]>=key)
                high--;
            arr[low] = arr[high];
            while(low<high && arr[low]<=key)
                low++;
            arr[high] = arr[low];
        }
        arr[low] = key;
        return low;    // 每次划分的中点坐标
    }

    // 2、归并排序
    public static void mergeSort(int arr[],int low ,int high){
        if(arr==null || low>=high)
            return;
        int mid = (high-low)/2 + low;
        mergeSort(arr,low,mid);
        mergeSort(arr,mid+1,high);
        merge(arr,low,mid,high);
    }

    // 归并排序的合并部分
    public static void merge(int arr[],int low,int mid,int high){
        int temp[] = new int [arr.length];   //  归并后的临时数组
        int i = mid;   // 初始化，一个指针指向第一个数组的最后一个位置
        int j = high;  // 初始化，一个指针指向第一个数组的最后一个位置
        int k = high;  // 初始化，一个指针指向临时数组的最后一个位置
        while(i>=low && j>= mid+1)
            if(arr[i]>=arr[j])
                temp[k--] = arr[i--];
            else
                temp[k--] = arr[j--];

        while(i>=low)
            temp[k--] = arr[i--];
        while(j>=mid+1)
            temp[k--] = arr[j--];

        for(i=low;i<=high;i++)
            arr[i] = temp[i];
    }

    // 3、希尔排序
    public static void shellSort(int arr[]){
        System.out.println("开始希尔排序");
        if(arr == null)       // 输入异常
            return ;
        int len = arr.length; // 数组长度
        for(int k=len/2; k>0; k/=2)    // 希尔排序的每次的步长
            for(int i=k ;i<len;i++)    // 从右往左开始循环
                for(int j=i-k; j>=0 && arr[j]>arr[j+k]; j=j-k){   // 从高位向低位开始交换，直到排序正常为止
                    int temp = arr[j];
                    arr[j] = arr[j+k];
                    arr[j+k] = temp;
                }
    }

    // 调整堆, 需要用到递归方式
    public static void  adjustHeap(int arr[],int begin,int end){
        int left = begin*2+1;   // 左孩子索引
        int right = begin*2+2;  // 右孩子索引
        int index = begin;      // 三个结点中最大值的索引，建立的是大根堆

        if(left<end && arr[index]<arr[left])     // 找到三个结点中最大值所在索引
            index = left;
        if(right<end && arr[index]<arr[right])
            index = right;

        if(index != begin){     // 不是原来位置则交换
            int temp = arr[index];
            arr[index] = arr[begin];
            arr[begin] = temp;
            adjustHeap(arr,index,end);    // 需要继续递归向下调整
        }
    }

    // 4、堆排序
    public static void heapSort(int arr[]){
        System.out.println("开始堆排序");
        if(arr==null)   // 异常输入
            return ;
        int len = arr.length;
        // 建大顶堆
        for(int i=len/2-1;i>=0;i--)
            adjustHeap(arr,i,len);

        // 调整堆，原地调整，每次将堆顶结点与末端位置交换（类似一趟冒泡排序），然后重新调整堆
        for(int i=len-1;i>0;i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            adjustHeap(arr,0,i);     // 重新从根结点向下调整时，只需要调整到上一次排好序的前一个位置
        }
    }

    // 5、冒泡排序 及 改进
    public static void bubbleSort(int arr[]){
        System.out.println("开始冒泡排序");
        if(arr == null)
            return;
        int len = arr.length;
        for(int i=1;i<len;i++){   // 需要循环的次数
            boolean flag = true;      // 标志每次是否有数据修改
            for(int j=0;j<len-i;j++)
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = false;
                }
            // 一次冒泡结束后，若没有数据交换，表示排序结束
            System.out.println("第"+i+"循环");
            if(flag)
                return;
        }
    }

    // 6、选择排序
    public static void chooseSort(int arr[]){
        System.out.println("开始选择排序");
        if(arr == null)
            return;
        int len = arr.length;
        for(int i=0;i<len-1;i++){  // 需要进行的选择次数
            int small = i;
            for(int j=i+1;j<len;j++)
                if(arr[j] < arr[small])
                    small = j;
            int temp = arr[small];
            arr[small] = arr[i];
            arr[i] = temp;
        }
    }

    // 7、插入排序  --- 精简版
    public static void insertSort11(int arr[]){
        System.out.println("开始插入排序");
        if(arr == null)
            return;
        int len = arr.length;
        for(int i=1;i<len;i++){  // 从下标为1开始向前插入，直到数组末尾
            int k = i-1;
            int key = arr[i];  //  将当前值存储下来
            while(k>=0 && arr[k] > key ) // 向前找到 插入位置
                k--;
            for(int j=i;j>k+1;j--)    // 逐个向后移动
                arr[j] = arr[j-1];
            arr[k+1] =  key;
        }
    }

    // 7、插入排序
    public static void insertSort(int arr[]){
        System.out.println("开始插入排序");
        if(arr == null)     // 输入异常
            return;
        int len = arr.length;
        for(int i=1;i<len;i++){    // 循环次数len - 1
            int j = i,temp = arr[i];
            int k=j-1;
            while(k>=0 && arr[j]<arr[k]) // 找不小于当前值第位置，准备插入
                k--;

            for(int m=j-1;m>k;m--)      // 向后移动 准备插入
                arr[m+1] = arr[m];

            arr[k+1] = temp;  // 插入值
        }
    }

    // 主函数用于处理循环输入部分
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // Java 怎么输入一个大小不知道的数组,使用链表接收数组
        while(scan.hasNextLine()){
            String str = scan.nextLine();
            String strs[] = str.split(" ");
            int len = strs.length;
            int arr[] = new int[len];
            for (int i = 0; i < len; i++)
                arr[i] = Integer.valueOf(strs[i]);

            //quickSort(arr, 0, len - 1);
            //mergeSort(arr, 0, len - 1);
            //shellSort(arr);
            //heapSort(arr);
            //bubbleSort(arr);
            //chooseSort(arr);
            //insertSort(arr);
            insertSort11(arr);
            //List<Integer> list = new ArrayList<Integer>();
            //Collections.sort(list);
            //Arrays.sort(arr);
            //Arrays.sort( arr);
            for (int i = 0; i < len; i++)
                System.out.print(arr[i]+"  ");
            System.out.println();
            for (int i = 0; i < 10; i++)
                System.out.println(i+" -location："+searchBin(arr, i));
        }
    }

}
