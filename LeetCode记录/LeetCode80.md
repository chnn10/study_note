LeetCode80

- 题目
  - 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。 
- 思路
  - 从第二数开始遍历
  - 如果当前的数不等于前两个位置的数，就将这个数复制给给慢指针的数组
  - 【注意】我们需要检查的是上上个保留的元素nums[slow-2]和当前的元素是不是相同，不相同的就复制给nums[slow]
- 代码
      int removeDuplicates(int* nums, int numsSize){
      
          if (numsSize <= 2) {
              return numsSize;
          }
      
          int fast = 2;
          int slow = 2;
      
          // 【注意】是拿当前的元素，和slow-2位置的元素进行比较
          while(fast < numsSize) {
              if(nums[fast] != nums[slow-2]) {  // 注意，这里不是 nums[fast] != nums[fast-2]
                  nums[slow] = nums[fast];
                  slow++;
              }
              fast++;
          }
      
          return slow;
      }
  
