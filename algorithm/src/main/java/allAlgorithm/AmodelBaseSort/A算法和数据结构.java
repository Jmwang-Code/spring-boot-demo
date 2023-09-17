package allAlgorithm.AmodelBaseSort;

import allAlgorithm.对数器.一维.一维整型.OneArrayLogarithmic;
import allAlgorithm.对数器.一维.一维整型.OneArrayNotReturn;

import java.util.Arrays;

public class A算法和数据结构 {

    //1. 常数时间的操作 ： + - * / %  >> >>> << | & ^ 数组寻址 o(1)
    //2. 对数时间的操作 ： 二分查找 o(logn)
    //3. 线性时间的操作 ： 顺序查找 o(n)
    //4. 线性对数时间的操作 ： 归并排序 o(nlogn)
    //5. 平方时间的操作 ： 选择排序 o(n^2)
    //6. 立方时间的操作 ： 二维矩阵乘法 o(n^3)
    //7. 指数时间的操作 ： 汉诺塔 o(2^n)
    //8. 阶乘时间的操作 ： 旅行商问题 o(n!)

    //时间复杂度计算 ：T(N) = an^2 + bn + c

    //master公式 ：T(N) = aT(N/b) + O(N^d)
    //log(b,a) > d -> 复杂度为O(N^log(b,a))
    //log(b,a) = d -> 复杂度为O(N^d * logN)
    //log(b,a) < d -> 复杂度为O(N^d)


    //归并
    public static void mergeSort(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        //获取中间
        int mid = L + ((R - L) >> 1);
        //归并排序
        mergeSort(arr, L, mid);
        mergeSort(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    //合并
    public static void merge(int[] arr, int L, int M, int R) {
        //创建一个新数组
        int[] help = new int[R - L + 1];
        //左边数组的指针
        int p1 = L;
        //右边数组的指针
        int p2 = M + 1;
        //新数组的指针，控制help
        int i = 0;
        while (p1 <= M && p2 <= R) help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];

        while (p1 <= M) help[i++] = arr[p1++];
        while (p2 <= R) help[i++] = arr[p2++];
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
    }

    //快排
    public static void quickSort(int[] arr, int L, int R) {
        if (L >= R) return;

        int q = partition(arr, L, R); // 获取分区点
        quickSort(arr, L, q - 1);
        quickSort(arr, q + 1, R);
    }

    private static int partition(int[] a, int L, int R) {
        int pivot = a[R];
        int i = L;
        for (int j = L; j < R; ++j) {
            if (a[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }

        int tmp = a[i];
        a[i] = a[R];
        a[R] = tmp;
        return i;
    }

    //桶排序
    public static void bucketSort(int[] arr, int bucketSize) {
        if (arr.length == 0) {
            return;
        }

        // 找到最大值和最小值
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                minValue = arr[i];
            } else if (arr[i] > maxValue) {
                maxValue = arr[i];
            }
        }

        // 计算桶的数量
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][bucketSize];

        // 将元素分配到桶中
        for (int i = 0; i < arr.length; i++) {
            int bucketIndex = (arr[i] - minValue) / bucketSize;
            for (int j = 0; j < bucketSize; j++) {
                if (buckets[bucketIndex][j] == 0) {
                    buckets[bucketIndex][j] = arr[i];
                    break;
                }
            }
        }

        // 对每个桶中的元素进行排序
        int currentIndex = 0;
        for (int i = 0; i < bucketCount; i++) {
            int[] bucket = buckets[i];
            Arrays.sort(bucket);
            for (int j = 0; j < bucketSize; j++) {
                if (bucket[j] != 0) {
                    arr[currentIndex++] = bucket[j];
                }
            }
        }
    }

    //基数排序
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }

        // 找到最大值和最小值
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                minValue = arr[i];
            } else if (arr[i] > maxValue) {
                maxValue = arr[i];
            }
        }

        // 计算最大值的位数
        int maxDigit = 0;
        while (maxValue != 0) {
            maxValue /= 10;
            maxDigit++;
        }

        // 申请一个桶空间
        int[][] buckets = new int[10][arr.length];
        int base = 10;

        // 从低位到高位，对每一位遍历，将所有元素分配到桶中
        for (int i = 0; i < maxDigit; i++) {
            // 重置每个桶的元素数量为 0
            int[] bucketLen = new int[10];

            // 将所有元素都分配到桶中
            for (int j = 0; j < arr.length; j++) {
                int whichBucket = (arr[j] % base) / (base / 10);
                buckets[whichBucket][bucketLen[whichBucket]] = arr[j];
                bucketLen[whichBucket]++;
            }

            // 将桶中的元素全部取出来，并赋值给原数组
            int k = 0;
            for (int b = 0; b < buckets.length; b++) {
                for (int p = 0; p < bucketLen[b]; p++) {
                    arr[k++] = buckets[b][p];
                }
            }
            base *= 10;
        }
    }

    //使用对数器进行验证
    public static void main(String[] args) {
        OneArrayLogarithmic.LogarithmicDevice(1000, 5, 10, 1, 30, new OneArrayNotReturn() {
            @Override
            public void processNotReturn(int[] arr) {
                quickSort(arr, 0, arr.length - 1);
            }
        }, new OneArrayNotReturn() {
            @Override
            public void processNotReturn(int[] arr) {
                mergeSort(arr, 0, arr.length - 1);
            }
        }, new OneArrayNotReturn() {
            @Override
            public void processNotReturn(int[] arr) {
                bucketSort(arr, arr.length);
            }
        }, new OneArrayNotReturn() {
            @Override
            public void processNotReturn(int[] arr) {
                radixSort(arr);
            }
        });

    }

}
