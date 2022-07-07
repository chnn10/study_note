package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo2datastruct.array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleArray {
    // ---------- 两数之和 ----------
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        int index1 = 0;
        int index2 = 0;

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target-nums[i])};
            } else {
                map.put(nums[i],i);
            }
        }
        return nums;
    }





    // ---------- 删除有序数组中的重复项 ----------
    /**
     * 总结
     * 1：nums[left]表示的是一份新的数组
     * 2：num[left] 和 nums[right]的值相等，right继续遍历，表示的是不要动新的数组
     * 3：如果不相等，表示要将这个右边的数放进left这个数组中，必须先将left++，再赋值
     */
    public int removeDuplicates(int[] nums) {
        // 使用双指针，for循环，右指针从1开始遍历
        // 右指针的值等于左指针的值，左指针不变，然后继续遍历
        // 右指针的值不等于左指针的值，右指针的值赋值给左指针，然后左指针前进一步，然后继续遍历

        int left = 0;
        int right = 1;

        while(right < nums.length) {
            if(nums[left] != nums[right]) {
                nums[++left] = nums[right];
            }
            right++;
        }
        return left + 1;
    }






    // ---------- 27. 移除元素 ----------
    // 使用双指针的方法
    // 就是快指针不等于目标值，就将快指针索引的值复制给慢指针，如果快制作的值不等于目标值就继续往下面走
    public int removeElement(int[] nums, int val) {
        int slow = 0;
        int fast = 0;
        int len = nums.length;

        for (fast = 0; fast < len; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }






    // ----------  53. 最大子数组和 ----------
    // 有一个ret记录当前的数，使用ret累加num
    // 如果ret大于0，保存这个ret
    // 如果ret小于0，将这个num赋值ret，然后继续遍历数组
    // 比较这个temp 和 sum
    public int maxSubArray(int[] nums) {

        // nums = [-2,1,-3,4,-1,2,1,-5,4]

        int temp= nums[0];  // 记得temp这里要使用的是数组里面的数，不能是任何一个数
        int sum = 0;
        for (int num : nums) {
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            // 获取 temp 和 sum 之间比较大的值
            temp = Math.max(temp, sum);
        }
        return temp;
    }






    // ----------   63. 加一 ----------
    /**
     *  不是9，我们就将这个数+1，然后将后面的数全部置为0，然后返回可以了
     *  如果全部是9，就新建一个数组，将最前面的数置为1
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len-1; i >= 0; i--) {
            if (digits[i] != 9) {
                digits[i]++;
                for (int j = i +1; j < len; j++) {
                    digits[j] = 0;
                }
                return digits;
            }
        }

        // 如果全部都是9，就会走到这里
        int[] ret = new int[len+1];
        ret[0] = 1;
        return ret;
    }









    // ----------  88. 合并两个有序数组 ----------
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0;
        int j = 0;
        int k = 0;
        int len = m+n;
        int ret[] = new int[m+n];

        while(i < m || j < n) {
            if (i == m) {
                ret[k] = nums2[j];
                k++;
                j++;
            } else if (j == n) {
                ret[k] = nums1[i];
                k++;
                i++;
            } else if(nums1[i] >= nums2[j]) {
                ret[k] = nums2[j];
                k++;
                j++;
            } else {
                ret[k] = nums1[i];
                k++;
                i++;
            }
        }
        System.arraycopy(ret, 0, nums1, 0, len);
    }






    // ----------  136. 只出现一次的数 ----------
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer num : nums) {
            Integer count = map.get(num);
            count = count == null ? 1 : ++count;
            map.put(num, count);
        }

        // keySet 就是 map 的 key 的集合
        for (Integer i : map.keySet()) {
            if (1 == map.get(i)) {
                return i;
            }
        }
        return -1;
    }








    // ----------  169. 多数元素 ----------
    /**
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     *
     * 输入：nums = [3,2,3]
     * 输出：3
     */

    public int majorityElement(int[] nums) {
        Map<Integer,Integer> map = new HashMap();

        for(int num : nums) {
            // 是否存在这个key，使用的是这个方法
            // 不能使用 map.get(num) != null ，这个是我自己想的，不要使用这个
            if (!map.containsKey(num)) {
                map.put(num,1);
            } else {
                map.put(num, map.get(num)+1);
            }
        }

        for (int key : map.keySet()) {
            if(map.get(key) > nums.length/2) {
                return key;
            }
        }

        return 0;

    }








    // ----------  217. 存在重复元素 ----------
    /**
     * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
     *
     * 输入：nums = [1,2,3,1]
     * 输出：true
     *
     *
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet();
        int cur = 0;
        while (cur < nums.length) {
            if (!set.add(nums[cur])) {
                return true;
            }
            cur++;
        }

        return false;
    }






    // ----------  283. 移动零 ----------
    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     */
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }











    // ----------  448. 找到所有数组中消失的数字 ----------
    /**
     * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
     *
     * 输入：nums = [4,3,2,7,8,2,3,1]
     * 输出：[5,6]
     */
    // 使用set的add方法
    // 调用add方法，如果没有存在这个元素就返回true，如果存在了这个元素，就返回false
    // 我们可以将数组的数放进一个set集合中，然后再调用add方法，看对应的值有没有存在了，不存在就添加到list中

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = (List<Integer>) new ArrayList();
        Set<Integer> set = new HashSet();

        for(int num : nums) {
            set.add(num);
        }

        for(int i = 1; i <= nums.length; i++) {
            if(set.add(i)) {
                list.add(i);
            }
        }

        return list;

    }







    // ----------  485. 最大连续 1 的个数 ----------
    /**
     * 给定一个二进制数组 nums ， 计算其中最大连续 1 的个数。
     * 输入：nums = [1,1,0,1,1,1]
     * 输出：3
     * 解释：开头的两位和最后的三位都是连续 1 ，所以最大连续 1 的个数是 3.
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0, count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount;
    }








    // ---------- 977. 有序数组的平方 -------------
    /**
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     *
     *
     * 示例 1：
     *
     * 输入：nums = [-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     * 解释：平方后，数组变为 [16,1,0,9,100]
     * 排序后，数组变为 [0,1,9,16,100]
     */
    public int[] sortedSquares(int[] nums) {
        int len = nums.length;
        int i = 0;
        int j = len - 1;
        int[] ret = new int[len];
        int pos = len - 1;

        while(i <= j) {
            if(nums[i] * nums[i] >= nums[j] * nums[j]) {
                ret[pos] = nums[i] * nums[i];
                i++;
            } else {
                ret[pos] = nums[j] * nums[j];
                j--;
            }
            pos--;
        }

        return ret;
    }













    // ---------- 1748. 唯一元素的和 -----------

    /**
     * 给你一个整数数组nums。数组中唯一元素是那些只出现恰好一次的元素。
     *
     * 请你返回 nums中唯一元素的 和。nums
     * @return
     */

    public int sumOfUnique(int[] nums) {
        // 遍历整个数组，将出现了数作为下标，使用count数组，++记录一下
        // 遍历count数组，出现的下标的个数 ==1， 将这个下标叠加起来
        // 最后将ret返回
        int len = nums.length;
        int[] count = new int[200];
        int ret = 0;
        for(int i = 0; i < len; i++) {
            count[nums[i]]++;
        }
        for (int j = 0; j < count.length; j++) {
            if (count[j] == 1) {
                ret += j;
            }
        }
        return ret;
    }








    // ---------- 剑指 Offer 03. 数组中重复的数字 ----------
    /**
     * 找出数组中重复的数字。
     *
     *
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     *
     * 示例 1：
     *
     * 输入：
     * [2, 3, 1, 0, 2, 5, 3]
     * 输出：2 或 3
     */
    public int findRepeatNumber(int[] nums) {
        Map<Integer,Integer> map = new HashMap();

        int count = 1;

        for(int num : nums) {
            if(!map.containsKey(num)) {
                map.put(num,count);
            } else {
                map.put(num, map.get(num)+1);
            }
        }

        for(int key : map.keySet()) {
            if (map.get(key) > 1) {
                return key;
            }
        }

        return -1;
    }










}



