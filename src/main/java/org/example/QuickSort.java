package org.example;

/**
 * @ClassName:QuickSort
 * @author: qm
 * @Description:
 * @date:2025-07-17
 */
public class QuickSort {

    // 快速排序主方法
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right); // 分区

            quickSort(arr, left, pivotIndex - 1);         // 排序左半部分
            quickSort(arr, pivotIndex + 1, right);        // 排序右半部分
        }
    }

    // 分区方法，返回基准值最终位置
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right]; // 选取最后一个元素为基准
        int i = left - 1;       // i指向小于pivot的区域末尾
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                if (i != j) {
                    swap(arr, i, j); // 把小于等于pivot的元素交换到前面
                }
            }
        }
        swap(arr, i + 1, right); // 把pivot放到中间
        return i + 1;
    }

    // 交换数组元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 测试
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 3, 7, 1, 8, 4, 6};
        quickSort(arr, 0, arr.length - 1);
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
