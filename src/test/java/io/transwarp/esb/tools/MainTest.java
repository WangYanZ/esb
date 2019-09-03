package io.transwarp.esb.tools;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int[][] array = new int[n][n];
//        for (int i=0;i<n;i++){
//            for (int j=0;j<n;j++){
//                array[i][j] = scanner.nextInt();
//            }
//        }
        int n=3;
        int[][] array = new int[][]{{0,4,0},{4,0,0},{0,0,0}};

        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                System.out.print(array[i][j]+"\t");
            }
        }
        System.out.println();
        int count = douYouCount(n,array);
        System.out.print(count);
    }
    public static int douYouCount(int n,int[][] array){
        int count = n;
        Map<Integer,Integer> result =new HashMap<Integer,Integer>();
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(array[i][j] >= 3){
                    if(result.isEmpty()){
                        count --;
                        result.put(i,1);
                        result.put(j,1);
                    }else{
                        if(!result.containsKey(j)){
                            count--;
                            result.put(j,1);
                        }

                    }
                }
            }
        }
        return count;
    }
}
