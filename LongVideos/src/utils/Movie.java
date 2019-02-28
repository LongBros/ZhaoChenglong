package utils;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Category;
import bean.VideoObj;


public class Movie {
	public static void main(String[] args) throws IOException {
		getCate("dongman");
	}
	/**
	 * 根据分类爬取分类下的类型
	 * @param type
	 * @throws IOException
	 */
	public static ArrayList<Category> getCate(String type) throws IOException {
		ArrayList<Category> gs=new ArrayList<Category>();
		   String url1="https://www.360kan.com/"+type+"/list";
			Document doc1=Jsoup.connect(url1).get();
			Elements cats=doc1.getElementsByClass("js-filter-content");
//			System.out.println(cats);//cats包含总，类型，年代，地区，明星四大分类
			Element cat=cats.get(1);//类型分类
//			System.out.println(cat);
			String scat=cat.toString();
			String[] ca=scat.split("href=\"");
			
			for(int i=1;i<ca.length;i++){
				//分类的链接
				String lcat=ca[i].substring(0,ca[i].indexOf("\" target"));
				//截取类型的id
	            String id=lcat.substring(lcat.indexOf("cat=")+4);
	           
//	            lcat="/LongVideos/SpideThree?cate="+id+"&type="+type+"&pageId=1";
	            //分类名
				String ncat=ca[i].substring(ca[i].indexOf("\">")+2, ca[i].indexOf("</a>"));
//				System.out.println(lcat+ncat+id);
				Category g=new Category();
				g.setId(id);
				g.setName(ncat);
				g.setUrl(lcat);
				gs.add(g);

			}
			return gs;
	}
	
	/**
	 * 爬取排行榜中影视的排名，片名，播放链接，播放量
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<VideoObj> getdata(String url) throws IOException {
		ArrayList<VideoObj> vos=new ArrayList<VideoObj>();
		Document doc=Jsoup.connect(url).get();

		Elements es=doc.getElementsByClass("rank");
//		System.out.println(es.size());//5  影视-电视剧-综艺-电影-动漫
		for(int j=0;j<5;j++){
			//每一个rankitem
			String ss[]=es.get(j).toString().split("<li class=\"rank-item\">");
			for(int i=1;i<ss.length;i++){
//				System.out.println(ss[i]);
				
				String pai="";
				if(ss[i].contains("top3\">")){
					pai=ss[i].substring(ss[i].indexOf("top3\">")+6, ss[i].indexOf("</span>"));
				}else{
					pai=ss[i].substring(ss[i].indexOf("class=\"num\">")+12, ss[i].indexOf("</span>"));
				}
				
				String name=ss[i].substring(ss[i].indexOf("title=\"")+7, ss[i].indexOf("href")-2);
				String hre=ss[i].substring(ss[i].indexOf("href")+6, ss[i].indexOf("\" class"));
				//分离处播放量因播放量均以万为单位，故可根据万分割
				String pn=ss[i].substring(ss[i].indexOf("class=\"vv\"")+11,ss[i].indexOf("万</span>")+1);

				VideoObj vo=new VideoObj();
				vo.setPai(Integer.parseInt(pai));
				vo.setName(name);
				vo.setHref(hre);
				vo.setPn(pn);
				vos.add(vo);
//				System.out.println(pai);System.out.println(name);
//				System.out.println(hre);System.out.println(pn);

			}
//			System.out.println("-----------");
		}
		return vos;
	}
	
	public static String getPUrl(String href) throws IOException{
		String url="";
		
		//得到hre中的网页源码，以从中筛选出想要的信息
		Document docu=Jsoup.connect(href).get();
		Elements btns=docu.getElementsByClass("s-cover");//播放链接在这个id中

		String sb=btns.toString();
//		 PlayerUrl获取该视频的播放链接
		 url=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
			
		
		return url;
	}
}
