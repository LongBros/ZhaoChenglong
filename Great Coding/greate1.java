import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *所用知识点：二维数组，list和map，双层for循环
 *
 */
public class Test {
	static String s[]={"name","sex","birth","age"};

	public static void main(String[] args){
		dataExport();
	}
	public static List dataExport(){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		String person[][]={
				{"ZhaoYongXuan","male","1973-10-16"},
				{"LianXiangping","female","1972-10-16"},
				{"ZhaoChenglong","male","1995-10-16"},
				{"ZhaoShuanglong","male","1999-10-16"},
				{"ZhaoYanhui","female","2000-04-05"}
		};
		for(int i=0;i<person.length;i++){
			Map<String,String> map=new HashMap<String, String>();
			for(int j=0;j<s.length;j++){
				if(j!=3){
					map.put(s[j], person[i][j]);			
				}else{//年龄是计算来的，而非在数组中
					map.put(s[j], (2019-Integer.parseInt(person[i][j-1].substring(0, 4)))+"");
				}
			}
			list.add(map);
		}		
		return list;
	}

}