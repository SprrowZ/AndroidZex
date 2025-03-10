package com.dawn.zgstep.leetcode.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2024/2/29 11:25
 */

//* Definition for a binary tree node.
class TreeNode {
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

class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length);
    }

    private TreeNode construct(int[] nums, int startIndex, int endIndex) {
        //length等于1时到达叶子结点，直接返回即可
        if (nums.length == 1) {//起码有一个结点
            return new TreeNode(nums[0]);
        }
        int maxValue = -1;
        int index = -1;
        for (int i = startIndex; i < endIndex; i++) {//前闭后开
            if (nums[i] > maxValue) {
                maxValue = nums[i];
                index = i;
            }
        }
        TreeNode root = new TreeNode(maxValue);
        //构造左、右子树
        if (index > startIndex) {//保证左子树至少有一个结点，方法第一行就是限定length至少为1
            root.left = construct(nums, startIndex, index);
        }
        if (index < endIndex - 1) {//保证右子树至少有一个结点
            root.right = construct(nums, index + 1, endIndex);
        }
        return root;
    }


//    private TreeNode mergeTree(TreeNode root1, TreeNode root2) {
//
//    }

    private TreeNode merge(TreeNode nodeA, TreeNode nodeB) {
        //前序遍历即可,以nodeA作为容器
        if (nodeA == null && nodeB == null) {
            return null;
        }
        if (nodeA != null && nodeB != null) {
            nodeA.val = nodeA.val + nodeB.val;
        }
        if (nodeA == null) {
            nodeA = new TreeNode(nodeB.val);
        }

        if (nodeB == null) {
            nodeA.left = merge(nodeA.left, null);
            nodeA.right = merge(nodeA.right, null);
        } else {
            nodeA.left = merge(nodeA.left, nodeB.left);
            nodeA.right = merge(nodeA.right, nodeB.right);
        }
        return nodeA;

    }

    //二叉搜索树
    public TreeNode searchBST(TreeNode root, int val) {  //左<中<右
        return search(root, val);
    }

    private TreeNode search(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        if (val < root.val) {
            return search(root.left, val);
        } else {
            return search(root.right, val);
        }
    }

    List<Integer> nodeList = new ArrayList<>();

    public boolean isValidBST(TreeNode root) {
        traversal(root);//中序：中左右，只需要判断中序数组是否递增即可判断是否是二叉搜索树
        int minValue = nodeList.get(0);
        for (int i = 1; i < nodeList.size(); i++) {
            if (nodeList.get(i) <= nodeList.get(i - 1)) return false;
        }
        return true;
    }

    private void traversal(TreeNode root) {
        if (root == null) return;
        traversal(root.left);
        nodeList.add(root.val);
        traversal(root.right);
    }

    //BST 插入操作
    public TreeNode insertIntoBST(TreeNode root, int val) {
        return insert(root, val);
    }

    private TreeNode insert(TreeNode root, int val) {
        //前序，中左右
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val < val) {
            root.right = insert(root, val);
        }
        if (root.val > val) {
            root.left = insert(root, val);
        }
        return root;
    }

    //-----
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> itemList = new LinkedList<>();

    public List<List<Integer>> combine(int n, int k) { //组合    1...n，k个数的所有组合
        backtracking(1, n, k);
        return result;
    }

    private void backtracking(int startIndex, int n, int k) {
        //结束条件
        if (itemList.size() == k) {
            result.add(new ArrayList<>(itemList));
            return;
        }

        for (int i = startIndex; i <= n; i++) {
            //结点操作
            itemList.add(startIndex);
            //回溯
            backtracking(startIndex + 1, n, k);
            //撤销处理结果
            itemList.removeLast();
        }


    }
//
//    public List<List<Integer>> combinationSum3(int k, int n) {// k个数，相加为n；只包含1-9
//
//    }


    //输入：s = "aab"
//输出：[["a","a","b"],["aa","b"]]
    List<List<String>> partitionResult = new ArrayList<>();
    LinkedList<String> partitionItem = new LinkedList<>();

    public List<List<String>> partition(String s) { //所有回文字符串的组合；
       backtracking(0,s);
       return partitionResult;
    }

    private void backtracking(int startIndex, String s) {
        if (startIndex == s.length()) {
            partitionResult.add(new ArrayList<>(partitionItem));
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            if (isPalindrome(s, startIndex, i)) {
                partitionItem.add(s.substring(startIndex, i + 1));
                backtracking(i+1, s);
                partitionItem.removeLast();
            } else {
                continue;
            }
        }
    }

    private boolean isPalindrome(String s, int startIndex, int endIndex) {//前闭后闭
        for (int i = startIndex, j = endIndex; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}

