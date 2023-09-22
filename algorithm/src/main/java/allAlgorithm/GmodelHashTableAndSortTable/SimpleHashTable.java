package allAlgorithm.GmodelHashTableAndSortTable;

//加入可能存在的元素有 10^6次 整型int可以存储32-1位
public class SimpleHashTable {

    int[] bucket = new int[40000];
    //分桶计算

    public void add(int key) {
        int bucketId = key / 32;
        int exact = key % 32;
        setArr(bucketId,exact,true);
    }

    public void remove(int key) {
        int bucketId = key / 32;
        int exact = key % 32;
        setArr(bucketId,exact,false);
    }

    public boolean contains(int key) {
        int bucketId = key / 32;
        int exact = key % 32;
        return getArr(bucketId,exact);
    }

    private void setArr(int bucketId,int exact,boolean val) {
        int u;
        if (val)u = bucket[bucketId] | (1 << exact);
        else u = bucket[bucketId] & (~(1 << exact));
        bucket[bucketId] = u;
    }

    private boolean getArr(int bucketId,int exact) {
        int u = (bucket[bucketId] >> exact) & 1;
        return u == 1;
    }
}
