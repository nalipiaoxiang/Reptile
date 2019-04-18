package cn.nalipiaoxiang.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import cn.nalipiaoxiang.entity.Article;

/**
 * Copyright: Copyright (c) 2019
 * 
 * @ClassName: Test03.java
 * @Description: 爬取xx粗略
 *
 * @version: v1.0.0
 * @author: CPC
 * @date: 2019年4月18日 下午3:36:45
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2019年4月18日
 *        CPC v1.0.0 修改原因
 */
public class Test03 {

	static String prefix = "https://m.shukeba.com";
	// 获取章节这个是粗略写的有误差
	private static int num = 500;

	public static void main(String[] args) throws Exception {

		String url = "https://m.shukeba.com/31477/";
		List<String> list = new ArrayList<>();

		loop(url, list);
		for (int i = 1; i < list.size(); i++) {
			if (i == num) {
				Article article = getArticle(list.get(i));
				System.out.println(article.getTitle());
			}
		}
	}

	public static void loop(String url, List<String> list) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements dl = document.select("dl");
		Elements dd = dl.select("dd");
		for (int i = 0; i < dd.size(); i++) {
			Element element = dd.get(i);
			Elements ahref = element.select("a[href]");
			String path = ahref.attr("href");
			list.add(prefix + path);
			if (i == dd.size() - 1) {
				Elements pages = document.select("[class=pages]");
				Elements next = pages.select("[class=next]");
				Elements href = next.select("a[href]");
				String attr = href.attr("href");
				loop(prefix + attr, list);
			}
		}
	}

	public static Article getArticle(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements labelh1 = document.select("h1");
		String title = labelh1.text();
		Elements contents = document.select("[class=content]");
		String content = contents.text();
		return new Article(title, content);
	}

}
