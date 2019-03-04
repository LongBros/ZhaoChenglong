package beans;

public class Sentence {
	private int s_Id;
	private String s_Date;//每日一句出现的日期
	private String s_Chinese;
	private String s_English;
	private String s_Time;//在词霸上出现的日期
	private String s_Ciba;
	public int getS_Id() {
		return s_Id;
	}
	public void setS_Id(int s_Id) {
		this.s_Id = s_Id;
	}
	public String getS_Date() {
		return s_Date;
	}
	public void setS_Date(String s_Date) {
		this.s_Date = s_Date;
	}
	public String getS_Chinese() {
		return s_Chinese;
	}
	public void setS_Chinese(String s_Chinese) {
		this.s_Chinese = s_Chinese;
	}
	public String getS_English() {
		return s_English;
	}
	public void setS_English(String s_English) {
		this.s_English = s_English;
	}
	public String getS_Time() {
		return s_Time;
	}
	public void setS_Time(String s_Time) {
		this.s_Time = s_Time;
	}
	public String getS_Ciba() {
		return s_Ciba;
	}
	public void setS_Ciba(String s_Ciba) {
		this.s_Ciba = s_Ciba;
	}
}
