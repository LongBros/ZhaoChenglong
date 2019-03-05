package utils;

import java.util.Random;

public class MathTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		generateNumber();
		String ip="123.126.113.85";//123.126.113.85//117.158.127.52
		String ip1="";
		if(ip.contains(",")){
			ip1=ip.substring(0, ip.indexOf(","));
		}else{
			ip1=ip;
		}
		System.out.println(ip1);
		String add=AddressUtils.getAddByIp(ip1);
		System.out.println(add);
	}
	
	private static void generateNumber() {
		Random ran=new Random();
		for(int i=0;i<1000;i++){
			int tx=ran.nextInt(9);
			System.out.println(tx);
		}
	}

}
