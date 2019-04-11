package cn.nalipiaoxiang.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MyUtils {

	public static Document getDocument(String url) throws Exception {
		CloseableHttpResponse response = execute(url);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			System.out.println("请求不成功,尝试再次请求.....第一次重试");
			response = execute(url);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("请求不成功,尝试再次请求.....第二次重试");
				response = execute(url);
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					System.out.println("请求不成功,尝试再次请求.....第三次重试");
					response = execute(url);
					statusCode = response.getStatusLine().getStatusCode();
				}
				System.out.println("请求不成功,不再尝试");
				return Jsoup.parse("<html></html>");
			}

		} else {
			String html = EntityUtils.toString(response.getEntity(), "utf-8");
			Document document = Jsoup.parse(html);
			return document;
		}
		return Jsoup.parse("<html></html>");
	}

	private static CloseableHttpResponse execute(String url) throws IOException, ClientProtocolException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		return response;
	}

}
