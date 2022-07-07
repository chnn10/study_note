package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class SimpleString {


    // ---------- 20. 有效的括号 -----------
    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *
     * 输入：s = "()[]{}"
     * 输出：true
     *
     *
     * 输入：s = "()"
     * 输出：true
     */

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (stack.isEmpty() || c ==')'&&stack.pop()!='(' || c ==']'&&stack.pop()!='[' || c=='}'&&stack.pop() != '{') {
                return false;
            }
        }

        return stack.isEmpty();
    }







    // ---------- 28. 实现 strStr() -----------
    /**
     * 输入：haystack = "hello", needle = "ll"
     * 输出：2
     */










    // ---------- 58. 最后一个单词的长度 -----------
    /**
     * 输入：s = "Hello World"
     * 输出：5
     * 解释：最后一个单词是“World”，长度为5。
     */
    // 1：我们可以从字符串的最后一个位置开始，但是这个最后的一个位置不是最后一个单词哦，我们还要考虑到空格
    // 2：处理掉空格，得到的index位置就是字符串的最后一个单词了
    // 3：得到了这个单词，我们就开始求出这个单词出现的次数就可以了
    public int lengthOfLastWord(String s) {
        int index = s.length() - 1;

        while(s.charAt(index) == ' ') {
            index--;
        }

        int count = 0;
        // 这里要大于等于0的，如果不是等于0，那么这种情况 "a"，就不能通过了
        while(index >= 0 && s.charAt(index) != ' ') {
            count++;
            index--;
        }

        return count;
    }





    // ---------- 125. 验证回文串 -----------
    /**
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     * 解释："amanaplanacanalpanama" 是回文串
     */
    public boolean isPalindrome(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            // 如果遍历到的这个是一个字符，就将这个字符放进sb中
            char temp = s.charAt(i);
            if (Character.isLetterOrDigit(s.charAt(i))) {
                sb.append(Character.toLowerCase(temp));
            }
        }

        // 下面开始进行判断
        int left = 0;
        int len = sb.length();
        int right = 0;
        while (left < len/2) {
            right = len - 1 - left;
            if (sb.charAt(left) != sb.charAt(right)) {
                return false;
            }
            left++;
        }

        return true;
    }








    // ---------- 344. 反转字符串 -----------
    /**
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    public void reverseString(char[] s) {
        int len = s.length;
        int left = 0;

        while (left < len/2) {
            int right = len - left - 1;
            char temp = s[right];
            s[right] = s[left];
            s[left] = temp;
            left++;
        }
    }








    // ---------- 345. 反转字符串中的元音字母 -----------
    /**
     * 输入：s = "hello"
     * 输出："holle"
     */
    public static String reverseVowels(String s) {
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        set.add('A');
        set.add('E');
        set.add('I');
        set.add('O');
        set.add('U');


        char[] arr = s.toCharArray();
        int len = s.length();

        int left = 0;
        int right = len-1;

        while(left < right) {
            while(left < len && !set.contains(arr[left])) {
                left++;
            }

            while(right > 0 && !set.contains(arr[right])) {
                right--;
            }

            if (left < right) {
                char temp = arr[right];
                arr[right] = arr[left];
                arr[left] = temp;
                left++;
                right--;
            }
        }
        return new String(arr);
    }





    // ---------- 387. 字符串中的第一个唯一字符 -----------
    /**
     * 输入: s = "leetcode"
     * 输出: 0
     */
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character key = s.charAt(i);
            Integer val = map.get(key) ;
            val = val == null ? 1 : val + 1;
            map.put(key,val);
        }

        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1 ;
    }







    // ---------- 389. 找不同 -----------
    /**
     * 输入：s = "abcd", t = "abcde"
     * 输出："e"
     * 解释：'e' 是那个被添加的字母。
     */
    public char findTheDifference(String s, String t) {
        // 我的思路：不正确，还是使用答案的方法好了
        // 答案思路：将s全部相加，再将t全部相加，最后将t-s，就可以得出差异的那一个字符了

        int sum1 = 0;
        int sum2 = 0;

        for(int i = 0; i < s.length(); i++) {
            sum1 += s.charAt(i);
        }

        for(int j = 0; j < t.length(); j++) {
            sum2 += t.charAt(j);
        }

        return (char)(sum2 - sum1);
    }







    // ---------- 392. 判断子序列 -----------
    /**
     * 输入：s = "abc", t = "ahbgdc"
     * 输出：true
     */
    public boolean isSubsequence(String s, String t) {
        // 思路：使用双指针的方法，左指针和右指针，一起移动。如果他们是相等的，就一起移动指针，如果不相等，就移动右指针。

        int len1 = s.length();
        int len2 = t.length();

        int left = 0;
        int right = 0;

        while(left < len1 && right < len2) {
            if(s.charAt(left) == t.charAt(right)) {
                left++;
                right++;
            } else{
                right++;
            }
        }

        return left == len1;  // left走完了，表示我们一直都有匹配成功的
    }






    // ---------- 412. Fizz Buzz -----------
    /**
     * 输入：n = 3
     * 输出：["1","2","Fizz"]
     */
    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList();
        for(int i = 1; i <= n; i++) {
            if((i%3 == 0) && (i%5==0)) {
                list.add("FizzBuzz");
            } else if(i%3==0) {
                list.add("Fizz");
            } else if(i%5==0) {
                list.add("Buzz");
            } else{
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                list.add(sb.toString());
            }
        }
        return list;
    }






    // ---------- 434. 字符串中的单词数 -----------
    /**
     * 输入: "Hello, my name is John"
     * 输出: 5
     * 解释: 这里的单词是指连续的不是空格的字符，所以 "Hello," 算作 1 个单词。
     */
    public int countSegments(String s) {
        int segmentCount = 0;

        for (int i = 0; i < s.length(); i++) {
            if ((i == 0 || s.charAt(i - 1) == ' ') && s.charAt(i) != ' ') {
                segmentCount++;
            }
        }

        return segmentCount;
    }







    // ---------- 20. 有效的括号 -----------





    // ---------- 20. 有效的括号 -----------






    // ---------- 20. 有效的括号 -----------






    // ---------- 20. 有效的括号 -----------








    // ---------- 20. 有效的括号 -----------










    // ---------- 20. 有效的括号 -----------

















    // ---------- 20. 有效的括号 -----------









    // ---------- 20. 有效的括号 -----------






    // ---------- 20. 有效的括号 -----------





    // ---------- 20. 有效的括号 -----------







    // ---------- 20. 有效的括号 -----------





    // ---------- 20. 有效的括号 -----------







    // ---------- 20. 有效的括号 -----------







    // ---------- 20. 有效的括号 -----------

}
