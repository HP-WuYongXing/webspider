package com.oliver.spiders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class FinanceCESpider extends NewsSpider{
	private static final String FOCUS_LIST_URL="http://finance.ce.cn//rolling/";
	private static final String STOCKS_URL="http://finance.ce.cn/stock/gsgdbd/index.shtml";
	private static final String FOCUS_URL_PATTERN ="http://finance.ce.cn//rolling/";
	private static final String STOCK_URL_PATTERN ="http://finance.ce.cn//rolling/";
	private static final int URL_CODE= ConstantsForNewsItem.URL_CE;
	public FinanceCESpider() {
		
	}

	public void executedRefreshFocus(){
		this.excutedRefreshFocus(this,URL_CODE);
	}
	
	@Override
	public void executedRefreshStocks(){
		this.excutedRefreshStocksNews(this,URL_CODE);
	}
	
	@Override
	public List<NewsItem> getFocusNewsList(){
		return getNewsListWithUrl(FOCUS_LIST_URL);
	}

	private List<NewsItem> getNewsListWithUrl(String url) {
		Document doc = DataUtils.doGet(url);
		if(doc==null)return null;
		Element el_list_left = doc.getElementsByClass("list_left").get(0);
		Element el_list_table = el_list_left.getElementsByTag("table").get(1);
		Elements el_list_table_td = el_list_table.getElementsByTag("td");
		//System.out.println("size: "+el_list_table_td.size());
		List<NewsItem> list = new ArrayList<NewsItem>();
		for(int i=0;i<el_list_table_td.size();i++){
			NewsItem item = new NewsItem();
			item.setUrlCode(URL_CODE);
			Elements el_td_a_list = el_list_table_td.get(i).getElementsByTag("a");
			if(el_td_a_list.size()==0)continue;
			Element el_td_a = el_td_a_list.get(0);
			String title = el_td_a.text();
			String link = el_td_a.attr("href");
			if(link.charAt(0)=='.'){
				link= getLinkFromUrl(url,link);
			}
			Element el_td_span = el_list_table_td.get(i).getElementsByTag("span").get(0);
			String time = el_td_span.text();
			time = time.substring(1, time.length()-1).trim();
			//System.out.println("title:¡¡"+title);
			item.setTitle(title);
			//System.out.println("time:¡¡"+title);
			item.setTime(time);
			//System.out.println("link:¡¡"+link);
			item.setLink(link);
			item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
			list.add(item);
		}
		return list;
	}
	
	

	@Override
	public NewsContent getFocusNewsContent(NewsItem title){
		System.out.println("newsContentFrom link: "+title.getLink());
		Document doc = DataUtils.doGet(title.getLink());
		if(doc==null)return null;
		NewsContent content =new NewsContent();
		List<Picture> picList =new ArrayList<Picture>();
		List<Paragraph> parList = new ArrayList<Paragraph>();
		Element el_title = doc.getElementById("articleTitle");      
		String str = el_title.text().trim();
		content.setSubTitle(str);
		Element el_time = doc.getElementById("articleTime");
		str = el_time.text();
	//	System.out.println("time: "+str);
		content.setTime(str);
		Element el_source = doc.getElementById("articleSource");
		str = el_source.text();
	//	System.out.println("source: "+str);
		content.setSource(str);
		Element el_text_div =doc.getElementById("articleText");
		Elements  el_text_ps = el_text_div.getElementsByTag("p");
		int maxLength = handlePTagList(parList, picList, el_text_ps);
		
		System.out.println("paragraph size: "+parList.size());
		System.out.println("picture size: "+picList.size());
		content.setPicList(picList);
		content.setParList(parList);
		content.setMaxLength(maxLength);
		System.out.println("newsContent length: "+maxLength);
		return content;
	}

	//@Override
	public List<NewsItem> getStocksNewsList() {
		return this.getNewsListWithUrl(STOCKS_URL);
	}

	//@Override
	public NewsContent getStocksNewsContent(NewsItem title) {
		return this.getFocusNewsContent(title);
	}

	
//	private int addParagraph(Element el_text_p,List<Paragraph> parList,int order){
//			Paragraph p = new Paragraph();
//			p.setContent(el_text_p.text());
//			p.setOrderNumber(order++);
//			parList.add(p);
//			return order;
//	}
//	
//	private int savePictures(Elements el_img_list,List<Picture> picList,int order){
//		int len = el_img_list.size();
//		for(int i=0;i<len;i++){
//			Picture p = new Picture();
//			Element el_img = el_img_list.get(i);
//			String link = el_img.attr("src");
//			String path=null;
//			try {
//				path = DataUtils.savePicture(link);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			p.setImagePath(path);
//			p.setOrderNumber(order++);
//			picList.add(p);
//		}
//		return order;
//	}
//	
//	public void checkHasNewsContent(){
//		NewsItemDaoImpl dao = new NewsItemDaoImpl();
//		NewsContentDaoImpl contentDao = new NewsContentDaoImpl();
//		List<NewsItem> items = dao.queryAll();
//		System.out.println("item size: "+items.size());
//		for(NewsItem item:items){
//			int titleId = item.getId();
//			List<NewsContent> content = contentDao.queryByTitileId(titleId);
//			if(content==null||content.size()==0){
//				System.out.println("no content on NewsTitle: "+titleId);
//				dao.deleteNewsItem(titleId);
//			}
//		}
//	}
}
