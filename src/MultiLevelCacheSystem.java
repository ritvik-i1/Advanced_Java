import java.util.*;

public class MultiLevelCacheSystem {

    private LinkedHashMap<String,String> L1 =
            new LinkedHashMap<>(10000,0.75f,true){
                protected boolean removeEldestEntry(
                        Map.Entry<String,String> e){
                    return size()>10000;
                }
            };

    private Map<String,String> L2 = new HashMap<>();
    private Map<String,String> database = new HashMap<>();

    public MultiLevelCacheSystem(){
        database.put("video_123","VideoData123");
        database.put("video_999","VideoData999");
    }

    public String getVideo(String id){

        if(L1.containsKey(id)){
            System.out.println("L1 HIT");
            return L1.get(id);
        }

        if(L2.containsKey(id)){
            System.out.println("L2 HIT");
            String v=L2.get(id);
            L1.put(id,v);
            return v;
        }

        System.out.println("DB HIT");
        String v=database.get(id);

        if(v!=null)
            L2.put(id,v);

        return v;
    }

    public static void main(String[] args){

        MultiLevelCacheSystem c=
                new MultiLevelCacheSystem();

        c.getVideo("video_123");
        c.getVideo("video_123");
        c.getVideo("video_999");
    }
}