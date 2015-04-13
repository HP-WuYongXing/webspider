package com.oliver.test;

import org.junit.Test;

import com.oliver.spiders.FinanceCESpider;
import com.oliver.spiders.FinanceCSSpider;
import com.oliver.spiders.FinanceHeXunSpider;
import com.oliver.spiders.FinanceP5wSpider;
import com.oliver.spiders.FinanceQQSpider;
import com.oliver.spiders.FinanceSTCNSpider;
import com.oliver.spiders.FinanceSinaHeaderSpider;
import com.oliver.spiders.FinanceSinaSpider;
import com.oliver.spiders.Money163Spider;
import com.oliver.spiders.manager.SpiderManager;

public class SpiderTest {

	
	public void testQQSpider(){
		FinanceQQSpider spider = new FinanceQQSpider();
		spider.executedRefreshFocus();
	}
	
    public void testCEspider(){
    	FinanceCESpider spider = new FinanceCESpider();
    	spider.executedRefreshFocus();
    	//spider.executedRefreshStocks();
    }
    
 
    public void testGetLink(){
    	String url = "http://finance.ce.cn/stock/gsgdbd/index.shtml";
    	String link = "../../rolling/201504/11/t20150411_5081448.shtml";
    	System.out.println(FinanceCESpider.getLinkFromUrl(url, link));
    }
    
    public void testCSSpider(){
    	FinanceCSSpider spider = new FinanceCSSpider();
    	spider.executedRefreshFocus();
    }
    
    public void testHeXunSpider(){
       FinanceHeXunSpider spider = new FinanceHeXunSpider();
       spider.executedRefreshFocus();
    }
    

    public void testP5wSpider(){
    	FinanceP5wSpider spider = new FinanceP5wSpider();
    	spider.executedRefreshStocks();
    }
    
    public void testSinaHeaderSpider(){
    	FinanceSinaHeaderSpider spider = new FinanceSinaHeaderSpider();
    	spider.executedRefreshFocus();
    }
    

    public void testSinaSpider(){
    	FinanceSinaSpider spider = new FinanceSinaSpider();
    	spider.executedRefreshFocus();
    }
    
    public void testSTCNSpider(){
    	FinanceSTCNSpider spider= new FinanceSTCNSpider();
    	spider.executedRefreshStocks();//.executedRefreshFocus();
    }
    
    public void testMoney163Spider(){
    	Money163Spider spider = new Money163Spider();
    	spider.executedRefreshStocks();
    }
    
    @Test
    public void testSpiderManager(){
    	SpiderManager manager = new SpiderManager();
    	manager.executeRefreshTask();
    	while(true){
    		
    	}
    }
    
}
