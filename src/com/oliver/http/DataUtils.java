package com.oliver.http;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DataUtils {
	
	private static final String SAVE_PIC_DIR="E://pics/";
	
	public static Document doGet(String urlStr){
		Document doc=null;
		try {
			     doc = Jsoup.connect(urlStr)
                        .header("User-Agent",
                                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
                        ).get();
			}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}
	
	public static String readJsonString(String urlStr){
        StringBuffer sb  = new StringBuffer();
        HttpURLConnection conn=null;
        try{
            URL url = new URL(urlStr);
            conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
            conn.addRequestProperty("Cookie", "pt2gguin=o0793808227; ptcz=f68f5499935280b573e507bc96cc67f93588a4f976d062d0aafb2d1c6ec1a8c9; ptui_loginuin=793808227; pgv_pvid=5157356428; o_cookie=793808227; ts_uid=9493158942; RK=yL0H5fUddy; roll_mod=1");
            conn.addRequestProperty("Referer", "http://roll.finance.qq.com/");
            conn.addRequestProperty("Host", "roll.finance.qq.com");
            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            if(conn.getResponseCode()==200){
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len=0;
                while(true){
                    len = is.read(buffer);
                    if(len==-1){
                        break;
                    }else {
                        outputStream.write(buffer,0,len);
                    }
                }
                byte[] result = outputStream.toByteArray();
                String str = new String(result, 0, result.length, "gbk");
                sb.append(str);
                outputStream.close();
                is.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null) {
                conn.disconnect();
            }
        }
        return sb.toString();
    }
	
	
	
	public static String savePicture(String url) throws IOException{
		URL imgUrl = null;
		imgUrl = new URL(url);
		String fileName = getPicFileName();
		if(imgUrl!=null){
			File imgFile = new File(fileName);
			HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
		    InputStream is = conn.getInputStream();
		    OutputStream os = new FileOutputStream(imgFile);
		    byte[]buff = new byte[1024];
		    while(true){
		    	int readed = is.read(buff);
		    	if(readed==-1){
		    		break;
		    	}
		    	byte[] tmp =new byte[readed];
		    	System.arraycopy(buff, 0, tmp, 0, readed);
		    	os.write(tmp);
		    }
		    is.close();
		    os.close();
		}
		return fileName;
	}
	
    private static String getPicFileName(){
    	return SAVE_PIC_DIR+(new Date().getTime())+".jpg";
    }
}
