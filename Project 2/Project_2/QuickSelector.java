/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adammeller
 */
import java.util.ArrayList;
public class QuickSelector {
    
    public static Integer quickSelect(ArrayList<Integer> array, int index, PivotRule rule){
        int l=0;
        int r = array.size()-1;
        Integer pivot;
        if(array.size()==1)
            return array.get(0);
        while(r >= l)
        {
            ArrayList<Integer> al = copyP(array,l,r); //(ArrayList<Integer>) array.subList(l, r);
            //System.out.println("List: " + array);

              al = part(array,l,r,rule.getPivot(al)+l);
              pivot = al.get(al.size()-1);
              al.remove(al.size()-1);
              //System.out.println("My Pivot rn: " + pivot + " and index: " + index);
            if(pivot==index)
                return al.get(pivot);
            else if(pivot<index)
               return quickSelect(copyP(al,pivot+1,r),index-pivot-1,rule);
            else if(pivot>index)
                return quickSelect(copyP(al,l,pivot-1),index,rule);
             
                
        }
        return null;
    }

    private static ArrayList<Integer> part(ArrayList<Integer> array, int l, int r, Integer pivot) {
        Integer pVal = array.get((int)pivot);
        //System.out.println("My Pivot Value: " + pVal);
        array = swap(array,r,pivot);
        //System.out.println("My Canvas: " + array);
        int index = l;
        for(int i = l; i < r; i++)
        {
            //System.out.println("Checking: " + array.get(i) + " with my PVal");
            if(array.get(i)<pVal)
            {
                array = swap(array,i,index);
                index++;
                //System.out.println("Swapping");
            }
        }
               array = swap(array,r,index); 
               array.add(index);
               //System.out.println("My fixed Array: " + array + " with index " + index);
        return array;
    }
    public int partitionIterative(Integer[] arr, int left, int right) {
        int pivot = arr[right], i = left;
        for (int j = left; j <= right - 1; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }
 
    public void swap(Integer[] arr, int n1, int n2) {
        int temp = arr[n2];
        arr[n2] = arr[n1];
        arr[n1] = temp;
    }
    
    
    private static ArrayList<Integer> copyP(ArrayList<Integer> array, int l, int r)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        while(l<=r)
        {
            al.add(array.get(l));
            l++;
        }
        return al;
    }
    
    private static ArrayList<Integer> swap(ArrayList<Integer> array, int i, int j)
    {
        Integer tempI = array.get(i);
        Integer tempJ = array.get(j);
        array.set(i, tempJ);
        array.set(j,tempI);
        return array;
        
        
    }
        
        
    
    
}
