package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.AbstractApplicationContext;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.models.Stock;
import com.oliver.service.impl.NewsItemService;
import com.oliver.constants.ConstantsForCommon;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.context.AppContext;
import com.oliver.http.DataUtils;

public class StockNewsSpider{
	
	public static final String PAGE_URL="http://stock1.sina.cn/dpool/stock_new/v2/related_news.php?type=stock&code=stockCode&page=pageNum&vt=4";
	public static final String ROOT_URL="http://stock1.sina.cn/";
	public static final String PATTERN_URL="http://stock1.sina.cn/dpool/stock_new/v2/related_news.php";
	public boolean refreshClear=true;
	private static AbstractApplicationContext context =AppContext.getContext();
	
	public void refreshStockNews(Stock stock){
		if(this.refreshClear){
			NewsSpider.clearNewsItems(PATTERN_URL,ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NEWS);
		}
		List<NewsItem> titleList = null;
		titleList = getNewsItemList(stock);
		titleList = NewsSpider.getReverseList(titleList);
		System.out.println("save titleList...");
		if (titleList != null && titleList.size() != 0) {
			NewsSpider.saveNewsTitle(titleList,ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NEWS);
		}
		int cnt = 1;
		System.out.println("save content...");
		for (NewsItem item : titleList) {
			System.out.println("the " + (cnt++) + "th item...");
			NewsContent content = getNewsContent(item);
			if (content == null)
				continue;
			List<Paragraph> parList = content.getParList();
			List<Picture> picList = content.getPicList();
			NewsSpider.saveNewsContent(item, content, parList,
					picList);
		}
		System.out.println("complited...");
		List<NewsItem> onContentList = NewsSpider.checkHasNewsContent(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NEWS);
		if(onContentList.size()!=0){
			reloadNewsItemContent(onContentList,ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NEWS);
		}
	}
	
	private void reloadNewsItemContent(List<NewsItem> noContentItemList,int newsType) {
		int cnt=0;
		while(cnt<ConstantsForCommon.RELOAD_TIME){
			for(NewsItem i:noContentItemList){
				System.out.println(cnt+" times:reload News content with titleId:"+i.getId());
				NewsContent content = getNewsContent(i);
				if (content == null)
					continue;
				List<Paragraph> parList = content.getParList();
				List<Picture> picList = content.getPicList();
				NewsSpider.saveNewsContent(i, content, parList,
						picList);
			}
			noContentItemList = NewsSpider.checkHasNewsContent(newsType);
			
			if(noContentItemList.size()==0)break;
			cnt++;
		}
		
		if(noContentItemList.size()!=0){
			NewsItemService itemService = (NewsItemService)context.getBean("newsItemService");
			for(NewsItem i:noContentItemList){
				System.out.println("delete news item with no content :"+i.getId());
				itemService.deleteNewsItem(i.getId());
			}
		}
	}
	

	
	public List<NewsItem> getNewsItemList(Stock stock){
		List<NewsItem> newsList = new ArrayList<NewsItem>();
		String stockCode = stock.getCode();
		int totalPage = getPageNumber(stockCode);
		int scanLen = 0 ;
		if(totalPage>3)scanLen= 1;
		else scanLen = totalPage;
		System.out.println("scanLen: "+scanLen);
		for(int i=0;i<scanLen;i++){
			String url = PAGE_URL.replace("stockCode", stockCode+"").replace("pageNum",(i+1)+"");
			Document docx = DataUtils.doGet(url);
			Element el_msg_div = docx.getElementsByClass("msg").get(0);
			Elements el_msg_a_list = el_msg_div.getElementsByTag("a");
			int size = el_msg_a_list.size();
			for(int j=0;j<size;j++){
				Element el_msg_a = el_msg_a_list.get(j);
				String linkStr = el_msg_a.attr("href");
				if(!filtLink(linkStr))continue;
				NewsItem item = new NewsItem();
				item.setLink(linkStr);
				String titleStr = el_msg_a.text();
				item.setTitle(titleStr);
				item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK_NEWS);
				newsList.add(item);
			}
		}
		return newsList;
	}
	
	private boolean filtLink(String str){//http://finance.sina.cn/2015-04-07/detail-iawzuney2704216.d.html?vt=4
	   Pattern p = Pattern.compile("^http://finance.sina.cn/.*");
	   Matcher matcher = p.matcher(str);
	   return matcher.matches();
	}
	
	private int getPageNumber(String stockCode){
		int page =1;
		String url1 = PAGE_URL.replace("stockCode", stockCode);
		String url2 = url1.replace("pageNum",page+"");
		System.out.println("url2: "+url2);
		Document doc = DataUtils.doGet(url2);
		Element el_form = doc.getElementsByTag("form").get(0);
		String str = el_form.text();
		int totalPage = getTotalPage(str);
		System.out.println("total: "+totalPage);
		return totalPage;
	}
	
	
	private int getTotalPage(String str){
		System.out.println("total page: "+str);
		int len = str.length();
		int idx = str.indexOf("/");
		StringBuilder sb = new StringBuilder();
		for(int i=idx;i<len;i++){
			char c = str.charAt(i);
			if(c>='0'&&c<='9'){
				sb.append(c);
			}
		}
		return Integer.valueOf(sb.toString());
	}
	
	public NewsContent getNewsContent(NewsItem title){
		System.out.println("analysing link: "+title.getLink());
		NewsContent content = new NewsContent();
		Document doc = DataUtils.doGet(title.getLink());
		if(doc==null)return null;
        Elements el_text_title_list = doc.getElementsByClass("art_title");
        Element el_text_title;
        if(el_text_title_list.size()!=0){
		   el_text_title = doc.getElementsByClass("art_title").get(0);
        }else{
//        	el_text_title_list = doc.getElementsByClass("articleTitle");
//        	if(el_text_title_list.size()!=0){
//        		el_text_title = el_text_title_list.get(0);
//        	}else{
//        		return null;
//        	}
        	return null;
        }
		Element el_text_title_h2 = el_text_title.getElementsByTag("h2").get(0);
		String titleStr = el_text_title_h2.text();
		System.out.println("title: "+titleStr);
		content.setSubTitle(titleStr);
		Element el_text_title_span = el_text_title.getElementsByClass("from").get(0);
		String infoStr = el_text_title_span.text();
		String infoArr[] = getTimeAndSource(infoStr);
		System.out.println("time: "+infoArr[0]);
		System.out.println("source: "+infoArr[1]);
		content.setTime(infoArr[0]);
		content.setSource(infoArr[1]);
		Element el_text_content_div = doc.getElementById("j_articleContent");
		Elements el_text_list = el_text_content_div.getElementsByClass("art_t");
		Element el_text_div = el_text_list.get(0).parent();//拿到parent

		if(checkIfMorePages(doc)){
			el_text_div= getCompletedContent(el_text_div,title.getLink());
		}
		
	    Elements el_art_pic_list = el_text_content_div.getElementsByClass("art_pic");
	    if(el_art_pic_list.size()!=0){
	    	for(int i=0;i<el_art_pic_list.size();i++){
	    		Element el_art_pic_div_clone = el_art_pic_list.get(i).clone();
	    		Element el_first_child = el_text_div.child(0);
	    		el_first_child.before(el_art_pic_div_clone);//添加到第一个节点
	    	}
	    }
	  //  System.out.println("after: "+el_text_content_div);
	    String innerHtml = el_text_div.html();
	    innerHtml = innerHtml.replace("div", "p");
	 //   System.out.println("innerHtml: "+innerHtml);
	    Document listDoc = Jsoup.parse(innerHtml);
	    Elements el_p_list = listDoc.getElementsByTag("p");
	    List<Paragraph> parList = new ArrayList<Paragraph>();
	    List<Picture> picList = new ArrayList<Picture>();
	    int maxLength = NewsSpider.handlePTagList(parList, picList, el_p_list);
	    content.setMaxLength(maxLength);
	    content.setParList(parList);
	    content.setPicList(picList);
	    content.setTitleId(title.getId());
		return content;
	}
	
	
	public String[] getTimeAndSource(String infoStr) {
		String[] arr1= infoStr.split(" ");
//		for(int i=0;i<arr1.length;i++){
//			System.out.println("arr "+i+":"+arr1[i]);
//		}
		
		String[] arr2 = new String[2];
		arr2[0] = arr1[0]+" "+arr1[1];
		arr2[1] = arr1[2];
//		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}");
//		Matcher matcher = p.matcher(infoStr);
//		String res=null;
//		while(matcher.find()){
//		    res = matcher.group();
//		    System.out.println("res: "+res);
//		    break;
//		}
		return arr2;
	}

	private Element getCompletedContent(Element el_text_div, String link) {
		System.out.println();
		String jsonUrl = link+"&pageAction=1";
		String json = DataUtils.readJsonString(jsonUrl);
		try {
			JSONObject jo = new JSONObject(json);
		//	System.out.println("jo:"+jo.toString());
			String contentStr = jo.getString("content");
			contentStr = contentStr.replace("\r\n\r\n", "");
		//	System.out.println("contentStr: "+contentStr);
			el_text_div.append(contentStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return el_text_div;
	}

	private boolean checkIfMorePages(Document doc){
		Element el_load_more = doc.getElementById("j_loadingbtn");
		System.out.println("el_load_more: ------->"+(el_load_more==null));
		return el_load_more!=null;
	}
	
}
