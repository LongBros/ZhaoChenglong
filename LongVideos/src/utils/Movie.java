package utils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Category;
import bean.SimInfo;
import bean.VideoObj;

/**
 * 1.根据分类爬取分类下的类型
 * 2.爬取排行榜中影视的排名，片名，播放链接，播放量
 * 3.得到某个视频的播放链接
 * 4.搜索视频的结果集
 * 5.得到电视剧，电影，动漫的年份
 * 6.根据影视类型和分类id查询分类名并返回
 * @author Administrator
 *
 */
public class Movie {
	public static void main(String[] args) {
//		getCatgoryNameById("dianying", "100");
		/*try {
			getCate("dongman");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			getdata("https://www.360kan.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 1.根据分类爬取分类下的类型
	 * @param type
	 * @throws IOException
	 */
	public static ArrayList<Category> getCate(String type) throws IOException {
		ArrayList<Category> gs=new ArrayList<Category>();
	    String url1="https://www.360kan.com/"+type+"/list";
		Document doc1=Jsoup.connect(url1).get();
		Elements cats=doc1.getElementsByClass("js-filter-content");
//		System.out.println(cats);//cats包含总，类型，年代，地区，明星四大分类
		Element cat=cats.get(4);
//		System.out.println(cat);
		//以下是类型为href="javascript:;"时的，为链接时均加4
		//电视剧和电影-0类型，1年代，2地区，3明星
		//综艺-0类型，1明星，2地区
		//动漫-0类型，1年代，2地区
		String scat=cat.toString();
		String[] ca=scat.split("href");
		ArrayList<String> cateNames=new ArrayList<String>();
		ArrayList<String> cateIds=new ArrayList<String>();
		for(int i=1;i<ca.length;i++){
			//截取类型的id
            String id=ca[i].substring(ca[i].indexOf(";cat=")+5,ca[i].indexOf("target")-2);
            //分类名
			String ncat=ca[i].substring(ca[i].indexOf("_self")+7, ca[i].indexOf("</a>")-1);
			if(ncat.contains("i")){//部分含有<i class="s-hot-icon"></i> 
				ncat=ncat.substring(0,ncat.indexOf("<i"));
			}
			//System.out.println(ncat);
			cateNames.add("\""+ncat+"\"");
			cateIds.add("\""+id+"\"");

			Category g=new Category();
			g.setId(id);
			g.setName(ncat);
			gs.add(g);
		}
//		System.out.println(cateNames);
//		System.out.println(cateIds);

		return gs;
	}
	
	/**
	 * 2.爬取排行榜中影视的排名，片名，播放链接，播放量
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<VideoObj> getdata(String url) throws IOException {
		ArrayList<VideoObj> vos=new ArrayList<VideoObj>();
		Document doc=Jsoup.connect(url).get();
		Elements es=doc.getElementsByClass("rank");
		//System.out.println(es);

		//System.out.println(es.size());//5  影视-电视剧-综艺-电影-动漫
		for(int j=0;j<4;j++){
			//每一个rankitem
			String ss[]=es.get(j).toString().split("<li class=\"rank-item\">");
			for(int i=1;i<ss.length;i++){
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
				//System.out.println(name);
				VideoObj vo=new VideoObj();
				vo.setPai(Integer.parseInt(pai));
				vo.setName(name);
				vo.setHref(hre);
				vo.setPn(pn);
				vos.add(vo);
			}
		}
		return vos;
	}
	/**
	 * 3.得到某个视频的播放链接
	 * @param href
	 * @return
	 * @throws IOException
	 */
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
	/**
	 * 4.搜索视频的结果集
	 * @param kw
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static ArrayList searchInfo(String kw)throws IOException{
        kw=URLEncoder.encode(kw,"utf-8");//将汉字转为url编码
        System.out.println(kw);
		String url="https://so.360kan.com/index.php?kw="+kw;
		Document doc=Jsoup.connect(url).get();
		Elements es=doc.getElementsByClass("js-dianshi");
		
		ArrayList<SimInfo> sis=new ArrayList<SimInfo>();
		for(Element s:es){
			String ss=s.toString();
//			System.out.println(ss);
//			System.out.println("---------------");
			//视频详情网址
			String uu=ss.substring(ss.indexOf("<a href=\"")+9,ss.indexOf("\" class=\"g-playicon") );
			//视频图片
			String img=ss.substring(ss.indexOf("<img src=\"")+10,ss.indexOf("\" alt=\""));
			//视频名
			String name=ss.substring(ss.indexOf("title=\"")+7,ss.indexOf("\" data-logger=\"ctype"));
			//2018.09.09对获取视频类型做以下修改
//			String type=ss.substring(ss.indexOf("<span class=\"playtype\">")+24,ss.indexOf("]</span>"));
//			String type=ss.substring(ss.indexOf("monitor-desc")+14,ss.indexOf("\" data-logger=\"hidstat"));
			//2018.12.02对获取视频类型做以下修改
			String type=ss.substring(ss.indexOf("class=\"playtype\">")+14,ss.indexOf("class=\"playtype\">")+24);
			System.out.println(uu+"\n"+img+"\n"+name+"\n"+type);
			if(type.contains("电视剧")){
				type="电视剧";
			}else if(type.contains("电影")){
				type="电影";
			}else if(type.contains("综艺")){
				type="综艺";
			}else if(type.contains("动漫")){
				type="动漫";
			}
			SimInfo si=new SimInfo();
			si.setUrl(uu);
			si.setImg(img);
			si.setVname(name);
			si.setType(type);
			sis.add(si);
		}
		//2018.09.09修改，因360将电视剧变为js-dianshi，故需分开爬取
		Elements esi=doc.getElementsByClass("js-longitem");
		for(Element s:esi){
			String ss=s.toString();
//			System.out.println(ss);
//			System.out.println("---------------");
			//视频详情网址
			String uu=ss.substring(ss.indexOf("<a href=\"")+9,ss.indexOf("\" class=\"g-playicon") );
			//视频图片
			String img=ss.substring(ss.indexOf("<img src=\"")+10,ss.indexOf("\" alt=\""));
			//视频名
			String name=ss.substring(ss.indexOf("title=\"")+7,ss.indexOf("\" data-logger=\"ctype"));
			//2018.09.09对获取视频类型做以下修改
//			String type=ss.substring(ss.indexOf("<span class=\"playtype\">")+24,ss.indexOf("]</span>"));
			//2018.12.02对获取视频类型做以下修改
			String type=ss.substring(ss.indexOf("class=\"playtype\">")+14,ss.indexOf("class=\"playtype\">")+24);
			System.out.println(uu+"\n"+img+"\n"+name+"\n"+type);
			if(type.contains("电视剧")){
				type="电视剧";
			}else if(type.contains("电影")){
				type="电影";
			}else if(type.contains("综艺")){
				type="综艺";
			}else if(type.contains("动漫")){
				type="动漫";
			}
			SimInfo si=new SimInfo();
			si.setUrl(uu);
			si.setImg(img);
			si.setVname(name);
			si.setType(type);
			sis.add(si);
		}
		return sis;
	}
	
	/**
	 * 5得到电视剧，电影，动漫的年份
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getYear(String type) throws IOException {
		ArrayList<String> years=new ArrayList<String>();
	    String url1="https://www.360kan.com/"+type+"/list";
		Document doc1=Jsoup.connect(url1).get();
		Elements cats=doc1.getElementsByClass("js-filter-content");
//		System.out.println(cats);//cats包含总，类型，年代，地区，明星四大分类
		Element cat=cats.get(5);
		//以下是类型为href="javascript:;"时的，为链接时均加4
		//电视剧和电影-0类型，1年代，2地区，3明星
		//综艺-0类型，1明星，2地区
		//动漫-0类型，1年代，2地区
		String scat=cat.toString();
		String[] ca=scat.split("href");
		for(int i=1;i<ca.length;i++){
			//截取所有年份
            String year=ca[i].substring(ca[i].indexOf("year=")+5,ca[i].indexOf("target")-2);
			years.add(year);
		}
		return years;
	}
	/**
	 * 6.根据影视类型和分类id查询分类名并返回
	 * 2018-11-12
	 * @param type影视类型
	 * @param id 影视下分类的id
	 * @return 分类对应的分类名
	 */
	public static String getCatgoryNameById(String type,String id) {
		String cateName="";
		//21
		String scates[]={"言情","伦理","喜剧","悬疑","都市","偶像","古装","军事","警匪","历史","宫廷","励志","神话","谍战","青春","家庭","动作","情景","武侠","科幻","其他"};
		String scateIds[]={"101","105","109","108","111","100","104","107","103","112","102","116","117","118","119","120","115","114","106","113","other"};
		//20
		String mcates[]={"喜剧","爱情","动作 ","恐怖","科幻","剧情","犯罪","奇幻","战争 ","悬疑","动画","文艺","伦理","纪录","传记","歌舞","古装","历史","惊悚","其他"};
		String mcateIds[]={"103","100","106","102","104","112","105","113","108","115","107","117","101","118","119","120","121","122","123","other"};
		//22
		String vcates[]={"脱口秀","真人秀 ","选秀","八卦","访谈","情感","生活","晚会","搞笑","音乐","时尚","游戏","少儿","体育","纪实","科教","曲艺","歌舞","财经","汽车","播报","其他"};
		String vcateIds[]={"121","120","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","other"};
		//34
		String ccates[]={"热血","科幻","美少女","魔幻","经典","励志","少儿","冒险","搞笑","推理","恋爱","治愈","幻想","校园","动物","机战","亲子","运动","悬疑","怪物","战争","益智","青春","童话","竞技","动作","社会","友情","真人版","电影版","OVA版","TV版","新番动画","完结动画"};
		String ccateIds[]={"100","134","102","109","135","136","111","107","105","137","101","138","106","104","110","112","131","103","108","113","115","114","123","121","119","126","116","117","127","130","128","129","132","133"};
//		System.out.println(scates.length);System.out.println(scateIds.length);
		if(type.equals("series")){
			for(int i=0;i<scates.length;i++){
				if(id.equals(scateIds[i])){
					cateName=scates[i];
				}
			}
		}else if(type.equals("movie")){
			for(int i=0;i<mcates.length;i++){
				if(id.equals(mcateIds[i])){
					cateName=mcates[i];
				}
			}
		}else if(type.equals("variety")){
			for(int i=0;i<vcates.length;i++){
				if(id.equals(vcateIds[i])){
					cateName=vcates[i];
				}
			}
		}else if(type.equals("cartoon")){
			for(int i=0;i<ccates.length;i++){
				if(id.equals(ccateIds[i])){
					cateName=ccates[i];
				}
			}
		}
		
		return cateName;
	}
	
}
