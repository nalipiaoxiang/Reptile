package cn.nalipiaoxiang.main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import cn.nalipiaoxiang.entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test05 {

	static String prefix = "https://m.shukeba.com";
	//private static int num = 500;

	/**
	 * @Description: 该函数的功能描述
	 * @param args
	 * @throws Exception void
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("请输入:");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String string = sc.nextLine();

		String url = "https://m.shukeba.com/31477/";
		List<String> list = new ArrayList<String>();

		loop(url, list);
		for (int i = 1; i < list.size(); i++) {
			if (i == Integer.parseInt(string)) {
				Article article = getArticle(list.get(i));
				String content = article.getContent();
				for (int j = 0; j < content.length(); j++) {
					if (j % 50 == 0) {
						System.out.println();
					}
					char c = content.charAt(j);
					System.out.print(c);
				}
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
