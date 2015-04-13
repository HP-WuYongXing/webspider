package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class FinanceCSSpider extends NewsSpider {

	private static final String FOCUS_URL="http://www.chinastock.com.cn/yhwz/information/yaowen.shtml";
	
	private static final String URL_ROOT="http://www.chinastock.com.cn";
	
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this,URL_ROOT);
	}

	
	
	@Override
	public void executedRefreshStocks() {
		
	}



	@Override
	public List<NewsItem> getFocusNewsList() {
		List<NewsItem> newsItemList = new ArrayList<NewsItem>();
		Document doc = DataUtils.doGet(FOCUS_URL);
		Element el_news_list_content = doc.getElementsByClass("new_list2").get(0);
		Elements el_news_list = el_news_list_content.getElementsByTag("li");
		int len = el_news_list.size();
		for(int i=0;i<len;i++){
			NewsItem item = new NewsItem();
			Element el_news_li = el_news_list.get(i);
			Element el_news_li_a = el_news_li.getElementsByTag("a").get(0);
			String str = el_news_li_a.text();
			System.out.println("title: "+str);
			item.setTitle(str);
			
			str = el_news_li_a.attr("href");
			str = URL_ROOT+str;
			System.out.println("link: "+str);
			item.setLink(str);
			
			Element el_news_li_span = el_news_li.getElementsByClass("R").get(0);
		    str = el_news_li_span.text();
		    System.out.println("time: "+str);
			item.setTime(str);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			newsItemList.add(item);
		}
		return newsItemList;
	}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		   NewsContent content = new NewsContent();
		   content.setTitleId(title.getId());
		   Document doc = DataUtils.doGet(title.getLink());
		   if(doc==null)return null;
		   Element el_text_div =  doc.getElementById("dleft1");
		   Element el_text_title = el_text_div.getElementsByClass("d_title").get(0);
		   String str = el_text_title.text();
		   System.out.println("title: "+str);
		   content.setSubTitle(str);
		   Element el_text_date = el_text_div.getElementsByClass("d_date").get(0);
		   str = el_text_date.text();
		   String arr[] = getTimeAndSource(str);
		   System.out.println("time: "+arr[0]);
		   content.setTime(arr[0]);
		   System.out.println("source: "+arr[1]);
		   content.setSource(arr[1]);
		   Element el_text_body =el_text_div.getElementById("Zoom");
		   String appPTagsStr = addPTags(el_text_body.toString());
		   Document subDoc = Jsoup.parse(appPTagsStr);
		   List<Paragraph> parList = new ArrayList<Paragraph>();
		   List<Picture> picList = new ArrayList<Picture>();
		   Elements el_text_p_list =subDoc.getElementsByTag("p");
		   int maxLength = this.handlePTagList(parList, picList, el_text_p_list);
		   System.out.println("maxlength: "+maxLength);
		   content.setMaxLength(maxLength);
		   content.setPicList(picList);
		   System.out.println("picList size: "+picList.size());
		   content.setParList(parList);
		   System.out.println("parList size: "+parList.size());
		return content;
	}

	private String addPTags(String string) {
		string = string.replace("&nbsp;", " ");
	    int firstLeft = string.indexOf(">");
	    int lastright = string.lastIndexOf("<");
	    System.out.println("firstLeft: "+firstLeft);
	    System.out.println("lastRight: "+lastright);
	    String body = string.substring(firstLeft+1,lastright);
	    body = "<p>"+body.replaceAll("<br />", "</p><p>")+"</p>";
		return body;
	}
	
	private String[] getTimeAndSource(String str){
		String arr[] = str.split(" ");
		String str1 = arr[0].substring(arr[0].length()-10);
		String str2 = arr[0].substring(0,arr[0].length()-10);
		String []arr2 = new String[2];
		arr2[0] = str1;
		arr2[1] = str2;
		return arr2;
	}

	@Override
	public List<NewsItem> getStocksNewsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
		// TODO Auto-generated method stub
		return null;
	}

}
