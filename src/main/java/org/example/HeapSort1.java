package org.example;

/**
 * @ClassName:HeapSort1
 * @author: qm
 * @Description:
 * @date:2025-07-18
 */
public class HeapSort1 {
    public static void createHeap(int[] arr, int arrLength,int fatherIndex){
        //左节点
        int leftIndex = fatherIndex * 2 + 1;
        //右节点
        int rightIndex = fatherIndex * 2 + 2;

        int lagestIndex = fatherIndex;
        //左节点比父大，父节点替换左节点
        if(leftIndex<arrLength&&arr[leftIndex]>arr[lagestIndex]){
            lagestIndex = leftIndex;
        }

        if(rightIndex<arrLength&&arr[rightIndex]>arr[lagestIndex]){
            lagestIndex = rightIndex;
        }

        if(fatherIndex!=lagestIndex){
            swap(arr,fatherIndex,lagestIndex);
            //继续
            createHeap(arr,arrLength,lagestIndex);
        }
    }

    public static void sort(int[] arr){
        //构建最大堆
        for(int i=arr.length/2-1;i>=0;i--){
            createHeap(arr,arr.length,i);
        }

        //堆顶最大放到最后
        for(int i=arr.length-1;i>0;i--){
            //将堆顶交换到最后
            swap(arr,0,i);
            createHeap(arr,i,0);
        }
    }

    public static void swap(int[] arr, int i, int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 3, 7,10,20, 1, 8, 4, 6};
        sort(arr);
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
