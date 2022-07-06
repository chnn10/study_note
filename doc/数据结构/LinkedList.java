package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.list;

public class LinkedList {

    private int size;

    // 头节点也是有数据的
    private Node head;

    // 表示的是尾节点，尾节点是有数据的
    private Node last;

    private static class Node {
        int data;
        Node prev;
        Node next;

        public Node(int data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedList() {
    }

    // 头插入
    // head->22->33->44->55->66
    public void addFirst(int ele) {
        Node firstNode = head;
        Node newNode = new Node(ele,null,null);
        if (firstNode == null) {
            head = last = newNode;
            size++;
        } else {
            head = newNode;
            firstNode.prev = newNode;
            newNode.next = firstNode;
            size++;
        }
    }

    // 尾插入
    public void addLast(int ele) {
        Node lastNode = last;
        Node newNode = new Node(ele,null,null);
        if (lastNode == null) {
            head = last = newNode;
            size++;
        } else {
            last = newNode;
            lastNode.next = newNode;
            newNode.prev = lastNode;
            size++;
        }
    }

    // 根据index插入，找到index位置的节点，而不是找index的前驱接待你
    // 分三种情况：index为0的情况、index为size的情况、index是中间的情况
    public void addByIndex(int index, int ele) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("添加节点的索引不合法");
        }
        if (index == 0) {
            addFirst(ele);
        } else if (index == size) {
            addLast(ele);
        } else {
            Node newNode = new Node(ele,null,null);
            Node preNode = getNodeByIndex(index - 1);
            Node indexNode = preNode.next;   // 现在是index位置的节点，插入之后，这个index位置就是next了
            indexNode.prev = newNode;
            newNode.next = indexNode;
            preNode.next = newNode;
            newNode.prev = preNode;
            size++;
        }
    }

    // 获取index节点
    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("获取节点索引非法");
        }

        // 从头头节点开始找
        if (index < (size/2)) {
            Node cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur;
        // 从尾节点开始找
        } else {
            Node cur = last;
            for (int i = size - 1; i > index; i--) {
                cur = cur.prev;
            }
            return cur;
        }
    }

    // 删除头节点
    // 还需要考虑，有没有头节点，只有一个头节点的问题
    public Node removeFirst() {
        if (head == null) {
            throw new IllegalArgumentException("链表为空");
        }
        Node delNode = head;
        Node next = head.next;
        if (next == null) {
            head = null;
            last = null;
        } else {
            next.prev = null;
            head = next;
        }
        size--;
        return delNode;
    }

    // 删除尾节点
    public Node removeLast() {
        if (last == null) {
            throw new IllegalArgumentException("链表为空");
        }
        Node delNode = last;
        Node prev = last.prev;
        if (prev == null) {
            last = null;
            head = null;
        } else {
            prev.next = null;
            last = prev;
        }
        size--;
        return delNode;
    }

    // 删除节点
    // 1：需要考虑前驱节点是null
    // 2：需要考虑后继节点是null
    // 其实上面两种情况，就是在删除头、尾的时候已经考虑到了
    public Node removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("删除节点的索引非法");
        }
        Node delNode = getNodeByIndex(index);

        if (index == 0) {
            removeFirst();
        } else if (index == size - 1) {
            removeLast();
        } else {
            // 3：走到这里，其实prev 和 next都不是为null的
            Node prev = delNode.prev;
            Node next = delNode.next;
            prev.next = next;
            next.prev = prev;
            delNode.prev = delNode.next = null;
            size--;
        }
        return delNode;
    }



    // --------------- 下面是测试需要用到的 ----------
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[size=").append(size).append(", ");
        Node node = head;
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
        LinkedList linkedList = new LinkedList();
//        System.out.println("---------- 测试addFirst方法 ----------");
//        linkedList.addFirst(11);
//        linkedList.addFirst(22);
//        linkedList.addFirst(33);
//        System.out.println(linkedList);
//        System.out.println("---------- 测试addLast方法 ----------");
        linkedList.addLast(11);
        linkedList.addLast(22);
//        linkedList.addLast(33);
//        linkedList.addLast(44);
//        linkedList.addLast(55);
//        linkedList.addLast(66);
        System.out.println(linkedList);
//        System.out.println("---------- 测试获取getNode的方法 ----------");
//        System.out.println(linkedList.getNodeByIndex(4).data);
//        System.out.println("---------- 测试获取addByIndex的方法 ----------");
//        linkedList.addByIndex(0,88);
//        System.out.println(linkedList);
//        linkedList.addByIndex(6,99);
//        System.out.println(linkedList);
//        System.out.println("---------- 测试删除头节点 ----------");
//        linkedList.removeFirst();
//        System.out.println(linkedList);
//        System.out.println("---------- 测试删除尾节点 ----------");
//        linkedList.removeLast();
//        System.out.println(linkedList);
//        System.out.println("---------- 测试删除头节点 ----------");
//        linkedList.removeFirst();
//        System.out.println(linkedList);
        System.out.println("---------- 测试删除节点 ----------");
        linkedList.removeByIndex(0);
        System.out.println(linkedList);



    }
}





