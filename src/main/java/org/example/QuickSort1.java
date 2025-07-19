package org.example;

/**
 * @ClassName:QuickSort1
 * @author: qm
 * @Description:
 * @date:2025-07-17
 */
public class QuickSort1 {
    //数组元素交换
    private static void swap(int[] arr,int left,int right){
        if(left==right){
            return;
        }
        if(left<0){
            return;
        }
        if(right>=arr.length){
            return;
        }
        int t = arr[left];
        arr[left] = arr[right];
        arr[right] = t;
    }

    private static int calculateMiddleIndex(int[] arr, int left, int right){
        int middleValue = arr[right];
        //比中间值小的索引
        int index = left-1;
        //筛选，比中间值小的放左边，比中间值大的放右边
        for (int i = left; i < right; i++){
            if(arr[i]<=middleValue){
                index++;
                swap(arr,index,i);
            }
        }
        //将中间值放到中间
        swap(arr,index+1,right);
        return index+1;
    }

    private static void quickSort(int[] arr, int left, int right) {
        int middleIndex = calculateMiddleIndex(arr,left,right);
        if(middleIndex-1>left){
            quickSort(arr,left,middleIndex-1);
        }
        if(middleIndex< right-1){
            quickSort(arr,middleIndex+1,right);
        }
    }

    public static void main(String[] aaa){
        int[] arr = {5,4,3,2,1,100,20,30,23,45,78,64};
        quickSort(arr,0,arr.length-1);
        for(int n:arr){
            System.out.print(n+" ");
        }
    }
}
