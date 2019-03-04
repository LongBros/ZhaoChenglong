package others;

//import java.io.UnsupportedEncodingException;

import dao.BlogsDao;
import net.sf.json.JSONObject;
import util.AddressUtils;

public class Test {
	public static void main(String[] args) throws Exception{
		getAddressByIp();
		
	}
	public static void getAddressByIp() throws Exception {  
	    // 鍙傛暟ip  
	    String ip1 = "125.46.111.78";  //骞抽《灞�
	    String ip2 = "223.104.107.230"; //鍛ㄥ彛
	    String ip3 = "223.104.105.81"; //閮戝窞
	    String ip4 = "219.157.79.110"; //骞抽《灞�
	    String ip5 = "117.136.44.153"; //閮戝窞
	    String ip6 = "117.136.61.120"; //閮戝窞

	    
	    String ip71="115.57.145.154";//郑州宾馆无线
	    
	    String ip11="219.157.79.134";//2018-05-11  Mine
	    String ip12="218.28.76.37";
	    
	    String ip21="125.46.187.1";//2018-05-10  Yinger

	    String ip31="117.136.104.231";//竟然未找到省份和城市
	    // json_result鐢ㄤ簬鎺ユ敹杩斿洖鐨刯son鏁版嵁  
	    String json_result = null;  
	    try {  
	        json_result = AddressUtils.getAddresses("ip=" + ip31, "utf-8");  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    JSONObject json = JSONObject.fromObject(json_result);  
//	    System.out.println("json鏁版嵁锛�" + json);  
	    String country = JSONObject.fromObject(json.get("data")).get("country").toString();  
	    String region = JSONObject.fromObject(json.get("data")).get("region").toString();  
	    String city = JSONObject.fromObject(json.get("data")).get("city").toString();  
	    String county = JSONObject.fromObject(json.get("data")).get("county").toString();  
	    String isp = JSONObject.fromObject(json.get("data")).get("isp").toString();  
	    String area = JSONObject.fromObject(json.get("data")).get("area").toString();  
//	    System.out.println("鍥藉锛�" + country);  
//	    System.out.println("鍦板尯锛�" + area);  
//	    System.out.println("鐪佷唤: " + region);  
//	    System.out.println("鍩庡競锛�" + city);  
//	    System.out.println("鍖�鍘匡細 " + county);  
//	    System.out.println("浜掕仈缃戞湇鍔℃彁渚涘晢锛�" + isp);  
	      
//	    String address = country + "/";  
//	    address += region + "/";  
//	    address += city + "/";  
//	    address += county;  
//	    System.out.println(address);  
	}  
}
