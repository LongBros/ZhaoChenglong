package others;

import java.util.Random;

import com.longbro.util.OtherUtil;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*for(;;){
			int i=OtherUtil.generateRan();
			System.out.println(i);

		}*/
//		Random ran=new Random();
//		System.out.println(ran.nextInt(40));
		String h="<p style=text-align: center;>致我们终将到来的<span style=font-size: large; color: rgb(249, 150, 59);>毕业</span></p><p style=text-align: left;>       .时光如白驹过隙，匆匆而过。还清晰的记得来到平院时";
//		String [] s={"a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		h=h.replaceAll("<[.[^<]]*>", "");

		System.out.println(h);
	}

}
