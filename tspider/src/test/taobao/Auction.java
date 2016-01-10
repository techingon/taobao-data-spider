package test.taobao;
/**
 * {
                        "comment_count": "74",
                        "nick": "hellohxh1",
                        "nid": "41855793801",
                        "pid": "-284746010",
                        "reserve_price": "120.00",
                        "title": "德国拜耳 <span class=H>露娜</span><span class=H>森</span> 进口杀菌剂100ml 靶斑病白粉病蔓枯病早疫叶霉病",
                        "user_id": "201913775",
                        "raw_title": "德国拜耳 露娜森 进口杀菌剂100ml 靶斑病白粉病蔓枯病早疫叶霉病",
                        "icon": [],
                        "i2iTags": {
                            "all": true,
                            "similar": {
                                "url": "/search?type=similar&app=i2i&rec_type=35&uniqpid=-284746010&nid=41855793801"
                            },
                            "dapei": 0,
                            "tongdian": true
                        },
                        "shopcard": {
                            "isTmall": 0,
                            "description": [
                                491,
                                1,
                                4014
                            ],
                            "service": [
                                492,
                                1,
                                3772
                            ],
                            "delivery": [
                                486,
                                0,
                                0
                            ],
                            "encryptedUserId": "UvCNYOFHGMGcb",
                            "sellerCredit": 7,
                            "totalRate": 9915
                        },
                        "pic_url": "http://g.search1.alicdn.com/img/bao/uploaded/i4/i4/TB1nS00GFXXXXXDapXXXXXXXXXX_!!0-item_pic.jpg",
                        "item_loc": "浙江 杭州",
                        "detail_url": "http://item.taobao.com/item.htm?id=41855793801&ns=1&abbucket=0#detail",
                        "comment_url": "http://item.taobao.com/item.htm?id=41855793801&ns=1&abbucket=0&on_comment=1",
                        "view_sales": "85人付款",
                        "view_price": "115.00",
                        "view_fee": "0.00",
                        "shopLink": "http://store.taobao.com/shop/view_shop.htm?user_number_id=201913775"
                    }
 * @author yaohongwei
 *
 */
public class Auction {
	private String nick;
	private String nid;
	private String reserve_price;
	private String view_price;
	private String raw_title;
	private String view_sales;
	private String view_fee;
	private String detail_url;
	
	private String pic_url;
	
	private String item_loc;
	
	private String salesMonthly;
	
	private String confirmGoods;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getReserve_price() {
		return reserve_price;
	}

	public void setReserve_price(String reserve_price) {
		this.reserve_price = reserve_price;
	}

	public String getView_price() {
		return view_price;
	}

	public void setView_price(String view_price) {
		this.view_price = view_price;
	}

	public String getRaw_title() {
		return raw_title;
	}

	public void setRaw_title(String raw_title) {
		this.raw_title = raw_title;
	}

	public String getView_sales() {
		return view_sales;
	}

	public void setView_sales(String view_sales) {
		this.view_sales = view_sales;
	}

	public String getView_fee() {
		return view_fee;
	}

	public void setView_fee(String view_fee) {
		this.view_fee = view_fee;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getItem_loc() {
		return item_loc;
	}

	public void setItem_loc(String item_loc) {
		this.item_loc = item_loc;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getSalesMonthly() {
		return salesMonthly;
	}

	public void setSalesMonthly(String salesMonthly) {
		this.salesMonthly = salesMonthly;
	}

	public String getConfirmGoods() {
		return confirmGoods;
	}

	public void setConfirmGoods(String confirmGoods) {
		this.confirmGoods = confirmGoods;
	}

}
