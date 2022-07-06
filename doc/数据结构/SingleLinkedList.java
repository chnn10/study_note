package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.list;

public class SingleLinkedList {

    private int size;

    // 我们使用虚拟头节点
    private Node head;

    // 我们使用Node节点来链接链表的，因此需要一个静态的内部类来表示这个Node，这个内部类是私有的
    private static class Node {
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    // 当我们新建以恶个链表的时候，我们就是新建一个虚拟头节点，这个虚拟头节点是不表示任何数据的
    public SingleLinkedList() {
        head = new Node(-1,null);
    }

    // 尾插法
    public void addLast(int ele) {
        Node cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new Node(ele,null);
        size++;
    }

    // 头插法
    // [size=5, 11, 12, 13, 14, 15]
    public void addFirst(int ele) {
        Node newNode = new Node(ele,null);
        Node nextNode = head.next;
        head.next = newNode;
        newNode.next = nextNode;
        size++;
    }

    /**
     * 1：如果index是第一个位置
     * 2：如果index是最后的位置
     * 3：如果index是中间的位置
     * @param index
     * @param ele
     */
    public void addByIndex(int index, int ele) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("链表添加操作索引不合法");
        }
        if (index == 0) {
            addFirst(ele);
        } else {
            // 1：找到前驱节点
            // 2：在next的前驱节点后面插入新节点
            Node prev = getNode(index - 1);
            Node newNode = new Node(ele,null);
            Node nextNode = prev.next;
            prev.next = newNode;
            newNode.next = nextNode;
            size++;
        }

    }

    /**
     * 获取低index位置的节点
     * @param index
     * @return
     */
    public Node getNode(int index) {
        Node cur = head;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 根据index删除节点
     * @param index
     * @return
     */
    public Node removeByIndex(int index) {
        // 1：判断index
        // 2：index为0的情况
        // 3：找到前驱节点
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("链表删除节点传入索引非法");
        }

        Node node = head.next;
        if (index == 0) {
            head.next = head.next.next;
        } else {
            Node prev = getNode(index - 1);
            node = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return node;
    }

    /**
     * ---------------------- 下面主要用于测试的场景 ----------------------
     * @return
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[size=").append(size).append(", ");
        Node node = head.next;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(node.data);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addFirst(11);
        singleLinkedList.addFirst(22);
        singleLinkedList.addFirst(33);
        singleLinkedList.addFirst(44);
        System.out.println(singleLinkedList);
        System.out.println("---------- 测试获取index位置的元素 ----------");
        System.out.println(singleLinkedList.getNode(0).data);
        System.out.println("---------- 测试index位置添加元素 ----------");
        singleLinkedList.addByIndex(0,99);
        System.out.println(singleLinkedList);
        System.out.println("---------- 测试index位置删除元素 ----------");
        singleLinkedList.removeByIndex(0);
        System.out.println(singleLinkedList);
    }
}



