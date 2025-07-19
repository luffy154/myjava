package org.example;

/**
 * @ClassName:MergeSort
 * @author: qm
 * @Description:
 * @date:2025-07-18
 */
public class MergeSort {

    // 归并排序主方法
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);      // 排序左半部分
            mergeSort(arr, mid + 1, right); // 排序右半部分
            merge(arr, left, mid, right);   // 合并两个有序数组
        }
    }

    // 合并两个有序数组
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1]; // 临时数组
        int i = left, j = mid + 1, k = 0;

        // 比较两个子数组的元素，按顺序放入临时数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 将剩余元素复制到临时数组
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // 将临时数组的元素复制回原数组
        for (i = 0; i < k; i++) {
            arr[left + i] = temp[i];
        }
    }

    // 测试
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 3, 7, 1, 8, 4, 6};
        mergeSort(arr, 0, arr.length - 1);
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
