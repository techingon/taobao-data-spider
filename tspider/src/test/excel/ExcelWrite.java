package test.excel;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import test.ali.ItemAli;
import test.taobao.Auction;

public class ExcelWrite {
	HSSFWorkbook wb;

	public ExcelWrite() {
		wb = new HSSFWorkbook();
	}

	public Boolean writeFromAli(String filePath, List<ItemAli> taobaoList,
			String sheetName) {
		if(sheet==null){
			sheet = wb.createSheet("data");// 添加sheet
		}
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 指定单元格居中对齐
		createRow(sheet, 0, style, "厂家","产品名称", "90天销量", "90天销售笔数", "店家", "省份","城市",
				"URL", "批量销售", "批量价格");

		
		for (int i = 0; i < taobaoList.size(); i++) {
			ItemAli auction = taobaoList.get(i);

			if (!auction.getName().contains(sheetName.split(" ")[0])) {
				System.out.println(sheetName + "--not in---"
						+ auction.getName());
				continue;
			}
			String[] ps = (auction.getShopPosition()==null?"":auction.getShopPosition()).split(" ");
			Object[] values = new Object[7 + auction.getSegments().size() * 2];
			int n = 0;
			values[n++] = sheetName.split(" ")[1];
			values[n++] = auction.getName();
			values[n++] = auction.getCount90d();
			values[n++] = auction.getTrans90d();
			values[n++] = auction.getShopName();
			values[n++] = ps[0];
			values[n++] = ps.length>1?ps[1]:ps[0];
			values[n++] = auction.getUrl();
			for (int j = 0; j < auction.getSegments().size(); j++) {
				values[n++] = auction.getSegments().get(j);
				values[n++] = auction.getPrices().get(j);
			}
			createRow(sheet, row++, style, values);
		}
		// 导出文件
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private HSSFSheet sheet = null;
	private int row = 1;
	public Boolean write(String filePath, List<Auction> taobaoList,
			String sheetName) {

		if(sheet==null){
			sheet = wb.createSheet("data");// 添加sheet
		}
		// 表格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 指定单元格居中对齐
		// // 边框
		// style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		// style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		// style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		// style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		// //设置字体
		// HSSFFont f = wb.createFont();
		// f.setFontHeightInPoints((short)10);
		// f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// style.setFont(f);
		// //设置列宽
		// sheet.setColumnWidth((short)0, (short)9600);
		// sheet.setColumnWidth((short)1, (short)4000);
		// sheet.setColumnWidth((short)2, (short)8000);
		// sheet.setColumnWidth((short)3, (short)8000);

		// 在索引0的位置创建第一行

		createRow(sheet, 0, style, "厂家","产品名称","商品名称", "价格", "月销量", "月销售笔数","店家", "地区", "产品唯一标识",
				"产品地址", "产品图片");

		
		for (int i = 0; i < taobaoList.size(); i++) {
			Auction auction = taobaoList.get(i);

			if (!auction.getRaw_title().contains(sheetName.split(" ")[0])) {
				System.out.println(sheetName + "--not in---"
						+ auction.getRaw_title());
				continue;
			}
			String countMonthy = auction.getSalesMonthly();
			String confirmGoodsMonthy = auction.getConfirmGoods();
			String price_str = auction.getView_price();
			Object coun = countMonthy;
			Object confirmGoods = confirmGoodsMonthy;
			Object price = price_str;
			try {
				coun = Integer.parseInt(countMonthy);
				confirmGoods = Integer.parseInt(confirmGoodsMonthy);
				price = Double.parseDouble(price_str);
			} catch (Exception e) {
			}
			createRow(sheet, row++, style, sheetName.split(" ")[1],sheetName.split(" ")[0],auction.getRaw_title(), price, coun,confirmGoods,
					auction.getNick(), auction.getItem_loc(), auction.getNid(),
					auction.getDetail_url(), auction.getPic_url());
		}
		// 导出文件
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void createRow(HSSFSheet sheet, int index, HSSFCellStyle style,
			Object... values) {
		HSSFRow row = sheet.createRow(index);
		int i = 0;
		for (Object v : values) {
			v = (v == null ? "--" : v);
			HSSFCell cell = row.createCell(i++);
			if (v instanceof Number) {
				cell.setCellValue(((Number) v).doubleValue());
			} else if (v instanceof Boolean) {
				cell.setCellValue((Boolean) v);
			} else {
				cell.setCellValue(v.toString());
			}
			cell.setCellStyle(style);
		}

	}

	public static void main(String[] args) throws Exception {

	}
}
