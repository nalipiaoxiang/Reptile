package cn.nalipiaoxiang.program;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import cn.nalipiaoxiang.entity.Article;
import cn.nalipiaoxiang.thread.TaskWithResult;
import cn.nalipiaoxiang.util.MyUtils;

public class Test02 {

	public static void main(String[] args) throws Exception {

		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream("1.txt")));
		// 某个首页
		String index = "https://www.23us.so/files/article/html/1/1319/index.html";
		// 获取document
		Document document = MyUtils.getDocument(index);
		Elements labelh1 = document.select("h1");
		String name = labelh1.text();
		// 获得某一标签
		Elements elements = document.select("[cellpadding=0][bgcolor=#E4E4E4][id=at]");
		Elements select = elements.select("a[href]");

		List<Element> list = new ArrayList<Element>();
		List<List<Element>> lList = new ArrayList<List<Element>>();
		for (int i = 0; i < select.size(); i++) {
			list.add(select.get(i));
			if (list.size() == 10) {
				lList.add(list);
				list = new ArrayList<Element>();
			}
		}
		lList.add(list);
		for (List<Element> ten : lList) {
			ExecutorService executor = Executors.newCachedThreadPool();
			@SuppressWarnings("rawtypes")
			ArrayList<Future> fulture = new ArrayList<Future>();
			for (Element e : ten) {
				fulture.add(executor.submit(new TaskWithResult(e.attr("href"), name)));
			}
			for (int i = 0; i < fulture.size(); i++) {
				Article article = (Article) fulture.get(i).get();
				System.out.println(article.title);

			}

		}

	}

}
