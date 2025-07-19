package org.example;

/**
 * @ClassName:MergeSort1
 * @author: qm
 * @Description:
 * @date:2025-07-18
 */
public class MergeSort1 {

    public static void sort(int[] arr, int left, int right){
        if(left< right) {
            //获取中间索引
            int middleIndex = (left + right) / 2;
            //左数组排序
            sort(arr, left, middleIndex);
            //右数组排序
            sort(arr,middleIndex+1, right);
            //合并左右数组
            mergeArr(arr, left, middleIndex, right);
        }
    }

    public static void mergeArr(int[] arr, int left, int middleIndex, int right){
        //创建中间数组
        int[] temp = new int[right-left+1];
        //左数组索引
        int leftIndex = left;
        //右数组索引
        int rightIndex = middleIndex+1;
        //循环中间数组
        for(int i=0;i<temp.length;i++){
            //左右索引都在边界内
            if(leftIndex<=middleIndex && rightIndex<=right){
                //比较两个数组元素大小
                if(arr[leftIndex]<=arr[rightIndex]){
                    temp[i] = arr[leftIndex++];
                }else{
                    temp[i] = arr[rightIndex++];
                }
            }else if(leftIndex<=middleIndex){
                temp[i] = arr[leftIndex++];
            }else if(rightIndex<=right){
                temp[i] = arr[rightIndex++];
            }
        }

        //复制回原数组
        for(int i=left;i<=right;i++){
            arr[i]=temp[i-left];
        }

    }

    public static void main(String[] sss){
        int[] arr = {5, 2, 9, 3, 7, 1, 8, 4, 6};
        sort(arr, 0, arr.length - 1);
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
