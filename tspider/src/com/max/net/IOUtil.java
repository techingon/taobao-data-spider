package com.max.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.star.cms.common.net.JSONUtil;

/**
 * 
 * @author yaohw
 */
public class IOUtil {
	private static Log log = LogFactory.getLog(IOUtil.class);
	private static HttpClient httpclient = null;

	private static HttpClient getHTTPClient(){
//		if(httpclient==null)
//		{
			HttpClientBuilder hb = HttpClientBuilder.create();
			httpclient = hb.build();	
//		}
		return httpclient;
	}
	public static String streamToString(InputStream is) throws UnsupportedEncodingException{
		return streamToString(is, "utf-8");
	}
	public static String streamToString(InputStream is,String charset) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,charset));//taobao utf-8  ali gbk
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new IllegalStateException("Read data from inputStream error", e);
		}
		try {
			reader.close();
			is.close();
		} catch (IOException e) {
			throw new IllegalStateException(
					"After inputStream parsing, io close error");
		}
		return sb.toString();
	}

	/**
	 * send post http request.
	 * 
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static HttpResponse httpPost(String url,
			List<NameValuePair> nameValuePairs) {

		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getHTTPClient().execute(httppost);
			return response;
		} catch (Exception e) {
			throw new IllegalStateException("HTTP Post error."+url, e);
		}
	}
	
	public static HttpResponse httpPost(String url,String param) {

		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new StringEntity(param));
			HttpResponse response = getHTTPClient().execute(httppost);
			return response;
		} catch (Exception e) {
			throw new IllegalStateException("HTTP Post error."+url, e);
		}
	}
	/**
	 * send post http request.
	 * 
	 * @param params  KV  V:String/JSON
	 * @param url
	 * @return JSON
	 */
	public static String httpPostToJSON(Map<String,Object> params,String url){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if(value instanceof String){
				nameValuePairs.add(new BasicNameValuePair(key,(String)value));
			}else{
				nameValuePairs.add(new BasicNameValuePair(key,JSONUtil.getJSON(value)));
			}
		}
		HttpResponse hr = IOUtil.httpPost(url, nameValuePairs);
		try {
			InputStream is = hr.getEntity().getContent();
			return IOUtil.streamToString(is);
		} catch (Exception e) {
			throw new IllegalStateException("Parse the data from http post error.", e);
		}
	}
	public static String httpPostToJSON(String url,Object param){
		HttpResponse hr = IOUtil.httpPost(url, JSONUtil.getJSON(param));
		try {
			InputStream is = hr.getEntity().getContent();
			return IOUtil.streamToString(is);
		} catch (Exception e) {
			throw new IllegalStateException("Parse the data from http post error.", e);
		}
	}

	public static String httpGetToJson(String url,String charset) throws UnsupportedEncodingException{
		return streamToString(httpGet(url),charset);
	}
	/**
	 * send get http request.
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream httpGet(String url) {
//		if (httpclient == null) {
//			httpclient = new DefaultHttpClient();
//		}
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = getHTTPClient().execute(httpGet);
//			response = httpclient.execute(httpGet);
			log.trace("HTTP GET from "+url);
			log.trace("HTTP GET responseCode "+response.getStatusLine().getStatusCode());
			return response.getEntity().getContent();
		} catch (Exception e) {
			throw new IllegalStateException("Http Get error", e);
		}
	}

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024*10;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static byte[] streamToByteArray(InputStream is) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024*10];
			int n = is.read(buffer);
			while (n > -1) {
			    bos.write(buffer,0,n);
			    n = is.read(buffer);
			}
			bos.flush();
		} catch (IOException e) {
			throw new IllegalStateException("inputstream to byte[] error.", e);
		}
		return bos.toByteArray();
	}
	
	public static void ifExistAndWrite(String filePath, String fileName,
			InputStream is) {
		if ((new File(filePath + fileName)).exists() == false) {
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdir();
			}
			try {
				OutputStream os = new FileOutputStream(filePath + fileName);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}

				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				throw new IllegalStateException(
						"io close error",e);
			}
		}
	}

}
