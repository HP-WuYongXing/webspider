package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.CompanyInfo;
import com.oliver.models.Manager;
import com.oliver.models.Participation;
import com.oliver.models.Stock;
import com.oliver.models.StockInfo;
import com.oliver.service.impl.CompanyInfoService;
import com.oliver.service.impl.ManagerService;
import com.oliver.service.impl.ParticipationService;
import com.oliver.service.impl.StockInfoService;
import com.oliver.context.AppContext;
import com.oliver.context.BeanLocator;
import com.oliver.http.DataUtils;

public class StockCompanyCommonInfoSpider {
	
	private static final String PAGE_URL="http://stockdata.stock.hexun.com/gszl/sstockCode.shtml";
	private static final String GG_JSON_URL ="http://stockdata.stock.hexun.com/gszl/data/jsondata/ggml.ashx?no=stockCode&&type=003&count=1000&titType=null&page=1&callback=hxbase_json25";
	private static final String DS_JSON_URL ="http://stockdata.stock.hexun.com/gszl/data/jsondata/ggml.ashx?no=stockCode&type=001&count=1000&titType=null&page=1&callback=hxbase_json26";
	private static final String JS_JSON_URL ="http://stockdata.stock.hexun.com/gszl/data/jsondata/ggml.ashx?no=stockCode&type=002&count=1000&titType=null&page=1&callback=hxbase_json27";
	private static final String PARTICIPATION_URL="http://stockdata.stock.hexun.com/gszl/zbyz-stockCode.shtml";
	private static final String PARTICIPATION_JSON_URL="http://stockdata.stock.hexun.com/gszl/data/jsondata/zbyzkgcg.ashx?no=stockCode&count=1000&date=timeStr&titType=null&page=1&callback=hxbase_json30";
	private static final int MANAGER_KIND_GG=1001;
	private static final int MANAGER_KIND_DS=1002;
	private static final int MANAGER_KIND_JS=1003;
	//private static AbstractApplicationContext context = AppContext.getContext();
	
	public void refreshCompanyInfo(Stock stock){
		CompanyInfo info = getCompanyInfo(stock);
		if(info==null)return;
		String stockCode =stock.getCode();
		if(checkIfExist(stockCode)){
			updateCompanyInfo(info);
		}else{
			saveCompanyInfo(info);
		}
		
		StockInfo stockInfo = info.getStockInfo();
		stockInfo.setCompanyId(info.getId());
		saveStockInfo(stockInfo);
		List<Manager> managerList = info.getManagerList();
		
		if (managerList != null) {
			for (Manager m : managerList) {
				m.setCompanyId(info.getId());
				saveManager(m);
			}
		}
		
		List<Participation> participationList = info.getParticipationList();
		if(participationList!=null){
			for(Participation p:participationList){
				p.setCompanyId(info.getId());
				saveParticipation(p);
			}
		}
		
	}
	
	private boolean checkIfExist(String stockCode){
		CompanyInfoService ciService = (CompanyInfoService)BeanLocator.getBean("companyInfoService");
		CompanyInfo existInfo = ciService.getByStockCode(stockCode);
		return existInfo==null;
	}
	
	
	private void updateCompanyInfo(CompanyInfo info) {
		CompanyInfoService ciService = (CompanyInfoService)BeanLocator.getBean("companyInfoService");
		
	}


	private void saveParticipation(Participation p){
		ParticipationService service = (ParticipationService)BeanLocator.getBean("participationService");
		service.addParticipation(p);
	}
	
	private void saveManager(Manager m){
		ManagerService service = (ManagerService)BeanLocator.getBean("managerService");
		service.addManager(m);
	}
	
	
	private void saveStockInfo(StockInfo stockInfo) {
		StockInfoService service = (StockInfoService)BeanLocator.getBean("stockInfoService");
		service.addStockInfo(stockInfo);
	}

	public void saveCompanyInfo(CompanyInfo info){
		CompanyInfoService service =(CompanyInfoService)BeanLocator.getBean("companyInfoService");
		service.addCompanyInfo(info);
	}
	
	
    public CompanyInfo getCompanyInfo (Stock stock){
    	String url = PAGE_URL.replace("stockCode", stock.getCode()+"");
    	System.out.println("common file url: "+url);
		Document doc = DataUtils.doGet(url);
		if(doc==null)return null;
    	CompanyInfo ci = new CompanyInfo();
    	Element el_box_left = doc.getElementsByClass("xin_boxl").get(0);
    	Elements el_box_left_list = el_box_left.getElementsByClass("xinx_l");
    	Element el_table1 = el_box_left_list.get(0);
    	Elements el_table1_tr_list = el_table1.getElementsByTag("tr");
    	int size1 = el_table1_tr_list.size();
    	for(int i=0;i<size1;i++){
    		Element el_tr = el_table1_tr_list.get(i);
    		switch(i){
    		case 0:	{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
    			String v = el_td.text();
    			ci.setAbbreviation(v);
    			}
    			break;
    		case 1: {
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setStockCode(v);
    			}break;
    		case 2:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setFullName(v);
    			}break;
    		case 3:/*英文名称*/break;
    		case 4:/*曾用名*/break;
    		case 5:{
    			 Element el_td =el_tr.getElementsByTag("td").get(1);
				 String v = el_td.text();
				 ci.setStartDate(v);
    			}break;
    		case 6:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setBusiness(v);
    		    }break;
    		case 7:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setConception(v);
    		    }break;
    		case 8:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setCompanyAddr(v);
    		}break;
    		case 9:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setRepresentative(v);
    		}break;
    		case 11:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
				ci.setEnquiryAgency(v);
    		}break;
    	  }
    	
    	}
    	
    	Element el_table2 = el_box_left_list.get(1);
    	Elements el_table2_tr_list = el_table2.getElementsByTag("tr");
    	int size2 = el_table2_tr_list.size();
    	for(int i=0;i<size2;i++){
    		Element el_tr = el_table2_tr_list.get(i);
    		switch(i){
    		  case 0:{
    			Element el_td =el_tr.getElementsByTag("td").get(1);
  				String v = el_td.getElementsByTag("a").get(0).text();
  				v = v.replace(",", "");
  				ci.setRegAssets(Float.valueOf(v));
    		  }break;
    		  case 1:{
    			  Element el_td =el_tr.getElementsByTag("td").get(1);
    			  String v = el_td.text();
    			  ci.setRegAddress(v);
    		  }break;
    		  case 2:break;
    		  case 3:{
    			  Element el_td =el_tr.getElementsByTag("td").get(1);
    			  String v = el_td.text();
    			  ci.setOfficeAddress(v);
    		  }break;
    		}
    	}
    	
    	Element el_table3 = el_box_left_list.get(2);
    	Elements el_table3_p_list = el_table3.getElementsByTag("p");
        if(el_table3_p_list.size()!=0){
        	String v = el_table3_p_list.get(0).text();
        	ci.setBusinessScope(v);
        }
        
        Element el_r_table  = doc.getElementsByClass("xin_boxr").get(0);
        Elements el_box_right_list = el_r_table.getElementsByClass("xinx_l");
        Element el_r_table2 = el_box_right_list.get(1);
        Elements el_r_table2_tr_list = el_r_table2.getElementsByTag("tr");
        int size3 = el_r_table2_tr_list.size();
        for(int i=0;i<size3;i++){
        	Element el_tr = el_r_table2_tr_list.get(i);
        	switch (i) {
			case 1:{
				 Element el_td =el_tr.getElementsByTag("td").get(1);
				 String v = el_td.text();
   			  	 ci.setCompanyFax(v);
			   }break;
			case 2:{
				Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	ci.setEmail(v);
			   }break;
			case 3:{
				Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	ci.setWebsite(v);
				}break;
			case 4:{
				Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	ci.setContacts(v);
				}break;
			case 5:{
				Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	ci.setZipCode(v);
				}break;
			}
        }
        
        Element el_r_table3 = el_box_right_list.get(2);
        Elements el_r_table3_p_list = el_r_table3.getElementsByTag("p");
        if(el_table3_p_list.size()!=0){
        	String v = el_r_table3_p_list.get(0).text();
        	ci.setCompanyInfo(v);
        }
        
        Element el_r_table1 =el_box_right_list.get(0);
        Elements el_r_table1_tr_list = el_r_table1.getElementsByTag("tr");
        int size4 = el_r_table1_tr_list.size();
        StockInfo si = new StockInfo();
        for(int i=0;i<size4;i++){
        	Element el_tr = el_r_table1_tr_list.get(i);
        	switch(i){
        	case 0:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	si.setIssuingDate(v);
        		}break;
        	case 1:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	si.setListingDate(v);
        		}break;
        	case 2:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	si.setExchange(v);
        		}break;
        	case 3:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
				String v = el_td.text();
  			  	si.setCategory(v);
        		}break;
        	case 4:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
        		Element el_td_a = el_td.getElementsByTag("a").get(0);
				String v = el_td_a.text().replace(",","");
				System.out.println("circulating stock: "+v);
  			  	si.setCirculatingStock(Double.valueOf(v));
        	    }break;
        	case 5:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
        		Element el_td_a = el_td.getElementsByTag("a").get(0);
				String v = el_td_a.text().replace(",","");
				System.out.println("total stock: "+v);
  			  	si.setTotalStock(Double.valueOf(v));
        	   }break;
        	case 6:{
        		Element el_td =el_tr.getElementsByTag("td").get(1);
        		String v = el_td.text();
        		si.setConsignee(v);
        	   }break;
          }
        }
        ci.setStockInfo(si);
        List<Manager> managerList = getManagers(stock);
        ci.setManagerList(managerList);
        
        List<Participation> participationList = getParticipationList(stock);
        if(participationList!=null)ci.setParticipationList(participationList);
      return ci;
    }
    
    private List<Participation> getParticipationList(Stock stock) {
    	List<Participation> pList = new ArrayList<Participation>();
    	String url = PARTICIPATION_URL.replace("stockCode", stock.getCode()+"");
    	System.out.println("participation url: "+url);
		Document doc = DataUtils.doGet(url);
		Element el_select = doc.getElementById("select_span1");
		if(el_select==null)return null;
		String timeStr = el_select.text();
		timeStr = timeStr.replace("年", "-").replace("月", "-").replace("日","");
		String jsonUrl = PARTICIPATION_JSON_URL.replace("timeStr", timeStr).replace("stockCode",stock.getCode()+"");
		String json = DataUtils.readJsonString(jsonUrl);
		int start = json.indexOf("{");
		int end  = json.lastIndexOf("}");
		json =json.substring(start,end+1);
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONArray jArr = jsonObj.getJSONArray("list");
			int len = jArr.length();
			for(int i=0;i<len;i++){
				Participation p = new Participation();
				JSONObject obj = jArr.getJSONObject(i);
				String companyStr = obj.getString("Issuingmode");
				System.out.println("Issuingmode: "+companyStr);
				p.setCompanyName(companyStr);
				String moneyStr = obj.getString("panebt").replace(",", "").trim();
				System.out.println("panebt: "+moneyStr);
				p.setMoney(Float.valueOf(moneyStr));
				String proportion = obj.getString("Stockdate").replace("%", "").trim();
				System.out.println("Stockdate: "+proportion);
				try{
					p.setProportion(Float.valueOf(proportion));
				}catch(NumberFormatException e){
					p.setProportion(-1f);
				}
				String business = obj.getString("Stockdisc");
				System.out.println("Stockdisc: "+business);
				p.setBusiness(business);
				pList.add(p);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pList;
	}

	private List<Manager> getManagers(Stock stock){
    	List<Manager> mList = new ArrayList<Manager>();
    	String url_gg = GG_JSON_URL.replace("stockCode",stock.getCode()+"");
    	analysJson(mList,url_gg,MANAGER_KIND_GG);
    	
    	String url_ds = DS_JSON_URL.replace("stockCode",stock.getCode()+"");
    	analysJson(mList,url_ds,MANAGER_KIND_DS);
    	
    	String url_js = JS_JSON_URL.replace("stockCode",stock.getCode()+"");
    	analysJson(mList,url_js,MANAGER_KIND_JS);
   
    	return mList;
    }

	private void analysJson(List<Manager> mList,String url,int jobType) {
		String json = DataUtils.readJsonString(url);
    	int left = json.indexOf("{");
    	int right = json.lastIndexOf("}");
    	json = json.substring(left,right+1);
    	try {
			JSONObject jsonObj = new JSONObject(json);
			JSONArray jArr = jsonObj.getJSONArray("list");
			int len = jArr.length();
			for(int i=0;i<len;i++){
				Manager m = new Manager();
				JSONObject mj = jArr.getJSONObject(i);
				String name = mj.getString("panelrate");
				System.out.println("panelrate:"+name);
				m.setName(name);
				String job = mj.getString("industry");
				System.out.println("industry:"+job);
				m.setJobName(job);
			    String onDuty = mj.getString("Number");
			    System.out.println("Number:"+onDuty);
			    m.setTermDay(onDuty);
			    String dismiss = mj.getString("Numdate");
			    System.out.println("Numdate:"+dismiss);
			    m.setDismissDay(dismiss);
			    String education = mj.getString("Stockdisc");
			    System.out.println("Stockdisc:"+education);
			    m.setEducation(education);
			    m.setJobType(jobType);
			    mList.add(m);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
