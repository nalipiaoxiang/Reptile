package cn.nalipiaoxiang.main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Copyright: Copyright (c) 2019
 * 
 * @ClassName: Test01.java
 * @Description: 爬取TXT文本并生产
 *
 * @version: v1.0.0
 * @author: CPC
 * @date: 2019年4月10日 下午4:23:06
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2019年4月10日
 *        CPC v1.0.0 修改原因
 */
public class Test01 {
	// 原理:使用httpclient获取页面内容,然后用jsoup解析页面

	// 获取页面内容
	public static Document getDocument(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String html = EntityUtils.toString(response.getEntity(), "utf-8");
		Document document = Jsoup.parse(html);
		return document;
	}

	// 主函数
	public static void main(String[] args) throws Exception {
		System.out.println("开始干活");
		// 首页
		String index = "https://www.23us.so/files/article/html/1/1319/index.html";
		// 获取页面内容
		Document document = getDocument(index);
		// 获取书籍名字
		Elements labelh1 = document.select("h1");
		String name = labelh1.text();
		int indexOf = name.indexOf("最新章节");
		name = name.substring(0, indexOf);
		// 如果获取不到名字
		if (StringUtils.isBlank(name)) {
			name = "无法获取名字";
		}
		// 获取所有章节的url
		Elements elements = document.select("[cellpadding=0][bgcolor=#E4E4E4][id=at]");
		Elements select = elements.select("a[href]");
		// 再遍历的过程中储存
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name + ".txt")));
		long t1 = System.currentTimeMillis();
		int count = 0;
		// 遍历所有的url
		for (Element element : select) {
			count++;
			long t2 = System.currentTimeMillis();
			// 获取每一个章节的内容
			String link = element.attr("href");
			Document chapter = getDocument(link);
			Elements h1 = chapter.select("h1");
			// 取出标题
			String title = h1.text();
			// <dd id="contents" style="font-size: 22px; color: rgb(0, 0, 0);">
			Elements contents = chapter.select("[id=contents]");
			// 取出内容
			String content = contents.text();
			// 储存
			bw.write(title);
			bw.newLine();
			bw.write(content);
			bw.newLine();
			bw.flush();
			long t3 = System.currentTimeMillis();
			System.out.println("抓取" + count + ",耗时" + (t3 - t2) + "毫秒");
		}
		bw.close();
		long t5 = System.currentTimeMillis();
		System.out.println("总共抓到章节数:" + select.size() + "耗时:" + (t5 - t1) / 1000 + "秒");

	}

}
