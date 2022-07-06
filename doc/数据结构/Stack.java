package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.list;

import java.util.Arrays;

public class Stack {

    private int[] data;
    private int size;

    public Stack() {
        data = new int[20];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 查看栈顶元素
    public int top() {
        return data[size-1];
    }

    // 入栈
    public void push(int ele) {
        data[size] = ele;
        size++;
    }

    // 弹栈
    public int pop() {
        int ret = data[size-1] ;
        data[size-1] = 0;    // 在这里，我们将栈顶位置设置的元素设置为0
        size--;
        return ret;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
//        System.out.println("---------- 测试入栈 ----------");
//        stack.push(11);
//        stack.push(22);
//        stack.push(33);
//        stack.push(44);
//        System.out.println(stack);
//        System.out.println("---------- 测试获取栈顶元素 ----------");
//        System.out.println(stack.top());
//        System.out.println("---------- 测试出栈 ----------");
//        stack.pop();
//        System.out.println(stack);
//        System.out.println(stack.pop());
        System.out.println("---------- 测试入栈和出栈的顺序 ----------");
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.pop();
        stack.push(88);
        System.out.println(stack);



    }

}



