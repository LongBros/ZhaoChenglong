package com.longbro.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import beans.Sentence;

public class OtherUtil {
	public static String web=" Long Bro Blog";
	public static String err="服务器崩啦，程序员哥哥加工修复中委屈";
	public static String err404="客官，您访问的页面还未出世";
	public static String skip="这里有更好的，进去瞅瞅吧>>";
	public static void main(String[] args) {
		 Random ran=new Random();
	        int p=ran.nextInt(18);
	        String lovesen=OtherUtil.loveSen(p);//爱的句子
	       
		System.out.print(lovesen);
	}
		public static String time(){
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long l=System.currentTimeMillis();
			Date date=new Date(l);
			String time=sf.format(date);//记录发表时间
			return time;
		}
		public static int generateRan(){
			Random ran=new Random();
			for(;;){
				try {
					Thread.sleep(5000);
					return ran.nextInt(11);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		public static String []words={
			"和你在一起好开心的呢","平西湖畔的的回忆","平院的操场,你,和我","宝贝好可爱的呢","要一直笑下去哟亲爱的","盈儿萌萌哒"
			,"老婆，我爱你","小可爱去郑航的路上","这个就是我了，嘻嘻","宝宝，要开心呢","是不是挺帅的，嘿嘿","刚起床头发有点乱呢"
			,"看看百年前的Long Bro颜值如何","真可爱，么么","嘻嘻，我好喜欢你","等你下课一起走好吗","嘿嘿，很爱很爱你","欧耶，又看见小可爱了呢",
			"多喜欢你"};
		//不一样的图，想对你说不一样的话
		public static String loveSen(int i){
			return words[i];
		}
		
		//aodamiao image
		private static String[] aodamiao={"baibai","bishi","caidao","cangsang","chanle","chijing","dengyan",
				"dese","deyi","guzhang","haixiu","haode","jingdaile","jingjingkan","keai",
				"kun","lianhong","nidongde","qidai","qinqin","shangxin","shengqi","shuai",
				"sikao","tongxin","touxiao","wabikong","weixiao","wulian","wuyu","xiaoku",
				"xiaozheku","xihuan","yaobai","yihuo","zan","zhayan","zhenjing","zhenjingku",
				"zhuakuang"};
		public static String imgPath(int i) {
			String path=aodamiao[i];
			return path;
		}
		
		/**
		 * 	获取客户端的IP地址
		 * @param request
		 */
		public static String getIp(HttpServletRequest request) {
			String l_Ip=request.getHeader("x-forwarded-for");
			if(l_Ip==null||l_Ip.length()==0){
			   l_Ip=request.getHeader("Proxy-Client-IP");
			}if(l_Ip==null||l_Ip.length()==0||"unknown".equalsIgnoreCase(l_Ip)){
			    l_Ip=request.getHeader("WL-Proxy-Client-IP");
			}if(l_Ip==null||l_Ip.length()==0||"unknown".equalsIgnoreCase(l_Ip)){
			    l_Ip=request.getRemoteAddr();
			    if(l_Ip.equals("127.0.0.1") ||l_Ip.equals("0:0:0:0:0:0:0:1")){
				    //根据网卡取本机配置的IP
				    InetAddress in=null;
				    try {
						in=InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    l_Ip=in.getHostAddress();//输出的为本机的IP为172.24.21.129，
				    //但是宿舍无线分配的IP为219.157.79.134手机热点分配的是223.104.105.248，而需要记录的正是这，而非本机 
			   }
			}
			return l_Ip;
		} 
		
		public static void genLog(HttpServletRequest request){
			String ip=getIp(request);
		    String ti=OtherUtil.time();//
//		    String add=AddressUtils.getAddByIp(ip);
		    String add="";
		    Connection conp=JdbcUtil.getConnection();
			try {
				Statement stp=conp.createStatement();
				//获取该IP最近一次访问的时间
				/* String sql1="select l_Time from log_vis where l_Ip='"+l_Ip+"' order by l_Id desc limit 0,1;";
				ResultSet rsp=stp.executeQuery(sql1);
				if(rsp.next()){//有该条ip，判断是否最近添加过
				   String tim=rsp.getString("l_Time");
				   //如果该ip十分钟内没有添加过，则新加一条访问记录
				   if(!tim.substring(0, 13).equals(ti.substring(0, 13))){
					   String sql="insert into log_vis (l_Ip,l_Time) values('"+l_Ip+"','"+ti+"')";
					   stp.executeUpdate(sql);
				   }
				   out.println(l_Ip);
				}else{//无该条ip，直接添加 */
				   String sql="insert into log_vis (l_Ip,l_Address,l_Time) values('"+ip+"','"+add+"','"+ti+"')";
				   stp.executeUpdate(sql);
				//}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//根据ip获取详细地址,方法返回IP的地址
		public static String getAddress(HttpServletRequest request){
			String ip=getIp(request);
			AddressUtil util=new AddressUtil();
			String address="";//含有地址的json字符串
			String detail="";//提取出来的地址
			try {
				address=util.getAddresses("ip="+ip, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String country=address.substring(address.indexOf("country")+10, address.indexOf("area")-3);
	        String province=address.substring(address.indexOf("region")+9, address.indexOf("city")-3);
	        String city=address.substring(address.indexOf("city")+7, address.indexOf("county")-3);
	        String county=address.substring(address.indexOf("county")+9, address.indexOf("isp")-3);
	        if(province.contains("X")){
	            detail=country;
	        }else if(city.contains("X")){
	            detail=country+province;
	        }else if(county.contains("X")){
	            detail=country+province+city;
	        }else{
	        	detail=country+province+city+county;
	        }
	        return detail;
		}
		
		/**
	     * 返回今日的一句话
	     * @return
	     */
		public static Sentence genSen() {
			Sentence sen=new Sentence();
			String[][] sens={
					//2018-10-15~2018-10-21
					{"好好去做吧，站在世界之巅!","Made it, Ma! Top of the world!","OCT 21 Sun","小编的话：这是《歼匪喋血战》里很霸气的一句话，或许我们未必能站在世界之巅，但是我们不能缺少站在世界之巅的豪迈之气，无论是在思想上还是在行动中。"},
					{"只有强者才懂得斗争；弱者甚至失败都不够资格，而是生来就是被征服的。","Strength alone knows conflict, weakness is below even defeat, and is born vanquished.","OCT 20 Sat","小编的话：我们每个人都在平凡地活着，但却都拥有一个英雄的梦想。也许你走的很慢，像是一只蜗牛，也请做一只努力向上爬的蜗牛。因为总有一天，你会站在最亮最远的地方，活成你自己曾经渴望的模样。道友们，加油！"},
					{"卓越的天才不屑走旁人走过的路，他寻找迄今未开拓的地区。","Towering genius disdains a beaten path. It seeks regions hitherto unexplored.","OCT 19 Fri","小编的话：一个人生活在世界上要善于另辟蹊径，如果只会踩着前人的足迹前进，是无法找到世外桃源的。"},//http://cdn.iciba.com/news/word/big_20181012b.jpg
					{"只有每天再度战胜生活并夺取自由的人，才配享受生活和自由。","None is of freedom or of life deserving unless he daily conquers it anew.","OCT 18 Thu","小编的话：生命不轻言放弃。漫长的人生中，谁也不可能一帆风顺，谁也难免要经历挫折和坎坷。被挫折历练后的人总是会更顽强、更成熟、更加的勇敢。战胜它，你就可以享受自由和生活。"},//http://cdn.iciba.com/news/word/big_20181011b.jpg
					{"世上最成功的人往往都是失败最多的人。","Some of the most successful people in the world are the ones who've had the most failures.","OCT 17 Wed","小编的话：每个人都不是天生的人才，不管是谁都有失败的时候，没有失败的人生是不壮美的的人生。失败是一次历练，只有经得起挫折并能从失败中吸取经验的人，才会成功。"},
					{"我的一生一次又一次失败过，而那正是我成功的原因。","I have failed over and over and over again in my life. And that is why I succeed.","OCT 16 Tue","小编的话：这句话出自迈克尔·乔丹。俗话说：失败是成功之母，这是被无数事实证明的一条真理。在通向成功的路上，失败是不可避免的；对奋斗者来说，失败就意味着向成功又迈进一步。任何事情的成功，无不是一次又一次失败摸索出来的。"},
					{"幸福不在于拥有金钱，而在于获得成就时的喜悦以及产生创造力的激情。","Happiness lies not in the mere possession of money, it lies in the joy of achievement, in the thrill of creative effort.","OCT 15 Mon","小编的话：每个人对幸福的定义不同。有的人的幸福是钱有多少，房子有多大，车有多奢侈。有的人的幸福是简单平安、自由快乐。不管你的幸福是什么，只希望在你成功时有人为你喝彩，而当你失意时有人对你说：朋友，加油！"},
					//2018-10-08~2018-10-14
					{"生活中最大的幸福是坚信有人爱我们。","The supreme happiness of life is the conviction that we are loved.","OCT 14 Sun","小编的话：这句话出自雨果《悲惨世界》。人往往经历过不幸福，才知道什么是幸福；就像遇见过错的人，才会知道谁是对的人。勇敢地做自己，不要为任何人而改变。如果他们不能接受最差的你，也不配拥有最好的你。要坚信永远有人爱着我们。"},
					{"要真正了解一个人，需在不幸中考察他。","To really understand a man, we must judge him in misfortune.","OCT 13 Sat","小编的话：这句话出自拿破仑。想要真正了解一个人,就必须察其言，观其行。而一个人的本质特征在他落难时尤为真实。"},
					{"有时候正是人们以为的无用之人成就无人敢想之事。","Sometimes it is the very people who no one imagines anything of who do the things that no one can imagine.","OCT 12 Fri","投稿人：谦友。小编的话：这句话出自电影《模仿游戏》。很多时候，我们做什么事情总是先想到自己做不到。要相信没有什么是不可能的，关键是你敢不敢尝试。每个人的身体里都住着一个沉睡的“巨人”，用顽强的毅力唤醒他，你一定能发挥出巨大的潜能。"},//http://cdn.iciba.com/news/word/big_20181012b.jpg
					{"我本可以忍受黑暗，如果我不曾见过太阳。","Had I not seen the sun, I could have borne the shade.","OCT 11 Thu","小编的话：顾城曾说 黑夜给了我一双黑色的眼睛，我却用它来寻找光明。这个世界，黑暗总是与光明共存。我们无法逃避黑暗，但是我们永远可以选择拥抱光明。因为黑夜无论怎样悠长，白昼总会到来。而人总是向往光明的。"},//http://cdn.iciba.com/news/word/big_20181011b.jpg
					{"我们笑着说再见，却深知再见遥遥无期。","We laughed and kept saying see you soon, but inside we both knew we'd never see each other again.","OCT 10 Wed","小编的话：这句话出自《海上钢琴师》。什么事都贵在坚持。如果找不到坚持下去的理由，那就找一个重新开始的理由。只需要一点点勇气，你就可以把你的生活转个身，重新开始。生命太短，没有时间留给遗憾，若不是终点，就请微笑一直向前。"},
					{"记住：不要依赖梦想而忘记生活。","It does not do well to depend on dreams and forget to live, remember that.","OCT 9 Tue","小编的话：有梦想固然好，但人不该依赖梦想而活。梦想很美好，却未必真实。一味的沉迷于梦想会让人忘记真正的活着。其实，生活就像故事一样：不在乎长短，而在于质量，这才是最重要的。"},
					{"真相有时之所以难以被接受，是因为谎言听起来美好多了。","Sometimes it's hard to accept the truth because the lies sound so much better.","OCT 8 Mon","小编的话：人人都向往美好，可以很容易接受美好的欺骗，却难以接受不友好的真相。可是人生岂是只有美好，只有直面真相学会接受，并努力改变自己，我们才能真正的热爱生活。"},
					//2018-10-01~2018-10-07
					{"最难过的日子也有尽头。","The longest day has an end.","OCT 7 Sun","小编的话：也许此刻的你正遭遇重重困难，但请不要放弃希望。因为苦日子总有个头的，人的一生不会一直不顺。我们要做的是感谢生命中遭遇的一切，正是这些将成为我们成长道路上的基石。怀揣不灭的希望，换一种心境生活，生活就可以很简单，而简单就是幸福。"},
					{"我不能掌控他人的恐惧，只能掌控自己的。","I can't control their fear, only my own.","OCT 6 Sat","小编的话：每个人都会恐惧，而恐惧来源于对自己的不自信和对未知的不安。其实，我们大多数人恐惧是因为不敢面对。学会直面恐惧，当你勇敢面对的时候，你已经没有畏惧了。只有这样，你才能接纳恐惧，最后承受它。记住：克服恐惧的最好办法就是面对恐惧！"},
					{"生活从来没有容易的答案，只有去克服重重问题。","There are no easy answers, there's only living through the questions.","OCT 5 Fri","小编的话：面对任何情况，我们都要学会耐心对待。把所有遭遇都看作是成长道路上必须经历的磨练，只有这样我们才能不为痛苦所动摇，才能下定决心克服一切困境。一旦走出困境，我们将不会担心困难再次重来，因为我们已经消除了那些会使我们陷入困境的不利因素."},
					{"我愿意为你赴死，但不会指望你而活。","I would die for you. But I wouldn't live for you.","OCT 4 Thu","小编的话：这句话出自美国哲学家安·兰德。在她看来：人首先必须要为自己活着，要实现一种理性的自私。她曾在《阿特拉斯耸耸肩》中写道：如果有人想知道我何时会回来，我在此向全世界再说一次——我以我的生命，以及我对他的热爱发誓：我永远不会为别人而活，也不会要求别人为我而活。"},//图片地址:http://cdn.iciba.com/news/word/big_20181004b.jpg
					{"不要把秘密传给风，风会传遍整个森林。","If you reveal your secrets to the wind, you should not blame the wind for revealing them to the trees.","OCT 3 Wed","小编的话：这句话出自纪伯伦。和谁都别熟的太快，不要以为刚开始话题一致，共同点很多，你们就是相见恨晚的知音。语言很多时候都是假的，一起经历的才是真的。人前不应该说的话，背后也别说。"},
					{"天地之大，比你所能梦想到的更多。","There are more things in heaven and earth than you've ever dream of.","OCT 2 Tue","小编的话：这句话出自莎剧对白《哈姆雷特》。天地有大美而不言。天地很大很大却从不言语，它只兀自美丽着。能否沐浴在天地万物中，感受到美无处不在，关乎于内心。我想莎翁向我们传达的不止这些，不知道各位小伙伴对这句话有什么见解？"},
					{"一个人可以选择爱上谁，当他要结束爱情时，他是没有选择的。","A man has free choice to begin love, but not to end it.","OCT 1 Mon","小编的话：爱情一直是不变的话题。遇见了心爱的人，可以说是幸运的，无论结局怎样，都可以说是幸福的。白头到老，固然很好，如果分手，或者为爱情伤心，也很幸福，因为毕竟爱过。每个人对爱情的理解不尽相同，毕竟感情这东西，如人饮水冷暖自知。"},
					//2018-9-24~2018-9-30
					{"有些人沦为平庸浅薄，金玉其外，败絮其中。可不经意间你会遇到一个彩虹般的人，从此以后，其他人不过是匆匆浮云。","Some of us get dipped in flat, some in satin, some in gloss. But every once in a while you find someone who's iridescent, and when you do, nothing will ever compare.","SEP 30 Sun","小编的话：这句话出自《怦然心动》。关于这句话还有另一种翻译：有人住高楼，有人在深沟；有人光万丈，有人一身锈。世人万千种，浮云莫去求，斯人若彩虹，遇上方知有。有的人相遇就像是流星，瞬间迸发令人羡慕的火花，却注定只是匆匆而过；有的人却是深情一眼，挚爱万年。"},//图片地址:http://cdn.iciba.com/news/word/big_20180930b.jpg
					{"如果你睁开眼睛，你会看到值得看的东西。","If your eyes are opened, you will see the things worth seeing.","SEP 29 Sat","小编的话：总有一些事，我们不愿它发生，却必须接受；总有些东西，我们不想知道，却必须了解；总有些人，我们不能没有，却必须学着放手。人只能自己去成全自己：不要因为惧怕黑暗而紧闭双眼。睁开眼睛你就会看到值得看的东西。"},//图片地址:http://cdn.iciba.com/news/word/big_20180929b.jpg
					{"我们都要做很多选择，最终这些选择创造了我们。","We all make choices, but in the end our choices make us.","SEP 28 Fri","小编的话：人生故事里的大多数结局都源于我们的选择，所以没有什么好抱怨的。选择了什么样的道路，你就会拥有什么样的人生。而人生从来不能回头，既然选择了，那就勇敢的走下去。即使道路并不平坦，也要坚定你当初的选择。毕竟，你选的路，跪着也要走完。"},
					{"正确的训练加上坚定的决心，你将攻无不克。","With the right kind of coaching and determination you can accomplish anything.","SEP 27 Thu","小编的话：没有什么决定是可以两全的，如果你总是这样想，你永远做不出什么决定。只要选择了方向与路途，就不要抱怨。一个人只有承担起旅途风雨，才能最终守得住彩虹满天。桃花源终会因你而存在，彼岸花始终为你而盛开。"},
					{"失败了没关系，重振旗鼓，从头再来。","If at first you don't succeed, then dust yourself off and try again.","SEP 26 Wed","小编的话：众生皆苦，不管是什么样的人生，都充满了苦涩。然而缺少苦涩的人生是不完整的。所以无论如何我们都要前进，看看自己究竟能做到什么地步。在失败的结果下，衡量自己的器量。成功从来不需要别人来定义！"},
					{"生命短暂，放慢脚步，别错过任何沿途的风景。","Life is too short to miss out anything, try to take it slowly.","SEP 25 Tue","小编的话：生活如水，人生似茶。会有人说你好，也会有人说你不好，何必执着于他人对你的评判。人生，需要有一些时刻，慢下来，静下来，听一听花开的声音，看一看叶子舒卷的曼妙。毕竟，生命短暂，别错过任何沿途的风景。"},
					{"月之圆，兆人之团圆。","The moon becomes round, which indicates the reunion of people.","SEP 24 Mon","小编的话：中秋节快乐各位！中秋的月亮，在空中静静地悬着。今夜的月最美，是那样诗情画意；今夜的月最柔，是那样柔情似水；今夜的月最明，是那样如水似镜。沐浴着皎洁的月光，我不禁诗兴大发：明月几时有，把饼问青天。不知饼中何馅，今日是五仁儿。我欲乘风观月，又恐琼楼玉宇，高处不胜寒。"},
					//2018-9-17~2018-9-23
					{"患难见真情。","A friend in need is a friend indeed.","SEP 23 Sun","小编的话：这是一句再简单不过描述友情的句子。人这一生会交很多朋友，但朋友不是先来的人或者认识最久的人, 而是那个来了以后再也没有走的人。也许平时TA会口无遮拦的损你，但在关键时刻却总能为你挺身而出。朋友就是彼此希望可以越来越好。"},
					{"正因为你为你的玫瑰花费了时间，这才使你的玫瑰变得如此重要。","It is the time you have wasted for your rose that makes your rose so important.","SEP 22 Sat","小编的话：这句话出自《小王子》。每件事情都有它的因果关系，因为选择，才会错过；因为错过，才会失去；因为失去，才会珍惜；因为珍惜，才会得到。小王子选择了玫瑰，却错过了时间，却也因此得到了他一生珍惜的朋友。"},
					{"你不能为取悦别人而活，你得有自己的选择","You cannot live your life to please others. The choice must be yours.","Sep 21 Fri","小编的话：一个人不可能改变世界，世界也不会因你而改变，所能做的，就是适应世界，不为取悦别人而活。你的生活，应该有你自己的精彩。"},
					{"永远别让旁人说你成不了事，就算是我也不行。","Don't ever let somebody tell you you can't do something, not even me. ","Sep 20 Thu","小编的话：这句话源自《幸福来敲门》，人一定要自信！村上春树曾在 《世界尽头与冷酷仙境》中写道：只要自信就无所畏惧。愉快的回忆、倾心于人的往事、哭泣的场景、儿童时代、将来的计划、心爱的音乐——什么都可以，只要这一类在头脑中穿梭不息，就没有什么可怕的。"},
					{"只要一息尚存，就不得不战。","As long as you can still grab a breath, you fight.","Sep 19 Wed","小编的话：这句话源自《荒野猎人》。大多数的事情并不是随便就可以做到的，我们要努力的去做，要有不服输、不认输的精神才可以。这里引用曲婉婷的一句歌词：谁说我们一定要走别人的路，谁说辉煌背后没有痛苦，只要为了梦想不服输，再苦也不停止脚步。"},
					{"愿你不再孤单，并学会享受孤独。","May you not be lonely but be able to enjoy being alone.","Sep 18 Tue","小编的话：孤独是一种心境，也是一种人生常态，就像没人能逃避“喜怒哀乐”一般，也没人能彻底摆脱“孤独”。有时，孤独也是一种美。置身其中，自行其乐，它会教会你直面困难，坦然应对一切困境，并真正体味人生，学会理智思考，理性面对。孤独，有时也是一种实实在在的美！"},
					{"以友情计岁月，而非年龄；以笑容量人生，而非泪水。","Count your age by friends, not years; Count your life by smiles, not tears.","Sep 17 Mon","小编的话：人一生下就会哭，笑是后来才学会的。所以忧伤是一种低级的本能，而快乐是一种更高级的能力。生命之长短也许并不重要，只要你活得快乐，在有生之年做些有意义的事，便已足够。"},
					//2018-9-10~2018-9-16
					{"我从未真正意识到我该抉择的是：活在别人的剧本还是叙写自己的人生。","I never really noticed that I had to decide, to play someone's game or live my own life.","Sep 16 Sun","小编的话：人生中经历最多的就是选择，一件事，一个人，一条路。选择权在自己的手中，如果选择错了，可以改变自己一生的命运，也会令自己后悔一生。所以当自己面临选择的时候，不要犹豫，选择坚定向前走，不管到底会出现什么，只要怀着信念，你就会成功。"},
					{"一门外语是人生之奋斗中的强大武器。","A foreign language is a weapon in the struggle of life.","Sep 15 Sat","投稿人：一个读书人。他想说的话：这句话出自马克思。现在越来越体会到学好英语的重要性，这不仅是学好一门语言，更是为自己打开一种新的视野，开辟一条新的路径，所以大家一起加油学好英语吧！"},
					{"每一个遭受痛苦的地方就是我要去的地方，做任何我能做到的事。","Anywhere I see suffering, that is where I want to be, doing what I can.","Sep 14 Fri","投稿人的话：这句话出自已故的英国威尔士王妃戴安娜，这是她被人熟知的一句名言。她生前亲民的举动引起了英国民众热烈的欢迎，一度被称之为“平民王妃”。"},
					{"我们虽然年少，但却如此相爱。让我们披荆斩棘，跨越所有障碍。","We are still kids but we are so in love, fighting against all odds.","Sep 13 Thu","小编的话：天地之间，一个人和另一个人相遇，这是一件值得珍惜的事。只缘是：岁月悠悠，人生无绪。我们原本是偶然为人，却只为千年一遇。所以，请视你的爱人为珍爱，在每一次入睡前对TA说：晚安，梦中见。"},
					{"岁月不过是一条长河，从你我的过往穿流而过。","Time is but a river flowing from our past.","Sep 12 Wed","小编的话：这句话出自《水形物语》。岁月刻下了一个字，一撇一捺；岁月吻上了一张脸，一沟一坎；岁月爬上了一座墓，一黑一白。人生是一场相逢，又是一场遗忘，最终我们都会成为岁月里的风景。"},
					{"延长白天最好的办法，就是从夜晚偷时间。","The best of all ways to strengthen our days is to steal hours from the night.","Sep 11 Tue","小编的话：要善于抓住每一个稍纵即逝的瞬间，做你想做的事，或学习，或工作。在认准目标的同时，不忽视每一个今天。因为我们能抓住的也只有今天，昨天已逝，明天未来，而抓住了今天，才能有更多美好的明天。"},
					{"教育的目的应是把人心转化为活的泉水，而不是贮水池。","The aim of education should be to convert the mind into living fountain, and not a reservoir.","Sep 10 Mon","小编的话：那些教会我们成长的平凡巨匠，曾经是每隔45分钟一次的梦魔。却也是我们成年后，一辈子都要感谢的人。你们是我带过的最差的一届、我再讲5分钟就下课、整栋楼就你们最吵。当记忆层层铺开，当日子再一次定格在9月10日，我想我们每个人欠老师一句：谢谢老师，您辛苦了！"},
					//2018-9-3~2018-9-9
					{"很奇妙的一种感觉是：曾经的陌生人，突然之间成为了你的整个世界。","It’s an amazing feeling to realize how one person who was once just a stranger suddenly meant the world to you.","Sep 9 Sun","投稿人想说的话：若无亏欠，怎会相见。我宁愿把我们相遇都看成一种缘，也感谢我们能有所亏欠。小编的话：这一生，有些人是陪你看日出的，另一些人是陪你看日落的，就像有些人只能陪你看月色，没能陪你等待明天的第一道曙光。然而，到了人生最后的一抹黄昏，你念念不忘，始终牵挂着的，是那个陪你看日出日落，还是陪你看尽夜色的人？"},
					{"我也许不能改变过去，但我能从中学习。","I may not be able to change the past, but I can learn from it.","Sep 8 Sat","投稿人的话：这句话出自《爱丽丝梦游仙境2：镜中奇遇记》。对于过去，我们不能改变，唯一能做的就是从中吸取教训不再“跌倒”。小编的话：我们都要学会一件事情，就是不回头，只为自己没有做过的事情后悔，不为自己做过的事情后悔。人生每一步行来，都是需要付出代价的。你得到了你想要的一些，失去了你不想失去的一些。可这世上的芸芸众生，谁又不是这样呢？"},
					{"一个人总要走陌生的路，看陌生的风景，听陌生的歌。总有一天，在某个不经意的瞬间，你会发现，曾经努力想要忘记的事情早已消逝。","One is always on a strange road, watching strange scenery and listening to strange music. Then one day, you will find that the things you try hard to forget are already gone.","Sep 7 Fri","投稿人的话：这是Mr.Chen在英语课上推荐的一句话。Mr.陪伴了我们一年半，在高三来临之际，却提前因分班而分别。虽然他不教我们了，但我们依旧记得每一句话。这些记忆，是时间带不走的，never forget!"},
					{"一个频频回头的人，是走不了远路的。","One who frequently looks back can't go far.","Sep 6 Thu","小编的话：“回顾”固然可以让我重温那些如水的月色， 但是，如果只坚持不断地“回顾”下去， 终将会使我错过了我的今夜，和这一夜里满天的星空。总有一天，你会站在最亮的地方，活成自己曾经渴望的模样。"},
					{"我们是美之爱好者，但我们的趣味是淡雅的；我们陶冶性灵，但我们也不让失却丈夫气。","For we are lovers of the beautiful,yet simple in our tastes; and we cultivate the mind without loss of manliness.","Sep 5 Wed","投稿人的话：这是希腊大政治家伯里克里斯所说的话。我们爱美，却是兴趣淡雅，我们爱高贵的美，但也爱直接的感官的一切。人呀，就该有高雅的性情和坚强的内在。"},
					{"被轻蔑的时候能平静一笑，这是一种自信。","Having a calm smile to face with being disdained indicates kind of confidence.","Sep 4 Tue","投稿人：夭夭。投稿人的话：每一个我，走在人生的路上，你被嘲笑的时候，其实不经意间你也嘲笑了人都是相互的，所以不要总为那所谓的优越感去相互比较了，赤裸裸来赤条条去我们都一样，还是自信的做好自已吧。"},
					{"我想像不出，在孩提时代，还有什么需要会像渴求父亲保护一样强烈。","I cannot think of any need in childhood as strong as the need for a father's protection.","Sep 3 Mon","投稿人的话：父爱是深情的凝望，父爱是厚重的抚摸，父爱是一座巍峨的大山，父爱是一片辽阔的草原，父爱是一颗燃烧的太阳，父爱是一条奔涌的江河。有一种责任叫男人，有一种承担叫父亲，有一种无言是父爱。"},
					//2018-8-27~2018-9-2
					{"我们最深的恐惧是我们的力量无可估量。","Our deepest fear is that we are powerful beyond measure.","Sep 2 Sun","投稿人的话：出自《发现真爱》一书。现在我们的恐惧已经不是懂得太少而是知道的太多，而我们所知道的相比于未知的东西更让我们心生畏惧。我们最深的恐惧，不是我们力不能及；我们最深的恐惧，是我们的力量无边无际；让我们感到害怕的不是我们的黑暗，而是我们的光芒。"},
					{"理性和非理性是互补的。两者分开的话，力量就会小很多。","The rational and the irrational complement each other. Individually, they are far less powerful.","Sep 1 Sat","投稿人：湖北医药学院的枫枫酱。小编的话：这句话出自《纸牌屋》。人只有将理性和非理性结合起来才能将其发挥最大的作用。到底怎么才能做个理性与非理性完美结合的人呢？我想，我们应该要为他人着想，对待生活、对待他人要充满热情，要勤于思考，用心去做好任何一件看似微小的事情。粗中有细，细中有序。"},
					{"找不到朋友时，为什么不翻翻书呢？","If you are alone, why not seek the company of books?","Aug 31 Fri","小编的话：我们要知道，读一本好书，就像是交了一个良师益友，我们的发展，进步都是离不开知识，离不开书籍的。"},
					{"我们的生活是由机遇所界定的，甚至是那些我们错失的机遇。","Our lives are defined by opportunities, even the one we miss.","Aug 30 Thu","小编的话：当机遇来临时，我们要稳稳的抓住它，机遇从来都是为有准备的人而来的，这就要我们平时对知识勤于积累，用知识武装自己，完善各种技能，不断提高自身的修养，戒骄戒躁，持之以恒。"},
					{"书籍是灵魂的镜子。","Books are the mirrors of the soul.","Aug 29 Wed","投稿人的话：这句话出自弗吉尼亚·伍尔芙的《幕间》。《幕间》是英国作家弗吉尼亚·伍尔芙最后一部小说，发表于1941年。描述了波因茨宅奥利弗一家和村民们观看露天历史剧的故事。作品中，女主人公伊莎的诗歌，露天历史剧的台词 以及叙述者幽默抒情的语言从不同角度表现了几代人在经历过一战又面临着二战即将爆发时的生存困境。"},
					{"个人对集体努力的承诺，它造就团队合作、公司运作、社会协作，文明工作。","Individual commitment to a group effort – that’s what makes a team work, a company work, a society work, a civilization work.","Aug 28 Tue","投稿人的话：个人致力于团队，是集体工作之所需。团队力量需要个人奉献，每个人都为团队着想，团队将坚不可摧。智慧从劳动来，行动从思想来，荣誉从集体来，力量从团结来。"},
					{"你可以赚取很多的钱，但是你不能赢得更多的时间。","You will always be able to make more money, but you cannot make more time.","Aug 27 Mon","小编的话：如果说人生来是平等的，那么这份平等恐怕非“时间”莫属。别人有的时间，你也有。然而坏消息是，时间像一条不会逆流的长河，总是奔腾着流逝而去；好消息是，你可以决定在你的时间里做些什么。但少年啊你要记得，不要等老来之时问自己，时间都去哪儿了。"},
					//2018-8-20~2018-8-26
					{"真正让你后退的，不是前面的困难，而是后面的退路。","What really makes you backward is not the difficulty in front, but the way back.","Aug 26 Sun","投稿人没有留下姓名。小编的话：很多时候，成败只在瞬间。不给自己留退路，就会将自己的信心与勇敢全部集中在前进的道路上，会竭尽全力、孤注一掷地不断前行。此时，任何困难都会被你踩在脚下，任何挫折都会被甩在身后。当你历经艰辛之后会发现：原来，成功就在自己眼前。"},
					{"如果你生来就与众不同，何苦非要融入这群乌合之众？","Why are you trying so hard to fit in when you were born to stand out? ","Aug 25 Sat","小编的话：这句话出自古斯塔夫·勒庞的《乌合之众》。即使过去了100多年，我们依然可以感受到书中的哲理和震撼。在困惑、迷茫时，了解是什么让我们盲从，如何克服盲从，从而确保一个稳定发展的幸福未来。"},
					{"不要让自己一天忙忙碌碌，最终却碌碌无为。","Don't let yourself get busy all day and end up doing nothing.","Aug 24 Fri","小编的话：上天不会亏待努力的人，也不会同情假勤奋的人。有时，付出了时间，并不等于努了力，你随时可以被取代。所以希望大家能够明确目标，并为之付出值得的汗水。"},
					{"生活有两种方式：一种是没有奇迹发生；另一种则是到处是奇迹。","There are only two ways to live your life. One is as though nothing is a miracle. The other is as though everything is a miracle.","Aug 23 Thu","小编的话：这句话出自爱因斯坦。前者会觉得人生平淡无味，后者会觉得人生处处充满色彩和惊喜。不同只在于对生活的态度。希望大家永远怀抱希望，永远乐观。"},
					{"做一个特别简单的人，不期待突如其来的好运，经营好自己，珍惜眼前的时光！","Be an especially simple person, do not expect good luck coming suddenly, manage yourself well and cherish time at the moment.","Aug 22 Wed","投稿人：小米同学。小编的话：做一个淡然的人，不浮不躁，不争不抢，不去计较浮华之事。淡然地过着自己的生活，不要轰轰烈烈，只求安安心心。"},
					{"年年岁岁花相似，岁岁年年人不同。","The flowers are similar every year, but people are different from year to year.","Aug 21 Tue","投稿人的话：我是幸运的，每年双十一都有那个人陪着我过。也祝愿大家能够一路相守，扶携到老。"},
					{"使人疲惫的不是远方的高山，而是鞋里的一粒沙子。","Make you tired of the mountain is not far away, but a grain of sand in my shoes.","Aug 20 Mon","小编的话：我们不少人都有过远行的经历，有时候正是鞋里的一颗小沙子磨肿了你的脚，让你寸步难行，最终仰望高山，兴叹不已。 人生就是一次长途旅行，迎接我们的并非都是奇幻的景色，更多的也许是险滩和坎坷，要征服漫漫长路，需要随时倒出鞋里的沙子。"},
					//2018-8-13~2018-8-19
					{"生命不息，奋斗不止。","Aspire to inspire until I expire.","Aug 19 Sun","投稿人没有留下姓名。小编的话：“生命不息，奋斗不止”，这不仅仅是一句豪言壮语，它还告诉我们奋斗非一朝一夕之事，应该持之以恒，应该切切实实地从小事做起。我们要记住古人“一屋不扫，何以扫天下”的教诲，踏踏实实地工作，在奋斗中实现自己的人生价值。"},
					{"要记住：爱我们的人永远不会真正离开我们。他们一直在你身边陪伴着你，在心里。","But know this: the ones that love us never really leave us. And you can always find them in heart.","Aug 18 Sat","投稿人：樱雨晗。投稿人的话：这句话出自《哈利波特》中的西里斯·布莱克，也就是小天狼星。当身边爱我们的人离开我们时，其实他们只是换了另外一种方式存在。"},
					{"无法描述你的形状，我感觉到你包围着我。因为你的存在，我满眼都是爱意。我的心变得柔软，只因你无处不在。","Unable to perceive the shape of you, I find you all around me. Your presence fills my eyes with your love, it humbles my heart. For you are everywhere.","Aug 17 Fri","投稿人想说的话：我们所有的人拥抱在一起就能对抗黑暗的世界。小编的话：这句英文出自电影《水形物语》，影片以这段文字结尾。这里分享一下这段英文其他几种翻译。1.（普通话）分辨不出你的轮廓，因你时刻在我左右。你存在着，将温情注满我的双眼。使我感到如此渺小，因你无所不在。2.（楚辞体）无以明兮荪何状，寻公子兮遍予形。意所属兮君所在，君所在兮安予心。3.（诗经体）泛舟南海，鲛人与游。不可以状，遍我左右。脉脉汝心，拳拳我意。如屡玄土，与子成歌。正值七夕，祝愿屏幕面前的每一个人都能够勇敢追爱，拥抱幸福。"},
					{"让我们离开人寰，去更遥远的未来，带我穿越漆黑的夜空，坠入那无边的黑暗。","Let's leave the world behind. Take me through the night. Fall into the dark side.","Aug 16 Thu","投搞人：Alan Walker。投稿人的话：来自一首歌《Dark side》，我想说， 愿马航失联遇难者在天堂再也没有悲痛。人生不如意十有八九，但求无愧我心。愿人间再无危难。"},
					{"白纸的美好在于：你可以随心所欲地描绘你的人生地图。","The beauty of white paper is that you can paint a map of your life as you please.","Aug 15 Wed","投稿人：Jeacker。她想说的话：你拥有的是无可限量的未来，去描绘自己的地图吧！这句话出自于电影《解忧杂货店》。"},
					{"我喜欢我身体破碎的声音和不断愈合的过程。","I love the broken sound in my body and the healing process.","Aug 14 Tue","投稿来自木檀。他想说的话：一场疾病打破了我所有的思绪，一开始并没有想到能坚持上学甚至是坚持到学期末考试。是啊，即便这无望的人生也是我所爱着的，尽管很糟糕甚至是勉强，也不知道未来会怎样。但吃到好吃的东西，看到想看的景，还是会热泪盈眶，十七岁的清茶二十岁的宣言，此刻还在努力的生长！出自《万物生长》，作者余秀华。"},
					{"我不知道我爱你的理由是什么，但我知道你是我不爱其他人的理由。","I don't know the reason why I love you, but I know you are the reason why I don't love others.","Aug 13 Mon","投稿人的话：喜欢她五年也追了她五年，三年前的高考本以为和她离的远点我就能放下了，但事实证明一千多公里不够远。为了有机会和她在一起我选择了考研，现在她也成了我考回去的动力。在这祝自己考研成功，也祝天下有情人终成眷属。"},
					//2018-8-6~2018-8-12
					{"既然选择了远方，便只顾风雨兼程。","Since I have chosen to go far, I will just walk down the path I have chosen step by step.","Aug 12 Sun","投稿人的话：把自己最喜欢的话分享给大家，想到和得到中间还需要有一个做到，心中有梦，就会有未来，希望每个人都可以为了自己的诗和远方而风雨兼程，加油！"},
					{"人非圣贤，孰能无过。","To err is human, to forgive is divine.","Aug 11 Sat","投稿人刘亚飞。他想说的话：这是英语较古老的表达方式，意为：犯错者为人，谅错者为神。类似中国的文言文：人非圣贤，孰能无过？原句出自亚历山大·蒲柏的著名长诗《批评论》。亚历山大·蒲柏是18世纪英国最伟大的诗人、杰出的启蒙主义者。"},
					{"人不应该是插在花瓶里供人观赏的静物，而是蔓延在草原上随风起舞的韵律。","People should not be inserted lives in the vase for people to watch, but spread on the prairie wind dance rhythm.","Aug 10 Fri","投稿人没有留下姓名。投稿人的话:生命不是安排，而是追求，人生的意义也许永远没有答案，但也要尽情感受这种没有答案的人生。生活只有一次，该努力的年纪就要迎风起舞。"},
					{"破晓黎明延续至晃晃白昼，宝贵如金也难以保留。","So dawn goes down to day, nothing gold can stay.","Aug 9 Thu","投稿人：凡妖孽。她想说的话：句子来源罗伯特·弗罗斯特的诗歌《美景易逝（Nothing Gold Can Stay）》。我们常说时光荏苒、岁月如梭，生命也就只有25000天，如何度过这些不可能重来的日子，如何回顾往昔我们不愤懑叹息，这值得每个人思索。"},
					{"你无从选择在世上是否会受到伤害，但你能选择谁来伤害你。","You don't get to choose if you get hurt in this world, but you do have a say in who hurts you.","Aug 8 Wed","投稿人：方沐木。她想说的话：句子来源同名小说及电影《The fault in our stars》。虽然我们可以骄傲的说出“我喜欢你但与你无关”这样的话，但不可否认因为他/她，我们无法克制的哀伤已经浸满了生活的蛛网。最后还是祝愿世间有情人终成眷属吧。"},
					{"当你还能付出时不要放弃，任何事都不会真正结束直到你停止努力的那一刻。","Don't give up when you still have something to give, nothing is really over until the moment you stop trying.","Aug 7 Tue","投稿人的话：向困难做出的反应，不是逃避或绕开它们，而是面对它们，以一种进取和明智的方式同它们战斗。无论遇到了多大的绝望，只要继续前行就一定还有希望！"},
					{"人的生命，似洪水奔流，不遇岛屿和暗礁，难以激起美丽的浪花。","Human life is like flowing water, only when it hits isles and reefs can it create beautiful waves.","Aug 6 Mon","小编的话：人生是一次航行。航行中必然会遇到从各个方向袭来的劲风，然而每一阵风都会加快你前行的速度。只要你稳住航舵，即使是暴风雨，也不会使你偏离航向。"},
					//2018-7-30~2018-8-5
					{"当我年轻的时候，我想成为除我之外的任何人。但我必须接受我自己，我的缺点，我的一切。","When I was young, I wanted to be anybody but myself. I would have to accept myself, my warts and all.","Aug 5 Sun","小编的话：这句话出自《玛丽和马克思》，该部澳大利亚粘土动画是圣丹斯电影节开幕大作。每个人都会有缺陷，就像被上帝咬过的苹果，有的人缺陷比较大，正是因为上帝特别喜欢他的芬芳。我们没有办法选择自己的缺点，它是我们的一部分，我们要试着接受它。"},
					{"沉重的担子是由那些有着坚强的肩膀的人来挑的。","Burdens are for shoulders strong enough to carry them.","Aug 4 Sat","小编的话：这句话出自《飘》。不要让别人告诉你，你不能做什么。只要有梦想，就要去追求。天将降大任于斯人也，必先苦其心志，劳其筋骨，饿其体肤，空乏其身，行拂乱其所为也。"},
					{"只求曾经拥有，不求天长地久。","Better to have loved and lost, than never to have loved at all.","Aug 3 Fri","投稿人：叶子。这句话出自阿尔弗雷德·罗德·丁尼，他是维多利亚时期代表诗人，主要作品有诗集《悼念集》、独白诗剧《莫德》、长诗《国王叙事诗》等。小编的话：在错的时间遇到对的人叫青春，在对的时间遇到对的人叫爱情。愿岁月轻率，偏爱了你。"},
					{"我只是一株向日葵，期待着属于自己的那缕阳光。","I am just a sunflower, waiting for my only sunshine.","Aug 2 Thu","投稿人的话：地球在转，人生再转，但我们对美好爱情的执着永远不会变。我们就像一株向日葵，期待着属于自己的那缕阳光。"},
					{"不管多努力蜕变成熟，一旦受伤哭泣时，我们还是像个孩子。","No matter how hard we try to be mature, we will always be a kid when we all get hurt and cry.","Aug 1 Wed","小编的话：这句话出自小说《彼得·潘》。岁月永远年轻，我们慢慢老去。不管你如何蜕变，最后你会发现：童心未泯，是一件值得骄傲的事情。长大有时很简单，但凡事都能抱着一颗童心去快乐享受却未必容易。"},
					{"我们为什么会跌倒？因为这样才能学会站起来！","Why do we fall? So we can learn to pick ourselves up.","Jul 31 Tue","投稿人：一名考研狗。他想说的话：这句话来自电影《蝙蝠侠：侠影之谜》。送给所有逐梦人，无论未来受到什么挫折，梦想的旅途永远不会停下脚步！"},
					{"心如猛虎，细嗅蔷薇；盛宴过后，泪流满面。","In me the tiger sniffes the rose, after the feast bursts the tear.","Jul 20 Mon","投稿人没有留下姓名。小编的话：上句出自英国当代诗人西格里夫·萨松(Siegfried Sasson)，这句话意在表达人性是有两面的，两两相对的人性又是调和的。下句“盛宴过后，泪流满面”由后人补充，与前句巧然天成。"},
					//2018-7-23~2018-7-29
					{"其实我不需要一个勋章开证明我是个好人，如果连那样的小女孩都喜欢我，我能坏到哪儿去呢？","It turns out I don't need a medal to tell me I'm a good guy. Because of that kid likes me. How bad can I be.","Jul 29 Sun","投稿人: 闲逛枫。这句话来自《无敌破坏王》哦～很温暖人心的动画片，虽然破坏王拉尔夫的角色设定是坏蛋，但依然有一颗善良的心，坚持自己的内心，接受自己，并为之努力，生活就会出现无限的可能～"},
					{"有些人只能看到已有的事物，然后只会问为什么会这样？我梦想那些从来没有的事情，然后问为什么不那样？","Some men see things as they are and say, why. I dream things that never were and say, why not.","Jul 28 Sat","投稿人的话：显然，前者是指那些缺少创新能力的人。而后者代表了那些勇于创新敢于实践的人，创新是国家经济发展的动力，任何时候都少不了创新，同时也希望我们当代大学生能够挑起国家科技创新的大梁，尽早实现中国制造2025。"},
					{"你最好给我狠狠的一击，但是我绝不会因此放弃，也绝不可能屈服。","Give me your best shot. I'm not gonna give up, I will never give in.","Jul 27 Fri","投稿人的话：这是来自“keep it coming”歌里的歌词，看到这句话时，总能让丧气的我瞬间充满斗志，脑子里想着白天遇到的糟心事，心里说来啊你们，还能把我咋滴了！其实歌词后面还有一句我也很喜欢：Take every lemon life hands out, and make some lemonade. 我一直相信生活会因为我们变好一点就会对我们更好。"},
					{"我知道再回首时，那些眼泪想来可笑；却不知再回眸时，那些欢声笑语也能叫我潸然泪下。","I always knew looking back on the tears would make me laugh, but I never knew looking back on the laughs would make me cry.","Jul 26 Thu","投稿人姓名：吴倩。她想说的话：毕业季 ，分手季，下面还得靠高一。小编想说：每一个矜持淡定的现在，都有一个很傻很天真的曾经。岁月正因为走过了，才留下美丽的痕迹。高中是人这一生中美好而短暂的时光。就这三年，或许四年，请为了自己的梦想好好努力！"},
					{"我用同样的激情去寻求知识。我希望能理解人类的心灵，希望能够知道群星为何闪烁。","With equal passion I have sought knowledge. I have wished to understand the hearts of men. I have wished to know why the stars shine.","Jul 25 Wed","投稿来自昵称我要成为海贼王的男人。投稿人说：她非常喜欢路飞（海贼迷无疑）。因为他渴望探索，即使在征服大海的路上受过伤，失去过挚爱的人。但为了那一片海他依旧勇往直前。她说想跟随路飞船长，永远走在追梦的路上~~"},
					{"如果你不能飞，那就跑；如果跑不动，那就走；实在走不了，那就爬。无论怎样，你都要勇往直前。","You can't fly then run, if you can't run then walk, if you can't walk then crawl, but whatever you do you have to keep moving forward.","Jul 24 Tue","投稿人刘亚飞。这句话出自马丁·路德·金。小编的话：很多时候，我们不缺方法，缺的是一往无前的决心和魄力。不要在事情开始的时候畏首畏尾，不要在事情进行的时候瞻前顾后。做一件事情，不管有多难，会不会有结果，这些都不重要，关键是你有没有勇气挣脱束缚的手脚，有没有胆量勇敢地面对。"},
					{"没有人真正看过一朵花，花那么小。我们又没有时间，看花可是要花时间的，就像交朋友也要花时间。","Nobody sees a flower really, it is so small. We haven't time, and to see takes time - like to have a friend takes time.","Jul 23 Mon","投稿人：兵家鲈鱼。他想说的话：若想欣赏美和情谊，请驻足请耐心，时间会给予你一切，或美或丑，或善或恶～"},
					//2018-7-16~2018-7-22
					{"只要相信你自己，就没有什么不可能。","Nothing is impossible when you believe in yourself.","JUL 22 Sun","投稿人的话：I want to say: I am freshman, and encounter so many things. My friends and my classmates help me, understand me. Thank you these guys about learning oral English. I make a promise, I will go for it!"},
					  {"生活意味着进步，进步意味着受苦。","Life means progress, and progress means suffering.","JUL 21 Sat","投稿人的话：要进步，就得受苦，从来没有不劳而获的进步。人们都说，如果你受不了苦，那就回到妈妈身边，等适应了这个社会再出来。"},
					  {"优于别人，并不高贵。真正的高贵应该是优于过去的自己。","There is nothing noble in being superior to some other man. The true nobility is in being superior to your previous self.","JUL 20 Fri","投稿人没有留下姓名。这句话出自海明威《真实的高贵》。小编想说：昨日种种，皆成今我；今日种种，方成新我。"},
					  {"人的生命，似洪水奔流，不遇岛屿和暗礁，难以激起美丽的浪花。","Human life is like flowing water, only when it hits isles and reefs can it create beautiful waves.","JUL 19 Thu","小编的话：人生是一次航行。航行中必然会遇到从各个方向袭来的劲风，然而每一阵风都会加快你前行的速度。只要你稳住航舵，即使是暴风雨，也不会使你偏离航向。"},
					  {"人生的目标是唯一值得发现的财富。追求你的目标，无论它是什么，都将是稳定和不屈不挠的。","An aim in life is the only fortune worth finding. Pursue your object, be it what it will, steadily and indefatigable.","JUL 18 Wen","投稿人的话：本人是一位准大学生，高考刚刚结束，这是老师给我班的毕业寄语，感觉非常有意义，希望让更多的人看到。✧٩(ˊωˋ*)و✧"},
				      {"不管我们面对什么处境，不管我们的内心多么矛盾，我们总有选择。我们是什么样的人，取决于我们选择做什么样的人。","No matter what situation we face, no matter how contradictory our hearts are, we always have a choice. What kind of person we are depends on who we choose to be.","JUL 17 Tue","投稿人的话：这句话选自《蜘蛛侠·英雄归来》。人生处处充满了选择，我们不得不为了一些东西去放弃另一些，但是俗话说有舍才有得，这才是人生成长的意义不是吗？"},
				      {"记忆是一种相见的形式，忘却是一种解脱的方式。","Memory is a kind of want to see, and forgetting is a form of relief.","JUL 16 Mon","投稿来自星星。想说的话：这是出自纪伯伦《沙与沫》中的一段话。我想送给我喜欢的一个女孩，因为复读我遇见你，所以我感到很幸运。但我一直没有勇气和你说话，我们都不是很熟悉，都是用眼神交流。直到快要高考的时候，我向你表白。我用了“I adore you”，但被你拒绝了。从此以后，我们形同陌路，就连最后道别都没有了，心中有些失落和悲伤。在高考英语考试七选五阅读题中出现了adore这个词，当时我心中思绪万千，我相信你一定会有一样感受。现在已经想通了，人生在世，能够遇见就已经很幸运了，而能在每个人心中留下东西就不负"},
     		
					//2018-7-9~2018-7-15
					{"积极的人是幸运的人，当别人只看到刺的时候，他们却可以看到玫瑰。","Positive people are lucky people, they can see the roses while others see only the thorns.","JUL 15 Sun","投稿人的话：生活也许总是有不如意的地方，但我们要以积极乐观的心态去面对，因为当你对任何事情都负有此心态时，你才会发现其实成功离你很近。"},
					  {"梭罗说：“大多数人都生活在平静的绝望中。”","Thoreau said: most men lead lives of quiet desperation.","JUL 14 Sat","投稿人：Rowen。他想说的话：当我们深知自己是庸俗的，卑怯的，甚至无用的，依然还热气腾腾的活着，用一颗平淡之心去接受这一切。"},
					  {"成功并不是终点，失败也不会致命，真正重要的是继续走下去的勇气。","Success is not final, failure is not fatal. It is the courage to continue that counts.","JUL 13 Fri","投稿来自尘世镜，这句话出自温斯顿·丘吉尔。投稿人想说的话：胜不骄败不馁，不要放弃前进的勇气！还有一位童鞋同样投稿了这句话，TA想说：没有一次成功是一劳永逸的，而一次失败却会使弱者一蹶不振。小编想说：凡事不论成败，只要经历。如果非要论成败，人生豪迈，大不了从头再来！"},
					  {"信念坚定，如虎添翼。","We are twice armed if we fight with faith.","JUL 12 Thu","投稿人的话：我们都应该为梦想而活，而我们所追求的可以不是具体的事，也许是一种精神，也许是一种人品，也许就是一种生活的态度。"},
					  {"林子里有两条路，我选择了行人稀少的那一条，它改变了我的一生。","Two roads diverged in a wood, and I took the one less traveled by, and that has made all the difference.","JUL 11 Wen","投稿人的话：人生路上，我们总是会面临各种选择，但却对未选择的那条路充满了遐想。如果当年选择的是另一条路，又会是怎样的一个结局。选自《The Road Not Taken》（未选择的路）。小编想说：既然已经做出了选择，跪着也要走完！共勉~"},
				      {"学，然后知不足。","The more you study, the more you will find yourself ignorant.","JUL 10 Tue","投稿人的话：我们的一生都在学习，从出生到死亡，很多事情不是学会了就能一直会，所以我们需要不断学习，不断提高自己的能力。"},
				      {"吾独爱世间三物：昼之日，夜之月，汝之永恒。","I love three things in the world: the sun, the moon, and you. The sun for day, the moon for night, and you forever.","JUL 9 Mon","投稿人的话：这句话出自《暮光之城》。泪目，曾经也有一个人对我许下相似的诺言，如今却已是陌生人。还有一位童鞋也投稿了这句话，只是感受完全不同：遇见对的人，一切就都好了！因为有你我觉得很幸运。老公，谢谢你给我一个家，鼓励我变成了更好的自己！小编想说：希望每一个人都能找到自己的真爱~"},
     		
					//2018-7-2~2018-7-8
					{"人们总是会写一些我的事情。这是他们的事，对我来说，我只需要尽最大努力踢好球。把球踢好就足够了。","People are always writing things about me. It is up to them, for me it is not a problem. I will just try to play well and do my best. I am just focused on playing good football.","JUL 8 Sun","小编的话：这句话出自C罗。他还说过：以我的人生经历，我可以告诉一些年轻球员：如果你想成为一位巨星，那么光有球技是不行的，你必须要懂得一些做人的道理！我与梅西的关系也并不是你们想的那样糟糕，未来有一天也许你们会看见我们在一起喝酒谈心。"},
					  {"人是生而自由的，却处处受到束缚。","Man was born free, and everywhere he is in chains.","JUL 7 Sat","投稿人的话：卢梭的自由观，原话：人是生而自由的，又无往不在枷锁之中，这可能就是生活的样子吧。还有就是我希望我的这句话能上榜，哈哈，想要周边。很感谢这位童鞋的投稿，记得把你的账号和想要的周边发到邮箱：1292943090@QQ.com~~不要太开心~~"},
					  {"世上没有绝对的善与恶，差别只在于强者和无法分清事实的弱者。","There is no good and evil, there is only power and those too weak to seek it.","JUL 6 Fri","小编的话：这句话出自《哈利·波特》中的伏地魔，就是那个想要统治魔法世界最后死于话多的大反派。小编以为世上没有绝对的善与恶，只要心中有爱，有勇有智，你绝对不会死于话多。哈哈~~"},
					  {"这样确信无疑的爱，一生只此一次。","This kind of certainty comes just once in a lifetime.","JUL 5 Thu","投稿人的话：这句话出自《廊桥遗梦》。投稿人在阅读影评时看到这句话投稿小编。对于这句话TA暂时没想到要说什么。大家有什么感想可以在评论区留言交流~~"},
					  {"假如生活欺骗了你，不要悲伤，不要心急！忧郁的日子里须要镇静：相信吧，快乐的日子将会来临！","If by life you were deceived, don't be dismal, don't be wild! In the day of grief, be mild. Merry days will come, believe.","JUL 4 Wen","投稿人：Chanchan。他想说的话：你以为下雨是天空挂满泪滴它在哭泣，却不知默默滋养了多少干涸的生命；你以为黑夜是恶魔的化身它有张狰狞的脸，却不知夜色深沉悄悄拉开新的帷幕；你以为曾经的伤痛是上天给你最不能承受之重，却也不知在静静无声地锻炼了你的精神~"},
				      {"一个人的人格不取决于他如何享受胜利，而在于这个人如何忍受失败。","A person's character is not determined by how he or she enjoys victory, but rather how he or she endures defeat.","JUL 3 Tue","投稿来自湖北医药学院的枫枫酱。这句出自美剧《纸牌屋》。小编想说：古往今来成大事者必定耐得住寂寞，承受得住痛苦，能在人生最无助的黑夜，最忧伤的惶惑里，不惧不移，不馁不弃。谨以此句共勉！"},
				      {"平凡的脚步也可以走完伟大的行程。","Ordinary steps can also take a great trip.","JUL 2 Mon","投稿人的话：平凡之路亦有璀璨星光，没有什么做不到的事，因为路在脚下。这里小编想引用朴树的歌《平凡之路》补充：我曾经像你像他像那野草野花，绝望着也渴望着；我曾经失落失望失掉所有方向，直到看见平凡才是唯一的答案。"},
     		
			//6-25--------7-1			
					  {"我发现人是值得活的。若再给我一次生命，我将欣然接受这难得的赐予。","I have found it worth living, and would gladly live it again if the chance were offered me.","JUN 24 Sun","小编的话：投稿来自词霸程序员哥哥。某programmer读到这句话颇有感触：他发现即使无法控制自己的发际线，人依旧是值得活的，毕竟还有那么多bug没改（小编皮一下很开心~~）。这位程序猿还向小编分享了一首歌：毛不易的《消愁》：一杯敬朝阳，一杯敬月光；一杯敬故乡，一杯敬远方"},
					  {"只要我们还在跑，只要我们始终不停下来，谁又知道前方会发生什么呢？","As long as we're running, as long as we don't stop, who knows what's going on ahead? ","JUN 30 Sat","投稿人的话：再笨的人，只要始终不停下脚步，未来就一定有期待。每一个人都有不同，我们只要尽自己最大努力做好自己就行了。"},
					  {"那只是一场比赛，重要的是永不低头。","It's just a game, so go and play.","JUN 29 Fri","小编的话：生活不是一场赛跑，不要因为一次失败，就放弃你原来决心想达到的目的。正值世界杯，给大家推荐一部应景的足球电影《最后一球》。这部电影通过落魄球星尤里与菜鸟球队“流星队”逆风前行的故事，向观众展现了永不低头的足球精神。"},
					  {"你也许会对我们失去信心，但永远不要对你自己失去信心。","You may lose faith with us, but never with yourself.","JUN 28 Thu","这句话来自源自《变形金刚3》。投稿人的话：信心最为宝贵，正如萧红所说：“人生激越之处，在于永不停息地向前，背负悲凉，仍有勇气迎接朝阳。”"},
					  {"自由是做你自己喜欢的事，而幸福却是喜欢你自己做的事。","Doing what you like is freedom, liking what you do is happiness. ","JUN 27 Wen","投稿人：淡蓝色的梦，他想说的话：这句话来自《破碎故事之心》，是美国作家杰罗姆·大卫·塞林格所创作的短篇小说。故事戏仿当时的浪漫小说和流行的警匪片，讽刺了商业杂志推销。"},
				      {"如果你错过我坐的这趟火车，你就该明白我已离去，你可以听见一百里外飘来的汽笛声。","If you missed the train I’m on, you will know that I am gone, you can hear the whistle blow a hundred miles.","JUN 26 Tue","投稿人张鹏，他想说的话：你我可能只是过客，相遇、错过、再重逢……我会翻看以前的照片，听你喜欢的音乐。我会铭记你的节奏和味道，也尊重你的选择。"},
				      {"没有人生来就因为肤色去憎恨别人。每个人都可以学会去爱，因为爱自在人心。","No one is born hating another person because of the color of his skin. They can be taught to love, for love comes more naturally to the human heart.","JUN 25 Mon","投稿人没有留下姓名。这段文字出自曼德拉总统自传《漫漫自由路：Long walk to freedom》。小编想说：我们的感情不是生而确定，而是在生活中慢慢感受而来。我们要学着去爱，因为爱才会让生活更美好。"},
       		
			//6-18--------6-24			
					  {"我发现人是值得活的。若再给我一次生命，我将欣然接受这难得的赐予。","I have found it worth living, and would gladly live it again if the chance were offered me.","JUN 24 Sun","小编的话：投稿来自词霸程序员哥哥。某programmer读到这句话颇有感触：他发现即使无法控制自己的发际线，人依旧是值得活的，毕竟还有那么多bug没改（小编皮一下很开心~~）。这位程序猿还向小编分享了一首歌：毛不易的《消愁》：一杯敬朝阳，一杯敬月光；一杯敬故乡，一杯敬远方"},
					  {"失去深爱的人，我们仍要学会好好的生活。学着放手，微笑着道别，尽管这并不容易。","You lost the people you love and you have to find a way to go on. Then let it go and say goodbye with a smile. Although it won't be easy.","JUN 23 Sat","为了爱情，小编想帮这位投稿人说：嗨！雅倩，那个爱笑的女孩，你过得好吗？高三后我复读了一年，碰巧和你同班。我喜欢了你四年，缘分让我窃窃欢喜。本打算高考后就向你表白，说尽这四年来埋藏在我心底想说却不敢说的话，但最终放弃。因为害怕打搅到你。我联系不到你，只是希望你能看见。雅倩，祝你一切都好！"},
					  {"如果你不出去走走，你就会以为这就是全世界。","If you don’t go for a walk, you’ll think this is the world. ","JUN 22 Fri","投稿人：『光畫』，他没有留下只言片语。小编想说：此处想引用邓布利多的一句话：决定我们成为什么样的人，不是我们的能力，而是我们的选择。"},
					  {"我将每天把我的爱情，装进以吻封缄的信封里送给你。","I will send you all my love everyday in a letter sealed with a kiss.","JUN 21 Thu","投稿人的话：这是在一次课堂上老师提到“以吻封缄”这个浪漫的词联想到的。生命中任何事情都需要有仪式感，爱情也一样，希望拥有爱情的人，每天都可以如初相恋，而爱情还未降临的人，充满期待与感激。"},
					  {"一粒珍珠，是痛苦围绕着一粒沙子所建造起来的庙宇。","A pearl is a temple built by pain around a grain of sand.","JUN 20 Wen","投稿人的话：这句话出自纪伯伦。“宝剑锋从磨砺出，梅花香自苦寒来”，沙粒变成珍珠，同样经受了痛苦的孕育。没有谁可以随随便便成功，所有的一切都是有代价的。成为你想成为的人，便要尝过那条路上的艰辛。把痛苦当做享受吧，因为你在做一件很伟大的事情：你可是在孕育珍珠耶！"},
				      {"不是所有有价值的事物都可以被计算，也不是所有可计算的事物都值得去计算。","Not everything that counts can be counted, and not everything that's counted truly counts.","JUN 19 Tue","投稿人的话：这句话是爱因斯坦的名言，也被引用在了演讲《在你感觉到压力之前》里：按自己的节奏走，不代表你不积极不努力。每个人都有自己的生活节奏，希望你们能够创造自己有意义的人生，学着如何去影响别人的生活。这，才是真正的成功。投稿人Tommy同样贡献了这句话，他认为：人生有无限可能，别给自己设限，别让任何人打乱你的节奏，余生漫长，莫要慌张。"},
				      {"如果你看到这个世界出了问题，你要么别插手，要么去改变她。","If you see something wrong happening in the world, you can either do nothing, or you can do something.","JUN 18 Mon","投稿人苏苏：这句话出自《神奇女侠》。他想说的话：选择无处不在，重要的是跟随自己的内心，我们或许改变不了什么，但是我们坚持自己的选择。Ps:祝亮晶晶生日快乐，同时也希望各位关注每日一句的小伙伴端午节快乐～苏苏真的是很有心了，各位记得吃粽子哦~~"},
         		
			//6-11--------6-17			
					  {"我的父亲就是我的英雄，从始至终。","My father is my hero, all throughout my life.","JUN 17 Sun","小编的话：总是向你索取，却不曾说谢谢你；直到长大以后才懂得你不容易。时光时光慢些吧，不要再让你变老了。我愿用我一切，换你岁月长留。祝所有的爸爸，节日快乐，身体安康！你们永远是孩子心目中的大英雄！爸爸，我爱您，真的真的很爱！"},
					  {"我只是在必要的时候才会勇敢，勇敢并不代表你要到处闯祸。","I am only brave when I have to be. Being brave does not mean you go looking for trouble.","JUN 16 Sat","词霸小编：这句话出自电影《狮子王》，投稿来自甘肃名族师范学院的学生东之伊。《狮子王》也是小编最喜欢的迪士尼电影。辛巴从一只小狮子蜕变为森林之王，他坚强勇敢的品质值得每一个大朋友、小朋友学习。"},
					  {"天堂地狱都没法给你慰藉，只有我们自己：渺小，孤独，奋斗，与彼此抗争。我向自己祈祷，为自己祈祷。","There is no solace above or below. Only us... Small, solitary, battling one another. I pray to myself, for myself.","JUN 15 Fri","词霸小编：投稿来自湖北医药学院的枫枫酱。出处《纸牌屋》。我为自己奋斗，我为自己祈祷。"},
					  {"有多大能力就会有多大责任。","With great power there must come great responsibility.","JUN 14 Thu","词霸小编：投稿来自甘肃名族师范学院的学生东之伊。这句话出自电影《蜘蛛侠》。在一次抢劫中由于彼得的任性，导致他的叔叔被劫犯重伤。在临终前叔叔对彼得说了这句话，从那以后彼得开始行侠仗义，并把这句话当成了自己的座右铭。"},
					  {"活着就是为了改变世界。","We are here to put a dent in the universe.","JUN 13 Wen","词霸小编：投稿来自鲁一修。很多人都说这句话是乔布斯说的，其实并不是。活着的意义有很多种，有些人活着就是为了改变世界。"},
				      {"每个人都是天才。但如果硬要以鱼儿爬树的本领，来评估它的能力，它这辈子都会觉得自己是条蠢鱼。","Everyone is a genius. But if you judge a fish by its ability to climb a tree, it will live its whole life believing that it is stupid. ","JUN 12 Tue","词霸小编：这句话出自爱因斯坦。投稿人没有留下姓名。小编想说，每个人都有自己的闪光点，要善于发现自己的优秀之处。"},
				      {"没有谁对不起谁，只有谁不懂得珍惜谁。","No one is indebted for others, while many people do not know how to cherish others.","JUN 11 Mon","词霸小编：投稿人发自我的小米手机。我想说:没有谁活得比谁容易，互相理解，海阔天空，相逢即是缘，一笑抿恩仇。"},
           
			//6-4--------6-10			
					  {"如果我们把我们的心真正投入到生活之中，那么幸福快乐就总是在我们身边。","Happiness is always around us if we put our hearts into life.","JUN 10 Sun","词霸小编：投稿人：婉茹。她想说的话：幸福就像玻璃，平时看不见，稍微调整一下角度，就会光芒四射。如果还记得自己的梦想，那就请一直坚持下去不要忘记！学生党加油啊，胜利就在眼前，不要畏惧黎明前的黑暗！"},
					  {"永远不要忘记你的家人有多爱你。","To never forget how much your family loves you.","JUN 9 Sat","投稿人的话：这句话选自电影《寻梦环游记》。我想说的是又是一年高考时期，高考之后大家就要各奔大学，有的人可能离家很远很远(比如我…)，但是不管你离家究竟有多远，家都是你最温暖的港湾和最坚强的后盾你的家人会永远的爱你、守护你、拥抱你。"},
					  {"别让你的梦想只成为梦想。","Do not let your dream be dream.","JUN 8 Fri","词霸小编：投稿人没有留下姓名。投稿人想说：高中的时候很喜欢这句话，把它 写在桌子上面激励自己。如果有了梦想而不努力去实现，梦想最后只能成为空想。"},
					  {"天下事有难易乎？为之，则难者亦易矣；不为，则易者亦难矣。","There is nothing difficult for us to do in the world. If we do it, the difficult thing will be easy; if we do not, the easy thing will be difficult.","JUN 7 Thu","词霸小编：投稿人没有留下姓名，投稿人想说的话：如果我们下定决心要做一件事，就不要怕前方会有的困难，只有做了，才知道我们到底行不行，不做就永远不会成功。SO, JUST DO IT."},
					  {"走的慢不重要，重要的是你不停止。","It does not matter how slowly you go as long as you do not stop.","JUN 6 Wen","投稿人小米同学。有时会发现身边的人比自己优秀好多，心里会惊慌。但是与其慌乱，不如控制自己的节奏，慢慢来，慢一点没关系，但千万不要停止努力。"},
				      {"若我会见到你，事隔经年。我该如何致意，以眼泪，以沉默。","If I should meet thee, after long years. How should I greet thee, with silence and tears.","JUN 5 Tue","投稿人刘怡然。高三的我，即将和那些与我发生故事的人们说：珍重 再见。如果有缘再见，过往的事都将不再被提起，只有对望时的无语凝噎。"},
				      {"世界总为梦想者让路。","The world always makes way for the dreamer.","JUN 4 Mon",""},
             //5-28--------6-3	
			          {"生活从来没有容易的答案，只有去克服重重问题。","There are no easy answers, there is only living through the questions.","JUN 3 Sun",""},
					  {"其实，生活就像洋葱，一片一片剥下去，总有一片让你流泪。","Truly, life smells like an onion: You peel it off one layer at a time, and sometimes you weep. ","JUN 2 Sat",""},
					  {"童年是金色的田野，是青青的小河，是五彩斑斓的卵石滩。","Childhood is the golden field, the green river, and the colorful pebble beach.","MAY 31 Fri","小编的话：祝各位大盆友小盆友儿童节快乐！要永远保持一颗纯真的童心。还记得小时候：白纸折西北东 南，玻璃弹珠挖坑弹。溜溜溜球手上转，嘴里吹着泡泡糖。现如今：各种核桃手中盘，吃饭都要养生餐。 都说我是伪中年，怎知我才95颜。"},
					  {"只有聋子才珍惜听力，只有盲人才能感受到得见光明的喜悦。","Only the deaf appreciate hearing, only the blind realize the manifold blessings that lie in sight. ","MAY 30 Thu","小编的话：这句话出自海伦凯勒的《假如给我三天光明》。"},
					  {"成功并不在于从不犯错，而在于不重蹈覆辙。","Success does not consist in never making mistakes but in never making the same one a second time.","MAY 29 Wen","词霸小编：没有一蹴而就的成功，成功的道路上总是会有各种各样的失误，我们不应该害怕犯错，而应该从这些失误中总结经验教训。"},
					  {"生命的意义不仅在于简单的存在与活着，而是去前行、进步、获取和征服。","The meaning of life is not simply to exist, to survive, but to move ahead, to go up, to achieve, to conquer.","MAY 28 Tue","词霸小编：与其一生碌碌无为，平庸度日，不如把握大好时光，做一个积极进取的人。"},
					  {"世界上最大的谎言就是你不行。","You cannot do it-that is the biggest lie on earth.","MAY 28 Mon","词霸小编：世界上最大的谎言就是你不行。每个人都有无限的潜力，要相信自己，能够努力做好每一件事。"},
			 //5-21--------5-27		
					  {"当你想做成一件事情的时候，全宇宙都会帮助你得到它。","When you want something, all the universe conspires in helping you to achieve it. ","MAY 27 Sun","词霸小编：这句话出自巴西著名作家保罗·柯艾略的经典寓言式小说《牧羊少年奇幻之旅》。小说运用富含哲理和诗意的语言讲述了牧羊少年圣地亚哥追寻宝藏的奇幻冒险故事，极具启发性和励志意义。"},
					  {"把握当下才是真。","Only those who capture the moment are real. ","MAY 26 Sat","词霸小编：人生短暂，无须沉溺在对往事的追忆或对未来的担忧之中，把握当下的每一分钟，方才不会浪费生命。"},
					  {"生活本身就是最美妙的童话故事。","Life itself is the most wonderful fairy tale. ","MAY 25 Fri","词霸小编：你准备好了吗？别再犹豫，握住生命之笔，书写属于你最美丽的童话。"},
					  {"拥有朋友是最美好的一件事，成为别人的朋友是最美妙的一件事。","A friend is one of the nicest things you can have, and one of the best things you can be.","MAY 24 Thu","词霸小编：友情是人生中最宝贵的精神财富之一，我们要珍惜生命中每一个朋友，并用同样的热情回馈他们。"},
					  {"繁星纵变，但智慧永恒。","The stars change, but the mind remains the same.","MAY 23 Wen","词霸小编：不管世界万物如何变化，你所学的知识永远属于你。"},
				      {"如果我有八小时砍倒一棵树，我会拿六小时来磨斧头。","If I had eight hours to chop down a tree, I would spend six hours sharpening my ax.","MAY 22 Tue","词霸小编：磨刀不误砍柴工，事先做好充分准备，就能提高工作效率。"},
					  {"我不害怕明天，因为我经历过昨天，又热爱今天。","I am not afraid of tomorrow for I have seen yesterday and love today. ","MAY 21 Mon","词霸小编：无须过于忧虑未来的事情，也无须沉溺在对往事的追忆之中，只要珍惜当下的点点滴滴，就不会挥霍宝贵的时光。"},
			 //5-14--------5-20		  
					  {"唯有爱可以把黑暗囚在波港湾里。","Love is the only thing that holds the dark at bay. ","MAY 20 Sun","词霸小编：爱是一种无形的强大力量，给人温暖，驱散寒夜里的黑暗。"},
					  {"我们看起来似乎痛苦的试炼，常常是伪装起来的好运。","What seems to us as bitter trials are often blessings in disguise.","MAY 19 Sat","词霸小编：凡事都有两面性，人生中经历的痛苦与挫折，往往会转化为宝贵的财富。"},
					  {"在遇到梦中人之前，上天也许会安排我们先遇到别的人，在我们终于遇见心仪的人时，便应当心存感激。","Maybe God wants us to meet a few wrong people before meeting the right one, so that when we finally meet the person, we will know how to be grateful.","MAY 18 Fri","词霸小编：人的一生会遇见许多人，在对的人出现的时候，你会发现，兜兜转转，原来是你。"},
					  {"爱不是从相互凝视之中获得的，倒是可以由同朝一个方向眺望而产生，这是生活给予我们的教训。","Life has taught us that love does not consist in gazing at each other but in looking outward together in the same direction.","MAY 17 Thu","词霸小编：有着共同的目标或是信仰的人们总是更容易互相吸引，因为他们身上散发着相近的魅力。"},
					  {"当你感觉希望不在的时候，看一下自己的内心世界，探寻一下自己的灵魂，你会在你心中找到一个英雄。","When you feel like hope is gone, look inside you and search your soul. You will find a hero lies in you.","MAY 16 Wen","词霸小编：当你怅然若失之时，可以自己帮助自己走出眼前的阴霾，那时，我们可以成为自己的英雄。"},
	                  {"只要一个人还有追求，他就没有老。直到后悔取代了梦想，一个人才算老。","A man is not old as long as he is seeking something. A man is not old until regrets take the place of dreams.","MAY 15 Tue","词霸小编：梦想是一种理性、一种追求、一种力量，激励人们去拼搏，鼓舞人们不断前行。"},
	                  {"强者能同命运的风暴抗争。","A strong man will struggle with the storms of fate.","MAY 14 Mon","词霸小编：强者从不愿意向命运低头，他们勇于同一切困难抗争，这是他们成功的关键所在。"},
	         //5-7---------5-13	      
					  {"知识是一座城堡，每个人都应为它增砖添瓦。","Knowledge is a city to the building of which every human being brought a stone.","MAY 13 Sun","词霸小编：知识如同珍宝，不是轻易获得的，必须学习、钻研、思考，最重要的是必须有强烈的求知欲。"},
				      {"只要我们还有回忆，昨日仍在。只要我们还有希望，明日尚待。","As long as we have memories, yesterday remains. As long as we have hopes, tomorrow awaits.","MAY 12 Sat","词霸小编：你荒废的今日，正是昨日殒身之人祈求的明日。"},
				      {"时间可以治愈一颗受伤的心，同样也可以撕裂一颗等待的心。","Time can heal a broken heart, but it can also break a waiting heart.","MAY 11 Fri","词霸小编：时间以里的是心事，时间以外的是故事，你是我时间以外的时间以里！！！"},
				      {"回到昨天毫无用处，因为今天的我和过去有所不同。","It is no use going back to yesterday, because I was a different person then. ","MAY 10 Thu","词霸小编：这句话出自刘易斯·卡罗尔的儿童文学作品《爱丽丝梦游仙境》，此书被誉为英国魔幻文学的代表作、世界十大著名哲理童话之一，奠定了怪诞、奇幻的现代童话基调，堪称跨时代的里程碑。"},
				      {"最长的路也有尽头，最黑暗的夜晚也会迎接清晨。","The longest way must have its close;the gloomiest night will wear on to a morning.","MAY 9 Wen","词霸小编：野草遮不住太阳的光芒，困难挡不住勇者的脚步。只要有方向，永远不会迷失；只要有信心，永远不会挫败。"},
				      {"勇气是一切美德攀登的阶梯。","Courage is the ladder on which all the other virtues mount.","MAY 8 Tue","词霸小编：勇气像一粒充满生机的种子，种在我们的身体里，是我们为人处世所不可缺少的，它能使我们积极进步。"},
				      {"身在井隅，心向璀璨。","We are all in the gutter, but some of us are looking at the stars.","MAY 7 Mon","词霸小编：生活不只有眼前的苟且，也有诗意的远方。井隅之中有庸庸碌碌，也有星光。救赎不在远方，而在于自己的人生态度。"},
			 //4-30---------5-6      
					  {"你天生与众不同,注定不会被淹没在人群中。","You are not ordinary, you are born to stand out.","MAY 6 Sun","词霸小编：感谢用户“流水无痕”的热情投稿。生而平凡，却不甘平凡。为了希望的明天，一直在奋斗。途中有泥泞坎坷，也有风花秋月。当你回顾旅途，发现走过一段不平凡的旅程，造就一个不凡的自我。"},
				      {"眼泪是来自心底却说不出口的话。","Tears are words from the heart that can’t be spoken.","MAY 5 Sat","词霸小编：或许只有苏轼的“相顾无言，惟有泪千行。”才能诠释此情此景此感吧~"},
				      {"生活不总是完美无缺，轻而易举或者合情合理。但这就是生活的美好之所在。","Life isn’t always perfect. Life isn’t always easy. Life doesn’t always make sense. But that’s the beauty of life.","MAY 4 Fri","词霸小编：卓别林的一句话送你们“用特写镜头看生活，生活是一个悲剧，但用长镜头看生活，就是一部喜剧。”"},
				      {"老天总是捉弄人，安排了相遇，却又策划了分离。","God is always playing tricks on people, arranging them to meet but then to separate.","MAY 3 Thu","词霸小编：#感谢投稿人“绿时”#，很有故事的一句话，绿时说：后来她才明白，有的分离让相遇变得更美，而有的却成了永别……"},
				      {"怕什么路途遥远。走一步有一步的风景，进一步有一步的欢喜。","Don’t be concerned with the distance of the journey. Every new step provides a new view; every new destination creates new joy.","MAY 2 Wen","词霸小编：当我骑行在路上，别人说路太远了，根本不能到，我没理，半道上我开车前行；当我开车时，别人说再往前开就是悬崖峭壁，没路了，我没理，继续往前开，开到悬崖峭壁我换飞机了，结果我去到了任何我想去的地方。有梦想，真好！"},
				      {"世上唯有贫穷可以不劳而获。","Nothing is to be got without pains but poverty.","MAY 1 Tue","词霸小编：我享受生活，但并不懒惰，我渴望闲适，但仍自食其力。我是一名劳动者，值此，以劳动者的名义，为自己放！个！假！五一快乐​​​​！"},
					  {"一旦选择做一个阳光，快乐的人，那风雨也只会是让你起舞的理由。","When you choose to be a sunny, happy person, a rainstorm becomes just another excuse to dance.","Apr 30 Mon","词霸小编：天再高又怎样，踮起脚尖就更接近阳光~~"},
			  //4-23---------4-29	  
					  {"重要的是，要选择一个让自己自在的人，因为两个人可能不再缠绵悱恻，却会相守一生。","It‘s important to be with someone who gives you comfort, because love fades, but companionship stays.","Apr 29 Sun","词霸小编：其实我一直觉得所谓爱，就是陪伴，没太多别的。——韩寒"},
				      {"我们生来就是为了坚持，这样我们才能明白自己是谁。","We are made to persist. That’s how we find out who we are. ","Apr 28 Sat","词霸小编：不管做什么，都不要急于回报， 因为播种和收获不在同一个季节，中间隔着的一段时间，我们叫它为‘坚持’ 。"},
					  {"黑夜默默地绽放花儿，把赞美留给白昼。","The night opens the flowers in secret and allows the day to get thanks.","Apr 27 Fri","词霸小编：我在黑夜里。看着光明，我不渴望光明的到来，我只愿……成为那黑夜也都染不去的暗！"},
					  {"若将人生一分为二，前半段叫做 “不犹豫”，后半段叫做 “不后悔”。","If life is divided by two,the former is “no hesitation” and the latter is “no regret”.","Apr 26 Thu","词霸小编：我的青春，花开花谢，虽有遗憾却不后悔。"},
					  {"懂道理的人，终会有放下自尊、坦承他犯下严重错误的时刻。","Here comes a point when a reasonable man will swallow his pride and admit that he's made a terrible mistake.","Apr 25 Wen","词霸小编：很多人都是这样，因一时的冲动和朋友发生了争吵，冷静过后，又主动地向朋友道歉，因为他知道，在朋友面前，自己可以放下自尊。道歉正面是认错，背面是珍惜"},
				      {"让我们成为什么样的人，并不是我们的能力，而是我们的选择。","It is not our abilities that show what we truly are, it is our choices.","Apr 24 Tue","词霸小编：努力，并不是做给某个人看，而是为了让自己有选择的权利，寻找更好的人生。"},
	                  {"爱意味着永远不必说抱歉。","Love means never having to say you’re sorry. ","Apr 23 Mon","词霸小编：最好的关系不是随叫随到，每天都聊，而是我发了消息，你看到了自然会回复，我不会因为你没有回复而胡乱猜忌，你也不会因为没有及时回复而感到抱歉，彼此信任，彼此牵挂就够了。"},
	          //4-16---------4-22       
	                  {"事情总是在你最不抱期望的时候得到解决，所以，永远不要放弃。","Things have a way of working out when you least expect it. Never give it up. ","Apr 22 Sun","词霸小编：任何事情都会有皆大欢喜的结果，如果没有，就证明还没有到最后。"},
	                  {"春风十里，不及相遇有你； 晴空万里，不及心中有你。","Spring breeze miles, less than to meet you; Clear blue skies, not as good as you are in my heart.","Apr 21 Sat","词霸小编：你相信吗，未来要和你共度一生的那个人，其实在与你相同的时间里，也忍受着同样的孤独。那个人一定也怀着满心的期待，拥着一腔孤勇，穿过茫茫人海，也要来与你相见。"},
	                  {"只有勇敢的人才懂得如何宽容；懦夫绝不会宽容，这不是他的本性。","Only the brave man know how to tolerant. Coward never tolerant, it is not his nature. ","Apr 20 Fri","词霸小编：有一天你会突然发现，你的好，就像一杯水，在别人嘴里一喝酒没；而你的不好，则像一粒种子，会在别人心里生根发芽。生活给了我们很多考验，我们要做的就是学会去接受和宽容。"},
	                  {"爱就是当你坐在他身边，哪怕什么都不做，也会感觉很开心。","Love is when you sit beside someone doing nothing yet you feel perfectly happy.","Apr 19 Thu","词霸小编：生命中遇到的每一个人都是有理由的，之前所有的错失与遗憾，都只是为了遇到最终那个对的人。当你受过了青春的伤、尝过了生活的苦，这时候遇见的人，才真的有可能是一辈子。在你最无助时出现的那个人，才是上天派来爱你的天使。"},
	                  {"人生就是不断收集回忆的过程，最终能陪伴我们的，也只有回忆了。","The business of life is the acquisition of memories. In the end that‘s all there is. ","Apr 18 Wen","词霸小编：学生时代，可以说是人生中最值得回忆的时代。买了一堆辣条，上课偷偷吃；拿笔使劲挫前桌的后背去借东西；作业本放在一起都觉得能一辈子；原来最讨厌穿的衣服，现在却成了进学校的通行证；去旅游，景点门票学生有打折...哪个瞬间让你觉得，学生时代很美好？"},
	                  {"真正的胜利是持久的和平，而不是战争。","True victory was lasting peace, not war. ","Apr 17 Tue","词霸小编：我们不是生活在一个和平的时代，而是生活在一个和平的国家！"},
	                  {"时间只是一个自称能包治百病的庸医。","Time is a self proclaimed quack which can cure all diseases.","Apr 16 Mon","词霸小编：很久之前，谎言和真实在河边洗澡。谎言先洗好，穿了真实的衣服离开，真实却不肯穿谎言的衣服。后来，在人们的眼里，只有穿着真实衣服的谎言，却很难接受赤裸裸的真实。时间暴露了谎言，改变了距离，也看清了人心。你曾经说过什么谎言？ "},
	          //4-9---------4-15
	                  {"有一种“贫穷”叫短视。","There’s a kind of poverty called short-sightedness.","Apr 15 Sun","词霸小编：-你为什么这么累？ -贫穷限制了我的休息。 "},
	                  {"对朋友爱得越深, 奉承得就越少。","The more we love our friends, the less we flatter them.","Apr 14 Sat","词霸小编：生活中，有一种情商低可以解释为善良过度。对自己的朋友比对自己还好，总是小心翼翼，甚至觉得是自己应该做的。而自己却不敢去麻烦他们，怕被拒绝。于是自己的那些友谊渐渐地只能靠帮助朋友来继续，担心感情因自己的不周而崩裂。有时候，一些人甚至觉得这是谦虚的表现。所以，真正交朋友，应该少些奉承，别让自己太累。 "},
	                  {"世界上最遥远的距离不是生与死，而是我就站在你面前，你却不知道我爱你。","The furthest distance in the world is not between life and death. But when I stand in front of you, yet you don‘t know that I love you. ","Apr 13 Fri","词霸小编：这一辈子，我们做过最徒劳的事就是不断回头，试图去挽救一段已经结束的关系。然而现实的很多事情我们无法理解，也许是昨晚失眠了，我就不想和你说话了。不再为过去的事而耿耿于怀，没有一个人会在原地等你。生活中，你做过最徒劳的一件事是什么？"},
	                  {"有时候，真的没有下一次，没有暂停继续，没有机会重来。有时候，错过了就永远没机会了。","Sometimes there is no next time, no time-outs, no second chances; sometimes it’s now or never. ","Apr 12 Thu","词霸小编：只有当快毕业的时候才知道自己该学什么，该如何去学，尽管无数次奉劝自己努力，但自己总无法坚持做到，当明白过来已晚。正如人生一样，别人的道理总是听不到心里去，总是要碰得头破血流才知道后悔虚度了时光。如果给你一次重来的机会，你会从何开始？"},
	                  {"音乐要用心灵去听， 用头脑去感觉。","In music one must think with the heart and feel with the brains.","Apr 11 Wen","词霸小编：有时候，我们喜欢一首歌，不是因为有多好听，而是歌词写的像自己。开心时入耳，伤心时入心，快乐的时候，听的是旋律，难过的时候，懂得是歌词！喜欢一首歌，往往是因为某句触动你心的歌词，因为这句歌词似乎在说你的故事…… 生活中，哪一首歌，曾触及你内心？ "},
	                  {"在你想要放弃的那一刻，想想为什么当初坚持走到了这里。","The minute you think of giving up, think of the reason why you held on so long.","Apr 10 Tue","词霸小编：放弃不难，但坚持一定很酷。—— 东野圭吾《解忧杂货店》。是什么让你在这座城市坚持下来？"},
	                  {"看到你的那一刻，我爱上了你。你微笑，因为你知道。","When I saw you I fell in love. And you smiled because you knew. ","Apr 9 Mon","词霸小编：“当初看到你啊，你蒙着红盖头。我当时心想，这姑娘脚这么大，长得一定不好看。结果你一掀开盖头，我傻了，你天生的一副老婆脸，又好看又耐看，就连两个眼袋都长得恰到好处。人们常说是日久生情，可我知道，我对你是一见钟情。”——《武林外传》 "},
	         //4-2---------4-8
	                  {"你越是害怕去改变和失去，就越容易因不思进取而一无所有。","The more you fear to change and lose, the more likely you will lose it without changing yourself. ","Apr 8 Sun","词霸小编：什么都无法舍弃的人，什么也改变不了。高中班主任经常说：只要你想学，什么时候都不晚。所以，如果你愿意努力的话。相信我。你最差的结果。不过是大器晚成。 "},
	                  {"耽溺回忆不如活在当下，让此刻成为未来荣耀之日。","Do not look back and grieve over the past, for it’s gone. Live in the present, and make it so beautiful that it will be worth remembering. ","Apr 7 Sat","词霸小编：对过去耿耿于怀的人，一般都抓不住未来；而一直“忐忑明天”的人，通常也会错过现在的美好。所以，活在当下，和你所热爱的一切，在一起。（via.思佳儿~投稿） "},
	                  {"成功不是终点，失败也并非末日，最重要的是继续前进的勇气。","Success is never final, failure is never fatal. It’s courage that counts.","Apr 6 Fri","词霸小编：生活是自己选的，命由自己不由天。与其羡慕别人抱怨自己，不如自个儿努力朝着向往的方向前进。将来的你一定会感谢现在拼命努力的你。"},
	                  {"在爱的记忆消失以前，请记住我。","Remember me before the memory of the love disappears.","Apr 5 Thu","词霸小编：清明寄哀思，让一个人在世界的某处永远的活着的唯一方式：就是不被活着的家人遗忘。 "},
	                  {"在差异中寻求彼此的共同点,这就是爱。","Love is the power to see similarity in the dissimilar.","Apr 4 Wen","词霸小编：希望你遇见一个与你相似的人，他懂你想要的自由洒脱，愿伴你登一座高山，愿陪你潜一次深海，与你一起成就更好的自己，当然也愿陪你共风雨。"},
	                  {"很多人之所以活得纠结，是因为既想得到别人的认可，却又无法静下心来提升自己！","Many people live a tangled life, for they want to gain others‘ recognition, but unable to polish themselves peacefully.","Apr 3 Tue","词霸小编：高考时，也许你努力不够，没能上好的学校；毕业时，可能是经验不足，没能进好的公司。生活就是这样，充满着各式各样的遗憾。有时候你为了一个机会，会选择等待，但有时候，你的纠结又会让你无法向前。生活中，你，有什么遗憾？ "},
	                  {"向上的路，并不拥挤。拥挤是因为，大部分人选择了安逸。","It is not crowded on the way up. It is crowded where many people choose to stay within the comfort zone. ","Apr 2 Mon","词霸小编：你若咬定了人只活一次，便没有随波逐流的理由。别在最能吃苦的年纪选择了安逸。 "},
	    	//3-26---------4-1
	                  {"不要欺骗任何相信你的人，不要相信任何欺骗你的人。","Never lie to someone who trust you. Never trust someone who lies to you. ","Apr 1 Sun","词霸小编：今天所有的戏精宝宝都会集体上线，小愚怡情，大愚伤身，记得把握好度哦！ "},
	                  {"偶尔赖赖床，没什么不好。","Save some time to be lazy, it’s good for you.","Mar 31 Sat","词霸小编：让我们在该休息的时候，休息；在该流泪的时候，哭泣。这不是上帝送给亚当和夏娃的礼物，而是你自己传给自己的生命秘籍。周末快乐！"},
	                  {"有时候沉默对我们而言胜过千言万语。","Sometimes, the silence seemed to connect us in a way that words never could.","Mar 30 Fri","词霸小编：当你无话可说的时候就别说话，在你不知如何回答别人的话的时候就保持沉默，这是生活中一个很好的策略。 "},
	                  {"最重要的事往往最难说出口。","Things that are hard to say, are usually the most important.","Mar 29 Thu","词霸小编：面对爱，有时候嘴怂，说话言不由衷，有时候只有行动，却羞于表达。容易说出口的话，最不结实，张口就说一辈子的人，兴许只热乎一阵子。 "},
	                  {"善良一点，因为大家的一生都不容易。如果你想真正了解他人，你需要用心去看。","Be kind, for everyone is fighting a hard battle. And if you really wanna see what people are, all you have to do is look.","Mar 28 Wen","词霸小编：一定要做一个不歧视别人缺点的人，善待身边或者仅仅路过的陌生人，因为我们都是有缺陷的孩子，只有努力了才能创造奇迹。 "},
	                  {"我们笑着说再见，却深知再见遥遥无期。","We laughed and kept saying ‘see u soon’, but inside we both knew we‘d never see each other again. ","Mar 27 Tue","词霸小编：你的从前，是我无法看清的未来；你的以后，是我无法插手的过去。往往在真正别离时，才知感情多深。只因回头却不是从前，再见已不再是少年 ​​​​。 "},
	                  {"我不懂，是我们有着各自不同的命运，还是，我们只不过都是在风中，茫然飘荡。","I don’t know if we each have a destiny, or if we‘re all just floating around accident alike on a breeze.","Mar 26 Mon","词霸小编：这个世界没有那么多理所当然，所谓命运，还是得自己一步步走出来。即便是你我相遇，也不过是命运的撮合。 "},

	       //3-19---------3-25
	                  {"在这个世界上，多得是比你丑和比你美的人。所以，不必纠结太多，勇往前行。","There will always be prettier and uglier people than you. Accept it and move on. ","Mar 25 Sun","词霸小编：既能够因当前的风景而动容，也能够冲着远方微微一笑，然后勇敢前行。只要活下去，我们都是孤独且骄傲的英雄。 "},
	                  {"一个人只有用心去看，才能看到真实。事情的真相只用眼睛是看不见的。","It is only with the heart that one can see rightly. What’s essential is invisible to the eyes.","Mar 24 Sun","词霸小编：生活就是这么现实，真真假假总是对立存在。对于一个事物，你不要急于用批判的眼光看待，因为那不一定就是你认为的真相。 "},
	                  {"平凡人也能够选择做到不平凡。","It is possible for ordinary people to choose to be extraordinary.","Mar 23 Sun","词霸小编：梦想可以把汽车送往太空，可以让火箭回收利用，可以让不可能成为可能。很多人羡慕那种生下来就知道自己人生目标的人，但别忘了，他们也是平凡人中的一员。 "},
	                  {"不要走在我前面，我可能追不上你 ；不要走在我后面，我可能不会引路；走在我旁边，做我的朋友就好。","Don‘t walk in front of me, I may not follow. Don’t walk behind me, I may not lead. Walk beside me, just be my friend. ","Mar 22 Sun","词霸小编：这个世界就是这样的，有些人陪我永远，我陪有些人一段。有些人陪我一段，我陪有些人永远。至少我们曾一起同行，就很足够了。"},
	                  {"所谓命运不过是托辞，因为我们无法忍受所有事情发生都是偶然的事实。","Destiny is something we‘ve invented because we can’t stand the fact that everything that happens is accidental. ","Mar 21 Sun","词霸小编：命运让你独自面对一些事情一定有它的道理，只有脆弱的人才会四处游说自己的不幸，坚强的人只会不动声色地愈渐强大 。 "},
	                  {"我们会用一分钟的时间去迷恋一个人却用一辈子的时间去忘记一个人。","We take a minute to have a crush on someone but a lifetime to forget someone. ","Mar 20 Sun","词霸小编：当你试图忘记一个人时，其实你心里已经想他好几百次了，既然忘不掉，不如在心里放好。"},
	                  {"我们曾经仰望星空，思考我们在宇宙中的位置，而现在我们只会低着头，担心如何在这片土地上活下去。","We used to look up at the sky and wonder at our place in the stars, now we just look down and worry about our place in the dirt. ","Mar 19 Sun","词霸小编：每一个人都在各自的城为了生活而拼命努力，美丽的梦想，与现实相遇，也许只能够暂时搁置一旁，毕竟为了生活，你不得不做一些退让。"},
	       //3-12---------3-18
	                  {"如果你真的照顾好自己的心，你会惊讶的发现，不知有多少那样的姑娘在你门前排长队。","If you truly take care of your heart, you will be amazed at how much girls start lining up at your front door.","Mar 18 Sun","词霸小编：这个世界上，最让人畏惧的恰恰是通向自己内心的路，唯有你的专一与坚守，决定了你有多少人会在未来等你，在每个路口拥抱你。 "},
	                  {"不要强迫你的朋友尝试去爱你所爱的，除非他们自已愿意。","Don‘t push your friends into your stuff, let them find it and like it if they want to.","Mar 17 Sun","词霸小编：人和人成长环境天差地别，每个人也各有各的三观和苦衷。人一生做好三件事就够了，知道什么是对的，去做，然而不强迫别人也去做。"},
	                  {"有时候人们需要的不是建议，而是能有一个人来倾听和关心自己。","Sometimes people don’t need advice, they just need someone to listen and care. ","Mar 16 Sun","词霸小编：一定要有几个这样的朋友，当你在他们面前可以很自然的相处，你们可以彼此倾听对方的心声，让你不用处事圆滑的面对每一个人，让你不用觉得每时每刻都那么累。 ​​​​ "},
	                  {"请仰望星空，而不要俯视脚下。","Look up at the stars, not down at your feet. ","Mar 15 Sun","词霸小编：不要让身体的不便限制了你的灵魂，无论生活如何艰难，要保持一颗好奇心。 "},
	                  {"勇敢的人随遇而安, 所到之处都是故乡。","Every soil,where he is well,is to a valiant man his natural country.","Mar 14 Sun","词霸小编：人本过客来无处，休说故里在何方，随遇而安无不可，人间到处有花香。"},
	                  {"我知道这世上有人在等我，尽管我不知道我在等谁。但是因为这样，我每天都非常快乐。","I know someone in the world is waiting for me, although I‘ve no idea of who he is. But I feel happy every day for this.","Mar 13 Sun","词霸小编：世上最幸福的事就是找个喜欢你真是面目的人，不管你是坏脾气还是好脾气，丑的，帅的，哪怕你放个屁都他都觉得香。然而在对的时间遇到对的人，恰恰需要耐心等待，在你的世界中总会有个人比想象中爱你。 "},
	                  {"生活有时不尽如人意。我们挣扎、哭泣，有时甚至放弃。但内心始终充满希望。","Life is not always what we want it to be. We fight. We cry. And sometimes, we give up. But there is always hope in our heart.","Mar 12 Sun","词霸小编：世事不能说死，有些事情总值得尝试。永不轻言放弃，前方总有希望在等待。愿你永远年轻，永远热泪盈眶！然后今天是植树节，要给自己种下一颗新希望哦~"},
	       //3-5-------------3-11        4:55-5:01录完七个      
	                  {"真正的朋友是‘全世界与我为敌，他与全世界为敌’。","A real friend is one who walks in when the rest of the world walks out.","Mar 11 Sun","词霸小编：真正的朋友，不会刻意装给对方看，也不会通过装来收获友谊。能与你沉沉浮浮，共患难，便是真朋友。"},
	                  {"水滴能穿石，即便困于一隅，也能另辟蹊径。","Water can carve its way, even through stone. And when trapped, water makes a new path.","Mar 10 Sat","词霸小编：虽然常说条条大路通罗马，但实在去不了，可以去土耳其啊！"},
	                  {"爱，就是把那个人的需要，看得比自己还重要。","Love is putting someone else’s needs before yours.","Mar 9 Fri","词霸小编：无论是亲情还是爱情，不都是把对方的需要放置于自己之上？不管是何种性质的爱，亘久不变的便是无私，打心底的为对方着想，始终将其摆在第一位。"},
	                  {"先爱自己不会让你变得无用或自私，它会让你无坚不摧。","Falling in love with yourself first doesn’t make you vain or selfish, it makes you indestructible.","Mar 8 Thu","词霸小编：有人曾说：“女人在婚姻中渐渐明白，最重要的或许不是嫁谁，而是无论嫁了谁，都要有让自己幸福的能力。”今天是妇女节，别忘了给自己放个假，也别忘了给妈妈打个电话。"},
	                  {"面对敌人需要勇气，但敢于直面朋友，需要更大的勇气。","It takes a great deal of bravery to stand up to your enemies, but a great deal more to stand up to your friends. ","Mar 7 Wen","词霸小编：我们都知道与敌人对抗需要勇气，但很少有人承认与朋友对抗也需要勇气。特别是在学生时代，如果朋友之间的调侃，玩笑，没有把握好尺度，随时可能变成校园霸凌。但如果你勇敢对抗，就可能变得不受欢迎，甚至失去朋友，所以很多人选择默默承受。 "},
	                  {"真正的爱情不是一时好感，而是我知道遇到你不容易，错过了会很可惜。","True love is not the temporary likeness, and I know it’s the feeling—meeting you is hard and it will be a pity if I miss you. ","Mar 6 Tue","词霸小编：当错过了失去了，忏悔的你，是否还能换回那颗，善良的心？这个拿青春和你赌的人，好好珍惜，有个爱你的人不容易。"},
	                  {"不要害怕黑暗，不要害怕追寻自己的梦想，不要害怕做与众不同的自己，走你自己的路。","Don‘t be afraid of the darkness. Don‘t be afraid to chase your dreams. Don’t be afraid to be yourself. Follow your own path. ","Mar 5 Mon","词霸小编：不要把全世界都放在自己的肩上，去做自己喜欢的事吧！做自己的梦，走自己的路，爱自己所爱的人。"},
	       //2-26-------------3-4      
					  {"能够在最美的时光和你相遇，才算没有辜负自己。","I don’t let myself down, because I have met you in my most gorgeous age.","Mar 4 Sun","词霸小编：因为有了人海，所以我们的相遇才显得那么意外。"},
	                  {"真心朋友总会找机会帮你，假意朋友总会找借口不帮。 ​​​​","True friends will always find a way to help you. Fake friends will always find an excuse. ","Mar 3 Sat","词霸小编：真朋友，淡中如水；假朋友，蜜里调油。因为在困难面前，前者生死相依，后者各奔东西，能跑多远跑多远。"},
	                  {"栖身之所不一定是家，有人懂你之处才是家。","Home is not where you live but where they understand you.","Mar 2 Fri","词霸小编：正月十五，元宵节快乐! 因为元宵灯会，所以这一天也称得上是中国的情人节。当然也别忘了和家人团聚，一起赏花灯，猜灯谜。还有些霸粉明天要开学了，你们快乐寒假写完了吗？"},
	                  {"有些事情不是看到希望才去坚持，而是坚持了才会看到希望。","Some things are not to see to insist, but insisted the will sees hope.","Mar 1 Thu","词霸小编：人都是为希望而活，因为有了希望，人才有生活的勇气。你好，三月！"},
	                  {"凋谢是真实的，盛开只是一种过去。","Fading is true while flowering is past. ","Feb 28 Wen","词霸小编：风月若凋零繁花，华胥梦断，劫灰散尽，唯余暖香依旧。"},
	                  {"承诺常常很像蝴蝶，美丽的盘旋后就不见了。","Promises are often like the butterfly, which disappear after beautiful hover. ","Feb 27 Tue","词霸小编：陪伴是最长情的告白， 相守是最温暖的承诺。"},
	                  {"交许许多多的朋友不是什么奇迹，真正的奇迹是当所有人都弃你而去的时候，还有一个朋友坚定的站在你这一边。","Making a million friends is not a miracle. The miracle is to make a friend who will stand by you when millions are against you. ","Feb 26 Mon","词霸小编：多个朋友多条路，可朋友多了也会迷路。"},
	      //2-19-------------2-25
					  {"不管你赢了一寸还是一里，赢了就是赢了。","It doesn’t matter if you win by an inch or a mile. Winning’s winning.","Feb 25 Sun","词霸小编：这是电影《速度与激情》里的一句经典台词，虽然有些人听了会感到不爽，但说的确实在理。我们毕竟活在一个现实世界，绝大多数人看重的不是过程，而是结果。所以有时候不用事事完美，把所有做到极致，尽力而为就行。"},
	                  {"如果你想要爱情，这就是爱。现实生活就是如此，它不完美，但千真万确。 ​​​​","If you want love, then this is it. This is real life. It’s not perfect but it’s real. ","Feb 24 Sat","词霸小编：电影《爱在三部曲》诠释了我们期待的爱情所有的模样，浪漫不乏真实。真实的爱情，能够让你放下所有的骄傲，并不断在生活中给你惊喜。"},
	                  {"友情是理解，不是妥协；是原谅，不是遗忘。即使不联系，感情依然在。","Friendship means understanding, not agreement. It means forgiveness, not forgetting. It means the memories last, even if contact is lost. ","Feb 23 Fri","词霸小编：真正的友情的确不用每时每刻都腻在一起，但断档的友情，绝对不是一个好的征兆。如果你对过去的朋友一无所知，怎么聊，没得聊。"},
	                  {"实现明天理想的唯一障碍是今天的疑虑。","The only limit to our realization of tomorrow will be our doubts of today.","Feb 22 Thu","词霸小编：梦想，可以天花乱坠，而理想，是我们一步一个脚印踩出来的坎坷道路。我们在不同的城市寻求不同的理想，虽总是谢了又开，给我们惊喜，但你仍需为之奔赴。今天是新年上班第一天，要一起加油哦！"},
	                  {"家，是爱之所在。也许我们脚步渐远，但心从未离开。","Where we love is home, home that our feet may leave, but not our hearts.  ","Feb 21 Wen","词霸小编：在家不知离家人的苦，离家方知想家人的愁。今天是春节后的返程高峰，大家一定要一路平安~"},
	                  {"遇见你是命运的安排，成为了朋友是我的选择，而爱上你是我无法控制的意外。","Meeting you was fate, becoming your friend was a choice, but falling in love with you was beyond my control.  ","Feb 20 Tue","词霸小编：人来人往，我遇见你，我记得你，这座城市天生就适合恋人，你天生就适合我的灵魂。"},
	                  {"人人都想要幸福，却不愿承受痛苦。可不经历风雨，怎能见彩虹？","Everybody wants happiness. No one wants pain. But how can you make a rainbow without a little rain?","Feb 19 Mon","词霸小编：外面不像你想的那么好，风雨都要自己挡。如果没有见识过风雨，就不算经历人生。今日雨水。"},
	      //2-12-------------2-28
	                  {"有很多别的事情会改变我们，然而我们源于家庭，归于家庭。因此，家人的爱是生命中最大的福分。","Other things may change us, but we start and end with family. So, the love of a family is life’s greatest blessing. ","Feb 18 Sun","词霸小编：生活不易，但为了爱的人从未想过要放弃。怕自己能力不足，拖累了全家人的幸福；但对家人来说，有自己为这个家付出的努力，就拥有了世界上最珍贵的东西。"},
	                  {"当找到真正的朋友时，得好好的把握住他们，珍惜他们，和他们共度美好时光，真心对他们，爱他们。","When you find real friends, hold on to them, treasure them, spend time with them, be kind to them, love them. ","Feb 17 Sat","词霸小编：真心的朋友，能有一两个就很好了。朋友之间，贵在那份踏实的信赖 ​​​​。"},
	                  {"新年是给我们一个新的机会去原谅，去进步，去给予更多，去爱得更多。 ​​​​新年快乐！","New Year is about getting another chance. A chance to forgive, to do better, to give more, to love more.Happy New Year！","Feb 16 Fri","词霸小编：Hello，小编祝大家新年快乐！谢谢大家过去一直陪伴词霸，与我们共成长。新的一年，新的希望，词霸还会继续和大家一起携手共进。祝愿霸粉们红包拿到手软哦！Happy New Year！"},
	                  {"享受生活才是最重要的，要快乐，这样就够了。","The most important thing is to enjoy your life—to be happy—it’s all that matters.","Feb 15 Thu","词霸小编：今天除夕了，首先小编祝大家除夕快乐！除夕是除旧迎新，全家团圆的日子。霸粉们，今天一定要和家人一起享受这一年中最快乐的日子哦！"},
	                  {"单身意味着你足够坚强，有足够的耐心去等待值得拥有你的那个人。","Being single means that you are strong enough and patient to wait for the one who deserves you.","Feb 14 Wed","词霸小编：虽然从明天开始，你的七大姑八大姨又会开始“盘问”你的爱情？但不用怕，单身并不意味着你不懂爱，事实上，单身要比陷入一段错误的爱明智得多。情人节快乐！"},
	                  {"获取幸福最重要的是：有所为，有所爱，有所期待。","The grand essentials of happiness are: something to do, something to love, and something to hope for.","Feb 13 Tue","词霸小编：最好的幸福莫如家人环绕，朋友相伴，现世安稳，岁月静好。"},
	                  {"没有人可以回到过去从头再来，但是每个人都可以从今天开始，创造一个全新的结局。","Nobody can go back and start a new beginning, but anyone can start today and make a new ending. ","Feb 12 Mon","词霸小编：每一段开始与结束，都只不过是新旧之间的交替。就好像未来的我们，都是一个全新的自己。今日廿七，除晦迎新。"},
	     //2-05-------------2-11
	                  {"时隔多年，你终于回到故乡，这才发现你想念的不是这个地方，而是你的童年。","When you finally go back to your old home, you find it wasn’t the old home you missed but your childhood.","Feb 11 Sun","词霸小编：我们一直想要留住时间，但最终，时间留给我们的只是记忆。腊月廿六，吃大肉。今天我们要像个孩子一样快乐！"},
	                  {"不要只因一次挫败，就放弃你原来决心想达到的目的。","Do not, for one repulse, forgo the purpose that you resolved to effort. ","Feb 10 Sat","词霸小编：一次挫败又能算什么，浩瀚人生，不问结果，于愧无心便好。但是，别让风雨扰乱了你的节奏。"},
	                  {"有时候，直到一些珍贵的时刻成为了回忆，你才会真正意识到它的价值所在。","Sometimes you will never know the true value of a moment until it becomes a memory. ","Feb 9 Fri","词霸小编：回忆是美的，所以更要努力去创造更美好的回忆。同时也要珍惜现在的每一刻，无论是亲人，爱人还是朋友。"},
	                  {"家是一个你年轻时想离开，年纪大了又想要回归的地方。","Home is a place you grow up wanting to leave, and grow old wanting to get back to.","Feb 8 Thu","词霸小编：小年小年，贴春联。今日已是廿三，离新年也越来越近。新年就是家的团圆。想家，就记得回家看看。小年快乐！"},
	                  {"最痛苦的事情是， 在太爱一个人的过程中丧失了自我，忘记了自己也很特别。","The most painful thing is losing yourself in the process of loving someone too much, and forgetting that you are special too. ","Feb 7 Wed","词霸小编：人生最糟的不是失去爱的人，而是因为太爱一个人，而失去了自己。我们总是在为对方不断地改变自己，忍受对方的过错，以为这就是最爱ta的表现。别忘了，你还是你自己。"},
	                  {"你必须努力去寻找自己的声音，因为你越迟开始寻找，找到的可能性越小。","You must strive to find your own voice. Because the longer you wait to begin, the less likely you are to find it at all. ","Feb 6 Tue","词霸小编：这是电影《死亡诗社》最经典的一句台词，它影响了无数人的价值观。随着时间流逝，成为一种象征。把握当下，坚持自己，这不仅是影片所流露，而是每个人都应铭记于心。"},
	                  {"囫囵吞枣的人生不是成长，成长是对过往经历的的细细品味领会。","Our growth depends not on how many experiences we devour, but on how many we digest. ","Feb 5 Mon","词霸小编：把弯路走直的人，是聪明的，因为找到了捷径；把直路走弯的人，是豁达的，因为可以多看几道风景。"},
	     //1-29-------------2-04
	                  {"人生若只如初见，你衔我一片纯白岁月，我陪你看细水长流。","Only if the first sign of life, you said I was a piece of pure time, I accompany you to see. ","Feb 4 Sun","词霸小编：爱是细水长流的陪伴，陪伴是最长情的告白。今日正立春！从今日起，要学会为爱付出。"},
	                  {"生命太短，没时间留给遗憾。若不是终点，请微笑一直向前。","Life is too short to waste on disappointments. If it is not the end, please smile and go straight ahead.","Feb 3 Sat","词霸小编：遗憾千万种，各人皆不同。漫漫人生路，遗憾不可少之。但微笑，是支撑你继续前行的力量。来，给小编笑一个~"},
	                  {"真正的朋友绝不会阻挡你的道路，除非你在走下坡路。","A true friend never gets in your way unless you happen to be going down.","Feb 2 Fri","词霸小编：时间，带不走真正的朋友；岁月，留不住虚幻的拥有。 走过一段路，总能有一次领悟；经历一些事，才能看清一些人。"},
	                  {"爱情很难，因为是你，所以再难我都坚持到最后。","Love is hard, but the reason I still persist is you. ","Feb 1 Thu","词霸小编：真正的爱情，就是切断自己的所有退路，去固执地坚持一个未知的结果，并且无怨无悔。"},
	                  {"坚持自己的梦想，即使没有翅膀也能飞翔。","Hold fast to your dreams and you can soar even without wings. ","Jan 31 Wed","词霸小编：电影《神秘巨星》中给人印象最深的是尹希娅说的一句：“你让我去睡觉，却不让我做梦。做梦是每个人的权利，而醒来则是为了实现梦想。如果梦都没有，人活着还有什么意义？”每个人都有自己的梦想，追梦路上，少不了冷眼与嘲笑。守住这份初心，迟早会有人为你疯狂打CALL。"},
	                  {"把努力当成你的习惯，而不是一时心血来潮。","Take endeavor as a habit instead of temporary passion.","Jan 30 Tue","词霸小编：就像往年高考期间的标语“只要学不死，就往死里学”一样，虽然不要这么极端，但自己也要尽量努力到无能为力，拼搏到感动自己。"},
	                  {"让生活变得轻松的三样东西：父母的拥抱、恋人的亲吻，以及朋友的支持。","Things that make life easier: a parent’s hug, a lover’s kiss, and a friend’s support.","Jan 29 Mon","词霸小编：我想不管是亲情爱情还是友情，能够与时间对抗的便是真情。"},
	    //1-22-------------2-28
	                  {"真正的朋友会为你考虑，会担心你，也会和你倾诉他们的忧愁。","True friends think about you, worry about you, and tell you when they’re worried. ","Jan 28 Sun","词霸小编：朋友不是先来的人或者认识最久的人，而是那个来了以后再也没有走的人。"},
	                  {"人生处处是风景，也处处是陷阱。努力使自己变得强大起来，当风雨来时才能够抵挡。","There are both views and traps everywhere in life.Make yourself strong enough to endure impending storms. ","Jan 27 Sat","词霸小编：人生就是在不断做选择题，对了就是风景，错了便是陷阱，你的选择就是你的人生，你的人生就是你的选择。"},
	                  {"愿你在迷茫时，坚信你的珍贵，爱你所爱，行你所行，听从你心，无问西东。","I truly beg you that you can believe how special you are and love who you really love, do what you should do, follow your heart without caring about practical gains or loss.","Jan 26 Fri","词霸小编：爱情最美的样子不是朝夕缠绵，甜言蜜语。而是携手相伴，走南闯北，无问西东。"},
	                  {"当你步步高升时，你的朋友知道你是谁；当你每况愈下时，你会知道谁是你的朋友。","When you rise in life, your friend knows who you are. When you fall down, you know who are your friends. ","Jan 25 Thu","小编的话：患难见真情，好朋友不一定是时刻都挂在嘴上，但却时刻珍藏于心。有时候，能够拥有一个知你懂你，关键时刻出现的好友，很是幸福。但我说的不是隔壁老王~"},
	                  {"每个失败者都知道成功的方法，只有成功者是按照方法坚持去做。","Every loser knows the ways to succeed, but only the successful ones really carry them out persistently.  ","Jan 24 Wed","词霸小编：十二月初八，今日腊八(The Laba Festival )。又是一年庆祝丰收的时节，过去一年的小目标你都实现了吗？腊八也是新旧交替的开始，你的过去成功终究是过往，只有不断坚持，实现新的目标，才会有新的成功。加油，霸粉们！"},
	                  {"大概没去过的地方都叫远方，没得到的人都比较难忘。","Maybe the places that have not been to have been called far away, the people who don’t be have are more memorable. ","Jan 23 Tue","词霸小编：常常说诗和远方，就像最近大家玩的《青蛙旅行》，你不知道它什么时候归来，不知道它下一次给你带来的是什么风景。爱的那个人也如此，你不知道或许下一秒你就可能得到。"},
	                  {"我们都有绝望的时候，只有在勇敢面对时，我们才知道我们有多坚强。","We all have moments of desperation. But if we can face them head on, that’s when we find out just how strong we really are. ","Jan 22 Mon","词霸小编：你可能并不是一个坚强的人，但是你必须在该坚强的时候坚强。你该明白，有些时候你别无选择。"},
	    //1-15-------------2-21        
	                  {"接受过去和现在的模样，才会有能量去追寻自己的未来。","Accept what was and what is, and you’ll have more positive energy to pursue what will be. ","Jan 21 Sun","词霸小编：可能现在你追寻的并不是所想要的 ，但它是生活中不可少的 。有时候为了生活得更好，你暂时放弃自己的梦，但未来你仍可追寻。"},
	                  {"穷困潦倒时，要抬头挺胸；春风得意时，要脚踏实地。","When you’re down and out, remember to keep your head up. When you’re up and well, remember to keep your feet down. ","Jan 20 Sat","词霸小编：失意与得意，是人生必不可的章节，唯有做的就是不忘初心，脚踏实地。"},
	                  {"我们一度梦见彼此是陌生人，醒来时发现彼此是相亲相爱的。","Once we dreamt that we were strangers. We wake up to find that we were dear to each other.","Jan 19 Fri","词霸小编：我们相爱，在眼前，在遥远，在时间洪荒的天地之间，在浩渺虚空的命长里面！！！"},
	                  {"要是总为过去的事后悔，那你会很难继续前行。不要沉溺于往事。活在当下，看向未来！","If you constantly regret things you did or didn’t do in the past, then you won’t be able to move forward. Don’t live in the past. Live in the present…and the future! ","Jan 18 Thu","词霸小编：不要拿过去的记忆，来折磨现在的自己。现在的生活也许不是你想要的，但绝对是你自找的。 "},
	                  {"当你害怕失去一样东西时，这意味着，你要么放弃这样东西，要么放弃你自己。","When you are afraid of losing something ,it means that you have no choice but to abandon it or give up yourself.","Jan 17 Wed","词霸小编：一个人如果没办法30秒内扔掉一切可有可无的东西就注定会被生活抛弃。"},
	                  {"懂得换位思考，人生才会更美好。","Life turns out to be better only if you think from others’ perspective. ","Jan 16 Tue","词霸小编：踩影子的人和影子的本体交换了人生:安生成了安稳的七月，七月成了流浪着的安生。"},
	                  {"生命中你会遇到两种人：一种是鼓励你的人，一种是打击你的人，最终，这两者都要感谢。 ​​​​","You will meet two kinds of people in life: ones who build you up and ones who tear you down. But in the end, you’ll thank them both. ","Jan 15 Mon","词霸小编：感谢曾经帮助和支持我的人，谢谢你们的指引和鼓励，照耀了那些黑暗而漫长的路。感谢一直憎恨和讨厌我的人，是你们掘凿出更深的痛苦，让我以后的幸福填充得更为丰盈。"},
	    //1-8-------------1-14           
	                  {"别哭泣，别叹息，别呻吟；悲伤唤不回流逝的时光。","Weep no more, no sigh, nor groan. Sorrow calls no time that’s gone. ","Jan 14 Sun","词霸小编：只要活下去，一定会有快乐的事，会有很多快乐的事。我已经决定不再哭了，我下定决心要看向远方~~"},
	                  {"永远不要把自己的幸福放到别人的手中。","Never put your happiness in someone else’s hands. ","Jan 13 Sat","词霸小编：别人眼里的幸福不会是你自己的幸福，埋在自己心里不愿说出的害羞的满足才是自己真正的幸福。"},
	                  {"我不想要你将就,我也不想成为任何人将就的对象。","I don’t want to be someone that you’re settling for. I don’t want to be someone that anyone settles for.","Jan 12 Fri","词霸小编：你说人山人海边走边爱怕什么孤单，我说人潮汹涌都不是你该怎样将就~~"},
	                  {"请别再欺骗你自己，别让自己活得太累。","Please don’t deceive yourself. Don’t let yourself get too tired. ","Jan 11 Thu","词霸小编：世界上，唯独骗不了的，是自己的心，它总在你最没提防时，暴露你的欢喜忧愁。"},
	                  {"过自己想要的生活不是自私，要求别人按自己的意愿生活才是。","Selfishness is not living as one wishes to live, it is asking others to live as one wishes to live. ","Jan 10 Wed","词霸小编：一遇到“不公平”的事件就强烈反弹的人，要么太自私，要么就是太傻。"},
	                  {"我今天才知道，我之所以漂泊就是在向你靠近。","Seems right now, that all I have done in my life was making my way here to you. ","Jan 9 Tue","词霸小编：漂泊是为了更好的停驻，我想和你在一起，看尽人间风景。"},
	                  {"行为发展成习惯，习惯发展为性格，性格伴随人的一生。","Sow an act, and you reap a habit. Sow a habit, and you reap a character. Sow a character, and you reap a destiny.","Jan 8 Mon","词霸小编：人缠不过自己的性格，常常在万籁俱寂的时刻，以刀铤与自己短兵相接。"},
	    //2018-1-1-------------1-7              
	                  {"问镜子里的自己，接下来的日子我每天想做什么… 然后去做。","Look yourself in the mirror and ask yourself, what do I want to do everyday for the rest of my life … Do that.","Jan 7 Sun","词霸小编：不要轻易说出你的理想，不给别人嘲笑你的机会，多读书多看报少吃零食多睡觉。"},
	                  {"听我说，朋友，希望是件危险的事。希望能叫人发疯。","Let me tell you something my friend: Hope is a dangerous thing. Hope can drive a man insane. ","Jan 6 Sat","词霸小编：希望是附丽于存在的，有存在，便有希望，有希望，便是光明。"},
	                  {"不是每件事都解释得通，也不是每件事都必须解释得通。","Not everything makes sense. Not everything has to.  ","Jan 5 Fri","词霸小编：如果是对的，何必解释，时间会证明一切。如果是错的，无需解释，时间会修正一切。"},
	                  {"真正的朋友会接受你的过去，力挺你的现在，鼓舞你的将来。","A true friend is someone who accepts your past, supports your present and encourages your future.","Jan 4 Thu","词霸小编：越是熟的朋友，对话就越粗鲁；越是熟的朋友，行为就越猥琐；越是熟的朋友，开玩笑就越不计较；越是熟的朋友，见面少了就越思念；越是熟的朋友，不开心时他想起的第一个人就越是你。"},
	                  {"主宰命运的不是机会，而是选择；机会不是等来的，是争取来的。","Destiny is not a matter of chance, it is a matter of choice; it is not a thing to be waited for, it is a thing to be achieved. ","Jan 3 Wed","词霸小编：乐观的人在每个危机里看到机会，悲观的人在每个机会里看见危机。"},
	                  {"我有的不是态度，而是一种你无法驾驭的个性。","I don’t have an attitude, just a personality you can’t handle. ","Jan 2 Tue","词霸小编：别把我的个性和态度混为一谈，我的个性是源于我是谁，而我的态度则取决于你是谁。"},
	                  {"没有人可以回到过去重新开始，但谁都可以从现在开始，书写一个全然不同的结局。","Nobody can go back and start a new beginning, but anyone can start now and make a new ending. ","Jan 1 Mon","词霸小编：有一只牛站在海边，一只螃蟹看到了说：“牛耶！”这时候一个大浪拍在牛身上，螃蟹说：“海披牛耶！”Happy New Year! 新年快乐！"},
	                  
		//2017-12-25-------------12-31             
	                  {"过去是经历，现在是尝试，未来是期待。用经历尝试就会达到你的期待。","Past is experience, present is experiment,and future is expectation. Use your experience for your experiments to achieve your expectation.","Dec 31 Sun","词霸小编：年轻人如果心存对未来的崇敬，对今天的认真踏实，对昨天的感恩，就会有机会。"},
	                  {"施人之恩，不发于言。受人之惠，不忘于心。","If you helped others, never say it. If you were helped, never forget it. ","Dec 30 Sat","词霸小编：滴水之恩涌泉相报，涌泉之恩无以为报，江湖的规矩是，无以为报时我们一般以身相许。我不干，你们干吗？"},
	                  {"不管你在错的路上已走了多远，必须回头。","No matter how far you have gone down the wrong road, turn back.  ","Dec 29 Fri","词霸小编：如果你在错误的路上，奔跑也没有用。还是回头重新开始吧，即使是爬也比无谓的奔跑有意义。"},
	                  {"爱不是彼此凝视，而是一起注视同一个方向。","Love does not consist of gazing at each other, but in looking outward together in the same direction. ","Dec 28 Thu","词霸小编：下大雪的时候，我们不打伞一直走，是不是就可以一路到白头?"},
	                  {"只有足够黑暗的时候，才能看见星星。","When it is dark enough, you can see the stars. ","Dec 27 Wed","词霸小编：生活坏到一定程度就会好起来，挺一挺，就过去了！某一天再回首，你能明白给你痛苦的人和事，却也是你的救赎。"},
	                  {"你没有成功因为你不知道你想要什么或你想要的还不够强烈。","You do not succeed because you do not know what you want or you don’t want it intensely enough. ","Dec 26 Tue","词霸小编：不知道自己想要什么没关系，但一定要牢记自己不想要什么。"},
	                  {"愿你所有的圣诞梦想都成真！","Hope all your Christmas dreams come true! ","Dec 25 Mon","词霸小编：“我是你的什么啊？” “你是我的Christmas 啊” “啊？就只是Christmas 么？” “是啊，这样你就可以marry me啦！”一则小笑话送给你们，Merry Christmas to all of you!"},
	    
		//2017-12-18-------------12-24             
	                  {"你若勤勤恳恳，又善良，那好事总会发生在你身上的。","If you work really hard and you’re kind, amazing things will happen. ","Dec 24 Sun","词霸小编：不管什么时候，都做一个不凑合不打折不便宜不糟糕的好姑娘。"},
	                  {"伟大始于渺小。","From small beginning come great things. ","Dec 23 Sat","词霸小编：千万不要忘记：我们飞翔得越高，我们在那些不能飞翔的人眼中的形象越是渺小。"},
	                  {"冬天来了,春天还会远吗?——雪莱","If winter comes,can spring be far behind? ","Dec 22 Fri","词霸小编：冬至人未至，未至雪先识。 先识飞雪语，雪语桃花时。冬至到了，你那要吃饺子吗？"},
	                  {"有志者事竟成。有时虽然劳其筋骨，但是命运可以彻底改变。","You can change your life if you want to. Sometimes, you have to be hard on yourself, but you can change it completely. ","Dec 21 Thu","词霸小编：放下你的浮躁，放下你的懒惰，放下你的三分钟热度，放开容易被任何事物吸引的眼睛，放淡你啥都想聊两句八卦的嘴巴，静下心来好好做你该做的事，该好好努力了！"},
	                  {"不是每个人都能成为销魂的船长，但都可以让梦想远航。","Not everybody could sail the ocean, but still we could share this dream. ","Dec 20 Wed","词霸小编：梦想，可以天花乱坠，理想，是我们一步一个脚印踩出来的坎坷道路。"},
	                  {"当全世界都要我放弃时，期待有人轻语一声，“再试一次”。","When the world says “Give up”, I hope there are whispers saying ’Try it one more time.’ ","Dec 19 Tue","词霸小编：告诉自己再多撑一天、一个礼拜、一个月，再多撑一年吧，你会发现，拒绝退场的结果令人惊讶。只有拒绝再试一次的人才会被打败。"},
	                  {"别拿自己和别人比较，这样只会破坏你的价值。","Don’t undermine your worth by comparing yourself and others. ","Dec 18 Mon","词霸小编：不要拿自己与别人比较，不值得，没意思。 你就是你，千万中之一，独一无二，就算是过客，也不会再遇见第二个你。"},
	    
		//2017-12-11-------------12-17            
	                  {"但愿你的一切烦恼都是小事。","May all your troubles be litle ones. ","Dec 17 Sun","词霸小编：人的烦恼就12个字：放不下，想不开，看不透，忘不了。简简单单开心就好！"},
	                  {"迄今为止，一切都好，加油干吧!","Up to now, everything is all right. More power to your elbow!","Dec 16 Sat","词霸小编：语文考试时，总觉得自己是英国人，英语考试时，又觉得自己是中国人，数学考试时，发现自己是外星人。今天的考试希望你们发现自己都是英国人，逢考必过！！！"},
	                  {"有多少欲望，就有多少慌张，就有多少焦虑。","​​​​The more desire you have, the more panic and anxious you become. ","Dec 15 Fri","词霸小编：不要期待，不要假想，不要强求，顺从自然，如果注定，便一定会发生。心安，便是活着的最美好状态。"},
	                  {"我好怕我的好朋友有了自己的好朋友。感觉像失恋了一样。","I’m afraid my best friend has a best friend of his own.It feels like being in broken relationship. ","Dec 14 Thu","词霸小编：朋友不是先来的人或者认识最久的人,而是那个来了以后再也没有走的人。"},
	                  {"一个今天胜似两个明天。","One today is worth two tomorrows. ","Dec 13 Wed","词霸小编：关于明天，有两种可能。一种是比今天好，所以今天应该开心！一种是比今天苦，那今天就更值得开心了！你说呢？"},
	                  {"幸福有如蝴蝶，你追逐它时永远捉不到，你静坐下来，它却可能落在你身上。","Happiness is a butterfly, which when pursued, is always just beyond your grasp,but which, if you will sit down quietly, may alight upon you. ","Dec 12 Tue","词霸小编：小时侯，幸福是一件东西，拥有就幸福；长大后，幸福是一个目标，达到就幸福；成熟后，发现幸福原来是一种心态，领悟就幸福。"},
	                  {"世界已经变了，我们必须同时改变。","The world has changed, and we must change with it. ","Dec 11 Mon","词霸小编：如果你改变不了世界，就只有改变自己；如果你不肯改变自己，就必须坦然接受世界对你的遗弃。"},
	    
        //2017-12-04-------------12-10           
	                  {"强大的头脑加上善良的内心永远是令人敬畏的。","A good head and a good heart are always a formidable combination.","Dec 10 Sun","词霸小编：有一天你会明白，善良比聪明更难。聪明是一种天赋，而善良是一种选择。善良拔掉牙齿就是软弱，善良带上武器就是恶意。"},
	                  {"我不懒，我只是喜欢什么也不做而已。","I’m not lazy, I just really enjoy doing nothing. ","Dec 09 Sat","词霸小编：你可以像猪一样懒,但你能像猪那样,懒得心安理得吗？"},
	                  {"你将遇到障碍、遇到怀疑你的人，你也会犯错，但透过努力，你不会有极限。","There will be obstacles.There will be doubters.There will be mistakes.But with hard work,there are no limits. ","Dec 08 Fri","词霸小编：最佳状态大概就是，默默努力，实现每一个自己吹过的牛逼。"},
	                  {"霜辄夜白，日暮尽苍生远。","The snow glows white on the mountain tonight. ","Dec 07 Thu","词霸小编：你在南方的艳阳里大雪纷飞，我在北方的寒夜里四季如春。友情提示：今日大雪，请注意防风保暖，爱你们~~"},
	                  {"我们总是忘记这一点:快乐不是得到我们所没有的，而是认识并感恩我们所拥有的。","We tend to forget that happiness doesn’t come as a result of getting sth. we don’t have,but rather of recognizing and appreciating what we do have. ","Dec 06 Wed","词霸小编：你觉得不快乐，是因为你追求的不是快乐，而是比别人快乐。"},
	                  {"我总在最深的绝望里，遇见最美丽的惊喜。","I always in the deepest despair, meet the most beautiful surprise. ","Dec 05 Tue","词霸小编：所有的故事都会有一个答案,在最终答案到来之前，你是否耐得住性子，守得稳初心，等得到转角的光明。"},
	                  {"同情一个朋友的苦难是任何人都能做到的，但认同一个朋友的成功则需要十分出色的素质。","Anybody can sympathize with the sufferings of a friend, but it requires a very fine nature to sympathize with a friend’s success.","Dec 04 Mon","词霸小编：不要同情自己，同情自己是卑劣懦夫干的勾当。要在自己的鼓励中鼓励自己。加油，少年！！！"},
	    
		//2017-11-27-------------12-03          
	                  {"你并非孤身一人，你还有那些在乎你、想要拯救你的人，那就是你的家人。","You’re not alone. You still have family, people who care for you and want to save you.  ","Dec 03 Sun","词霸小编：如果有个人，喜欢你素颜不化妆，你瘦了他心疼，你胖了他高兴...那他一定是你父母。我妈永远对我说“你不胖”。说说你爸妈常对你说的一句话是神马~~"},
	                  {"我所认为最深沉的爱,莫过于分开以后,我将自己活成了你的样子。","The deepest love I think,later than apart,I will live as you like.  ","Dec 02 Sat","词霸小编：山有木兮木有枝，心悦君兮君不知。相忘谁先忘，倾国是故国。"},
	                  {"拥抱不同！用关爱消除歧视！","To embrace difference,and to end discrimination with love. ","Dec 01 Fri","词霸小编：一次偶然的放纵坠入无底的深渊；一根小小的针管迎来病魔的召唤。朋友，为了自己，为了家人，请洁身自好远离病魔。世界艾滋病日祝您健康快乐。"},
	                  {"人生的伟大目的不在于知而在于行。","The great end of life is not knowledge but action.","NOV 30 Thu","词霸小编：唯有梦想才配让你不安，唯有行动才能解除你的不安。"},
	                  {"这或许是个悲伤的章节，但你不是其忧伤的一笔。","This may be a sad chapter but you are not a sad story. ","NOV 29 Wed","词霸小编：不开心的时候请假装乐观,装着装着可能就像了。"},
	                  {"自视高一点，这世界是以你对自己的评估来判断你。","Think highly of yourself because the world takes you at your own estimate. ","NOV 28 Tue","词霸小编：人贵自重。别人如何轻贱你都不要紧，重要的是你自己别轻贱了自己。来日别人自然不敢轻贱你分毫。"},
	                  {"人的天性是面对挑战… 我们必须做这些事，就像鲑鱼逆流而上。","It‘s in the nature of the human being to face challenges. … We’re required to do these things just as salmon swim upstream. ","NOV 27 Mon","词霸小编：其实，如果你足够强大，敢于挑战一下，你就会发现有些貌似强大的东西其实很脆弱。"},
	    
		//2017-11-20-------------2017-11-26         
	                  {"生命中，再无聊的时光，也都是限量版。","Even the most boring times in life are limited. ","NOV 26 Sun","词霸小编：那些抹不去的笑脸，回不去的日子，叫时光。那些看过的海，落下的夕阳，叫时光。那些牵过的手，留下的泪，也叫时光。再见，时光。再也不见。"},
	                  {"永远都不要说再见，因为说再见意味着离开，离开意味着遗忘。","Never say goodbye because saying goodbye means going away and going away means forgetting. ","NOV 25 Sat","词霸小编：真正的忘记，并非不再想起，而是偶尔想起，心中却不再有波澜。"},
	                  {"舌无骨却能折断骨。","The tongue is boneless but it breaks bones. ","NOV 24 Fri","词霸小编：总以为清者自清，却忘了人言可畏。"},
	                  {"感谢是感恩的开始，感恩是感谢的升华。","Thankfulness is the beginning of gratitude. Gratitude is the completion of thankfulness. ","NOV 23 Thu","词霸小编：没有人天生该对谁好 所以我们要学会感恩。趁着感恩节表达一下你的谢意吧！感恩节快乐！"},
	                  {"我们最值得自豪的不在于从不跌倒，而在于每次跌倒之后都能爬起来。","Our greatest glory is not in never falling , but in rising every time we fall. ","NOV 22 Wed","词霸小编：#奚梦瑶维密摔跤#，站起来的你是最棒的，站起来仍是一枚小仙女，为你打call！华丽的跌倒，胜过无谓的徘徊。"},
	                  {"与亚里士多德为友,与柏拉图为友,更与真理为友。——《哈佛大学校训》","Let Plato be your friend, and Aristotle, but more let your friend be truth. ","NOV 21 Tue","词霸小编：终于知道为什么一读书就困了，因为读书，是梦开始的地方..."},
	                  {"每一种美，都会有一双眼睛能看到；每一个真相，都有一双耳朵会听到；每一份爱，总会有一颗心会感受到。","For every beauty there is an eye to see it. For every truth there is an ear to hear it. For every love there is a heart to receive it. ","NOV 20 Mon","词霸小编：学问之美，在于使人一头雾水； 诗歌之美，在于煽动男女出轨； 女人之美，在于蠢得无怨无悔； 男人之美，在于说谎说得白日见鬼。——周立波"},
	    
		//2017-11-13-------------2017-11-19         
	                  {"这辈子我等你，没有因为，没有所以。《三行情书》","In this life I wait for you, not because of, not so.","NOV 19 Sun","词霸小编：如果人类有尾巴的话，说起来不好意思，只要和你在一起，一定会止不住摇起来！！"},
	                  {"最重要的是享受生活 - 要快乐。——赫本","The most important thing is to enjoy life - to be happy. ","NOV 18 Sat","词霸小编：我爱哭的时候便哭，想笑的时候便笑，只要这一切出于自然。我不求深刻，只求简单。"},
	                  {"我最后的敬礼献给那些知道我不完美却依然爱我的人。——泰戈尔","My last salutation are to them who knew me imperfect and loved me. ","NOV 17 Fri","词霸小编：感恩别人的每一份给予，因为他完全可以不这么做。"},
	                  {"正义不仅应得到实现，而且还应以人们看得见的方式实现。","Justice must not only be done, but must be seen to be done. ","NOV 16 Thu","词霸小编：#江歌被害案#一场夹杂着人性善恶的悲剧案件近期刷爆了朋友圈，对此小编认为我们要做的是相信法律，声援正义，不做无谓的口舌之争，只愿“逝者已矣，生者如斯”~~"},
	                  {"每个人的心都是一片海，闪耀着深不见底的幽蓝。","Everyone’s heart is a piece of sea, shining bottomless blue.","NOV 15 Wed","词霸小编：心小了，所有的小事就大了；心大了，所有的大事都小了；看淡世事沧桑，内心安然无恙。"},
					  {"知足使贫穷的人富有；而贪婪使富足的人贫穷。","Content makes poor men rich;discontent makes rich men poor. ","NOV 14 Tue","词霸小编：平安是幸，知足是福，清心是禄，寡欲是寿。只愿福禄寿常伴左右！！！"},
	                  {"所有的光鲜靓丽都敌不过时间，并且一去不复返。——《了不起的盖茨比》","All the bright,precious things fade so fast. And they don’t come back.  ","NOV 13 Mon","词霸小编：生命是以时间为单位的，浪费别人的时间等于谋财害命；浪费自己的时间，等于慢性自杀。"},
	    
		//2017-11-06-------------2017-11-12         
	                  {"如果是我，要是我有五十三分钟可以自由运用，那我会悠哉游哉向一道清泉走去。——《小王子》","As for me, if I had fifty-three minutes to spend as I liked, I should walk at my leisure toward a spring of fresh water. ","NOV 12 Sun","词霸小编：要是我有53分钟可以自由运用，我想找个林荫小道，就这么慢慢的走着，看着...你们会怎么做呢？"},
	                  {"我们的相遇，是上帝的指引，月老的安排。——《天生不对》","Thanks to Cupid‘s arrow, we meet each other. ","NOV 11 Sat","词霸小编：相遇是春风十里， 原来是你； 相爱是山长水阔， 最后是你。"},
	                  {"悔恨自己的错误，而且力求不再重蹈覆辙，这才是真正的悔悟。——海明威","Be regretting your own mistakes, and strive not to repeat, which is true repentance. ","NOV 10 Fri","词霸小编：春风得意的浪子回头最珍贵，穷途末路的认错悔改最虚伪。"},
	                  {"如果我们做该做的事，所有的胜算都会高一点。","If we do what is necessary, all the odds are in our favor. ","NOV 9 Thu","词霸小编：一句话，先是太胆小，明知不该做的事却不敢不做；后来也还是太胆小，明知该做的事却不敢去做。什么时候你才能胆大？"},
	                  {"只要你记得我，我不介意整个世界都把我遗忘了。","If you remember me, then I don’t care if everyone else forgets. ","NOV 8 Wed","词霸小编：记得我的好，或者，记得我就好。"},
					  {"通百艺而专一长。","Knowing something of everything and everything of something. ","NOV 7 Tue","词霸小编：身兼百计，不如专攻一术；鲜花烹油，不如细水长流。"},
	                  {"得之，我幸。不得，我命。","It‘s lucky to gain and fated to miss. ","NOV 6 Mon","词霸小编：人生不如意事常八九，岂能尽如人意，但求无愧我心。 "},
	    
        //2017-10-30-------------2017-11-05         
	                  {"读书而不思考,等于吃饭而不消化。","To read without reflecting is like eating without digesting. ","NOV 5 Sun","词霸小编：读书多了，容颜自然改变，许多时候，自己可能以为许多看过的书籍都成了过眼云烟，不复记忆，其实他们仍是潜在的。在气质里，在谈吐上，在胸襟的无涯，当然也可能显露在生活和文字里。"},
	                  {"如果你想要做出改变，就从第一步开始。做出一点小的行动，任何一种行动都行，然后就从这里开始。","If you want to change something, start with baby steps. Take a small action - any action - and grow from there. ","NOV 4 Sat","如果你想要做出改变，就从第一步开始。做出一点小的行动，任何一种行动都行，然后就从这里开始。"},
	                  {"只有在你的微笑里，我才有呼吸。——狄更斯 ","Only in your smile, I just have to breathe. -- Dickens ","NOV 3 Fri","词霸小编：我笑，便面如春花，定是能感动人的，任他是谁。"},
	                  {"天赋是上帝给予的，要谦虚。名声是别人给予的，要感激。自负是自己给的，要小心。","Talent is God given. Be humble. Fame is man-given. Be grateful. Conceit is self-given. Be careful. ","NOV 2 Thu","词霸小编：自负是一座深不见底的坟墓,足以埋葬任何一位伟人和智者。请你远离他！"},
	                  {"历史巨轮飞转，分分秒秒的时间都十分宝贵，也独具意义。","Each moment in history is a fleeting time, precious and unique. ","NOV 1 Wed","词霸小编：少年的时间就是晃。用大把的时间彷徨，只用几个瞬间来成长。不知你现在在彷徨还是成长？"},
					  {"天是蓝色，地是绿色，齐来庆祝万圣节前夜！","The sky is blue, the grass is green, may we have our Halloween. ","OCT 31 Tue","词霸小编：【节日科普】万圣节的晚上，小朋友们会穿著女巫、精灵等特异的装扮，手上提着南瓜灯笼，挨家挨户地模仿妖怪来索取糖果、糕饼，嘴里还会大喊着：不给糖就捣蛋(trick or treat)！而在苏格兰，小孩要糖果时会说：The sky is blue, the grass is green, may we have our Halloween.小编在此也祝大家今晚玩的愉快！"},
	                  {"如果你继续去寻找幸福是由什么组成的，那你永远不会找到幸福。如果你一直在找人生的意义，你永远不会生活。","You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life. ","OCT 30 Mon","词霸小编：一个人活得幸福不幸福，一要看是不是能睡着，二要看是不是想醒来。能睡着，说明心安，此前问心无愧；想醒来，说明心美，当下正是所要。人生，也不过是这六字的快活。 "},
	    //2017-10-23-------------2017-10-29 
		              {"你是某个人生命拼图中的一小块，也许你永远都不知道自己的位置在哪儿，但请记住: 没有你，他的生命是不完整的。","You are a part and puzzle of someone’s life. You may never know where you fit, but always remember that someone‘s life may not be complete without you! ","OCT 29 Sun","词霸小编：我要你知道,在这个世界上总有一个人是等着你的,不管在什么时候,不管在什么地方,反正你知道,总有这么个人。"},
	                  {"你的生命有限，所以别浪费时间去过别人的生活。——史蒂夫•乔布斯","Your time is limited, so don‘t waste it living someone else’s life. – Steve Jobs ","OCT 28 Sat","词霸小编：不怕在自己的梦想里跌倒，只怕在别人的奇迹中迷路。只做自己梦想的主人，不做别人奇迹的听众。为自己打call."},
	                  {"在你说话之前，先听；在你回应之前，先想；在你消费之前，先挣；在你退出之前，先试。","Before you talk, listen; before you react, think; before you spend, earn; before you quit, try. ","OCT 27 Fri","词霸小编：【小编力荐】词霸官方《晨夕阅读训练营·第Ⅱ期》开课啦！30天轻松读完1本英文名著，现正在火速报名中：微信中搜索并关注“晨夕阅读”公众号——点击“立即报名”即可"},
	                  {"遇到对方的时候都微笑一下吧，因为微笑是爱的开始。","Let us always meet each other with a smile,for the smile is the beginning of love. ","OCT 26 Thu","词霸小编：我笑起来会露出七颗牙齿，一颗代表真诚，一颗代表快乐，一颗代表热情，一颗代表善良，一颗代表自信，一颗代表纯真，还有一颗代表悲伤，它只是偶尔出来透透气而已。"},
	                  {"我们不能帮助每一个人，但每个人都可以帮助别人。","We can‘t help everyone,but everyone can help someone. ","OCT 25 Wed","词霸小编：赠人玫瑰手有余香，人家帮我，永志不忘；我帮人家，莫记心上。"},
					  {"虽然我们互相笑着说“回头见”，但是我们都心知肚明，分离即永别。——《海上钢琴师》","We laughed and kept saying “See you soon”. But inside we both knew we’d never see each other again. ","OCT 24 Tue","词霸小编：人生如梦，聚散分离，朝如春花幕凋零，几许相聚，几许分离，缘来缘去岂随心。青丝白发转眼间，漠然回首，几许沧桑在心头。"},
	                  {"只要我们能善用时间,就永远不愁时间不够用。——歌德","We always have time enough, if we will but use it aright.-- Goethe ","OCT 23 Mon","词霸小编：我们像是表面上的针，不停的转动，一面转，一面看着时间匆匆离去，却无能为力。 "},
	    //2017-10-16-------------2017-10-22 
		              {"有一天那个人走进了你的生命，你就会明白，真爱总是值得等待的。","One day someone will walk into your life, then you realize love is always worth waiting for.  ","OCT 22 Sun","词霸小编：当备胎成真爱，世界就承认了等待。"},
	                  {"不管阴雨如何连绵，太阳总会出现。","No matter how bad it gets,the sun will always come out. ","OCT 21 Sat","词霸小编：风雨过后未必有彩虹，但肯定会有希望，会有光芒。加油，小编相信你能行！！！"},
	                  {"如果自己不找准定位，别人就会按他们的想法给我定位。","If I didn‘t define who I was and what I wanted, then someone else would. ","OCT 20 Fri","词霸小编：我们最可怕的敌人不是怀才不遇，而是我们的踌躇，犹豫。将自己定位为某一种人，于是，自己便成了那种人。"},
	                  {"只有一种成功，那就是能够用自己的方式度过自己的一生。","There is only one success --- to be able to spend your life in your own way. ","OCT 19 Thu","词霸小编：你可以不成功，但你不能不成长。也许有人会阻碍你成功，但没人会阻挡你成长。"},
	                  {"做你自己，这世界崇拜原创性。","Be yourself. The world worships the original. ","OCT 18 Wed","词霸小编：最好的我们，最美的时光，做自己最喜欢的事。#小编现在喜欢研究“新引力波”，你们呢？#"},
					  {"成熟的人不问过去，聪明的人不问现在，豁达的人不问未来。","The mature never ask the past, the wise never ask the present and the open-minded never ask the future. ","OCT 17 Tue","词霸小编：别和往事过不去，因为它已经过去；别和现实过不去，因为你还要过下去！不知道你现在跟什么过不去？"},
	                  {"生活是世上最罕见的事情，大多数人只是存在，仅此而已。","To live is the rarest thing in the world. Most people exist, that is all. ","OCT 16 Mon","词霸小编：我以为我一眼就能看穿生活，结果生活给了我响亮的一耳光！我只想说，疼！！！"},
	    //2017-10-9-------------2017-10-15 
		              {"香烟爱上火柴，注定会受到伤害。","When a cigarette falls in love with a match,it is destined to be hurt. ","OCT 15 Sun","词霸小编：一个有爱，一个有情。 你给了爱，他不领情。 如何，爱情？"},
	                  {"与众不同的你是幸运的，何必让自己变得与别人一样。","If you’re lucky enough to be different, don‘t ever change. ","OCT 14 Sat","词霸小编：当你自己选择了与众不同的生活方式之后，又何必去在乎别人以与众不同的眼光来看你。"},
	                  {"鱼放三天发臭，客住三天讨嫌。——富兰克林","Fish and visitors smell three days. - Franklin ","OCT 13 Fri","词霸小编：凡事有尺度，切莫过了头。勇敢过头是鲁莽，自信过头是狂妄。坚持过头是顽固，老实过头是迂腐。执着过头是偏执，善良过头是糊涂。小编就是太善良了，有点糊涂是不是？"},
	                  {"我们天生就是输家，所以必须竭尽全力才能得到我们想要的东西。——《天才枪手》","We are born losers, so we must do our best to get what we want. ","OCT 12 Thu","词霸小编：我要努力努力再努力，直到有一天骄傲的站在你们的面前！！！#小编换头像了，惊不惊喜，意不意外~~#"},
	                  {"心态积极的人能够看到别人无法看到的，感悟别人无法感悟的，完成他人无法完成的。","The positive thinker sees the invisible, feels the intangible, and achieves the impossible. ","OCT 11 Wed","词霸小编：没有公主命，就必须有一颗女皇的心，用积极的态度去过不完美的人生。"},
					  {"不论你什么时候开始，重要的是开始之后就不要轻言放弃;不论你什么时候结束，重要的是结束之后就不要后悔。","No matter when you start, it is important that you do not stop after starting. No matter when you end, it is more important that you do not regret after ending. ","OCT 10 Tue","词霸小编：放弃该放弃的是无奈；放弃不该放弃的是无能；不放弃该放弃的是无知；不放弃不该放弃的是执着。"},
	                  {"郁闷的时候蹲下来抱抱自己，原谅别人也原谅自己。","Crouch down and hold your knees when you are depressed. Forgive others and yourself. ","OCT 9 Mon","词霸小编：习惯郁闷，便一直郁闷！习惯阳光，便会阳光！"},
	    //2017-10-2-------------2017-10-8 
		              {"不要祈求安逸的人生，祈求拥有撑过艰难的力量。– 李小龙","Do not pray for an easy life, pray for the strength to endure a difficult one.– Bruce Lee ","OCT 8 Sun","词霸小编：你不能要求拥有一个没有风暴的人生海洋，因为痛苦和磨难是人生的一部分。一个没有风暴的海洋，那不是海，是泥塘。"},
	                  {"如果我没有刀，我就不能保护你。如果我有刀，我就不能拥抱你。","If I have no knife,I can’t protect you.If I had a sword,I can’t hold you.  ","OCT 7 Sat","词霸小编：奋斗的男人就像搬砖工人，他搬起砖就无法抱你，他放下砖就无法养活你。 "},
	                  {"生活如一个剧本：重要的不是长度而是演出精彩与否。","Life‘s like a play: It’s not the length, but the excellence of the acting that matters. ","OCT 6 Fri","词霸小编：别在生活里找你想要的，要去感受生活里发生的东西。"},
	                  {"世上最简单的事就是做自己，最困难的事是成为别人眼中的自己。别让他人使自己陷入困难的境地。","The easiest thing to be in the world is you. The most difficult thing to be is what other people want you to be. Don’t let them put you in that position. ","OCT 5 Thu","词霸小编：【心情不好时对自己说】1、用心做自己该做的事；2、每个人都有自己的活法；3、别总是自己跟自己过不去；4、不要过于计较别人的评价；5、不妨暂时丢开烦心事；6、自己感觉幸福就是幸福。7、最重要的是今天开心；8、木已成舟便要顺其自然；9、不必一味讨好别人；10、喜欢自己才会拥抱生活。"},
	                  {"海上生明月，天涯共此时。","As the bright moon shines over the sea,From far away you share this moment with me. ","OCT 4 Wed","词霸小编：一斤花生二斤枣，好运经常跟你跑；三斤苹果四斤梨，吉祥和你不分离；五斤橘子六斤蕉，大捆钞票进腰包；祝中秋快乐！"},
					  {"我大好的一个人，凭什么跑到别人的生命里去当插曲。","Why must I be a tiny part of others‘ life since I am such a nice person? ","OCT 3 Tue","词霸小编：学着主宰自己的生活；即使孑然一身，也不算一个太坏的局面。不自怜、不自卑、不怨叹，一日一日来，一步一步走，那份柳暗花明的喜乐必然抵达。"},
	                  {"好，更好，最好，别让它停止。直到你的好成为更好，你的更好成为最好。","Good, better, best. Never let it rest. Till your good is better and your better is best. ","OCT 2 Mon","词霸小编：想要的总以为是最好的，得到后才明白世上没有最好的东西，只有比较好的东西！"},
	    //2017-9-25-------------2017-10-1 
		              {"每天多开心一些就是对自己最好的回报。","A little bit more happy every day is the best return. ","OCT 1 Sun","词霸小编：心情，在快乐中徜徉；日子，在美好中飞度；灯笼，在欢喜中摇曳；红旗，在喜庆中飘扬；国庆，在盼望中来临。国庆了，愿你开心，逍遥无限！"},
	                  {"告诉敌人，他们也许能夺走我们的生命，但是，他们永远夺不走我们的自由！","Tell our enemies that they may take our lives, but they’ll never take our Freedom! ","SEP 30 Sat","词霸小编：贪安稳就没有自由，要自由就要历些危险。只有这两条路。 "},
	                  {"确认某些人是否可以信任的最好的办法，就是信任他们。——海明威","The best way to find out if you can trust somebody is to trust them. - Hemingway ","SEP 29 Fri","词霸小编：如果你信任我，就不需要我说什么，也不需要费心求证。因为你的心会告诉你，这种事我不屑于做。"},
	                  {"做一个幸福的人，读书，旅行，努力工作，关心身体和心情。","To be a happy man: read, travel, work hard and care for the body and mind. ","SEP 28 Thu","词霸小编：待到老了，坐在摇椅上，戴上花镜读书看报，品茗喝茶，看夕阳西下，幸福足矣~~~"},
	                  {"虽然我们互相笑着说“回头见”，但是我们都心知肚明，分离即永别。","We laughed and kept saying “See you soon”. But inside we both knew we’d never see each other again. ","SEP 27 Wed","词霸小编：当你真的学会了放手，那纵算是一生云水漂泊，亦是可淡若清风，自在安宁。"},
					  {"我们不是进步就是在退步，人生沒有所谓的停滞不前。","We are either progressing or retrograding all the while. There is no such thing as remaining stationary in this life. ","SEP 26 Tue","词霸小编：或许前路永夜，即便如此我也要前进，因为星光即使微弱也会为我照亮前路。"},
	                  {"但凡不能杀死你的，最终都会使你更强大。——尼采","That which does not kill us makes us stronger. ","SEP 25 Mon","词霸小编：生活将我们磨圆，不是为了别的，只是为了能让我们滚的更远！"},
	    //2017-9-18-------------2017-9-24   2:22-27
		              {"我的生活不曾取悦于我，所以我创造了自己的生活。——加布里埃·香奈儿","My life didn’t please me, so I created my life. - Coco Chanel ","SEP 24 Sun","词霸小编：生活总是这样，不能叫人处处都满意。但我们还要热情地活下去。人活一生，值得爱的东西很多，不要因为一个不满意，就灰心。"},
	                  {"痛苦的秘密在于有闲功夫担心自己是否幸福。","The secret of being miserable is to have leisure to bother about whether you are happy or not.  ","SEP 23 Sat","词霸小编：快乐的人不是没有痛苦，而是不会被痛苦所左右。"},
	                  {"人必须相信自己，这是成功的秘诀。——卓别林","You have to believe in yourself. That’s the secret of success.--Chaplin ","SEP 22 Fri","词霸小编：【词汇辨析】1. believe sb与believe in sb区别：believe sb：可译为”相信……说的是真的“；believe in sb：可译为“信任，信赖，对……有信心”。如：I believe him, though his story sounds unlikely. 我相信他说的话，尽管听起来不大可能。 ；Some of his supporters have stopped believing in him. 他的一些支持者已经对他失去信心。2. believe sth与"},
	                  {"只有去尝试做一些你不熟悉的事情你才能有所成长。","Unless you try to do something beyond what you have already mastered, you will never grow. ","SEP 21 Thu","词霸小编：一个从未犯错的人是因为他不曾尝试新鲜事物。"},
	                  {"知足常乐者最富有，因为知足本身就是天然的财富。","He, who is content with the least, is the richest, for content is the wealth of nature. ","SEP 20 Wed","词霸小编：生活上要知足，学习上要不知足，工作上要知不足。"},
					  {"有些风景，一旦入眼入心，即便刹那，也是永恒。","Some scenery, once to see into the heart, even in the moment, is eternal. ","SEP 19 Tue","词霸小编：有些人的生命没有风景，是因为他只在别人造好的、最方便的水管里流过来流过去。你不要理那些水管，你要真的流经一个又一个风景，你才会是一条河。——蔡康永"},
	                  {"别让你的骄傲使你孤独一人。——《吸血鬼日记》","Don‘t let your pride leave you all alone. ","SEP 18 Mon","词霸小编：一个骄傲的人，结果总是在骄傲里毁灭了自己。——莎士比亚"},
	    //2017-9-11-------------2017-9-17   2:28-33
		              {"每天早上都觉得自己晚上真不该熬夜！","I stay up late every night and realize it‘s a bad idea every morning! ","SEP 17 Sun","词霸小编：有一种妥协叫早睡，有一种委屈叫早起。"},
	                  {"有时候，你要做的就是，昂起自己的头，不让眼泪掉下来，然后潇洒的说一声：再见……","Sometimes you just have to hold your head up high, try not to cry, and say goodbye！ ","SEP 16 Sat","词霸小编：红尘潇洒，独自前行，但尽人事，莫问前程。"},
	                  {"我不能给你全世界，但是我的世界可以全部给你。","I can’t give you the world,but I can give you my world.  ","SEP 15 Fri","词霸小编：人的生存只有两种：“美丽的活着”，亦或“为美丽的人活着”。"},
	                  {"强大一些，要相信你自己。坚定一些，要相信自己的感觉。","Be strong, believe in who you are. Be strong, believe in what you feel. ","SEP 14 Thu","词霸小编：张牙舞爪的人，往往是脆弱的。因为真正强大的人，是自信的，自信就会温和，温和就会坚定。"},
	                  {"我不太肯定我的方向，但是我希望自己能走的远一点。","I don’t really know where I‘m going, but I hope I can go far. ","SEP 13 Wed","词霸小编：你一定得认识到自己想往哪个方向发展，然后一定要对准那个方向出发，要马上。你再也浪费不起多一秒的时间了，你浪费不起。"},
					  {"试着用左手握住右手，给自己最简单的温暖，不再奢求别人的给予，开始学着自己给自己。","Try to hold the right hand with your left hand, and give yourself the most simple warmth. We should learn to get it by ourselves instead of craving for warmth from others. ","SEP 12 Tue","词霸小编：当你长大时，你会发现你有两只手，一只用来帮助自己，一只用来帮助别人。我不要奢求别人的帮助，而要慷慨地给予别人帮助。"},
	                  {"如果你有足够的勇气，那么一切皆有可能。","Anything is possible if you have got enough nerve. ","SEP 11 Mon","词霸小编：你以为挑起生活的担子是勇气，其实去过自己真正想要的生活才更需要勇气。"},
	    //2017-9-4-------------2017-9-10   2:33-38
		              {"你有你的路。我有我的路。至于适当的路，正确的路和唯一的路，这样的路并不存在。——尼采","You have your way. I have my way. As for the right way, the correct way, and the only way, it does not exist.  ","SEP 10 Sun","词霸小编：我没有路，但我知道前进的方向。"},
	                  {"花总是表里不一，而我太年轻了，不知道该怎样爱护她……","Flowers are so inconsistent! But I was too young to know how to love her… ","SEP 9 Sat","词霸小编：如果不能成为别人生命中的礼物，就不要走进别人的生活。"},
	                  {"聆听他人之意见，但保留自己之判断。","Take each man’s censure, but reserve your judgment. ","SEP 8 Fri","词霸小编：生命短促，没有时间可以再浪费，一切随心自由才是应该努力去追求的，别人如何想我便是那么的无足轻重了。"},
	                  {"有时候世界虽然是假的，但并不缺少真心对待我们的人。——《楚门的世界》","The world may be full of cheating, however we never lack friends with a warm heart.  ","SEP 7 Thu","词霸小编：即使整个世界恨你，并且相信你很坏，只要你自己问心无愧，知道你是清白的，你就不会没有朋友。——《简爱》"},
	                  {"我们把世界看错了，反说它欺骗我们。——泰戈尔《飞鸟集》","We read the world wrong and say that it deceives us. ","SEP 6 Wed","词霸小编：心态不好，说穿了，就是心太小了。 心态的“态”字，拆解开来，就是心大一点。"},
					  {"小时候，乡愁是一枚小小的邮票，我在这头，母亲在那头。——《乡愁》","When I was a child,Nostalgia seemed a small stamp:“Here am I,And there ……my mother.” ","SEP 5 Tue","词霸小编：【诗歌鉴赏】《乡愁》作者余光中。此诗的思乡之愁不是直白地说出来的，而是通过联想、想象，塑造了四幅生活艺术形象（邮票、船票、坟墓、海峡），使之呈现在读者眼前。作者把对母亲、妻子、祖国的思念、眷念之情熔于一炉，表达出渴望亲人团聚、国家统一的强烈愿望。"},
	                  {"当你可以永生、你将为何而活？——《暮光之城》","When you can live forever, what do you live for? ","SEP 4 Mon","词霸小编：活着不是靠泪水博得同情，而是靠汗水赢得掌声。"},
	    //2017-8-28-------------2017-9-3   2:39-44
		              {"爱自己，保持乐观很重要，因为美丽是由内而外散发出来的。","Love yourself. It is important to stay positive because beauty comes from the inside. ","SEP 3 Sun","词霸小编：美丽的女人经得起时间的推敲。时间不是美丽的敌人，而只是美丽的代理人。它让美丽在不同的时刻呈现出不同的状态，从单纯走向深邃。"},
	                  {"应该有更好的方式开始新一天, 而不是千篇一律的在每个上午醒来。——《加菲猫》","There should be a better way to start a day than waking up every morning. ","SEP 2 Sat","词霸小编：每天早上起床的时候，就想想怎么填完整下面这句话：我的目标是____。"},
	                  {"人生并非都是选择题，而是应用题，要我们一点一滴的论证。","The life is not always full of multiple choices, but application questions which need us to prove little by little. ","SEP 1 Fri","词霸小编：人生在世，不管做多做少，乐在其中就可以，当你快乐，你的世界也会快乐，在你世界里的人也会快乐。"},
	                  {"世界如一面镜子：皱眉视之，它也皱眉看你；笑着对它，它也笑着看你。","The world is like a mirror; frown at it and it frowns at you; smile and it smiles too. ","AUG 31 Thu","词霸小编：在我有生之年，做一个真诚的人，不放弃对生活的热爱和执着，在有限的空间里过无限广大的日子。"},
	                  {"当我说认识你的时，我是说我认识昨天的你。我不认识现在的你。","When I say I know you,I mean I knew you yesterday. I do not know you actually now. ","AUG 30 Wed","词霸小编：当你知道你是你的时候，你不是你！当你知道你不是你的时候，你就是你！"},
					  {"不要讨厌那些嫉妒你的人，相反你应该尊重他们的妒忌心，因为在他们心里，你要比他们优秀。","Never hate those people who are jealous of you but respect their jealousy because they are the ones who think that you are better than them.  ","AUG 29 Tue","词霸小编：在嫉妒的人眼中，幸福不在于得到，而在于别人得不到。——八月长安"},
	                  {"你微微地笑着，不同我说什么话。而我觉得，为了这个，我已等待很久了。——泰戈尔","You smiled and talked to me of nothing and I felt that for this I had been waiting long. ","AUG 28 Mon","词霸小编：初遇倾心，再见痴心，用尽苦心，终生费心，欲得芳心，难道你心，不懂孤心？七夕快乐！！！"},
	    //2017-8-21-------------2017-8-27   2:45-51
		              {"永远宽恕你的敌人，没有什么能比这个更让他们恼怒的了。——王尔德","Always forgive your enemies - nothing annoys them so much.  ","AUG 27 Sun","词霸小编：原谅他人的错误，不一定全是美德。漠视自己的错误，倒是一种最不负责的释放。"},
	                  {"不要走在我后面，因为我可能不会引路；不要走在我前面，因为我可能不会跟随；请走在我的身边，做我的朋友。","Don‘t walk behind me, I may not lead. Don’t walk in front of me, I may not follow. Just walk beside me and be my friend. ","AUG 26 Sat","词霸小编：朋友不是先来的人或者认识最久的人, 而是那个来了以后再也没有走的人。"},
	                  {"不要去等待完美的时刻，把握好现在，让此刻变完美。","Don‘t wait for the perfect moment. Take the moment and make it perfect. ","AUG 25 Fri","词霸小编：世界上的事情，最忌讳的就是十全十美，你看那天上的月亮，一旦圆满了，马上就要亏厌；树上的果子，一旦熟透了，马上就要坠落。凡事总要稍留欠缺，才能持恒。"},
	                  {"生活中大多数的阴霾归咎于我们挡住了自己的阳光。——爱默生","Most of the shadows of life are caused by standing in our own sunshine.  ","AUG 24 Thu","词霸小编：大部份的痛苦，都是不肯离场的结果，没有命定的不幸，只有死不放手的执着。优雅的放手，一切将云开雾散！！！"},
	                  {"抱怨就跟呕吐一样。你自己是爽了，可你周围的人都得跟着遭殃。","Complaining is like vomiting. Afterwards you feel better but everyone around you feels sick. ","AUG 23 Wed","词霸小编：不要再抱怨这个社会有多么虚伪和不公，因为我们都是帮凶。"},
					  {"对于很多事情，你只需紧闭双眼，用心去感受。","For many things, you only need to close your eyes and feel them with your heart.","AUG 22 Tue","词霸小编：用心倾听，生活中的点点快乐，用心感受，生活中的滴滴浓情，留住每一个精彩瞬间！"},
	                  {"不要着急，最好的总会在最不经意的时候出现。","Don’t try so hard, the best things come when you least expect them to. ","AUG 21 Mon","词霸小编：所有故事都会有一个好的结局，如果没有，别急，说明故事还没到结局。"},
	    //2017-8-14-------------2017-8-20   2:52-57
		              {"不曾傻过，蠢过，不曾疯狂过，不曾困窘过，人生便毫无乐趣。","If you’re not doing anything silly, stupid, crazy, or embarrassing...you‘re not having any fun! ","AUG 20 Sun","词霸小编：你住几层楼？——“人生有三层楼：第一层是物质生活，第二层是精神生活，第三层是灵魂生活。“"},
	                  {"长大了之后，我们发现，拥有几个真心朋友比找一大堆朋友要更重要。","As we grow up, we realize it becomes less important to have a tons of friends, and more important to have real ones. ","AUG 19 Sat","词霸小编：朋友这种关系，最美在于锦上添花；最可贵，贵在雪中送炭；朋友中的极品，便如好茶，淡而不涩，清香但不扑鼻，缓缓飘来，似水长流。"},
	                  {"如果你愿意改变和学习，你就能脱胎换骨。——《王牌特工》","If you’re prepared to adapt and learn, you can transform. ","AUG 18 Fri","词霸小编：此刻打盹，你将做梦；而此刻学习，你将圆梦。"},
	                  {"挑战愈艰钜，胜利愈辉煌。","The greater the struggle, the more glorious the triumph. ","AUG 17 Thu","词霸小编：有时候，我们做出的最艰难的决定，最终成为我们做过的最漂亮的事。"},
	                  {"女人如果不性感，就要感性；如果没有感性，就要理性；如果没有理性，就要有自知之明；如果连这个都没有了，她只有不幸。","If a woman is not sexy, she needs emotion; if she is not emotional, she needs reason; if she is not reasonable, she has to know herself clearly. If not, she has only misfortune.","AUG 16 Wed","词霸小编：我要做个思想上的女流氓，生活上的好菇凉、外形上的柔情少女、心理上的变形金刚 。"},
					  {"生活是条沉船,但我们不要忘了在救生艇上高歌。","Life is a shipwreck, but we must not forget to sing in the lifeboats.","AUG 15 Tue","词霸小编：人生，总会有不期而遇的温暖，和生生不息的希望。我们要饱含热情地坚强地活下去。"},
	                  {"没有谁对不起谁,只有谁不懂得珍惜谁。","No one is indebted to others,while many people don’t know how to cherish others. ","AUG 14 Mon","词霸小编：无论你有多好，总会有不珍惜你的人；幸好，到了最后，所有不珍惜的人，都会成为过去。"},
	    //2017-8-7-------------2017-8-13   2:57-3:01
		              {"工作撵跑三个魔鬼：无聊，堕落和贫穷。","Work banishes those three great evils: boredom, vice and poverty.","AUG 13 Sun","词霸小编：以前我的生活就是工作，以后我的工作就是生活。祝福我能好好生活吧，嘻嘻……"},
	                  {"事情已经发生了，还希望事情不要发生没有意义，当务之急是减少损失。","When something bad happens, there’s no point in wishing it had not happened. The only option is to minimize the damage.  ","AUG 12 Sat","词霸小编：【固定搭配】there is no point in doing something表示“做某事无意义”，类似的搭配还有：“It’s no use doing something”，意思是“做某事没用”。这个句型里的真正的主语是动名词，it为形式主语。如：It is no use crying over spilt milk. / There is no point in crying over spilt milk."},
	                  {"我是无名之辈，你是谁？你也是无名之辈？——Emily Dickinson","I’m nobody! Who are you? Are you nobody, too? ","AUG 11 Fri","词霸小编：【涨姿势】Emily Dickinson是美国19世纪诗歌史上重要的诗人，也是20世纪英美意象派诗歌的先驱。她在美国文学史中之所以占有重要地位，不是取决于她的诗歌的思想内容，而是取决于她的诗歌的技巧和形式。其怪诞的风格、奇特的意象、巧妙的暗喻和不拘形式的格局，成为西方近代和当代诗歌的一个重要渊源。"},
	                  {"人到了暮年，比起自己干过的事，会更后悔没有干过的。——《生活大爆炸》","At the end of your life, you regret the stuff you didn’t do more than the stuff that you did.  ","AUG 10 Thu","词霸小编：哪怕是冲动，也就是后悔一阵子，但要是活得太怂呢，就会后悔一辈子。"},
	                  {"优雅地迎接成功，勇敢地面对挫折。","Meet success like a gentleman and disaster like a man.","AUG 9 Wed","词霸小编：当我们面对一个害怕的人，一桩恐惧的事，一份使人不安的心境时，唯一克服这种感觉的态度，便是面对它。"},
					  {"也许只有夜晚一床温暖的棉被和一个好梦才可以慰藉我们。","Maybe the only way to console us is to have a warm quilt and an unknown dream. ","AUG 8 Tue","词霸小编：老师说就算是Believe，中间也藏了一个lie；就算是Friend，还是免不了end；就算是Lover，还可能会over；就算是Wife，心里也夹杂着if；欣慰的是：即便是Forget，也曾经get，就算impossible，但还藏着possible，如果现在Unhappy，谁又保证不会happy？"},
	                  {"忘掉所有那些“不可能”的借口，去坚持那一个“可能”的理由。","Forget all the reasons why it won’t work, and believe the one reason why it will.  ","AUG 7 Mon","词霸小编：你可以有一万个理由一蹶不振，但是只要有一个理由你就要坚强站起！"},
	    //2017-7-31-------------2017-8-6   3:02-3:07
		              {"想要无可取代，就必须时刻与众不同。","In order to be irreplaceable, one must always be different.","AUG 6 Sun","词霸小编：当你自己选择了与众不同的生活方式之后，又何必去在乎别人用与众不同的眼光来看你。"},
	                  {"轻轻的我走了，正如我轻轻的来。——《再别康桥 》","I leave softly, gently,exactly as I came. ","AUG 5 Sat","词霸小编：《再别康桥》是现代诗人徐志摩脍炙人口的诗篇，是新月派诗歌的代表作品。全诗以离别康桥时感情起伏为线索，抒发了对康桥依依惜别的深情。语言轻盈柔和，形式精巧圆熟，诗人用虚实相间的手法，描绘了一幅幅流动的画面，构成了一处处美妙的意境，细致入微地将诗人对康桥的爱恋，对往昔生活的憧憬，对眼前的无可奈何的离愁，表现得真挚、浓郁、隽永，是徐志摩诗作中的绝唱。"},
	                  {"当一个女人有千言万语要说但默默无语时，她的沉默振聋发聩。","When a woman who has much to say says nothing, her silence can be deafening. ","AUG 4 Fri","词霸小编：美丽的女子令人喜欢，坚强的女子令人敬重，当一个女子既美丽又坚强时，她将无往不胜。"},
	                  {"耐心是苦涩的树，但它会长出甜美的果实。","Patience is a bitter plant that produces sweet fruit. ","AUG 3 Thu","词霸小编：有些路看起来很近走去却很远的，缺少耐心永远走不到头。——沈从文"},
	                  {"若你真的想做某事，你得努力上进、善用机会，而最重要的是决不放弃。","If you really want to do something, you have to work very hard, take advantage of opportunity, and above all never give up. ","AUG 2 Wed","词霸小编：一切都应该还有机会，一切都应该还来得及，所有糟糕的结果都还能改变，在命运的轮盘中没有最终停下之前。"},
					  {"只要你认真地欣赏自己，你就会拥有一个真正的自我。","If you earnestly admire yourself you’ll have a real sense of self-appreciation. ","AUG 1 Tue","词霸小编：没有自我的人的自我感觉都特别良好。"},
	                  {"有些事情，当我们年轻的时候无法懂得，当我们懂得的时候已不再年轻。 ","Somethings we couldn’t understand when we were young; when we finally understood, we are not young anymore. ","JUL 31 Mon","词霸小编：一句哲理在年轻人嘴里说出和在老年人嘴里说出是不一样的。年轻人说的只是这句哲理本身，尽管他可能理解得完全正确。而老年人不只是说了这句哲理，其中还包含了他的全部生活！"},
	    //2017-7-24-------------2017-7-30   3:09-3:14
		              {"当你心存感恩，恐惧会消失，内心也会感到丰裕富足。","When you are grateful, fear disappears and abundance appears.","JUL 30 Sun","词霸小编：生活欠我们一个“满意”，我们欠生活一个“知足”。"},
	                  {"撑着油纸伞，独自彷徨在悠长，悠长又寂寥的雨巷。——《雨巷》","Alone holding an oil-paper umbrella,I wander along a long solitary lane in the rain. ","JUL 29 Sat","词霸小编：【知识点】《雨巷》是戴望舒的成名作，作者通过对狭窄阴沉的雨巷，在雨巷中徘徊的独行者，以及那个像丁香一样结着愁怨的姑娘的描写 ， 含蓄地暗示出作者既迷惘感伤又有期待的情怀，并给人一种朦胧而又幽深的美感。也有人把这些意象解读为反映当时黑暗的社会的缩影，或者是在革命中失败的人和朦胧的、时有时无的希望。"},
	                  {"浪漫的精髓就在于它充满种种可能。","The very essence of romance is uncertainty. ","JUL 28 Fri","词霸小编：趁着年轻，多说些浪漫的话，多做些幼稚的事，不怕别人笑话而错过了生命中最美好的片段和场合。"},
	                  {"信任就像一张纸，皱了，即使抚平，也恢复不了原样了。","Trust is like a piece of paper. When it creases, it cannot recover even it is smoothed.","JUL 27 Thu","词霸小编：如果你信任我，就不需要我说什么，也不需要费心求证。如果你不信任你，再多的解释都显得多余。"},
	                  {"欣赏和喜欢你拥有的东西，而不是你没有的东西，你才能快乐。","The talent for being happy is appreciating and liking what you have, instead of what you don’t have.  ","JUL 26 Wed","词霸小编：【词汇辨析】instead 与 instead of 的区别：1.instead意为“代替”“替代”,作副词用,通常位于句尾。如位于句首时常用逗号与后面隔开。instead 在顺接句子中作“代替”讲,而在转折句子中作“然而”讲。如：Lily isn’t here.Ask Lucy instead.Lily不在这儿,去问Lucy吧。She didn’t answer me,instead,she asked me another question.她没有回答我,反而问了我另外一个问题.2.inst"},
					  {"从明天起，做一个幸福的人；喂马，劈柴，周游世界。——《面朝大海 春暖花开》","From tomorrow on, I will be a happy man; grooming, chopping,and traveling all over the world. ","JUL 25 Tue","词霸小编：【背景知识】《面朝大海，春暖花开》是海子于1989年所写的一首抒情诗。全诗共三节，第一节表现了诗人对质朴、单纯而自由的人生境界的向往，对“永恒”、未知世界的探寻精神。第二节写诗人找到幸福后无法抑制的喜悦之情。第三节写诗人对世界的祝福。诗人将直抒胸臆与暗示、象征手法结合起来，使全诗既清澈又深厚，既明朗又含蓄，畅快淋漓而又凝重、丰富，抒发了诗人的向往幸福而又孤独凄凉之情。"},
	                  {"在你想要放弃的那一刻，想想为什么当初坚持走到了这里。 ","The minute you think of giving up, think of the reason why you held on so long.","JUL 24 Mon","词霸小编：放弃有十五画，而坚持有十六画，所以，坚持只比放弃多一点。"},
	    //2017-7-17-------------2017-7-23   3:14-3:18
		              {"与其等着别人来爱你，不如自己努力爱自己。","It’s better to try hard to love yourself more than to wait someone to love you. ","JUL 23 Sun","词霸小编：对自己好点，因为一辈子不长；对身边的人好点，因为下辈子不一定能够遇见！"},
	                  {"生活有时是令人沮丧的，但你可以努力让自己过得开心。","Life is sad at times, but it is up to you to make your own life happy. ","JUL 22 Sat","词霸小编：很多人不快乐，因为总觉得过去太美好，现在太糟糕，将来又太飘渺。"},
	                  {"生活中最大的幸福是坚信有人爱我们。","The super happiness of life is the conviction that we are loved. ","JUL 21 Fri","词霸小编：有人爱你，有人崇拜你，有人羡慕你，有人嫉妒你，有人议论你，有人鄙视你，有人在背后中伤你，有人恨你……这就是生活。"},
	                  {"世上只有一件事比被人议论更糟糕了，那就是没有人议论你。","The only thing worse than being talked about is not being talked about. ","JUL 20 Thu","词霸小编：与其在意别人的背弃和不善，不如经营自己的尊严和美好。"},
	                  {"最难开口的事就是，初次的问好，和最终的道别。","Two of the hardest things to say in life are: hello for the first time and goodbye for the last. ","JUL 19 Wed","词霸小编：无奈的是，语言这东西，在表达爱意的时候如此无力；在表达伤害的时候，却又如此锋利。"},
					  {"年轻的好处在于，你还没有太多经验，并天生相信一切皆可能。","The good thing about being young is that you are not experienced enough to know you cannot possibly do the things you are doing. ","JUL 18 Tue","词霸小编：因为年轻,才有的是时间;因为年轻,才更容易接受新事物;因为年轻,才有最为饱满的激情与最智慧的大脑。趁年轻,相信希望,相信梦想,为自己的梦想拼搏闯一次！！！"},
	                  {"没有人值得你流泪，值得你流泪的人是不会让你哭的。","No person deserves your tears, and who deserves them won’t make you cry. ","JUL 17 Mon","词霸小编：小时候，哭着哭着，就笑了；长大了，笑着笑着，就哭了。"},
	    //2017-7-10-------------2017-7-16   3:32-3:36
		              {"丢下一些包袱后，你会惊讶到，自己竟能飞得那么高。","You’ll be amazed at how high you can fly when you leave some of the baggage behind! ","JUL 16 Sun","词霸小编：我知道何时止步，何时放手，何时前行。但是，我“知道”不代表我“能够”。臣妾做不到啊，你们说怎么办？"},
	                  {"愿你生命中有够多的云翳，来造就一个美丽的黄昏。——冰心","May there be enough clouds in your life to make a beautiful sunset. ","JUL 15 Sat","词霸小编：此句是个比喻句，意思是说希望你有一个丰富多彩的人生经历，唯此你才能真正体会到人生的美好。这里的“云翳”喻指丰富多彩的经历，体验。"},
	                  {"能冲刷一切的除了眼泪，就是时间，以时间来推移感情，时间越长，冲突越淡。","Apart from tears, only time could wear everything away. While feeling is being processed by time, conflicts would be reconciled as time goes by. ","JUL 14 Fri","词霸小编：时间笑我不成长，我笑时间太猖狂。——李宫俊"},
	                  {"必须说“可以”的时候，真心诚意地说出来；必须说“不行”的时候，毫不畏惧地说出来。","If you must say yes, say it with an open heart. If you must say no, say it without fear. ","JUL 13 Thu","词霸小编：to be or not to be,that’s not a question. 身随心动，舞出我人生！！！"},
	                  {"你在乎他，他才那么闪耀。你不在乎他，他就什么都不是。","It is because you care for him so much that makes him outstanding. But if you don’t, he is just nothing.","JUL 12 Wed","词霸小编：我一直以为山是水的故事，云是风的故事，你是我的故事，可是却不知道，我是不是你的故事。——《梦里花落知多少》"},
					  {"你每天都在做很多看起来毫无意义的决定，但某天你的某个决定就能改变你的一生。—《西雅图夜未眠》","You make millions of decisions that mean nothing and then one day your order takes out and it changes your life.","JUL 11 Tue","词霸小编：做不了决定的时候，让时间帮你决定。如果还是无法决定，做了再说。宁愿犯错，不留遗憾。"},
	                  {"做出改变的秘诀不是多么努力地和过去做斗争，而是全力以赴地去打造全新的自己。","The secret of change is to focus all of your energy, not on fighting the old, but on building the new. ","JUL 10 Mon","词霸小编：让过去的过去，让开始的开始，改变我可以改变的事情，接受我不能改变的！"},
	    //2017-7-3-------------2017-7-9   3:36-3:41
		              {"快乐人生的三个必要元素是，有要做的事、热爱的事及盼望的事。– 约瑟夫‧艾迪生","Three grand essentials to happiness in this life are something to do, something to love, and something to hope for.— Joseph Addison","JUL 9 Sun","词霸小编：快乐的秘诀是装傻，装傻，再装傻；烦恼的秘诀是较真，较真，再较真；痛苦的秘诀是攀比，攀比，再攀比。"},
	                  {"还是结婚吧；如果你找到了一位好妻子，你会变得幸福；如果你有找到了一位坏妻子，你会成为一位哲人。——苏格拉底","By all means marry; if you get a good wife, you’ll become happy; if you get a bad one, you’ll become a philosopher. ","JUL 8 Sat","词霸小编：女人感叹男人：有才华的长得丑；长得帅的挣钱少；挣钱多的不顾家；顾家的没出息；有出息的不浪漫；会浪漫的靠不住；靠的住又窝囊。男人感叹女人：漂亮的不下厨房；下厨房的不温柔；温柔的没主见；有主见的没女人味；有女人味的乱花钱；不乱花钱的不时尚；时尚的不放心；放心的没法看。"},
	                  {"对于你走完的路感到骄傲，对于你可以走多远感到自信，但不要忘记享受旅程。","Take pride in how far you’ve come. Have faith in how far you can go. But don’t forget to enjoy the journey. ","JUL 7 Fri","词霸小编：生命的过程，无论是阳春白雪，青菜豆腐，我都得尝尝是什么滋味，才不枉来走这么一遭！—— 三毛"},
	                  {"真正的朋友能读懂你眼神中的哀伤，而其他人却相信你脸上的微笑。","A true friend is someone who sees the pain in your eyes, while everyone else believes the smile on your face.","JUL 6 Thu","词霸小编：当大部分人都在关注你飞的高不高时，只有少部分人关心你飞的累不累，这就是友情。"},
	                  {"雨儿在到处降落，它落在田野和树梢，它落在这边的雨伞上，又落在航海的船只上。","Rain is falling all around;It falls on field and tree;It rains on the umbrella here;And on the ships at sea. ","JUL 5 Wed","词霸小编：下雨了,有人在等雨伞，有人在等雨停。不知你是在等什么？"},
					  {"我认为从众的奖励就是除了你自己所有人都喜欢你。","I think the reward for conformity is that everyone likes you except yourself. ","JUL 4 Tue","词霸小编：人生不过如此，且行且珍惜。自己永远是自己的主角，不要总在别人的戏剧里充当着配角。"},
	                  {"如果你没有经历过别人所经历的事情，就不要去评判一个人的过去！","Don’t judge someone’s past, when you haven’t walked their journey! ","JUL 3 Mon","词霸小编：不要妄加评断我，你看到的，只是我选择让你看到的。"},
	    //2017-6-26-------------2017-7-2   3:42-3:47
		              {"我可以接受失败，但我不能接受从不去尝试。——迈克尔·乔丹","I can accept failure,but I can’t accept not trying.  ","JUL 2 Sun","词霸小编:当你连尝试的勇气都没有，你就不配拥有幸福，也永远不会得到幸福，伤过，痛过，才知道有多深刻。——三毛"},
	                  {"知道什么时候应该说话的人，也知道什么时候应保持沉默。","One who knows when to speak will also know when to be silent.","JUL 1 Sat","词霸小编：说话要讲技巧，不要哪壶不开提哪壶：急事，慢慢说；小事，幽默说；没把握的事，谨慎说；没发生的事，不胡说；伤害人的事，不能说；伤心的事，别见人就说；别人的事，小心说。"},
	                  {"世间最好的感受，就是发现自己的心在微笑。","The best feeling in the world is when you know your heart is smiling. ","JUN 30 Fri","词霸小编：想吃了不要嫌贵，想穿了不要说浪费，保持心态的平和永远是最美，天天快乐才对。"},
	                  {"最好的妆容是微笑，最好的饰品是谦虚，最好的衣裳是自信。","The best make-up is smile. The best jewelry is modesty. The best clothing is confidence. ","JUN 29 Thu","词霸小编：【词汇辨析】clothes与clothing的区别：一、clothes的用法：1.clothes 表示“衣服”,是复数名词,其前不可加不定冠词,也不可加数词,但可用some,these,those,many,(a) few 等词修饰。如：I need to buy some clothes.2.此外clothes可受某些名词修饰,表示不同用途的衣服,如school clothes（校服）,sports clothes（运动服）。3.它还可以受季节名词修饰,表示不同季节穿的服装,如spring c"},
	                  {"有的时候，人们之所以哭泣并不是因为软弱，而是因为他们坚强了太久。","Sometimes, people cry not because they’re weak. It’s because they have been strong for too long. ","JUN 28 Wed","词霸小编：不是因为够坚强，就不需要安慰；只是因为缺少了安慰，才不得不坚强。刚开始是假装坚强，后来就真的坚强了，坚强到让你心痛。"},
					  {"不管过去有多么困难，你都可以重新开始。– 杰克．康菲尔德","No matter how hard the past is, you can always begin again. - Jack Kornfield ","JUN 27 Tue","词霸小编：没有不会谢的花，没有不会退的浪，没有不会暗的光，没有不会好的伤，没有不会停下来的绝望。那么，你在烦恼什么？"},
	                  {"时间几乎会愈合所有伤口，如果你的伤口还没有愈合，请给时间一点时间！","Time would heal almost all wounds. If your wounds have not been healed up, please wait for a short while. ","JUN 26 Mon","词霸小编：给时间一点时间，让过去成为过去。给过去一点时间，让时间成为过去。"},
	    //2017-6-19-------------2017-6-25   3:47-3:52
		              {"你必须在没有人相信你时相信自己。","You have to believe in yourself when no one else does. ","JUN 25 Sun","词霸小编：你要做的是相信自己，你能作茧自缚，那么你也就能破茧成蝶。"},
	                  {"那只敏捷的棕色狐狸跳过了一只懒惰的狗。","The quick brown fox jumps over a lazy dog. ","JUN 24 Sat","词霸小编：一个神奇的句子，有木有发现它的特别之处呢？Bingo，这个句子包含了英语中的26个字母，你答对了吗？"},
	                  {"逃避困难是一场永远跑不赢的比赛。","Running away from your problems is a race you’ll never win.","JUN 23 Fri","词霸小编：六点起床很困难，背单词很困难，静下心很困难……但是总有一些人，五点可以起床，一天背六课单词，耐心读完一本书。你以为没有了路，事实上路可能就在前方一点点。那些比我们强大的人都在迎难而上，你有什么理由止步不前？"},
	                  {"优雅是唯一不会褪色的美。——奥黛丽赫本","Elegance is the only beauty that never fades. - Audrey Hepburn ","JUN 22 Thu","词霸小编：人生的优雅并非训练或装扮出来的，而是百千阅历后的坦然，饱受沧桑后的睿智，无数沉浮后的淡泊。是把尘事看轻些，学会舍得与放下；是把人际看浅些，少些倾轧与争斗；是把得失看淡些，宠辱不惊来去无意，如此心宁静，优雅随之。"},
	                  {"Raj：我不喜欢虫子行了吧？吓得我半死。谢耳朵：有意思。你害怕虫子，还有女人。要见着个花大姐准让你神经分裂了。","Raj: I don’t like bugs, okay? They freak me out. Sheldon: Interesting.You’re afraid of insects and women.Ladybugs must render you catatonic. ","JUN 21 Wed","词霸小编：毒舌Sheldon还真是个吐槽高手，戏弄别人都带着高智商。ladybug：lady女士+bug昆虫 复合名词瓢虫的意思；render v.使成为，使处于某状态；catatonic adj.患紧张症的"},
					  {"人们需要困难，因为有困难才能享受成功。","Man needs his difficulties because they are necessary to enjoy success.","JUN 20 Tue","词霸小编：油条：不受煎熬，你就不会成熟；总受煎熬，你终究会成为老油条"},
	                  {"不要让自己被这三件事所控制：过去、别人和金钱。","Don’t let yourself be controlled by these three things: your past, people, and money. ","JUN 19 Mon","词霸小编：你要学会捂上自己的耳朵，不去听那些熙熙攘攘的声音；这个世界上没有不苦逼的人，真正能治愈自己的，只有你自己。"},
	    //2017-6-12-------------2017-6-18   3:52-3:57
		              {"我的父亲是什么样的人并不重要，重要的是我心目中的父亲是什么样的人。——安妮·塞克斯顿","It doesn’t matter who my father was; it matters who I remember he was. - Anne Sexton","JUN 18 Sun","词霸小编：爸爸我想对您说：“总有一天我会成为您的骄傲！”父亲节快乐！"},
	                  {"你不需要把自己弄得骨瘦如柴才漂亮。开开心心接受你自己的身材，这才是让你变漂亮的原因。","You don’t have to be skinny to be pretty. You just have to be happy with your body, that’s what actually makes you look pretty. ","JUN 17 Sat","词霸小编：三月不减肥，四月徒伤悲，五月路人雷，六月男友没，七月被晒黑，八月待室内，九月更加肥！十月相亲累，十一月无人陪，十二月无三围，一月肉更肥，二月不知谁。"},
	                  {"只要持续的为梦想而努力，尽所能得让梦想实现，梦想是不会伤害任何人的。","Dreams never hurt anybody if he keeps working right behind the dream to make as much of it become real as he can.","JUN 16 Fri","词霸小编：【语句分析】right behind 是“正后面”，有强调的意味。to make as much of it become real as he can 用的是 “as… as… ” 句型，说明两件事情的对等性。make it become real在句中被分开来，是“使它 (梦想) 成为现实”的意思，as much of it as he can 译为“尽他所能让更多梦想实现”。"},
	                  {"爱，起于微笑，浓于亲吻，逝于泪水。","Love begins with a smile, grows with a kiss and ends with a tear.","JUN 15 Thu","词霸小编：爱可以爱到骨头里，但爱不能低到尘埃里。"},
	                  {"任何事情都应该去尝试一下，因为你无法知道，什么样的事或者什么样的人将会改变你的一生。","Give everything a shot. You never know what or who is going to change your life. ","JUN 14 Wed","词霸小编：我不会逃避也不会迟疑，愿去尝试生命不同的绮丽。——《疯狂动物城》"},
					  {"想想你目前拥有的幸福，这每人都有很多 – 別回想以前的不幸，这每人或多或少都有一点。——查尔斯·狄更斯","Reflect upon your present blessings of which every man has many - not on your past misfortunes, of which all men have some.— Charles Dickens ","JUN 13 Tue","词霸小编：你的问题主要在于读书不多，而想得太多。——杨绛"},
	                  {"没人能够贬低我们，除非是我们自己。","No one but ourselves can degrade us.  ","JUN 12 Mon","词霸小编：自己选择45°仰视别人，就休怪他人135°俯视着看你。"},
	    //2017-6-5-------------2017-6-11   3:57-3:57
		              {"有点缺点没关系，这样才真实。","It’s okay to have flaws, which make you real. ","JUN 11 Sun","词霸小编：我的优点是：我很帅。我的缺点是：我帅的不明显。——张伟《爱情公寓》"},
	                  {"生活中不是没有美，而是缺少发现美的眼睛。","Life is not lack of beauty, but lack of the eyes to find beauty. ","JUN 10 Sat","词霸小编：【lack相关短语的区别】1.lack n.缺乏 (a) lack of sth（物质上的,具体的）如：a lack of money 缺乏资金；There was no lack of wolunteers.志愿者不乏其人。2.lack vt.缺乏,不足,短缺 lack sth 如：He lacks confidence.他缺乏信心.3.lacking adj. 缺乏的 be lacking in （某一方面,抽象的）如：She’s not usually lacking in confide"},
	                  {"你勇敢，世界就会让步。如果有时它战胜你，你要不断地勇敢再勇敢，它就会屈服。","Dare and the world always yields. If it beats you sometimes, dare it again and again and it will succumb. ","JUN 9 Fri","词霸小编：我哭，你说我不勇敢；你勇敢，为什么不敢哭？"},
	                  {"诺不轻信，故人不负我；诺不轻许，故我不负人。","Don’t count on others rashly, so they don’t have the chance to betray you; Don’t make rash promises either, in case you may regret. ","JUN 8 Thu","词霸小编：不要做让自己后悔的事，要做，就做让别人后悔的事。"},
	                  {"昨天已成历史，明天仍是未知，但今天是上天赐予我们的礼物，你能做的就是把握今天！","Yesterday is history. Tomorrow is a mystery. But today is a gift. All you can do is to grasp it. ","JUN 7 Wed","词霸小编：致高考：愿你们合上笔盖的那一刻有武士将刀收回剑鞘般的骄傲！高考加油！！！"},
					  {"任何一个人，如果真诚地试着帮助他人，必然也同时帮助了自己，这乃是人生中最优美的补偿之一。","It is one of the most beautiful compensations of life that no man can sincerely try to help another without helping himself. ","JUN 6 Tue","词霸小编：【语法扩充】英语句子中常可见到两个表示否定意义的词连用的情况，这一现象常称为双重否定。这种句子形式上虽为否定，实则表示强烈的肯定语气。英语双重否定句常见的有如下三种句型：1.否定词 no/not等＋表示否定意义的形容词。如：Nothing is unnecessary.没有什么是不必要的。 2.否定词 no/not/never等＋without...如：They never meet without quarreling．他们每次见面必吵架。3.否定词 no/not/never/nobody/f"},
	                  {"麻烦没来找你，就别去自找麻烦。","Never trouble trouble till trouble troubles you. ","JUN 5 Mon","词霸小编：不该看的不看，不该说的不说，不该听的不听，不该想的不想，该干什么干什么去。"},
	    
			};
	    	
	    	int l=sens.length;
	    	//查询数据库中有无该天的每日一句，若有，则输出，否则，随机分配一个并录入数据库
	    	try {
	    		Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/longblog?useUnicode=true&characterEncoding=utf-8", "root", "ZCLZY");
				Statement st=con.createStatement();
				//获取当前日期
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String day=sdf.format(date);
				//判断库中是否有当前日期的数据
				String sql="select * from sentence where s_Date='"+day+"';";
				//sentence表            id，date,chi,eng,time,ciba
				ResultSet rs=st.executeQuery(sql);
				if(rs.next()){//有数据，直接输出
					//输出
					String chi=rs.getString("s_Chinese");
					String eng=rs.getString("s_English");
					String time=rs.getString("s_Time");//原句子的发布时间,包括月,日，星期
					String ciba=rs.getString("s_Ciba");
					sen.setS_Chinese(chi);
					sen.setS_English(eng);
					sen.setS_Time(time);
					sen.setS_Ciba(ciba);
				}else{//为今天分配一句话
					//每天显示随机的一句话----关键是每天无论运行几次都要是这句话
			    	Random ran=new Random();
			    	int r=ran.nextInt(l);
			    	System.out.println(r+sens[r][0]);
			    	String sql1="insert into sentence(s_Date,s_Chinese,s_English,s_Time,s_Ciba) values('"+day+"','"+sens[r][0]+"','"+sens[r][1]+"','"+sens[r][2]+"','"+sens[r][3]+"');";
			    	st.executeUpdate(sql1);
			    	sen.setS_Chinese(sens[r][0]);
			    	sen.setS_English(sens[r][1]);
			    	sen.setS_Time(sens[r][2]);
			    	sen.setS_Ciba(sens[r][3]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return sen;
		}
}