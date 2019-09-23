package com.aspire.commons.sort;

import com.google.common.primitives.Ints;

import java.util.Arrays;



public class SortTest {

    public static void main(String[] args) {
        //int[] nums = new int[]{55, 11, 44, 33, 77, 66, 99, 22, 88};
        //int[] temp = new int[nums.length];
        //Ints.asList(mpSort(nums)).forEach(System.out::println);
        //Ints.asList(checkSort(nums)).forEach(System.out::println);
        //Ints.asList(insertSort(nums)).forEach(System.out::println);
        //Ints.asList(shellSort(nums)).forEach(System.out::println);
        //mergeSort(nums,0,nums.length-1,temp);
        //Ints.asList(temp).forEach(System.out::println);
        int[] nums = new int[]{3,3};
        twoSum(nums,6);

    }

    /**
     * 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    private static int[] mpSort(int[] nums) {
        int[] newNums = Arrays.copyOf(nums, nums.length);
        for (int i = 1; i < newNums.length; i++) {
            for (int j = 0; j < newNums.length - i; j++) {
                if (newNums[j] > newNums[j + 1]) {
                    int temp = newNums[j];
                    newNums[j] = newNums[j + 1];
                    newNums[j + 1] = temp;
                }
            }
        }
        return newNums;
    }

    /**
     * 选择排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。
     * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 重复第二步，直到所有元素均排序完毕。
     */
    private static int[] checkSort(int[] nums) {
        int[] newNums = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < newNums.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < newNums.length; j++) {
                if (newNums[j] < newNums[min]) {
                    min = j;
                }
            }
            if (i != min) {
                int temp = newNums[i];
                newNums[i] = newNums[min];
                newNums[min] = temp;
            }
        }
        return newNums;
    }

    /**
     * 插入排序
     * 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。
     * 从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。
     * 如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面
     */
    private static int[] insertSort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < arr.length; i++) {
            // 记录要插入的数据
            int tmp = arr[i];
            // 从已经排序的序列最右边的开始比较，找到比其小的数
            int j = i;
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            // 存在比其小的数，插入
            if (j != i) {
                arr[j] = tmp;
            }
        }
        return arr;
    }

    /**
     * 希尔排序
     * 选择一个增量序列 t1，t2，……，tk，其中 ti > tj, tk = 1；
     * 按增量序列个数 k，对序列进行 k 趟排序；
     * 每趟排序，根据对应的增量 ti，将待排序列分割成若干长度为 m 的子序列，分别对各子表进行直接插入排序。
     * 仅增量因子为 1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     */
    private static int[] shellSort(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);

        int gap = 1;
        while (gap < arr.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 3);
        }
        return arr;
    }


    /**
     * 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列；
     * 设定两个指针，最初位置分别为两个已经排序序列的起始位置；
     * 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置；
     * 重复步骤 3 直到某一指针达到序列尾；
     * 将另一序列剩下的所有元素直接复制到合并序列尾。
     */
    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        //参数分别为 待排序数组，左指针，有指针，辅助数组
        //因为使用了递归，所以我们必须规定递归条件否则将进行无线循环
        if (left < right) {
            //将数组进行分割
            int mid = (left + right) / 2;
            //对左子数组继续进行归并排序
            mergeSort(arr, left, mid, temp);
            //对右子数组继续进行归并排序
            mergeSort(arr, mid + 1, right, temp);
            //将数组进行合并
            Merge(arr, left, mid, right, temp);
        }

    }

    //合并函数
    private static void Merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        //t为辅助数组的索引
        int t = 0;
        while (i <= mid && j <= right) {
            //当二者都没有到达最后一位时，进行比较并向辅助数组复制
            if (arr[i] < arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        //当其中一个数组复制完毕后，将另一个数组内的数组全部复制进辅助数组
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        t = 0;
        //将辅助数组内已经排好的数据全部复制进原数组，排序完成
        while (left <= right) {
            arr[left++] = temp[t++];
        }

    }


    /**
     * 递归排序
     * 从数列中挑出一个元素，称为 "基准"（pivot）;
     * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。
     * 在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
     * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序；
     */
    private static void quicksort(int nums[], int start, int end) {
        //定义一个中间值
        int dp;

        //第一次排序找到中间值（当前后两个指针相遇，那么这个数即为中间值）
        if (start < end) {
            dp = partition(nums, start, end);
            quicksort(nums, start, dp - 1);
            quicksort(nums, dp + 1, end);
        }
    }

    private static int partition(int nums[], int start, int end) {
        //首先把初始值默认为中间值
        int pivot = nums[start];
        while (start < end) {
            //1.从后往前找，直到找到有小于基准数的
            while (start < end && nums[end] >= pivot){
                end--;
            }
            if (start < end){
                nums[start] = nums[end];
                start++;
            }
            //2.从前往后找，直到找到有大于基准数的
            while (start < end && nums[start] <= pivot){
                start++;
            }
            //把找到的数赋给最后指针，然后指针-1
            if (start < end){
                nums[end] = nums[start];
                end--;
            }
        }
        nums[start] = pivot;
        return start;
    }

    private static int[] twoSum(int[] nums, int target) {
        int[] results = null;
        for (int i=0;i<nums.length;i++){
            for (int j=0;j<nums.length;j++){
                if (nums[i]+nums[j] == target && i != j){
                    if (i<j)
                        results = new int[]{i,j};
                    if (i>j)
                        results = new int[]{j,i};
                }
            }
        }
        return results;
    }

}
