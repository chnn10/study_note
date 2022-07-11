package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.sort;

public class MaxHeap {
    // 最大二叉堆，索引0是最大的节点
    // 一个节点是index，那么它的父节点是(index-1)/2


    private int[] heap;
    int size;

    public MaxHeap() {
        heap = new int[100];
    }

    int maxValue() {
        return heap[0];
    }

    // 添加操作，在最后一个位置添加新的元素，然后将这个新元素上浮
    public void add(int ele) {
        heap[size] = ele;
        size++;
        shiftUp(size-1);    // 上浮
    }

    private void shiftUp(int index) {
        // 不断地和父节点比较，如果这个节点大于父节点，那么就交换，如果不是就停止交换

        int ele = heap[index];
        while (index > 0) {    // 当index大于0的时候，就表示这里已经是根节点了，不需要再上浮
            int parent = (index-1) / 2;
            if (ele < heap[parent]) {
                return;
            }
            // 走到这里表示的是当前节点需要上浮，进行交换
            int temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;

            // 此时，上浮的节点变成了父节点的索引了
            index = parent;
        }
    }


    // 删除最后一个元素，然后将根节点往下沉
    // 和子节点的最大的进行比较，如果父节点大，就不需要再下沉了，否则就和子节点的最大节点进行比较
    public int removeEle() {
        return 0;
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.add(12);
        maxHeap.add(22);
        maxHeap.add(44);
        maxHeap.add(11);
        maxHeap.add(55);
        maxHeap.add(77);
        int max = maxHeap.maxValue();
        System.out.println(max);
    }
}




