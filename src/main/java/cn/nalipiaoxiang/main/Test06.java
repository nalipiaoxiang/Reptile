package cn.nalipiaoxiang.main;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.nalipiaoxiang.entity.Article;

public class Test06 {

	private static final String prefix = "http://m.shuketxt.net";
	private static final String url = "http://m.shuketxt.net/0/675/";
//	private static final int num = 500;

	public static void main(String[] args) throws Exception {

		Document document = Jsoup.connect(url).get();
//		Elements et = document.select("title");
		Document parse = Jsoup.parse("<a href=/0/675/>666</a>");
		Elements select = parse.select("a");

		List<Elements> list = new ArrayList<Elements>();
		loop(select, list);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("123.txt")));
		for (Elements elements : list) {
			String attr = elements.attr("href");
			bw.write(prefix + attr);
			bw.flush();
			bw.newLine();
		}
		bw.close();
		System.out.println("Over");

	}

	static int count = 0;

	public static void loop(Elements e, List<Elements> list) throws IOException {
		String dest = e.select("a[href]").attr("href");
		String URL = prefix + dest;
		Document document = Jsoup.connect(URL).get();
		if (document.select("ul[class=p2]").size() > 0) {
			Element dl = document.select("ul[class=p2]").get(1);
			Elements dd = dl.select("a");
			for (int i = 0; i < dd.size(); i++) {
				count++;
				Element element = dd.get(i);
				Elements ahref = element.select("a[href]");
				System.out.println(count);
				list.add(ahref);
				if (i == dd.size() - 1) {
					Elements pages = document.select("[class=listpage]");
					Elements next = pages.select("[class=right]");
					Elements href = next.select("a[href]");
					loop(href, list);
				}
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
