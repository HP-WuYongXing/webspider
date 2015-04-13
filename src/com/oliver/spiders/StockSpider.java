package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.CompanyInfo;
import com.oliver.models.Stock;
import com.oliver.service.impl.CompanyInfoService;
import com.oliver.service.impl.StockService;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.http.DataUtils;

public class StockSpider {
	
	private String STOCK_URL="http://quote.stockstar.com/stock/stock_index.htm";
	//private static AbstractApplicationContext context = AppContext.getContext();
	//private static BeanLocator beanLocator = BeanLocator.getInstance();
	
	public void executRefreshStock(){
		
		List<Stock> list = getStocks();
		if(list!=null&&list.size()!=0){
			deleteAllStock();
			saveStocks(list);
		}
//		StockCompanyCommonInfoSpider spider = new StockCompanyCommonInfoSpider();
//		for(int i=0;i<list.size();i++){
//			spider.refreshCompanyInfo(list.get(i));
//		}
//		
//		List<Stock> noCompanyInfoList = checkNoCompanyInfo(list);
//		if(noCompanyInfoList.size()!=0){
//			reloadCompanyInfo(noCompanyInfoList);
//		}
	}
	
	
	
private void reloadCompanyInfo(List<Stock> noCompanyInfoList) {
		int cnt=0;
		CompanyInfoService ciService = (CompanyInfoService)BeanLocator.getBean("companyInfoService");
		List<Stock> noList = new ArrayList<Stock>();
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			for(Stock s:noCompanyInfoList){
				CompanyInfo ci = ciService.getByStockId(s.getId());
				if(ci==null){
					noList.add(s);
				}
			}
			if(noList.size()==0)break;
			noCompanyInfoList = noList;
			cnt++;
		}
   }


private List<Stock> checkNoCompanyInfo(List<Stock> list) {
		CompanyInfoService ciService = (CompanyInfoService)BeanLocator.getBean("companyInfoService");
		List<Stock> noCompanyInfoList = new ArrayList<Stock>();
		for(Stock s:list){
			CompanyInfo ci= ciService.getByStockId(s.getId());
			if(ci==null){
				noCompanyInfoList.add(s);
			}
		}
		return noCompanyInfoList;
	}



private void deleteAllStock(){
	  StockService service = (StockService) BeanLocator.getBean("stockService");
	   service.deleteAll();
   }
	
	public List<Stock> getStocks(){
		List<Stock> stockList = new ArrayList<Stock>();
		Document doc = DataUtils.doGet(STOCK_URL);
		Element el_con_div =doc.getElementsByClass("seo_keywordsCon").get(0);
		for(int index=0;index<4;index++){
			Element el_list_div = el_con_div.getElementById("index_data_"+index);
			Elements el_list = el_list_div.getElementsByTag("li");
			int size = el_list.size();
			for(int i=0;i<size;i++){
				Stock st =new Stock();
				Element el_li = el_list.get(i);
				Element el_li_span = el_li.getElementsByTag("span").get(0);
				Element el_li_span_a = el_li_span.getElementsByTag("a").get(0);
				String str = el_li_span_a.text();
				st.setCode(str.trim());
				Element el_li_a = el_li.getElementsByTag("a").get(1);
				str = el_li_a.text();
				st.setName(str);
				stockList.add(st);
			}
		}
		return stockList;
	}
	
	private void saveStocks(List<Stock> stockList){
		 StockService service = (StockService) BeanLocator.getBean("stockService");
		for(Stock s:stockList){
			service.addStock(s);
		}
	}
}
