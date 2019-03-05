package utils;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dao.UtilDao;

/**
 * -1爬取四大类型视频详细信息，以视频名为主
 * 0爬取热播电影
 * 360影视网站内容数据爬取
 * 1.爬取主界面的基本信息
 * 2.爬取播放界面单个视频的剧集，详情介绍等信息
 * 3.360视频搜索功能
 * 4.抓取电视剧，电影，综艺，动漫的类型的链接
 * 5.抓取电视剧或动漫的剧集的链接
 * @author 赵成龙
 * @time  2018-04-27 02:22整合
 *
 */
public class ThreeTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String url="https://www.360kan.com";
//		Document doc=Jsoup.connect(url).get();
//      Elements s=doc.getElementsByClass("list"); 
//      System.out.println(s);
//		Movie.searchInfo("哆啦A梦");
//		detailInfo();
//		newSerial();
		spideInfo1();
 	}
	
	/**
	 * -1爬取四大类型视频详细信息，以视频名为主
	 * 将类型放入第一层循环，但需判断对应类型的视频名在不同位置
	 * @date 2018-11-06
	 * @throws IOException 
	 * 
	 */
	public static void spideInfo1() throws IOException{
		String type[]={"dianshi","dianying","zongyi","dongman"};
		String t[]={"电视剧","电影","综艺","动漫"};
		String main="https://www.360kan.com";
		//双重循环实现逐类型逐页爬取影视信息
		for(int i=0;i<4;i++){
			for(int j=1;j<26;j++){
				System.out.println("开始爬取"+type[i]+"第"+j+"页的视频信息");
				String url="https://www.360kan.com/"+type[i]+"/list.php?rank=rankhot&cat=all&area=all&year=all&pageno="+j;
				Document doc=Jsoup.connect(url).get();
				Elements plays=doc.getElementsByClass("js-tongjic"); 
				for(Element play: plays){
					String p=play.toString();
					String[] s=p.split(">");
				    String hr=s[0]+">";
				    //视频详细信息界面链接
				    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
					String sr=s[2]+">";
					//图片链接
					String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
					//视频名，更新情况，年份，评分，演员
					String name="",update="",year="",score="",actor="";
					//期别，期别详情
					String stage="",desc="";
				    if(type[i].equals("dianshi")){
				    	if(p.contains("point")){
				    		update=s[5].substring(0,4);	//更新情况	
							score=s[7].split("<")[0];//评分				  
							name=s[13].split("<")[0];					  
							actor=s[16].split("<")[0];//主演
				    	}else{
				    		update=s[5].substring(0, 4);					  
							name=s[11].split("<")[0];					  
							actor=s[14].split("<")[0];
				    	}
				    }else if(type[i].equals("dianying")){
				    	if(p.contains("付费")){
					    	//有评分的和无评分的，不一样
							if(p.contains("point")){//有评分
								year=s[7].substring(0,4);					  
								name=s[15].split("<")[0];					  
								score=s[16].split("<")[0];					  
								actor=s[18].split("<")[0];				  
							}else{
								year=s[7].substring(0,4);					  
								name=s[13].split("<")[0];					  
								actor=s[16].split("<")[0];					  
							}
						}else{
							if(p.contains("point")){//有评分
								year=s[5].substring(0, 4);					  
								name=s[13].split("<")[0];					  
								score=s[7].split("<")[0];
								actor=s[16].split("<")[0];					  
							}else{
								year=s[5].substring(0, 4);					  
								name=s[11].split("<")[0];					  
								actor=s[14].split("<")[0];			  
							}
						}
				    }else if(type[i].equals("zongyi")){
				    	if(p.contains("付费")){
				    		//期别
					    	stage=s[7].substring(0, s[7].indexOf("</span"));	//期别				  
							//名
					    	name=s[13].split("<")[0];	
					    	//期别介绍
							desc=s[16].split("<")[0];//期别介绍
				    	}else{
				    		//期别
							stage=s[5].substring(0, s[5].indexOf("</span"));
							name=s[11].split("<")[0];
							desc=s[14].split("<")[0];
				    	}
				    }else if(type[i].equals("dongman")){
						name=s[11].substring(0, s[11].indexOf("</span"));	
						update=s[5].substring(0, s[5].indexOf("</span"));					  
						name=s[11].substring(0, s[11].indexOf("</span"));	
				    }
					
					String sql="insert into video (v_Name,v_Type,v_Link,v_Img) values('"
							+name+"','"+t[i]+"','"+hre+"','"+src+"');";
					UtilDao.insertData(sql);
					System.out.println(sql);
					
				}
			}
		}
	}
	/**
	 * 爬取四大类型视频详细信息，以视频名为主
	 * @date 2018-11-06
	 * @throws IOException
	 */
	public static void spideInfo2() throws IOException{
		String main="https://www.360kan.com";
		//四个循环实现逐类型分别逐页爬取影视信息
		//第一个：爬取电视剧的
		for(int j=1;j<26;j++){
			System.out.println("开始爬取电视剧的第"+j+"页的视频信息");
			String url="https://www.360kan.com/dianshi/list.php?rank=rankhot&cat=all&area=all&year=all&pageno="+j;
			Document doc=Jsoup.connect(url).get();
			Elements plays=doc.getElementsByClass("js-tongjic"); 
			for(Element play: plays){
				String p=play.toString();
				String[] s=p.split(">");
			    String hr=s[0]+">";
			    //视频详细信息界面链接
			    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				String sr=s[2]+">";
				//图片链接
				String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
				String year,name="",score,actor;
		    	if(p.contains("point")){
		    		name=s[13].split("<")[0];
		    	}else{
		    		name=s[11].split("<")[0];
		    	}
				System.out.println(name);//hre,src
			}
		}
		//第二个：爬取电影的
		for(int j=1;j<26;j++){
			System.out.println("开始爬取电影的第"+j+"页的视频信息");
			String url="https://www.360kan.com/dianshi/list.php?rank=rankhot&cat=all&area=all&year=all&pageno="+j;
			Document doc=Jsoup.connect(url).get();
			Elements plays=doc.getElementsByClass("js-tongjic"); 
			for(Element play: plays){
				String p=play.toString();
				String[] s=p.split(">");
			    String hr=s[0]+">";
			    //视频详细信息界面链接
			    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				String sr=s[2]+">";
				//图片链接
				String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
				String year,name="",score,actor;
				if(p.contains("付费")){
			    	//有评分的和无评分的，不一样
					if(p.contains("point")){//有评分
						name=s[15].split("<")[0];					  
					}else{
						name=s[13].split("<")[0];					  
					}
				}else{
					if(p.contains("point")){//有评分
						name=s[13].split("<")[0];					  
					}else{
						name=s[11].split("<")[0];					  
					}
				}
				System.out.println(name);//hre,src
			}
		}
		//第三个：爬取综艺的
		for(int j=1;j<26;j++){
			System.out.println("开始爬取综艺的第"+j+"页的视频信息");
			String url="https://www.360kan.com/dianshi/list.php?rank=rankhot&cat=all&area=all&year=all&pageno="+j;
			Document doc=Jsoup.connect(url).get();
			Elements plays=doc.getElementsByClass("js-tongjic"); 
			for(Element play: plays){
				String p=play.toString();
				String[] s=p.split(">");
			    String hr=s[0]+">";
			    //视频详细信息界面链接
			    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				String sr=s[2]+">";
				//图片链接
				String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
				String year,name="",score,actor;
				if(p.contains("付费")){
		    		name=s[13].split("<")[0];
		    	}else{
		    		name=s[11].split("<")[0];
		    	}
				System.out.println(name);//hre,src
			}
		}
		//第四个：爬取动漫的
		for(int j=1;j<26;j++){
			System.out.println("开始爬取动漫的第"+j+"页的视频信息");
			String url="https://www.360kan.com/dianshi/list.php?rank=rankhot&cat=all&area=all&year=all&pageno="+j;
			Document doc=Jsoup.connect(url).get();
			Elements plays=doc.getElementsByClass("js-tongjic"); 
			for(Element play: plays){
				String p=play.toString();
				String[] s=p.split(">");
			    String hr=s[0]+">";
			    //视频详细信息界面链接
			    String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				String sr=s[2]+">";
				//图片链接
				String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));
				String year,name="",score,actor;
				name=s[11].substring(0, s[11].indexOf("</span"));	
				System.out.println(name);//hre,src
			}
		}
	}
	
    /**
     * 0爬取热播电影
     * @throws IOException
     */
	private static void hotfilm() throws IOException {
		String url="https://www.360kan.com";
		Document doc=Jsoup.connect(url).get();
        Elements s=doc.getElementsByClass("rmcontent"); 
//      System.out.println(s);
        String ss=s.toString();
        String hot[]=ss.split("</li>");
        for(int i=0;i<hot.length-1;i++){
        	String href=hot[i].substring(hot[i].indexOf("href")+6,hot[i].indexOf("\" class=\"js-link\">"));
//        	String year=hot[i].substring(hot[i].indexOf("class=\"w-newfigure-hint\">")+25,hot[i].indexOf("</span></div>"));
        	String img=hot[i].substring(hot[i].indexOf("data-src")+10,hot[i].indexOf("\" alt="));
        	String title="";
        	String score="";
        	if(hot[i].contains("class=\"s2")){//有评分
            	title=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span><span"));
                score=hot[i].substring(hot[i].indexOf("class=\"s2")+11,hot[i].indexOf("</span></p"));
        	}else{//无评分
            	title=hot[i].substring(hot[i].indexOf("class=\"s1")+11,hot[i].indexOf("</span></p"));
        	}
        	System.out.println(href+"\n"+img+"\n"+title+"\n"+score);
//        	String des=hot[i].substring(hot[i].indexOf("class=\"w-newfigure-desc\">")+25,hot[i].indexOf("</p></div></a>"));
        	System.out.println("-------------------------------");
        }
	}
	
	public static void newSerial() throws IOException{
		String url="https://www.360kan.com";
		Document doc=Jsoup.connect(url).get();
        Elements es=doc.getElementsByClass("w-newfigure-list"); 
        Element e=es.get(0);
//        System.out.println(e);
        String se=e.toString();
        String ne[]=se.split("</li>");
        for(int i=0;i<ne.length-1;i++){
        	String href=ne[i].substring(ne[i].indexOf("href")+6,ne[i].indexOf("\" class=\"js-link\">"));
        	String img=ne[i].substring(ne[i].indexOf("data-src")+10,ne[i].indexOf("\" alt="));
        	String update=ne[i].substring(ne[i].indexOf("w-newfigure-hint")+18,ne[i].indexOf("</span>"));
        	String title="";
        	String score="";
        	if(ne[i].contains("class=\"s2")){//有评分
            	title=ne[i].substring(ne[i].indexOf("class=\"s1")+11,ne[i].indexOf("</span><span"));
                score=ne[i].substring(ne[i].indexOf("class=\"s2")+11,ne[i].indexOf("</span></p"));
        	}else{//无评分
            	title=ne[i].substring(ne[i].indexOf("class=\"s1")+11,ne[i].indexOf("</span></p"));
        	}
//        	System.out.println(title);
//        	System.out.println(score);
//        	System.out.println(href);
//        	System.out.println(img);
        	System.out.println(update);


        }
	}
	
	//1.爬取主页展示的内容，包括图片链接，视频链接，视频的播放链接，年份，片名，评分，导演
	private static void spideIndex() throws IOException {
			String url="https://www.360kan.com/dianying/list.php";//?rank=rankhot&cat=all&area=all&year=all&pageno=2
			String main="https://www.360kan.com";

			 Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
	         Elements plays=doc.getElementsByClass("js-tongjic"); 
	         
	         for(Element play: plays){
				  //  Attribute

//				  String href=play.attr("href");
				  String p=play.toString();
				 
				  //写一个方法，取出href里面的内容，src里面的内容，以及span里的年份，视频名和评分，还有演员
				  String[] s=p.split(">");
				  String hr=s[0]+">";
				  String hre=main+hr.substring(hr.indexOf("href=")+6, hr.indexOf("\">"));
				  
				  String sr=s[2]+">";
				  String src=sr.substring(sr.indexOf("src=")+5, sr.indexOf("\">"));

				  String year,name,score,actor;
				  if(p.contains("付费")){
					  //年份
					  year=s[6].substring(0,4);
					  //片名
					  name=s[11].split("<")[0];
					  //评分
					  score=s[13].split("<")[0];
					  //演员
					  actor=s[16].split("<")[0];
				  }else{
					  year=s[4].substring(0, 4);					  
					  name=s[9].split("<")[0];					  
					  score=s[11].split("<")[0];
					  actor=s[14].split("<")[0];
				  }
				 
				  ///LongVideos/player.jsp?href=
				  System.out.println(hre);
				  Document docu=Jsoup.connect(hre).get();
				  Element btn=docu.getElementById("js-site-wrap");
				  String sb=btn.toString();
//				  System.out.println(sb);
				  //PlayerUrl
				  String pu=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
				  System.out.println(pu);
				  System.out.println("--------------------");

				  
		  }
		}
	
	/**
	 * 2.抓取正在播放的视频的详细信息
	 * 电影，先演员后导演
	 * 电视剧，先导演后演员
	 * 动漫，先导演后人物
	 * 综艺，播出的卫视和主持
	 * @author Administrator
	 *
	 */
	public static void detailInfo() throws Exception{
//		 String url="https://www.360kan.com/tv/QLVqan7lRz8mMX.html";//电视剧
		 String url="https://www.360kan.com/va/ZsEob6Jv7JQ3Dz.html";//综艺   
		 Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
		   Elements s=doc.getElementsByClass("top-info");//电影，综艺
//		   Elements s=doc.getElementsByClass("s-top-info");//电视剧,动漫

		   String ss=s.toString();
//		   System.out.println(ss);
		   //片名
		   String title=ss.substring(ss.indexOf("<h1>")+4,ss.indexOf("</h1>"));
//		   System.out.println(title);
		   
		   //评分
		   String score=ss.substring(ss.indexOf("<span class=\"s\">")+16,ss.indexOf("</span>"));
//		   System.out.println(score);

		 //简介
		   String desc=ss.substring(ss.indexOf("<p class=\"item-desc js-close-wrap\" style=\"display:none;\">")+73,ss.indexOf("<a href=\"#\" class=\"js-close btn\">"));
//		   System.out.println(desc);
		   
		   Element s8=doc.getElementById("js-desc-switch");
//		   System.out.println(s8);
		   String[] r=s8.toString().split("<span>");
//		   System.out.println("------------------------");
		   
		   
		   /**
		    * 电影：类型，年代，地区，演员，导演
		    * 电视剧：分集介绍，类型，年代，地区，导演，演员
		    * 动漫：分集介绍，类型，年代，地区，导演，人物
		    */		   
	/*//电影
		   //年份
		   System.out.println(r[1].substring(10, r[1].indexOf("</p>")));

		   System.out.println("------------------------");
		   //地区
		   System.out.println(r[2].substring(10,r[2].indexOf("</p>")));
		   System.out.println("------------------------");
		   //演员
		   System.out.println(r[3].substring(10,r[3].indexOf("</p>")));

		   System.out.println("------------------------");
		   //导演
		   String lD=r[4].substring(r[4].indexOf("href")+6, r[4].indexOf("\">"));
		   String director=r[4].substring(r[4].indexOf("\">")+2, r[4].indexOf("</a>"));
		   System.out.println(lD+director);

		   System.out.println("------------------------");
		   //简介
//		   System.out.println(r[5]);
		   */
	   
		   //综艺
		 //类型
		   System.out.println(r[0].substring(r[0].indexOf("_blank")+8, r[0].indexOf("</p>")-5));
		   System.out.println("------------------------");
		   //年份
		   System.out.println(r[1].substring(11, r[1].indexOf("</p>")));
		   System.out.println("------------------------");
		 //地区
		   System.out.println(r[2].substring(11,r[2].indexOf("</p>")));
		   System.out.println("------------------------");
//		   //播出电台
		   System.out.println(r[3].substring(11, r[3].indexOf("</p>")));
		   System.out.println("------------------------");
		   //主持
		   System.out.println(r[5]);
//		   System.out.println(r[5].substring(10, r[5].indexOf("</p>")));
		   /*2018.09.09日新加代码，因网站中加入明星项目，主持项目被注释，导致抛出异常，在此修改为以下明星项目，由原来的r[5]改为r[6]*/
//		   System.out.println(r[6]);
		   System.out.println("------------------------");
		   System.out.println(r[6].substring(10, r[6].indexOf("</p>")));
	/*//电视剧   
		   
		   //类型
		   System.out.println(r[1].substring(10, r[1].indexOf("</p>")));

		   System.out.println("------------------------");
		   //年份
		   System.out.println(r[2].substring(10,r[2].indexOf("</p>")));
		   System.out.println("------------------------");
		   //地区
		   System.out.println(r[3].substring(10,r[3].indexOf("</p>")));

		   System.out.println("------------------------");
		   //导演
		   String lD=r[4].substring(r[4].indexOf("href")+6, r[4].indexOf("\">"));
		   String director=r[4].substring(r[4].indexOf("\">")+2, r[4].indexOf("</a>"));
		   System.out.println(lD+director);

		   System.out.println("------------------------");
		   //演员
		   System.out.println(r[5]);*/

	}
	
    //3.提供360视频搜索功能
	private static void searchVideo() throws IOException {
		//https://so.360kan.com/index.php?kw=%E8%BF%9C%E5%A4%A7%E5%89%8D%E7%A8%8B&from=
				String url="https://so.360kan.com/index.php?kw=下一站";
				Document doc=Jsoup.connect(url).get();
//			System.out.println(doc);
				Elements es=doc.getElementsByClass("js-longitem");
//			System.out.println(es);
			
				for(Element s:es){
//				System.out.println(s);
					System.out.println("----------------------------------------------------");
					String ss=s.toString();
					//视频详情网址
					String u=ss.substring(ss.indexOf("<a href=\"")+9,ss.indexOf("\" class=\"g-playicon") );
					//得到播放链接
					Document docu=Jsoup.connect(u).get();
					  Elements btns=docu.getElementsByClass("s-cover");//播放链接在这个id中
					  String pu=null;

					  String sb=btns.toString();
//				    System.out.println(sb);
//					  PlayerUrl获取该视频的播放链接
					  pu=sb.substring(sb.indexOf("href=\"")+6,sb.indexOf("\" class=\""));
//				    System.out.println(pu);
					
					//视频图片
					String img=ss.substring(ss.indexOf("<img src=\"")+10,ss.indexOf("\" alt=\""));
					//视频名
					String name=ss.substring(ss.indexOf("title=\"")+7,ss.indexOf("\" data-logger=\"ctype"));
//				    System.out.println(name);
					String type=ss.substring(ss.indexOf("<span class=\"playtype\">")+24,ss.indexOf("]</span>"));
//					System.out.println(type);
					
				}
	}
	
	//4.抓取电视剧，电影，综艺，动漫的类型的链接
	private static void spideType() throws IOException {
		String type="dianshi";
		String url="https://www.360kan.com/"+type+"/list";
		Document doc=Jsoup.connect(url).get();
		Elements cats=doc.getElementsByClass("js-filter-content");
//		System.out.println(cats);//cats包含总，类型，年代，地区，明星四大分类
		Element cat=cats.get(1);//类型分类
//		System.out.println(cat);
		String scat=cat.toString();
		String[] ca=scat.split("href=\"");
		for(int i=1;i<ca.length;i++){
//			System.out.println(ca[i]);
			//分类的链接
			//https://www.360kan.com/dianshi/list?year=all&amp;area=all&amp;act=all&amp;cat=101
			//将链接中的&amp;截去
			String lcat=ca[i].substring(0,ca[i].indexOf("\" target"));
//			lcat="https://www.360kan.com/dianshi/list?year=all&area=all&act=all&cat="+lcat.substring(lcat.indexOf("cat=")+4);
            lcat="https://www.360kan.com/"+type+"/list?year=all&area=all&act=all&cat="+lcat.substring(lcat.indexOf("cat=")+4);
			//分类名
			String ncat=ca[i].substring(ca[i].indexOf("\">")+2, ca[i].indexOf("</a>"));
			System.out.println(lcat+"    "+ncat);
			System.out.println("---------------------------");
		}
		int num=ca.length-1;
		System.out.println(num);
	}
	/**
	 * 5抓取电视剧或动漫的剧集的链接
	 * @author Administrator
	 *
	 */
	private static void spideSeries() throws IOException {
		String url="https://www.360kan.com/ct/O0DncMDbLYC2DD.html";//?rank=rankhot&cat=all&area=all&year=all&pageno=2
		   Document doc=Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
		   Elements s=doc.getElementsByClass("s-top-list-ji");
		   String ss=s.toString();
		   System.out.println(ss);
//		   System.out.println("------------------------");
		   String se="";
		   //1.集数多有折叠，但更新完结的2.集数多有折叠，未更新完3集数少，更新完4.集数少，未更新完
		   if(ss.contains("display:none")){//集数较多，折叠的
			    se=s.toString().substring(ss.indexOf("display:none")+15,ss.indexOf("收起</a>")-63);

		   }else if(ss.contains("<i class=\"ico-yugao\">")){//有预告片的
			   
			    se=s.toString().substring(ss.indexOf("js-tab")+14,ss.indexOf("<i class=\"ico-yugao\">"));
		   }else{
			    se=s.toString().substring(ss.indexOf("js-tab")+14);

		   }
		   System.out.println(se);

		   String[] hs=se.split("href=\"");
		   String u="";
		   for(int i=1;i<hs.length;i++){
			   //<a data-num="40" data-daochu="to=qq" http://v.qq.com/x/cover/uutv6yv0c4jn95h/d002608a679.html?ptag=360kan.tv.free"> 40 <i class="ico-new"></i> </a> 
			   u=hs[i].substring(0,hs[i].indexOf("\">"));
//			   h=h.substring(0,42);
			   System.out.println(u);
			   System.out.println("-------------------------------");

		   }
	}	
	public static void spideQi() throws Exception{
		String url="https://www.360kan.com/va/ZM5lanNw72I7ED.html";
		//top-list g-clear
		Document doc=Jsoup.connect(url).get();
		
		//部分剧集
		
/*		Elements es=doc.getElementsByClass("list-wrap");
//		System.out.println(es);
		String ses=es.toString();
		String[] s=ses.split("<li title");
		for(int i=1;i<s.length;i++){
//			System.out.println(s[i]);
			//期别
			System.out.println(s[i].substring(2,s[i].indexOf("\" class")));
			//链接
			System.out.println(s[i].substring(s[i].indexOf("<a href=\"")+9,s[i].indexOf("\" data-daochu")));
			//该期的img
			System.out.println(s[i].substring(s[i].indexOf("data-src=\"")+10,s[i].indexOf("\" alt=")));
			//期别日期
			System.out.println(s[i].substring(s[i].indexOf("<span class=\"w-newfigure-hint\">")+31,s[i].indexOf("</span>")));

			System.out.println("--------------------------------------------------------------");
		}*/
		
		
		Elements es=doc.getElementsByClass("juji-main-wrap");//p-mod包括猜你喜欢
//		System.out.println(es);
		String ses=es.toString();
		String[] s=ses.split("<li title");
		for(int i=1;i<s.length;i++){
//			System.out.println(s[i]);
			//期别
			System.out.println(s[i].substring(2,s[i].indexOf("\" class")));
			//链接
			System.out.println(s[i].substring(s[i].indexOf("<a href=\"")+9,s[i].indexOf("\" data-daochu")));
			//该期的img
			System.out.println(s[i].substring(s[i].indexOf("data-src=\"")+10,s[i].indexOf("\" alt=")));
			//期别日期
			System.out.println(s[i].substring(s[i].indexOf("<span class=\"w-newfigure-hint\">")+31,s[i].indexOf("</span>")));

			System.out.println("--------------------------------------------------------------");
		}
	}

}
