package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class SimpleLinkedList {
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





    // ---------- 21. 合并两个有序链表 n----------
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 虚拟头节点
        ListNode ret = new ListNode(0);
        ListNode cur = ret;

        ListNode left = list1;
        ListNode right = list2;

        // 两个链表都不是空的，才走下去
        while(left != null && right != null) {
            // 如果昨天链表的值小于右边链表的值，将左边的放到右边的，然后发生变动的链表，一起走下去
            if(left.val <= right.val) {
                cur.next = left;
                left = left.next;
                cur = cur.next;
            } else {
                cur.next = right;
                right = right.next;
                cur = cur.next;
            }
        }

        // 表示有链表是空的了，需要将剩下不是空的链表，全部插入新的链表中
        if(left == null) {
            cur.next = right;
        }

        if(right == null) {
            cur.next = left;
        }

        return ret.next;
    }





    // ---------- 83. 删除链表重复元素 ----------
    // 思路：如果当前节点和next节点的值是相等的，就删除next节点
    // 注意：这个链表是有序的
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        // 当前节点 和 后面一个节点，都不是空的，才继续走，如果不是的话，表示已经走到最后了
        // 如果当前节点和next节点的值是相等的，就当前节点next、next（经典的删除操作，当前节点就是前驱节点，删除就是删除后继的节点）
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }






    // ---------- 160. 相交链表 ----------
    // 方法1：将链表A的所有结点都放在HashSet中，然后判断链表B的每一个节点是否存在这个set中。时间复杂度是m+n，这个方法比较容易理解
    // 方法2：使用双指针（还是不明白这个解法），这里，看了好久都看不懂，还是使用上面那个方法做吧

    /**
     * 题目
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> visited = new HashSet<ListNode>();
        ListNode temp = headA;
        while (temp != null) {
            visited.add(temp);
            temp = temp.next;
        }
        temp = headB;
        while (temp != null) {
            if (visited.contains(temp)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }






    // ---------- 203. 移除链表元素 ----------
    /**
     * 题目
     * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode cur = dummyHead;

        // 善于利用虚拟头节点，如果是删除第一个节点，就好办很多了
        // 从虚拟头节点开始遍历的，一般都是cur.next != null，从第一个节点开始的，一般都是cur != null
        while(cur.next != null) {
            if(cur.next.val == val) {
                cur.next = cur.next.next;  // 这里其实已经走了一步了，所以后买你要加else，表示不等于目标的值往前走
            } else {
                cur = cur.next;
            }

        }
        return dummyHead.next;
    }





    // ---------- 206. 反转链表 ----------
    // 多看几个方法，可以背一下
    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode next = head;
        ListNode pre = null;

        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }





    // ---------- 234. 回文链表 ----------

    /**
     * 题目：给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     */
    public static boolean isPalindrome(ListNode head) {
        // 思路1：使用list，拿第一个和最后一个做比较
        List<Integer> list = new ArrayList();
        // 将链表中的值，都放到list中
        ListNode cur = head;
        while(cur != null) {
            list.add(cur.val);
            cur = cur.next;
        }
        // 回文，一般都是第一个节点在左边，最后一个节点在右边，然后一个一个进行比较
        // 我们只需要遍历到size/2这个位置就可以了
        int left = 0;
        int right = list.size() - 1;
        for(int i = 0; i < list.size()/2; i++) {
            if(list.get(left) != list.get(right)) {    // 如果发现有不相等的，直接返回false
                return false;
            }
            // 走到这里，表示的是相等的，然后左边和右边都继续前进
            left++;
            right--;
        }
        return true;
    }

    // 回文方法2
    // 使用的是数组，使用list会比数组稍微好一点，不会发生数组越界
    public static boolean isPalindrome1(ListNode head) {
        int[] nums = new int[100000];
        int i = 0;
        ListNode cur = head;
        int count = 0;
        while (cur != null) {
            nums[i++] = cur.val;
            count++;
            cur = cur.next;
        }
        int len = count;
        for (int j = 0; j < len/2; j++) {
            if (nums[j] != nums[len-j-1]){    // j 和 len -j -1是相对的
                return false;
            }
        }
        return true;
    }







    // -------------------- 237. 删除链表节点 ------------------------------
    /**
     * 题目：请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点 head ，只能直接访问 要被删除的节点。
     */
    // 将next节点保存到当前节点中，然后删除next节点，这个方法还是很妙的
    public void deleteNode1(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }







    // ---------- offer22. 获取链表倒数第k个节点 ----------
    // 倒数第k个元素，就是整数n-k个元素，先遍历整个链表得到n，然后再一个遍历链表获取n-k位置
    // 我们首先求出链表的长度 nn，然后顺序遍历到链表的第 n - kn−k 个节点返回即可。
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode cur = head;
        int l = 0;    // 记录链表的长度

        while(cur!=null) {
            l++;
            cur = cur.next;
        }

        ListNode ret = head;

        // [1,2,3,4,5] 2
        // 这里l是5，倒数是2
        // 等同于i=0开始，一直走到3位置上就可以了
        for(int i = l; i > k; i--) {
            ret = ret.next;
        }

        return ret;
    }

    // 方法2：双指针，快指针先走k步，走到k+1位置了，慢指针开始走，当快指针走完之后，慢指针就到了k位置了
    // 我们首先将fast 指向链表的头节点，然后向后走 kk 步，则此时 fast 指针刚好指向链表的第 k + 1k+1 个节点。
    // 我们首先将slow 指向链表的头节点，同时slow 与 fast 同步向后走，当 fast 指针指向链表的尾部空节点时，则此时返回slow 所指向的节点即可。
    // 1->2->3->4->5->6->7（倒数第三个）
    // for(i=0;i<3;i++) fast=fast.next;
    // 还是比较简单的，一步一步走

    public ListNode getKthFromEnd1(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;

        // 快指针走到了k的位置上
        while (fast != null && k > 0) {
            fast = fast.next;
            k--;
        }

        // 这时候，慢指针从头节点开始走，当快指针走完的时候，慢指针就走到了倒数k的位置了
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }









    // ----------------- 876. 链表中间节点 ---------------------
    // 思路1：遍历两次链表
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // 慢指针走一步，快指针走两步，因为是走两步，所以需要还需要判断fast.next != null
        // 快指针，走到最后的时候，这时候慢指针就刚好是中间的位置了
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }





    

    // ----------------------- offer6. 从链表尾打印链表 -----------------------
    // 思路1：使用栈
    public int[] reversePrint(ListNode head) {
        // 将链表中的值放到一个栈中
        Stack<Integer> stack = new Stack();
        ListNode cur = head;
        // 经典的遍历链表
        while(cur != null) {
            stack.push(cur.val);
            cur = cur.next;
        }

        // 再将这个栈的值，放到一个数组中
        int[] ret = new int[stack.size()];
        int index = 0;

        while(!stack.isEmpty()) {
            ret[index++] = stack.pop();
        }

        return ret;
    }





}






