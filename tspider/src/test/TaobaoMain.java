package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.excel.ExcelWrite;
import test.taobao.Auction;
import test.taobao.TaobaoSearchDTO;

import com.max.net.IOUtil;
import com.max.net.JSONUtil;
import com.max.web.BrowserSimulator;

public class TaobaoMain {

	private static final String RegexpForSearch = "g_page_config = (\\{.*\\});.*g_srp_loadCss\\(\\)";
	private static final String RegexpForItemCountURL = "apiItemInfo\\s*:\\s*'(//detailskip\\.taobao\\.com/json/ifq\\.htm[^']*)'";
	private static final String RegexpForItemCount = "quanity:(\\d*)";
	private static final String RegexpForItemCount_M = "月售(\\d*)";
	private static final String RegexpForItemConfirmGoodsItems = "confirmGoods:\\s*(\\d*)";

	public static void main(String[] args) throws InterruptedException {
		String fileName = "/Users/max/test/data_"
				+ System.currentTimeMillis()+ ".xls";
		ExcelWrite we = new ExcelWrite();
		String names[] = {/*"阿克白 巴斯夫", "尊保 巴斯夫", */"百泰 巴斯夫"
		};
		for (String name : names) {
			int page = 0;
			List<Auction> as_ = new ArrayList<Auction>();
			while (true) {
				TaobaoSearchDTO tsd = null;
				tsd = parse(name, page);
				List<Auction> as = null;
				if("show".equals(tsd.getMods().getTips().getStatus())){
					System.err.println(tsd.getMods().getTips().getData().getHtml());
					if(tsd.getMods().getTips().getData().getHtml().contains("抱歉！没有找到")){
						break;
					}else{
						System.err.println("go on...");
					}
				}
				try {
					as = tsd.getMods().getItemlist().getData().getAuctions();
					System.out.println(name + " items " + as.size());
				} catch (Exception e) {
					System.err.println(tsd + " " + e);
					break;
				}
				if (as == null || as.size() < 1) {
					break;
				}
				as_.addAll(as);//http://item.taobao.com/item.htm?id=35051514137&ns=1&abbucket=0#detail
				page += 44;
				Thread.sleep(200);
			}

			for (Auction auction : as_) {
				if (!auction.getRaw_title().contains(name.split(" ")[0])) {
					continue;
				}
				try {
					parseCount_M(name, auction);
				} catch (Exception e) {
					System.err.println("sales monthly " + e.getMessage());
					Thread.sleep(1000*60*10);
				}
				Thread.sleep(1000+(int)(Math.random()*2000));
			}
			boolean result = we.write(fileName, as_, name);
			System.out.println("Write Excel For +" + name + ":" + result);
			Thread.sleep(1000);
		}
		// Auction auction = new Auction();
		// auction.setDetail_url("http://item.taobao.com/item.htm?spm=a230r.1.14.4.MOjC27&id=37460957595&ns=1&abbucket=9#detail");

	}

	private static void parseCount_M(String key, Auction auction) throws UnsupportedEncodingException {
		String result = null;
		/**
		 * http://h5.m.taobao.com/awp/core/detail.htm?id=40597168510&ns=1&abbucket=0#detail
		 * https://item.taobao.com/item.htm?id=40597168510&ns=1&abbucket=0#detail
		 */
//		System.out.println("url:"+auction.getDetail_url());
//		String url = auction.getDetail_url();
//		if(!url.startsWith("http")){
//			url = "https:"+url;
//		}
		String url = "http://h5.m.taobao.com/awp/core/detail.htm?id="+auction.getNid()+"&ns=1&abbucket=0#detail";
		System.out.println("url:"+url);
		result = BrowserSimulator.parse(url);
//		result = IOUtil.httpGetToJson(url,"utf-8");
		Pattern p = Pattern.compile(RegexpForItemCount_M,
				Pattern.CASE_INSENSITIVE);
		Matcher m0 = p.matcher(result);
		if (m0.find()) {
			String mount = m0.group(1);
			System.out.println(key+" monthly sale:"+mount);
			auction.setSalesMonthly(mount);
		} else {
			System.err.println("Not find sales monthly of --- "
					+ auction.getRaw_title());
		}
	}
	
	private static void parseCount_PC(String key, Auction auction) throws UnsupportedEncodingException {
		String result = null;
		/**
		 * http://h5.m.taobao.com/awp/core/detail.htm?id=40597168510&ns=1&abbucket=0#detail
		 * https://item.taobao.com/item.htm?id=40597168510&ns=1&abbucket=0#detail
		 */
		System.out.println("url:"+auction.getDetail_url());
		String url = auction.getDetail_url();
		if(!url.startsWith("http")){
			url = "https:"+url;
		}
//		url = "http://h5.m.taobao.com/awp/core/detail.htm?id="+auction.getNid()+"&ns=1&abbucket=0#detail";
		System.out.println("url:"+url);
//		result = BrowserSimulator.parse(url);
		result = IOUtil.httpGetToJson(url,"gbk");
//		System.out.println(result);
		Pattern itemsPattern = Pattern.compile(RegexpForItemCountURL,
				Pattern.CASE_INSENSITIVE);
		Matcher m = itemsPattern.matcher(result);
		if (m.find()) {
			String json = m.group(1);
			 System.out.println("monthly count url:https:"+json);
			String r = IOUtil.httpGetToJson("https:"+json,"gbk");
			System.out.println(key + ":" + r);
			Pattern p = Pattern.compile(RegexpForItemCount,
					Pattern.CASE_INSENSITIVE);
			Matcher m0 = p.matcher(r);
			if (m0.find()) {
				auction.setSalesMonthly(m0.group(1));
			} else {
				System.err.println("Not find sales monthly of --- "
						+ auction.getRaw_title());
			}
			
			p = Pattern.compile(RegexpForItemConfirmGoodsItems,
					Pattern.CASE_INSENSITIVE);
			m0 = p.matcher(r);
			if (m0.find()) {
				auction.setConfirmGoods(m0.group(1));
			} else {
				System.err.println("Not find confirm goods count monthly of --- "
						+ auction.getRaw_title());
			}
		}
	}

	// private static void testTaobao() {
	// TaobaoClient client = new DefaultTaobaoClient(
	// "http://gw.api.taobao.com/router/rest", "23075866",
	// "3c14ccd9f385c469ad0e30221504be41");
	// TaeItemsListRequest req = new TaeItemsListRequest();
	// req.setFields("title,nick,price");
	// req.setNumIids("2345338438,294857333");
	// TaeItemsListResponse response;
	// try {
	// response = client.execute(req,
	// "610051149c54d6e95721c0c8d2ece612a6314d79885d6b7158515724");
	// System.out.println(response.getBody());
	// } catch (ApiException e) {
	// e.printStackTrace();
	// }
	// }

	private static TaobaoSearchDTO parse(String name, int page) {
		String result = "";
		try {
			result = IOUtil.httpGetToJson("https://s.taobao.com/search?q="
					+ URLEncoder.encode(name,"utf-8") + "&s=" + page,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Pattern itemsPattern = Pattern.compile(RegexpForSearch,
				Pattern.CASE_INSENSITIVE);
		Matcher m = itemsPattern.matcher(result);
		if (m.find()) {
			String json = m.group(1);
//			System.out.println("list:"+json);
			TaobaoSearchDTO tsd = JSONUtil.getFromJSON(json,
					TaobaoSearchDTO.class);
			System.out.println(name + " page:" + page + " json length:"
					+ json.length());
			return tsd;
		}

		System.err.println(name+" error response: "+result);
		return null;

	}
	
}
