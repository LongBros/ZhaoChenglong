package bean;
/**
 * 时间，库中该时间段（年-月/年-月-日）对应的博客的数量
 * @author Administrator
 *
 */
public class TimeNum {
	private String time;
	private int num;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	

}
