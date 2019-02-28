import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**

 * @author Administrator
 *
 */
public class Test1 {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="https://www.360kan.com/dianshi/list?year=all&amp;area=all&amp;act=all&amp;cat=101";//?rank=rankhot&cat=all&area=all&year=all&pageno=2
		//String的replace方法，可用于去除字符串中的特定字符
//		url=url.replace("amp;", "");
//		System.out.println(url);
		
		String[] trimChars={"amp;"};
		url=url.trim();
		System.out.println(url);
	}

}
