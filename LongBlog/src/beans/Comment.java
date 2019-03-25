package beans;

public class Comment {
	int id;//评论的id
	int b_Id;//被评论的博文的id
	String content;
	String time;
	String ip;
	String add;
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	String path;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getB_Id() {
		return b_Id;
	}
	public void setB_Id(int b_Id) {
		this.b_Id = b_Id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

}
