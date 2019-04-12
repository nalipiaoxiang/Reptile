package cn.nalipiaoxiang.main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import cn.nalipiaoxiang.entity.Article;
import cn.nalipiaoxiang.thread.TaskWithResult;
import cn.nalipiaoxiang.util.MyUtils;

public class Test02 {

	public static Integer threadCount = 25;

	public static void main(String[] args) throws Exception {
		System.out.println("程序启动");
		System.out.println("启动" + threadCount + "线程");
		long t1 = System.currentTimeMillis();
		// 某个首页
		String index = "https://www.23us.so/files/article/html/1/1319/index.html";
		// 获取document
		Document document = MyUtils.getDocument(index);
		Elements labelh1 = document.select("h1");
		String name = labelh1.text();
		// 获得某一标签
		Elements elements = document.select("[cellpadding=0][bgcolor=#E4E4E4][id=at]");
		Elements select = elements.select("a[href]");

		if (StringUtils.isBlank(name)) {
			name = "未命名";
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name + ".txt")));
		List<Element> list = new ArrayList<Element>();
		List<List<Element>> lList = new ArrayList<List<Element>>();
		for (int i = 0; i < select.size(); i++) {
			list.add(select.get(i));
			if (list.size() == threadCount) {
				lList.add(list);
				list = new ArrayList<Element>();
			}
		}
		// 计数器
		int count = 0;
		lList.add(list);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (List<Element> numberContainer : lList) {
			@SuppressWarnings("rawtypes")
			ArrayList<Future> fulture = new ArrayList<Future>();
			for (Element e : numberContainer) {
				fulture.add(executor.submit(new TaskWithResult(e.attr("href"), name)));
			}
			for (int i = 0; i < fulture.size(); i++) {
				count++;
				System.out.println("执行线程:" + (i + 1));
				System.out.println("执行抓取数:" + count);
				Article article = (Article) fulture.get(i).get();
				String title = article.getTitle();
				String content = article.getContent();
				// 对标题校验
				if (StringUtils.isNotBlank(title)) {
					bw.write(title);
					bw.newLine();
					bw.flush();
					bw.write(content);
					bw.newLine();
					bw.flush();
				} else {
					continue;
				}
			}
		}
		bw.close();
		executor.shutdown();
		long t2 = System.currentTimeMillis();
		System.out.println("执行线程数:" + threadCount + "耗时:" + (t2 - t1) + "毫秒");
		System.out.println("执行线程数:" + threadCount + "耗时:" + (t2 - t1) / 1000 + "秒");

	}

}
