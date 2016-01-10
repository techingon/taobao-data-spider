package test.china;

import java.util.HashMap;
import java.util.Map;

import com.max.net.IOUtil;

public class TestMain {

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				"__VIEWSTATE",
				"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");
		params.put("__VIEWSTATEGENERATOR", "52C08E86");
		params.put("__EVENTVALIDATION", "/wEWAwLu/I6wAwLzlKGwCgLvjry/BT1kFDCqJ/lFPJKN1QsjclM/aUTS");
		params.put("Text1", "%C3%C0%B9%FA%C4%AC%C8%FC%BC%BC%CA%F5%B9%AB%CB%BE");
		params.put("btnQuery", "%B2%E9%D1%AF");
		String json = IOUtil.httpPostToJSON(params, "http://www.chinapesticide.gov.cn/service/aspx/B2.aspx");
		System.out.println(json);
	}

}
