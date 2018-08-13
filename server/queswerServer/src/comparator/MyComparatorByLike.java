package comparator;

import java.util.Comparator;

import net.sf.json.JSONObject;

public class MyComparatorByLike implements Comparator<JSONObject>{
	 public int compare(JSONObject o1, JSONObject o2) {
	        String key1 = o1.getString("ans_liked");
	        String key2 = o2.getString("ans_liked");
	        int int1 = Integer.parseInt(key1);
	        int int2 = Integer.parseInt(key2);

	        return int2-int1;
	    }
}
