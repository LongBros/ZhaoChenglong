import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Test2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="http://www.pdsu.edu.cn";
		Document doc=Jsoup.connect(url).get();
		
		
		
		Element z=doc.getElementById("Tzh").val("151360247");
		System.out.println(z);
		System.out.println("-----------------");

		Element m=doc.getElementById("Tmm").val("145989");
		Element f= doc.getElementById("form1");
		System.out.println(f);
		System.out.println("-----------------");
		System.out.println(m);

		
		
		
		
		
	}

}
