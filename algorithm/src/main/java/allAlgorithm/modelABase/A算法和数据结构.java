package allAlgorithm.modelABase;

import allAlgorithm.对数器.一维.一维整型.OneArrayLogarithmic;
import allAlgorithm.对数器.一维.一维整型.OneArrayNotReturn;

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
    public static void mergeSort(int[] arr,int L,int R) {
        if (L<=R) {
            return;
        }
        //获取中间
        int mid = L + ((R - L) >> 1);
        //归并排序
        mergeSort(arr, L, mid);
        mergeSort(arr,mid+1,R);
        merge(arr,L,mid,R);
    }

    //合并
    public static void merge(int[] arr,int L,int M,int R){
        //创建一个新数组
        int[] help = new int[R-L+1];
        //左边数组的指针
        int p1 = L;
        //右边数组的指针
        int p2 = M+1;
        //新数组的指针，控制help
        int i = 0;
        while (p1 <= M && p2 <= R)help[i++] = arr[p1]<arr[p2]?arr[p1++]:arr[p2++];

        while (p1 <= M)arr[i++] = arr[p1];
        while (p2 <= M)arr[i++] = arr[p2];
        for (int j = 0; j < help.length; j++) {
            arr[L+i] = help[j];
        }
    }

    //快排
    public static void quickSort(int[] arr,int L,int R){
        if (L >= R) return;

        int q = partition(arr ,L, R); // 获取分区点
        quickSort(arr, L, q-1);
        quickSort(arr, q+1, R);
    }

    private static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for(int j = p; j < r; ++j) {
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
        a[i] = a[r];
        a[r] = tmp;
        return i;
    }

    //使用对数器进行验证
    public static void main(String[] args) {
        OneArrayLogarithmic.LogarithmicDevice(new OneArrayNotReturn() {
            @Override
            public void processNotReturn(int[] arr) {
                mergeSort(arr,0,arr.length-1);
            }
        },new OneArrayNotReturn () {
            @Override
            public void processNotReturn(int[] arr) {
                quickSort(arr,0,arr.length-1);
            }
        },1000,10,20,1,50);
    }

}
