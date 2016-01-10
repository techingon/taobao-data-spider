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
		String fileName = "/Users/yaohongwei/Public/百度云同步盘/taobao"
				+ System.currentTimeMillis()+ ".xls";
		ExcelWrite we = new ExcelWrite();
		String names[] = {/*"阿克白 巴斯夫", "尊保 巴斯夫", */"百泰 巴斯夫", "凯特 巴斯夫", "凯润 巴斯夫",
				"凯泽 巴斯夫", "翠泽 巴斯夫", "成标 巴斯夫", "品润 巴斯夫", "欧宝 巴斯夫", "欧博 巴斯夫",
				"翠贝 巴斯夫", "德劲 巴斯夫", "苞卫 巴斯夫", "谷欢 巴斯夫", "排草丹 巴斯夫", "巴佰金 巴斯夫",
				"百垄通 巴斯夫", "田普 巴斯夫", "施田补 巴斯夫", "艾法迪 巴斯夫", "卡死克 巴斯夫", "除尽 巴斯夫",
				"帕力特 巴斯夫", "灭百可 巴斯夫", "除芽通 巴斯夫", "扑力猛 巴斯夫", "健武 巴斯夫",
				"健达 巴斯夫", "爱丽欧 巴斯夫",
				
				
				"阿米多彩 先正达", "阿米妙收 先正达", "阿米西达 先正达",
				"爱苗 先正达", "和瑞 先正达", "达科宁 先正达", "菲格 先正达", "杨彩 先正达", "瑞凡 先正达",
				"金雷多米尔 先正达", "杀毒矾 先正达", "山德生 先正达", "世高 先正达", "势克 先正达",
				"敌力脱 先正达", "秀特 先正达", "爱秀 先正达", "丰杰 先正达", "耕杰 先正达", "千层红 先正达",
				"金都尔 先正达", "英斧 先正达", "虎威 先正达", "吉旺草甘膦 先正达", "克瑞踪 先正达",
				"克芜踪 先正达", "克草快 先正达", "立收谷 先正达", "瑞飞特 先正达", "扫氟特 先正达",
				"麦极 先正达", "泰草达 先正达", "大能 先正达", "阿克泰 先正达", "阿立卡 先正达", "福奇 先正达",
				"卉福 先正达", "库龙 先正达", "度锐 先正达", "劲彪 先正达", "功夫 先正达", "美除 先正达",
				"顶峰 先正达", "福戈 先正达", "宝剑 先正达", "抑芽敏 先正达", "亮盾 先正达", "金阿普隆 先正达",
				"适乐时 先正达", "艾科顿 先正达", "锐胜 先正达", "适麦丹 先正达", "敌萎丹 先正达", "宝路 先正达",
				"满适金 先正达", "锐勇 先正达", "金雷 先正达", "迈舒平 先正达", "绿妃 先正达", "禾大壮 先正达",
				"瑞镇 先正达", "抹绿 先正达", "拔绿 先正达", "绿妃 先正达",
				
				"阿砣 杜邦", "万兴 杜邦", "克露 杜邦",
				"易保 杜邦", "抑快净 杜邦", "可杀得2000 杜邦", "可杀得3000 杜邦", "福星 杜邦",
				"爱将 杜邦", "稻将 杜邦", "巨星 杜邦", "农得时 杜邦", "欧特 杜邦", "新得力 杜邦",
				"宝成 杜邦", "威农 杜邦", "奥得腾 杜邦", "安打 杜邦", "倍内威 杜邦", "康宽 杜邦",
				"凯恩 杜邦", "万灵 杜邦", "普尊 杜邦", "稻艳 陶氏", "大生富 陶氏", "大生 陶氏",
				"大生绿 陶氏", "应得 陶氏", "卡拉生 陶氏", "赛深 陶氏", "赛纳松 陶氏", "信生 陶氏",
				"陶斯安 陶氏", "陶斯仙 陶氏", "满秋 陶氏", "千金 陶氏", "稻欢 陶氏", "普瑞麦 陶氏",
				"麦喜 陶氏", "稻杰 陶氏", "迈士通 陶氏", "盖草能 陶氏", "盖灌能 陶氏", "果尔 陶氏",
				"好麦农 陶氏", "开路生 陶氏", "龙拳 陶氏", "麦施达 陶氏", "农地隆 陶氏", "豆杰 陶氏",
				"优先 陶氏", "稻喜 陶氏", "使它隆 陶氏", "陶斯果 陶氏", "陶斯杰 陶氏", "可立施 陶氏",
				"艾绿士 陶氏", "乐斯本 陶氏", "农地乐 陶氏", "雷通 陶氏", "喜斯本 陶氏", "特福力 陶氏",
				"阔草清 陶氏", "菜喜 陶氏", "立可施 陶氏", "康佛 陶氏", "催杀 陶氏", 
				
				"卓旺 诺普信",
				"势赢 诺普信", "扫线宝 诺普信", "锐宁 诺普信", "金极冠 诺普信", "金爱维丁 诺普信", "格猛 诺普信",
				"高猛 诺普信", "跟爽 诺普信", "甲龙 诺普信", "晶玛 诺普信", "瑞功 诺普信", "秀库 诺普信",
				"银典 诺普信", "越众 诺普信", "锄当家 诺普信", "强势 诺普信", "克蛾宝 诺普信", "宝飞龙 诺普信",
				"贝萃 诺普信", "标冠 诺普信", "粉翠 诺普信", "皇令 诺普信", "品顶 诺普信", "浓稠 诺普信",
				"双巧 诺普信", "顶妙 诺普信", "好施保 诺普信", "高威严 诺普信", "冲尔壮 诺普信", "根聚地 诺普信",
				"好鲜收 诺普信", "金葆丰 诺普信", "麦伴侣 诺普信", "金玉捷 诺普信", "斗虎 诺普信", "玉典 诺普信",
				
				
				"安泰生 拜耳","百理通 拜耳","富力库 拜耳","好力克 拜耳","银法利 拜耳","露娜森 拜耳","霉多克 拜耳",
				"拿敌稳 拜耳","普力克 拜耳","施佳乐 拜耳","阿罗津 拜耳","保试达 拜耳","骠马 拜耳","农思它 拜耳",
				"阔世玛 拜耳","世玛 拜耳","稻思达 拜耳","威霸 拜耳","艾美乐 拜耳","保得 拜耳","康福多 拜耳",
				"酷毕 拜耳","稻腾 拜耳","敌杀死 拜耳","螨危 拜耳","亩旺特 拜耳","脱吐隆 拜耳","高巧 拜耳",
				"立克秀 拜耳","稳特 拜耳","露娜润 拜耳","路富达 拜耳","宝试达 拜耳"
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
