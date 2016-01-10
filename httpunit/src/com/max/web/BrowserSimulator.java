package com.max.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BrowserSimulator {
	static Log log = LogFactory.getLog(BrowserSimulator.class);
	static final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	public static void main(String[] args) {
		try {
			final HtmlPage page = webClient
					.getPage("https://item.taobao.com/item.htm?id=40597168510&ns=1&abbucket=0#detail");
			//http://h5.m.taobao.com/awp/core/detail.htm?id=40597168510&ns=1&abbucket=0#detail
			log.info("Content:"+page.asText());
		} catch (Exception e) {
			log.error("",e);
		}
	}
	
	public static String parse(String url) {
		try {
			final HtmlPage page = webClient
					.getPage(url);
			//http://h5.m.taobao.com/awp/core/detail.htm?id=40597168510&ns=1&abbucket=0#detail
//			log.info("Content:"+page.asText());
			Thread.sleep(4000);
			String result = page.asText();
			page.cleanUp();
			return result;
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}

}
