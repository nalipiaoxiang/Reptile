package cn.nalipiaoxiang.thread;

import java.util.concurrent.Callable;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.nalipiaoxiang.entity.Article;
import cn.nalipiaoxiang.util.MyUtils;

public class TaskWithResult implements Callable<Article> {

	public String url;
	public String name;

	public TaskWithResult(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	public Article call() throws Exception {
		Document document = MyUtils.getDocument(url);
		Elements h1 = document.select("h1");
		String title = h1.text();
		Elements contents = document.select("[id=contents]");
		String content = contents.text();
		Article article = new Article(title, content);
		return article;
	}

}
