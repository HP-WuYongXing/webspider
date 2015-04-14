package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class FinanceP5wSpider extends NewsSpider {

    public static final String FOCUS_URL="http://www.p5w.net/stock/news/zqyw/";
    private static final int URL_CODE=ConstantsForNewsItem.URL_P5W;
    
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this,URL_CODE);
	}

	@Override
	public void executedRefreshStocks() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<NewsItem> getFocusNewsList() {
		List<NewsItem> itemList = new ArrayList<NewsItem>();
		Document doc = DataUtils.doGet(FOCUS_URL);
		Elements el_h2_title_list = doc.getElementsByClass("title");
		System.out.println(el_h2_title_list.toString());
		int size= el_h2_title_list.size();
		System.out.println("size: "+size);
		for(int i=0;i<size;i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			Element el_h2_title =el_h2_title_list.get(i);
			Element el_h2_title_a = el_h2_title.getElementsByTag("a").get(0);
			String str = el_h2_title_a.text();
			item.setTitle(str);
			str =el_h2_title_a.attr("href");
			if(str.startsWith("./")){
				str = FOCUS_URL+str.substring(2);
			}
			item.setLink(str);
			itemList.add(item);
			System.out.println("item: "+item);
		}
		System.out.println("list size: "+size);
		return itemList;
	}

	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		String linkUrl = title.getLink();
		int index = linkUrl.lastIndexOf('/');
		linkUrl = linkUrl.substring(0, index+1);
		System.out.println("analysing link:"+title.getLink());
		NewsContent content = new NewsContent();
		content.setTitleId(title.getId());
		Document doc = DataUtils.doGet(title.getLink());
		Element el_title = doc.getElementsByClass("title").get(0);
		String str = el_title.text();
		System.out.println("title: "+str);
		content.setSubTitle(str);
		
		Element el_source_time = doc.getElementsByClass("source").get(0);
		Element el_source_time_span = el_source_time.getElementsByTag("span").get(0);
		Element el_source_time_span_ins = el_source_time_span.getElementsByTag("ins").get(0);
		str =el_source_time_span_ins.text();
		System.out.println("source: "+str);
		content.setSource(str);
		str = el_source_time_span.text();
//	System.out.println("str:"+str);
		str =getTimeFromSource(str);
		System.out.println("str:"+str);
		content.setTime(str);
		Element el_text  = doc.getElementsByClass("text").get(0);
		
		Elements el_text_p_list = el_text.getElementsByTag("p");
		int size = el_text_p_list.size();
		if(size==0)return null;
		
		List<Paragraph> parList = new ArrayList<Paragraph>();
		List<Picture> picList = new ArrayList<Picture>();
		int order=0;
		for(int i=0;i<size;i++){
			Element el_text_p = el_text_p_list.get(i);
			str = el_text_p.text();
			if(str.length()>2){
				order = addParagraph(el_text_p, parList, order);
			}
			Elements el_text_p_img_list = el_text_p.getElementsByTag("img");
			if(el_text_p_img_list.size()>0){
				order = savePictures(el_text_p_img_list, picList, order,linkUrl);
			}
		}
		System.out.println("parList size:"+parList.size());
		System.out.println("picList size:"+picList.size());
		System.out.println("maxLength:"+order);
		content.setParList(parList);
		content.setPicList(picList);
		content.setMaxLength(order);
		return content;
	}
	
    public String getTimeFromSource(String str){
    //	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
     //Pattern p = Pattern.compile("\\d{4}.*\\d{2}.*\\d{2}.*\\s*\\d{2}:\\d{2}");
    //	Matcher matcher = p.matcher(str);
    	String strArr[] =str.split(" ");
//    	for(int i=0;i<strArr.length;i++){
//    		System.out.println(i+": "+strArr[i]);
//    	}
    	return strArr[2]+" "+strArr[3];
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
