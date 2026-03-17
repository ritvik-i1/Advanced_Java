import java.util.*;

class Transaction {
    int id;
    int amount;

    Transaction(int id,int amount){
        this.id=id;
        this.amount=amount;
    }
}

public class TwoSumFraudDetection {

    public List<int[]> findTwoSum(List<Transaction> tx, int target){

        Map<Integer,Integer> map=new HashMap<>();
        List<int[]> result=new ArrayList<>();

        for(Transaction t:tx){

            int complement=target-t.amount;

            if(map.containsKey(complement)){
                result.add(new int[]{map.get(complement),t.id});
            }

            map.put(t.amount,t.id);
        }

        return result;
    }

    public static void main(String[] args){

        TwoSumFraudDetection t=new TwoSumFraudDetection();

        List<Transaction> list=new ArrayList<>();
        list.add(new Transaction(1,500));
        list.add(new Transaction(2,300));
        list.add(new Transaction(3,200));

        var res=t.findTwoSum(list,500);

        for(int[] p:res)
            System.out.println(p[0]+" "+p[1]);
    }
}