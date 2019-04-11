package cn.nalipiaoxiang.main;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;

public class Test03 {

	public static void main(String[] args) {
		 System.out.println("=======start========");
	        HttpGetRequest startUrl = new HttpGetRequest("https://www.23us.so/files/article/html/1/1319/index.html");
	        startUrl.setCharset("GBK");
	        GeccoEngine.create()
	                //Gecco搜索的包路径
	                .classpath("com.crawler.gecco")
	                //开始抓取的页面地址
	                .start(startUrl)
	                //开启几个爬虫线程
	                .thread(1)
	                //单个爬虫每次抓取完一个请求后的间隔时间
	                .interval(2000)
	                .run();
	}
	

}
