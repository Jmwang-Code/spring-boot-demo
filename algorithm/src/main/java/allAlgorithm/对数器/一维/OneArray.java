package allAlgorithm.对数器.一维;

public interface OneArray<T extends OneArray> {

    /**
     * 有返回对象
     * @param arr
     * @return
     */
    default int[] processHasReturn(int[] arr){
        return null;
    }


    /**
     * 无返回对象
     * @param arr
     */
    default void processNotReturn(int[] arr){

    }
}
