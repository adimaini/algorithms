package com.baeldung.algorithms.kthlargest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class findKthLargest {

    public static void main(String args[]){
        //get new points arrays
        pointGenerator points_obj = new pointGenerator();
        Point[] points = points_obj.generator();

        //assign the left and right most indices
        int left = 0;
        int right = points.length;
        //assign the median position to 'k'. Also check if the points arr is odd length
        int k_round = points.length%2;
        int k = points.length / 2;
        //if the array is off length, then just assign the median position as k/2 - 1 index
        if(k_round == 1){
            k = (points.length-1) / 2;
        }

        //make new objects and find the median
        findKthLargest quickSelect_obj = new findKthLargest();
        int answer = quickSelect_obj.findKthElementByRandomizedQuickSelect(points, left, right, k);
        
        System.out.println(answer);
    }

    private int partition(Point[] arr, int left, int right) {
        int pivot = arr[right];
        Point[] leftArr;
        Point[] rightArr;

        leftArr = IntStream.range(left, right)
            .filter(i -> arr[i] < pivot)
            .map(i -> arr[i])
            .boxed()
            .toArray(Point[]::new);

        rightArr = IntStream.range(left, right)
            .filter(i -> arr[i] > pivot)
            .map(i -> arr[i])
            .boxed()
            .toArray(Point[]::new);

        int leftArraySize = leftArr.length;
        System.arraycopy(leftArr, 0, arr, left, leftArraySize);
        arr[leftArraySize + left] = pivot;
        System.arraycopy(rightArr, 0, arr, left + leftArraySize + 1, rightArr.length);

        return left + leftArraySize;
    }

    public int findKthElementByRandomizedQuickSelect(Point[] arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = randomPartition(arr, left, right);
            if (pos - left == k) {
                return arr[pos];
            }
            if (pos - left > k) {
                return findKthElementByRandomizedQuickSelect(arr, left, pos - 1, k);
            }
            return findKthElementByRandomizedQuickSelect(arr, pos + 1, right, k - pos + left - 1);
        }
        return 0;
    }

    private int randomPartition(Point[] arr, int left, int right) {
        int n = right - left + 1;
        int pivot = (int) (Math.random() * n);
        swap(arr, left + pivot, right);
        return partition(arr, left, right);
    }

    // not used for our purpose for this project but code still good to have as reference
    // public int findKthLargestBySorting(Integer[] arr, int k) {
    //     Arrays.sort(arr);
    //     int targetIndex = arr.length - k;
    //     return arr[targetIndex];
    // }

    // public int findKthLargestBySortingDesc(Integer[] arr, int k) {
    //     Arrays.sort(arr, Collections.reverseOrder());
    //     return arr[k - 1];
    // }

}