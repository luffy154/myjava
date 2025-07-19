package org.example;

/**
 * @ClassName:HeapSort
 * @author: qm
 * @Description:
 * @date:2025-07-18
 */
public class HeapSort {

    // 堆排序主方法
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // 构建最大堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 逐个取出堆顶元素
        for (int i = n - 1; i > 0; i--) {
            // 将堆顶元素（最大值）放到数组末尾
            swap(arr, 0, i);
            // 重新调整堆
            heapify(arr, i, 0);
        }
    }

    // 调整堆（下沉操作）
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // 假设当前节点最大
        int left = 2 * i + 1;  // 左子节点
        int right = 2 * i + 2; // 右子节点

        // 如果左子节点比当前节点大
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点比当前节点大
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是当前节点，交换并继续调整
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
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
        heapSort(arr);
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }
}
