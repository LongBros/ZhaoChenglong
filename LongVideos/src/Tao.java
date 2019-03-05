import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;


public class Tao {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String url="https://eco.taobao.com/router/rest";
//		String url="http://gw.api.taobao.com/router/rest";
		String key="24936018";
		String secret="e8dccee0ce5e8a549630eb79a670d60f";
		
		queryGoods(url, key, secret);
	}
	private static void queryGoods(String url, String key, String secret)
			throws Exception {
		TaobaoClient client = new DefaultTaobaoClient(url, key, secret);
		TbkItemGetRequest req = new TbkItemGetRequest(); 
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick"); 
		req.setQ("女装"); 
		req.setCat("16"); 
		TbkItemGetResponse response = client.execute(req);
		String r=response.getBody();
		System.out.println(r);
		String t[]=r.split("item_url");
//		System.out.println(t.length);
		for(int i=1;i<t.length;i++){
			System.out.println("-----------------------------------------------------------------------");
			System.out.println(t[i]);
			String ur=t[i].substring(3,t[i].indexOf("\",\""));
			ur=ur.replace("\\", "");
			System.out.println(ur);
			String nick=t[i].substring(t[i].indexOf("nick")+7,t[i].indexOf("num_iid")-3);
			System.out.println(nick);
			String num_iid=t[i].substring(t[i].indexOf("num_iid")+9,t[i].indexOf("pict_url")-2);
			System.out.println(num_iid);
			String pict_url=t[i].substring(t[i].indexOf("pict_url")+11,t[i].indexOf("provcity")-3);
			pict_url=pict_url.replace("\\", "");
			System.out.println(pict_url);
			
			String provcity=t[i].substring(t[i].indexOf("provcity")+11,t[i].indexOf("reserve_price")-3);
			System.out.println(provcity);
			String reserve_price=t[i].substring(t[i].indexOf("reserve_price")+16,t[i].indexOf("seller_id")-3);
			System.out.println(reserve_price);
			String seller_id=t[i].substring(t[i].indexOf("seller_id")+11,t[i].indexOf("small_images")-2);
			System.out.println(seller_id);
			
			String small_images=t[i].substring(t[i].indexOf("small_images")+14,t[i].indexOf("title")-2);
			System.out.println(small_images);
			String title=t[i].substring(t[i].indexOf("title")+8,t[i].indexOf("user_type")-3);
			System.out.println(title);
			String user_type=t[i].substring(t[i].indexOf("user_type")+11,t[i].indexOf("volume")-2);
			System.out.println(user_type);
			String volume=t[i].substring(t[i].indexOf("volume")+8,t[i].indexOf("zk_final_price")-2);
			System.out.println(volume);
			String zk_final_price=t[i].substring(t[i].indexOf("zk_final_price")+17);
			System.out.println(zk_final_price);
		}
	}

}
