
package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BSTLeetCode {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }






    // ---------- 700. 二叉搜索树搜索节点 ----------
    /**
     * 题目
     * 给定二叉搜索树（BST）的根节点root和一个整数值val。
     *你需要在 BST 中找到节点值等于val的节点。 返回以该节点为根的子树。 如果节点不存在，则返回null。
     *
     */
    public TreeNode searchBST(TreeNode root, int val) {

        // 迭代
        // 如果是等于的就直接返回; 如果值小于cur的值，往左边继续走下去; 否则就往右边继续走下去
        TreeNode node = root;
        while (node != null) {
            if (node.val == val) {
                return node;
            } else if (val < node.val) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }







    // ---------- 701. 二叉搜索树搜索节点 ----------
    /**
     * 题目
     * 给定二叉搜索树（BST）的根节点root和要插入树中的值value，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
     */

    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 如果是空的，在新建一个当作root
        // 如果是小于的，往左边走，当左子树为空的，才能插入新的节点，插入之后就break
        // 如果是大于的，往右边走，当右子树是空的，才能插入新的节点，插入之后就break

        if (root == null) {
            root = new TreeNode(val);
            return root;
        }

        TreeNode node = root;
        while (node != null) {
            if (val < node.val) {    // 往左边插入
                if (node.left == null) {
                    node.left = new TreeNode(val);
                    break;
                }
                node = node.left;
            } else {    // 往右边插入
                if (node.right == null) {
                    node.right = new TreeNode(val);
                    break;
                }
                node = node.right;
            }
        }
        return root;    // 注意：这里不是返回node的
    }






    // ---------- 450. 删除二叉树节点 ----------（代办）
    public TreeNode deleteNode(TreeNode root, int key) {



        return null;
    }







    // ---------- 144. 前序遍历 ----------
    // （根->左->右）
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                ret.add(cur.val);
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            cur = cur.right;
        }
        return ret;
    }








    // ---------- 94. 二叉树的中序遍历 ----------
    /**
     * 题目：给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     */
    // 中序遍历（左->根->右）
    public List<Integer> inorderTraversal(SimpleTree.TreeNode root) {
        // 新建一个栈，准备存放二叉树的节点
        // 如果当前节点不是空的，或者栈不是空的，while
        // 里面还有一个while，指的是当前节点不是空的，然后不断低将左子树进栈，将当前所有的左子树都进栈了，就跳出while
        // 将栈顶的节点跳出，打印这个节点，然后走当前节点的右节点

        Stack<SimpleTree.TreeNode> stack = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            ret.add(root.val);
            root = root.right;    // 如果右节点为null，会重新走外层的while
        }
        return ret;
    }







    // ---------- 145. 二叉树的后序遍历 ----------
    // 后续遍历（左->右->根）
    public List<Integer> postorderTraversal(TreeNode root) {
        return null;
    }













    // ---------- 98. 验证二叉搜索树 ----------

    /**
     * 题目
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * 思路1：使用递归
     * 思路2：使用中序
     *
     */
    public boolean isValidBST(TreeNode root) {
        // 使用递归


        return false;

    }


}






