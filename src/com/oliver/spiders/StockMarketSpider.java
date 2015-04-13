package com.oliver.spiders;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.Stock;
import com.oliver.models.StockMarket;
import com.oliver.service.impl.StockMarketService;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.context.AppContext;
import com.oliver.http.DataUtils;

public class StockMarketSpider {
	
	private static String STOCK_URL = "http://stocks.sina.cn/sh/";
	private static AbstractApplicationContext context = AppContext.getContext();
	
	public void refreshStockMarket(Stock stock){
		StockMarket sm = getStockMarket(stock);
		if(sm==null){
			sm = reloadStockMarket(stock);
		}
		saveStockMarket(sm);
	}
	
	
	private StockMarket reloadStockMarket(Stock stock) {
		int cnt=0;
		StockMarket sm=null;
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			sm = getStockMarket(stock);
			if(sm!=null)break;
			cnt++;
		}
		return sm;
	}


	private void saveStockMarket(StockMarket sm){
		if (sm != null) {
			StockMarketService service = (StockMarketService) context
					.getBean("StockMarketService");
			service.addStockMarket(sm);
		}
	}
	
	public StockMarket getStockMarket(Stock stock){
		StockMarket sm = new StockMarket();
		sm.setStockId(stock.getId());
		String urlStr = STOCK_URL+"?code=sh"+stock.getCode();
		System.out.println("urlStr: "+urlStr);
		Document doc = DataUtils.doGet(urlStr);
		if(doc==null)return null;
		Element el_stock_fifth_div = doc.getElementsByClass("stockfifth").get(0);
		Element el_stock_mc_list_ul = el_stock_fifth_div.getElementsByTag("ul").get(0);
		Elements el_stock_mc_list = el_stock_mc_list_ul.getElementsByTag("li");
		int size = el_stock_mc_list.size();
		for(int i=0;i<size;i++){
			Element el_stock_mc_li = el_stock_mc_list.get(i);
			if(i==0){
				Element el_mc_span1 = el_stock_mc_li.getElementsByTag("span").get(1);
				String str = el_mc_span1.text();
			    sm.setMc1(Float.valueOf(str.trim()));
			    Element el_mc_span2 = el_stock_mc_li.getElementsByTag("span").get(2);
			    str = el_mc_span2.text();
			    sm.setMc1Num(Integer.valueOf(str.trim()));
			}else if(i==1){
				Element el_mc_span1 = el_stock_mc_li.getElementsByTag("span").get(1);
				String str = el_mc_span1.text();
			    sm.setMc2(Float.valueOf(str.trim()));
			    Element el_mc_span2 = el_stock_mc_li.getElementsByTag("span").get(2);
			    str = el_mc_span2.text();
			    sm.setMc2Num(Integer.valueOf(str.trim()));
			}else if(i==2){
				Element el_mc_span1 = el_stock_mc_li.getElementsByTag("span").get(1);
				String str = el_mc_span1.text();
			    sm.setMc3(Float.valueOf(str.trim()));
			    Element el_mc_span2 = el_stock_mc_li.getElementsByTag("span").get(2);
			    str = el_mc_span2.text();
			    sm.setMc3Num(Integer.valueOf(str.trim()));
			}else if(i==3){
				Element el_mc_span1 = el_stock_mc_li.getElementsByTag("span").get(1);
				String str = el_mc_span1.text();
			    sm.setMc4(Float.valueOf(str.trim()));
			    Element el_mc_span2 = el_stock_mc_li.getElementsByTag("span").get(2);
			    str = el_mc_span2.text();
			    sm.setMc4Num(Integer.valueOf(str.trim()));
			}else if(i==4){
				Element el_mc_span1 = el_stock_mc_li.getElementsByTag("span").get(1);
				String str = el_mc_span1.text();
			    sm.setMc5(Float.valueOf(str.trim()));
			    Element el_mc_span2 = el_stock_mc_li.getElementsByTag("span").get(2);
			    str = el_mc_span2.text();
			    sm.setMc5Num(Integer.valueOf(str.trim()));
			}
		}
		
		
		Element el_stock_mr_list_ul = el_stock_fifth_div.getElementsByTag("ul").get(1);
		Elements el_stock_mr_list = el_stock_mr_list_ul.getElementsByTag("li");
		int size1 = el_stock_mc_list.size();
		for(int i=0;i<size1;i++){
			Element el_stock_mr_li = el_stock_mr_list.get(i);
			if(i==0){
				Element el_mr_span1 = el_stock_mr_li.getElementsByTag("span").get(1);
				String str = el_mr_span1.text();
			    sm.setMr1(Float.valueOf(str.trim()));
			    Element el_mr_span2 = el_stock_mr_li.getElementsByTag("span").get(2);
			    str = el_mr_span2.text();
			    sm.setMr1Num(Integer.valueOf(str.trim()));
			}else if(i==1){
				Element el_mr_span1 = el_stock_mr_li.getElementsByTag("span").get(1);
				String str = el_mr_span1.text();
			    sm.setMr2(Float.valueOf(str.trim()));
			    Element el_mr_span2 = el_stock_mr_li.getElementsByTag("span").get(2);
			    str = el_mr_span2.text();
			    sm.setMr2Num(Integer.valueOf(str.trim()));
			}else if(i==2){
				Element el_mr_span1 = el_stock_mr_li.getElementsByTag("span").get(1);
				String str = el_mr_span1.text();
			    sm.setMr3(Float.valueOf(str.trim()));
			    Element el_mr_span2 = el_stock_mr_li.getElementsByTag("span").get(2);
			    str = el_mr_span2.text();
			    sm.setMr3Num(Integer.valueOf(str.trim()));
			}else if(i==3){
				Element el_mr_span1 = el_stock_mr_li.getElementsByTag("span").get(1);
				String str = el_mr_span1.text();
			    sm.setMr4(Float.valueOf(str.trim()));
			    Element el_mr_span2 = el_stock_mr_li.getElementsByTag("span").get(2);
			    str = el_mr_span2.text();
			    sm.setMr4Num(Integer.valueOf(str.trim()));
			}else if(i==4){
				Element el_mr_span1 = el_stock_mr_li.getElementsByTag("span").get(1);
				String str = el_mr_span1.text();
			    sm.setMr5(Float.valueOf(str.trim()));
			    Element el_mr_span2 = el_stock_mr_li.getElementsByTag("span").get(2);
			    str = el_mr_span2.text();
			    sm.setMr5Num(Integer.valueOf(str.trim()));
			}
		}
		
		Elements el_stock_content_list = doc.getElementsByClass("stock_content");
		int size2 = el_stock_content_list.size();
		for(int i=0;i<size2;i++){
		   Element el_stock_content = el_stock_content_list.get(i);
		   Element el_stock_content_span = el_stock_content.getElementsByTag("span").get(1);
		   String str = el_stock_content_span.text();
		   switch(i){
		   case 0:sm.setJkp(Float.valueOf(str.trim()));
			   break;
		   case 1:sm.setZsp(Float.valueOf(str.trim()));
			   break;
		   case 2:sm.setZgj(Float.valueOf(str.trim()));
			   break;
		   case 3:sm.setZdj(Float.valueOf(str.trim()));
			   break;
		   case 4:sm.setZtj(Float.valueOf(str.trim()));
			   break;
		   case 5:sm.setDtj(Float.valueOf(str.trim()));
			   break;
		   case 6:sm.setHsl(Float.valueOf(getPercent(str.trim())));
			   break;
		   case 7:sm.setZf(Float.valueOf(getPercent(str.trim())));
			   break;
		   case 8:
			   if("".equals(str)){
				   sm.setSyl(-1f);
			   }else{
				   sm.setSyl(Float.valueOf(str.trim()));
			   }
			   break;
		   case 9:
			   if("".equals(str)){
				   sm.setSjl(-1f);
			   }else{
				   sm.setSjl(Float.valueOf(str.trim()));
			   }
			   break;
		   case 10:
			   sm.setCjl(getChengJiaoLiang(str.trim()));
			   break;
		   case 11:
			   sm.setCje(getMoneyFloat(str.trim()));
			   break;
		   case 12:
			   sm.setZsz(getMoneyFloat(str.trim()));
			   break;
		   case 13:
			   sm.setLtsz(getMoneyFloat(str.trim()));
			   break;
		   }
		}
		return sm;
	}

	private String getPercent(String str) {
	    int idx = str.indexOf("%");
	    String s = str.substring(0, idx);
		return s;
	}
	
	private int getChengJiaoLiang(String str){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();i++){
			char c= str.charAt(i);
			if(c=='.'||(c<='9'&&c>='0')){
				sb.append(c);
			}
		}
		
	    float f = Float.valueOf(sb.toString());
        if(str.indexOf("Íò")!=-1){
	    	f=f*10000;
	    }
        return (int)f;
	}
	
	private float getMoneyFloat(String str){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			if(c=='.'||(c<='9'&&c>='0')){
				sb.append(c);
			}
		}
		float f = Float.valueOf(sb.toString());
        if(str.indexOf("Íò")!=-1){
	    	f=f*10000;
	    }else if(str.indexOf("ÒÚ")!=-1){
	    	f=f*100000000;
	    }
        return f;
	}
	
}
