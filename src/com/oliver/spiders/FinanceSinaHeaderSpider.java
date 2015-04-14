package com.oliver.spiders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;
import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.http.DataUtils;

public class FinanceSinaHeaderSpider extends NewsSpider{

	private static final String FOCUS_URL="http://finance.sina.com.cn/money/";
	private static final int URL_CODE= ConstantsForNewsItem.URL_SINA;
	@Override
	public void executedRefreshFocus() {
		this.excutedRefreshFocus(this,URL_CODE);
	}
	
	

	@Override
	public void executedRefreshStocks() {
		
	}



	@Override
	public List<NewsItem> getFocusNewsList(){
		try {
			Document doc = DataUtils.doGet(FOCUS_URL);
			List<NewsItem> itemList = new ArrayList<NewsItem>();
			Elements el_news_item_list = doc
					.getElementsByClass("img-news-item");
			int len = el_news_item_list.size();
			System.out.println("news item list size: " + len);
			for (int i = 0; i < len; i++) {
				NewsItem item = new NewsItem();
				item.setUrlCode(URL_CODE);
				Element el_news_item = el_news_item_list.get(i);
				Element el_news_item_img = el_news_item.getElementsByTag("img")
						.get(0);
				String imgUrl = el_news_item_img.attr("src");
				
				String imgpath = DataUtils.savePicture(imgUrl);
				System.out.println("img path: "+imgpath);
				item.setThumbnail(imgpath);
				
				Elements el_h2_list = el_news_item.getElementsByTag("h2");
				if (el_h2_list.size() == 0)
					continue;
				Element el_h2 = el_h2_list.get(0);
				Element el_h2_a = el_h2.getElementsByTag("a").get(0);
				String str = el_h2_a.text();
				item.setTitle(str);
				str = el_h2_a.attr("href");
				if (!linkFilter(str))
					continue;
				item.setLink(str);
				Element el_time = el_news_item.getElementsByClass("time")
						.get(0);
				str = el_time.text();
				item.setTime(str);
				item.setNewsType(ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS_HEADER);
				itemList.add(item);
			}
			return itemList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean linkFilter(String url){
		Pattern p = Pattern.compile("^http://finance.sina.com.cn/money/cfgs/.*");
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}
	
	@Override
	public NewsContent getFocusNewsContent(NewsItem title) {
		FinanceSinaSpider spider = new FinanceSinaSpider();
		NewsContent content = spider.getFocusNewsContent(title);
		return content;
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
