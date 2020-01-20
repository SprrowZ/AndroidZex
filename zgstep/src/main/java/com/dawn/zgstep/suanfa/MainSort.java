package com.dawn.zgstep.suanfa;

/**
 * 排序算法
 */
public class MainSort {
    public static void main(String[] args){
        int[] arr={1,5,9,2,4,7,6};
//        insert_sort(arr);

  //      bubble_sort(arr);
   //     select_sort(arr);
        quick_sort(arr,0,arr.length-1);
    }


    /**
     * 插入排序：在插入第n个数的时候假设前N-1数已经排好序
     * @param arr
     */
    public static void insert_sort(int arr[]){

         int temp,i,j;
         for (i=1;i<arr.length;i++){
             j=i;
             temp=arr[j];
             while(j>0&&temp<arr[j-1]){
                 arr[j]=arr[j-1];
                 j--;
             }
             arr[j]=temp;
         }
        sys(arr);
    }

    /**
     * 冒泡排序:前后两个元素比较，大的放后面，每次遍历完最大的元素都会放最后面
     * @param arr
     */
    public static void bubble_sort(int arr[]){
        sys(arr);
        int temp;
        for (int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if (arr[j]>arr[j+1]){
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        sys(arr);
    }

    /**
     * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
     * 第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     * @param arr
     */
    public static void select_sort(int arr[]){
       for (int i=0;i<arr.length-1;i++){
           int minIndex=i;
           for (int j=i+1;j<arr.length;j++){
               if (arr[j]<arr[minIndex]){
                   minIndex=j;
               }
           }
           if (minIndex!=i){
               int temp=arr[i];
               arr[i]=arr[minIndex];
               arr[minIndex]=temp;
           }
       }
       sys(arr);
    }

    /**
     * 快速排序：
     * @param arr
     */
   public static void quick_sort(int[] arr,int left,int right){
      if (left>=right) return ;
      int i=left,j=right,key=arr[i];
      while(i<j){
          while(i<j&&arr[j]>=key)
              j--;
          if (i<j){
              arr[i]=arr[j];
              i++;
          }

          while(i<j&&arr[i]<key)
              i++;
          if (i<j){
              arr[j]=arr[i];
              j--;
          }
          arr[i]=key;
          quick_sort(arr,left,i-1);
          quick_sort(arr,i+1,right);
      }
      sys(arr);
   }



    public static void sys(int arr[]){
        for (int i=0;i<arr.length;i++){
            System.out.println("Index :"+i+"--"+arr[i]);
        }
    }
}
