/**
 * 
 */
package com.vanstone.weixin.client;


/**
 * @author shipeng
 */
public class HttpsClientMainApp {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
//	public static void main(String[] args) throws ClientProtocolException, IOException {
//		HttpsClientMainApp app = new HttpsClientMainApp();
//		DefaultHttpClient httpClient = (DefaultHttpClient)app.createHttpClient();
//		CookieStore cookieStore = httpClient.getCookieStore();
//		System.out.println(cookieStore);
//		DefaultHttpClient httpClient2 = (DefaultHttpClient)app.createHttpClient();
//		httpClient2.setCookieStore(cookieStore);
//		
//		HttpGet httpGet = new HttpGet("https://vpn.chinawch.cn/web/1/http/0/allergy.chinawch.cn:8080/service/question/save-baby");
//		httpGet.getParams().setParameter("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows 98)");
//		httpGet.addHeader("Referer", "https://vpn.chinawch.cn");
//		HttpResponse httpResponse = httpClient2.execute(httpGet);
//		if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
//			System.out.println(content);
//		}else{
//			System.out.println("连接失败");
//		}
//	}

//	/**
//	 * 获取HttpClient
//	 * @return
//	 */
//	public HttpClient createHttpClient() {
//		HttpParams params = new BasicHttpParams();
//		params.setParameter("charset", "utf-8");
//		HttpClient httpClient = new DefaultHttpClient(params);
//		httpsWrapClient(httpClient);
//		return httpClient;
//	}
	
//	public void httpsWrapClient(HttpClient httpclient) {
//		try {
//			SSLContext sslcontext = SSLContext.getInstance("TLS");
//			sslcontext.init(null, new TrustManager[] { new DefaultX509TrustManager() }, null);
//			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
//			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//			Scheme https = new Scheme("https", sf, 443);
//			httpclient.getConnectionManager().getSchemeRegistry().register(https);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void uploadNewsBean() {
//		
//	}
}
