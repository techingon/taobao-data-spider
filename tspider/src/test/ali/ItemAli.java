package test.ali;

import java.util.ArrayList;
import java.util.List;

public class ItemAli {
	private String shopMemberID;
	private String shopName;
	private String shopPosition;
	private String name;
	private List<String> segments = new ArrayList<String>();
	private List<Float> prices = new ArrayList<Float>();
	
	private int trans90d;
	
	private int count90d;
	
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSegments() {
		return segments;
	}

	public void setSegments(List<String> segments) {
		this.segments = segments;
	}

	public List<Float> getPrices() {
		return prices;
	}

	public void setPrices(List<Float> prices) {
		this.prices = prices;
	}

	public int getTrans90d() {
		return trans90d;
	}

	public void setTrans90d(int trans90d) {
		this.trans90d = trans90d;
	}

	public int getCount90d() {
		return count90d;
	}

	public void setCount90d(int count90d) {
		this.count90d = count90d;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShopMemberID() {
		return shopMemberID;
	}

	public void setShopMemberID(String shopMemberID) {
		this.shopMemberID = shopMemberID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopPosition() {
		return shopPosition;
	}

	public void setShopPosition(String shopPosition) {
		this.shopPosition = shopPosition;
	}
	
	

}
