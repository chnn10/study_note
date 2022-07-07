package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.tree;

// 添加    done
// 查找最小节点    done
// 查找最大节点    done
// 删除最小节点    todo
// 删除最大节点    todo
// 删除节点       todo
// 前序遍历（根->左->右）
// 中序遍历（左->根->右）
// 后续遍历（左->右->根）
// 层层次遍历（上->下）

public class BinarySearchTree {

    private int size;
    private Node root;

    public BinarySearchTree(){

    }

    static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    // 1：空树的情况
    // 2：值小于节点的，插入在左子树中
    // 3：值大于节点的，插入在右节点中
    // 注意：没有考虑到子树和父节点的交替
    public void add(int ele) {
        if (root == null) {
            root = new Node(ele,null,null);
            size++;
        } else {
            Node newNode = new Node(ele,null,null);
            Node node = root;
            // 一直走到带插入的节点，代码还是有问题
            while (true) {
                if (ele < node.data) {
                    if (node.left == null) {
                        node.left = newNode;
                        size++;
                        return;
                    }
                    node = node.left;
                } else {
                    if (node.right == null) {
                        node.right = newNode;
                        size++;
                        return;
                    }
                    node = node.right;
                }
            }
        }
    }

    // 删除节点
    public void deleteNode(int ele){
        // 1：找到这个节点
        Node node = root;
        while (node != null) {
            if (ele < node.data) {
                node = node.left;
            } else if (ele > node.data) {
                node = node.right;
            } else {
                delete(node);
            }
        }
    }


    private void delete(Node node) {
        Node delNode = node;
        if (delNode.left == null && delNode.right == null) {    // 当前节点是一个叶子节点
            node = null;
            size--;
        } else if (delNode.left != null && delNode.right == null) {    // 当前节点只有左子树

        } else if (delNode.left == null && delNode.right != null) {    // 当前节点只有右子树

        } else {    // 当前节点右两个子树

        }
    }

    // 删除最小节点
    public Node deleteMin() {
        Node node = root;
        while (node != null) {
            if (node.left == null) {
            }
        }
        return null;
    }

    // 删除最大节点
    public Node deleteMax() {
        return null;
    }

    // 获取最小节点的值
    public Node getMin() {
        Node node = root;
        while (node != null) {
            if (node.left == null) {
                return node;
            }
            node = node.left;
        }
        return null;
    }

    // 获取最小节点的值
    public Node getMax() {
        Node node = root;
        while (node != null) {
            if (node.right == null) {
                return node;
            }
            node = node.right;
        }
        return null;
    }

    // 前序遍历
    // 根->左->右，使用栈
    public void preOrder() {
    }


    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        System.out.println("---------- 测试二叉树的添加操作 ----------");
        binarySearchTree.add(55);
        binarySearchTree.add(44);
        binarySearchTree.add(66);
        binarySearchTree.add(40);
        binarySearchTree.add(50);
        binarySearchTree.add(66);
        binarySearchTree.add(60);
        binarySearchTree.add(80);
        binarySearchTree.add(30);
        binarySearchTree.add(42);
        binarySearchTree.add(47);
        binarySearchTree.add(52);
        binarySearchTree.add(58);
        binarySearchTree.add(63);
        binarySearchTree.add(70);
        binarySearchTree.add(85);
        System.out.println("---------- 测试获取最小的值 ----------");
        System.out.println(binarySearchTree.getMin().data);
        System.out.println("---------- 测试获取最大的值 ----------");
        System.out.println(binarySearchTree.getMax().data);
    }
}





