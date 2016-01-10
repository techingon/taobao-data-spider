package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestMain {
	
	public static void main(String[] args) {
		testHTTP();
	}

	private static void testHTTP(){
		Document doc;
		try {
			 doc =
			 Jsoup.connect("http://detailskip.taobao.com/json/sib.htm?itemId=45354363026&sellerId=1709073651&p=1&rcid=50007216&sts=270274560,1170936092094889988,2130176,70373041374211&chnl=&price=760&shopId=&vd=1&skil=false&pf=1&al=false&ap=1&ss=0&free=0&st=1&ct=1&prior=1").get();
//			 doc = Jsoup.parse(result);
			 System.out.println(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
