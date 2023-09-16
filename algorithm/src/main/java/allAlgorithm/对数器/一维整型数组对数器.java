package allAlgorithm.对数器;

public class 一维整型数组对数器 {

    //获取随机数组 有最大值 有最大长度
    public static int[] getRandomArray(int maxSize, int maxValue) {
        //Math.random() -> double [0,1)
        //Math.random() * N -> double [0,N)
        //(int)(Math.random() * N) -> int [0,N-1]
        //生成长度随机的数组
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        //数组中的值随机
        for (int i = 0; i < arr.length; i++) {
            //生成随机值
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        //返回数组
        return arr;
    }

    //获取随机数组 有最小值和最大值 有最小长度和最大长度
    public static int[] getRandomArray(int minSize,int maxSize,int minValue ,int maxValue) {
        //Math.random() -> double [0,1)
        //Math.random() * N -> double [0,N)
        //(int)(Math.random() * N) -> int [0,N-1]
        //生成长度随机的数组
        int[] arr = new int[(int) ((maxSize - minSize + 1) * Math.random()) + minSize];
        //数组中的值随机
        for (int i = 0; i < arr.length; i++) {
            //生成随机值
            arr[i] = (int) ((maxValue - minValue + 1) * Math.random()) + minValue;
        }
        //返回数组
        return arr;
    }

    // 拷贝数组
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }


    //比较10000次2个数组是否相等
    public static boolean isEqual(int[] arr1, int[] arr2) {
        //如果2个数组都为空
        if (arr1 == null && arr2 == null) {
            return true;
        }
        //如果2个数组有一个为空
        if (arr1 == null || arr2 == null) {
            return false;
        }
        //如果2个数组长度不一样
        if (arr1.length != arr2.length) {
            return false;
        }
        //如果2个数组长度一样
        for (int i = 0; i < arr1.length; i++) {
            //如果2个数组中的值不一样
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        //如果2个数组中的值都一样
        return true;
    }

    //输出错误的数组，为了查验自己的问题出在哪
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 对数器2个int[] 不能控制参数
    public static void LogarithmicDevice(OneArrayNotReturn a, OneArrayNotReturn b,int testTimes) {
        //testTime绝对正确的方法与被测方法的比较次数，一旦有1次结果不同立刻打印错误用例并跳出
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArray(1, 100, 1,100);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            a.process(arr1);
            a.process(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    //对数器2个int[] 可以控制参数
    public static void LogarithmicDevice(OneArrayNotReturn a, OneArrayNotReturn b,int testTimes,int minSize,int maxSize,int minValue ,int maxValue){
        //testTime绝对正确的方法与被测方法的比较次数，一旦有1次结果不同立刻打印错误用例并跳出
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArray(minSize, maxSize, minValue,maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            a.process(arr1);
            a.process(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }


}
