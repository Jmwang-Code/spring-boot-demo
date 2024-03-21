package myCollections;

import java.util.Arrays;
import java.util.RandomAccess;

/**
 * 属性:
 * 默认容量
 * 元素数组
 * 元素size
 *
 * 方法:
 * add(E e)：在列表末尾添加元素
 * get(int index)：获取指定位置的元素
 * 确认容量扩容 ensureCapacity()
 * size()：返回列表中的元素个数。
 * isEmpty()：判断列表是否为空。
 *
 * remove(int index)：移除指定位置的元素。
 * contains(Object o)：判断列表是否包含指定元素。
 * clear()：清空列表中的所有元素。
 *
 */
//RandomAccess作用是instanceof来判断是否支持随机访问
public class MyArrayList<E> implements RandomAccess {
    // 默认初始容量
    private static final int DEFAULT_CAPACITY = 10;

    // 保存元素的数组
    private Object[] elementData;

    // 当前元素个数
    private int size;

    // 构造方法，初始化数组和大小
    public MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // 添加元素
    public void add(E e) {
        ensureCapacity(size + 1); // 确保容量足够
        elementData[size++] = e; // 将元素添加到数组末尾
    }

    // 获取指定位置的元素
    @SuppressWarnings("unchecked")
    public E get(int index) {
        // 检查索引是否有效
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elementData[index]; // 返回指定位置的元素
    }

    // 确保容量足够
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) { // 如果需要的容量超过当前容量
            int oldCapacity = elementData.length; // 保存旧容量
            int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩容为原来的 1.5 倍
            if (newCapacity < minCapacity) { // 如果扩容后容量仍然不足
                newCapacity = minCapacity; // 直接扩容到所需容量
            }
            // 扩容数组并赋值给 elementData
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    // 获取当前元素个数
    public int size() {
        return size;
    }

    // 判断是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 可选：实现其他方法，如 remove、contains、clear 等

    //
    public E remove(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldValue = get(index); // 获取要删除的元素
        int numMoved = size - index - 1; // 计算需要移动的元素个数
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved); // 移动元素
        }
        elementData[--size] = null; // 将最后一个元素置为 null，并更新 size
        return oldValue;
    }
}

