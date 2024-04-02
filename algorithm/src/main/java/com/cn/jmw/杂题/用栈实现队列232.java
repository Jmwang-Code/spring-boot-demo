package com.cn.jmw.杂题;

import java.util.Stack;

public class 用栈实现队列232 {

    //stack1 压入  stack2 弹出
    Stack<Integer> stack1;
    Stack<Integer> stack2;


    /** Initialize your data structure here. */
    public 用栈实现队列232() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        //stack2有数据的时候 弹出，并且将数据压入stack1
        while(stack2.size() !=0){
            int j= stack2.pop();
            stack1.push(j);
        }
        //stack2没数据就直接压入 stack1
        stack1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        //stack1有数据 就弹出，并且压入stack2
        while (stack1.size()!=0){
            Integer pop = stack1.pop();
            stack2.push(pop);
        }
        //stack1没数据 就直接弹出
        return stack2.pop();
    }

    /** Get the front element. */
    public int peek() {
        //stack1有数据 弹出stack1 ，压入stack2
        while(stack1.size() != 0){
            int x= stack1.pop();
            stack2.push(x);
        }
        //并且返回stack2中元素
        return stack2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        if (stack1.isEmpty() && stack2.isEmpty()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        用栈实现队列232 myQueue = new 用栈实现队列232();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.peek());
        System.out.println(myQueue.empty());
    }
}
