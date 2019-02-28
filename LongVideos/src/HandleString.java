import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HandleString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="<a class='js-tongjic' href='/m/gafjY0L4S0X5UB.html'> ";
		String href=s.substring(s.indexOf("href='")+6, s.indexOf("'>"));
		System.out.println(href);

		System.out.println(getContent(s,"href", "'>" ));


		String s1="<img src='https://p.ssl.qhimg.com/t01575b9ac4db91d0eb.jpg'>";
		String src=s1.substring(s1.indexOf("src='")+5, s1.indexOf("'>"));
		System.out.print(src);

	}
	//参数，待处理的字符串，返回要得到的href里面的内容
	public static String getHref(String s){
		if(s.contains("href")){
//			Pattern pa=Pattern.compile("^href");
			StringBuilder sb=new StringBuilder(s);
			
			return sb.delete(0, 28).delete(22, 26).toString();
		}else{
			return null;

		}
	}
	//截取src
	public static String getSrc(String s){
		if(s.contains("src")){
//			Pattern pa=Pattern.compile("^href");
			StringBuilder sb=new StringBuilder(s);
			
			return sb.delete(0, 14).delete(47, 49).toString();
		}else{
			return null;

		}
	}
	//去掉一个字符串中的所有空白
	public String clearBla(String old){
		return null;
	}
	//截取字符串s的从a到b之间的内容      <a class='js-tongjic' href='/m/gafjY0L4S0X5UB.html'>
	public static String getContent(String s,String a,String b) {
		String newS=null;
		if(s.contains(a)&&s.contains(b)){
//			开始截取
			int i=s.indexOf(a);//获取href所在的位置
			int j=s.indexOf(b);
			newS=s.substring(i+6, j);
		}
		return newS;
	}

}
