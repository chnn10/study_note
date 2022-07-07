package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.list;

import java.util.Stack;

public class MiddleLinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }





    // ---------- 19. 删除链表倒数第n个节点 ----------
    //我们也可以在遍历链表的同时将所有节点依次入栈。根据栈「先进后出」的原则，我们弹出栈的第 n 个节点就是需要删除的节点，并且目前栈顶的节点就是待删除节点的前驱节点。这样一来，删除操作就变得十分方便了
    public ListNode removeNthFromEnd(ListNode head, int n) {
        Stack<ListNode> stack = new Stack();

        ListNode dummyHead = new ListNode(0,head);
        ListNode cur = dummyHead;

        // 遍历整个链表，然后将所有节点都入栈
        while(cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        // 这里走完之后，就将倒数的节点都出栈了，那么栈顶的节点就是待删除节点的前驱节点，我们就是要获取到这个节点，然后next next可以了
        for(int i = 0; i < n; i++) {
            stack.pop();
        }

        ListNode pre = stack.peek();
        pre.next = pre.next.next;
        return dummyHead.next;
    }





    // ---------- 24. 两两交换链表中的节点 ----------
    /**
     * 题目：给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
     * 输入：head = [1,2,3,4]
     * 输出：[2,1,4,3]
     *
     * 也可以通过迭代的方式实现两两交换链表中的节点。
     * 创建哑结点 dummyHead，令 dummyHead.next = head。令 temp 表示当前到达的节点，初始时 temp = dummyHead。
     * 如果 temp 的后面没有节点或者只有一个节点，则没有更多的节点需要交换，因此结束交换。否则，获得 temp 后面的两个节点 node1 和 node2，通过更新节点的指针关系实现两两交换节点。
     */
    public ListNode swapPairs(ListNode head) {

        // 我们需要一个虚拟头节点
        // while循环，如果next和next.next都不是空的，我们就跳出循环，然后就在里面进行交换可以了
        // dummy->1->2->3->4
        // dummy->2->1->4->3
        ListNode dummyNode = new ListNode(0,head);
        ListNode cur = dummyNode;

        // 每次需要交换 temp 后面的两个节点。
        while (cur.next != null && cur.next.next != null) {
            ListNode node1 = cur.next;
            ListNode node2 = cur.next.next;
            cur.next = node2;    // 将node2放到前面
            node1.next = node2.next;    // 将node2的next节点，赋值给node1节点，确保node1可以走下去
            node2.next = node1;    // 将node1放到node2后面
            cur = node1;    // 确保后面可以循环下去
        }

        return dummyNode.next;
    }











    // ------------ 86. 分隔链表 --------------------
    /**
     * 题目：给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 输入：head = [1,4,3,2,5,2], x = 3
     * 输出：[1,2,2,4,3,5]
     * 使用连两个链表还是比较简单的
     */
    public ListNode partition(ListNode head, int x) {
        // 使用两个链表，小的链表存放小于x的节点，大的链表存放大于x的节点，最后将大的链表头放插入小的链表的尾就可以了
        ListNode smallHead = new ListNode(-1);
        ListNode bigHead = new ListNode(-1);
        ListNode cur = head;
        ListNode small = smallHead;
        ListNode big = bigHead;
        while (cur != null) {
            if (cur.val < x) {
                small.next=cur;
                small = small.next;
            } else {
                big.next = cur;
                big = big.next;
            }
            cur = cur.next;
        }

        big.next = null;    // 如果没有这里， Found cycle in the ListNode
        small.next = bigHead.next;    // 如果步使用虚拟头节点，small.next会报null，都需要注意一下

        return smallHead.next;
    }









    // ------------ 146. LRU 缓存 ---------------------（代办）







    // ---------- 328. 奇偶链表 ----------

    /**
     * 题目
     * 给定单链表的头节点head，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
     * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为偶数 ，以此类推。
     * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。你必须在O(1)的额外空间复杂度和O(n)的时间复杂度下解决这个问题。
     *
     */
    public ListNode oddEvenList(ListNode head) {

        // 记录奇数链表的虚拟头节点
        ListNode dummyLeftHead = new ListNode(0);

        // 记录偶数链表的虚拟头节点
        ListNode dummyRightHead = new ListNode(0);


        ListNode leftCur = dummyLeftHead;
        ListNode rightCur = dummyRightHead;

        int index = 1;

        for(ListNode cur = head; cur != null; cur = cur.next) {
            if(index % 2 == 0) {
                rightCur.next = new ListNode(cur.val);
                rightCur = rightCur.next;
            } else {
                leftCur.next = new ListNode(cur.val);
                leftCur = leftCur.next;
            }
            index++;
        }

        leftCur.next = dummyRightHead.next;

        return dummyLeftHead.next;
    }







}




