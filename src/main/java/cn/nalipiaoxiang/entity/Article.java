package cn.nalipiaoxiang.entity;

public class Article {

	public String title;
	public String content;

	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Article(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", content=" + content + "]";
	}

}
