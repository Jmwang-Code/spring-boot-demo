1. 常数时间的操作 ： + - * / %  >> >>> << | & ^ 数组寻址 o(1)
2. 对数时间的操作 ： 二分查找 o(logn)
3. 线性时间的操作 ： 顺序查找 o(n)
4. 线性对数时间的操作 ： 归并排序 o(nlogn)
5. 平方时间的操作 ： 选择排序 o(n^2)
6. 立方时间的操作 ： 二维矩阵乘法 o(n^3)
7. 指数时间的操作 ： 汉诺塔 o(2^n)
8. 阶乘时间的操作 ： 旅行商问题 o(n!)
   时间复杂度计算 ：f(n) = an^2 + bn + c =>  O(n^2)

稳定性：排序后两个相等的数相对位置不变
可以做到排序稳定的：冒泡排序、插入排序、归并排序和基数排序

|                  --                   | 平均时间复杂度  |   最好情况   |   最坏情况   |  空间复杂度   | 稳定性 |
|:-------------------------------------:|:--------:|:--------:|:--------:|:--------:|:---:|
|                 冒泡排序                  |  O(n^2)  |   O(n)   |  O(n^2)  |   O(1)   | 稳定  |
|                 选择排序                  |  O(n^2)  |  O(n^2)  |  O(n^2)  |   O(1)   | 不稳定 |
|                 插入排序                  |  O(n^2)  |   O(n)   |  O(n^2)  |   O(1)   | 稳定  |
|                 希尔排序                  | O(n^1.3) |   O(n)   |  O(n^2)  |   O(1)   | 不稳定 |
| <font color="red">归并排序（有稳定性需求)</font> | O(nlogn) | O(nlogn) | O(nlogn) |   O(n)   | 稳定  |
|     <font color="red">快速排序</font>     | O(nlogn) | O(nlogn) |  O(n^2)  | O(nlogn) | 不稳定 |
|  <font color="red">堆排序(有空间限制)</font>  | O(nlogn) | O(nlogn) | O(nlogn) |   O(1)   | 不稳定 |
|                 计数排序                  |  O(n+k)  |  O(n+k)  |  O(n+k)  |   O(k)   | 稳定  |
|                  桶排序                  |  O(n+k)  |  O(n+k)  |  O(n^2)  |  O(n+k)  | 稳定  |
|                 基数排序                  |  O(n*k)  |  O(n*k)  |  O(n*k)  |  O(n+k)  | 稳定  |


# 坑
1. 归并排序的额外空间复杂度可以变成O(1)，“归并排序 内部缓存法”，但是将变得不再稳定。
2. “原地归并排序" 是垃圾贴，会让时间复杂度变成O(N^2)
3. 快速排序稳定性改进，“01 stable sort”，但是会对样本数据要求更多。
4. 所有的改进都是以空间换时间，或者以时间换空间。目前没有时间复杂度O(nlogn)的排序算法，空间复杂度为O(1)，并且是稳定的。
5. 奇数放在数组左边，偶数放在数组右边，要求相对次序不变，要求空间复杂度O(1),时间复杂度O(n)：快排无法保证稳定性。

# 综合排序
```java
public class Sort {

   //快排
   public static void quickSort(int[] arr, int left, int right) {
      if (left < right) return;
      if (left - right <= 60) {
         insertSort(arr);
         return;
      }

      int pivot = partition(arr, left, right);
      quickSort(arr, left, pivot - 1);
      quickSort(arr, pivot + 1, right);
   }


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
   public static void insertSort(int[] arr) {
      for (int i = 1; i < arr.length; i++) {
         int temp = arr[i];
         int j = i - 1;
         while (j >= 0 && arr[j] > temp) {
            arr[j + 1] = arr[j];
            j--;
         }
         arr[j + 1] = temp;
      }
   }
    
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 5, 4, 6, 7, 9, 8, 0};
        //bubbleSort(arr);
        //selectSort(arr);
        //mergeSort(arr, 0, arr.length - 1);
        //quickSort(arr, 0, arr.length - 1);
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}



```