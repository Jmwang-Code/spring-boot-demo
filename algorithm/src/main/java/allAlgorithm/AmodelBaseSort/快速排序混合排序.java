package allAlgorithm.AmodelBaseSort;

import org.junit.Test;

import java.util.Arrays;

public class 快速排序混合排序 {

    //混合 快排+插入排序
    public static void quickSort(int[] arr, int left, int right) {
        //递归结束条件 如果此段下小于60下标，直接插入排序
        if (right - left < 60) {
            快速排序混合排序.insertSort(arr, left, right);
            return;
        }

        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }

    //划分
    public static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }

    //插入排序
    public static void insertSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    @Test
    public void test() {
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 8, 9, 10};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
