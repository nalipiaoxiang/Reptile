package cn.nalipiaoxiang.entity;

public class Article {

	public String title;
	public String contains;

	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Article(String title, String contains) {
		super();
		this.title = title;
		this.contains = contains;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContains() {
		return contains;
	}

	public void setContains(String contains) {
		this.contains = contains;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", contains=" + contains + "]";
	}

}
