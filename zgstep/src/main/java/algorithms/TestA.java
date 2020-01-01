package algorithms;

import java.util.ArrayList;

public class TestA {

    public static void main(String[] args){
        String[] params=new String[]{"X = 10","X = 2","X >= 3","X >= 4","X >= 0","X < 1"};
        String[] param2=new String[]{"X = 1","X = 2","X = 3","X < 4"};
      int result=    cal(4,param2);
        System.out.println(result);
    }

    public static int cal(int length,String[] inequals){
        int num=0;
        int[] results=new int[length];int index=0;
        String item,item2;
        String symbol,symbol2;
        int data,data2;
       for (int i=0;i<length;i++){
            int result=1;
            item=inequals[i];
            symbol=item.split(" ")[1];
            data=Integer.valueOf(item.split(" ")[2]);//具体的值
            if (symbol.equals("=")){//依次判断有几个条件满足
                for (int j=0;j<length;j++){
                    item2=inequals[j];
                    symbol2=item2.split(" ")[1];
                    data2=Integer.valueOf(item2.split(" ")[2]);
                    switch (symbol2){
                        case ">=":
                            if (data>=data2) result++;
                            break;
                        case ">":
                            if (data>data2) result++;
                            break;
                        case "<":
                            if (data<data2) result++;
                            break;
                        case "<=":
                            if (data<=data2) result++;
                            break;
                    }
                }
            }else{
                break;
            }
            results[index++]=result;
       }

       for (int i=0;i<results.length;i++){
           if (results[i]>num){
               num=results[i];
           }
       }
      return num;
    }


}
