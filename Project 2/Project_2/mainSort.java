
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adammeller
 */
public class mainSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for(int i = 0; i < 1000000; i++)
        {
            al.add(i);
        }
        
        Collections.shuffle(al);
        
        PivotRule m = new MedianOfMediansRule();
        PivotRule r = new RandomizedRule();
        //System.out.println(m.getPivot(al));
        QuickSelector qs = new QuickSelector();
        
        long startTime  =  System.currentTimeMillis( ) ;
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        System.out.println(qs.quickSelect(al,40000,m));
        long totalTime = System.currentTimeMillis( )  - startTime;
        System.out.println("With MoM Test: " + totalTime);
        
        startTime  =  System.currentTimeMillis( ) ;
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        System.out.println(qs.quickSelect(al,40000,r));
        totalTime = System.currentTimeMillis( )  - startTime;
        System.out.println("With Random Test: " + totalTime);
        
        
    }
    
}
