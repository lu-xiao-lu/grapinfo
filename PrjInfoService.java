package com.lxl.grap.service;

import com.lxl.grap.model.PrjInfo;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PrjInfoService {

	
	/*
	 * ��ȡ��Ŀ��Ϣ
	 * */
	@SuppressWarnings("null")
	public static ArrayList<PrjInfo> getPrjInfo() throws HttpHostConnectException {
		//public static void getPrjInfo() {		
		ArrayList<PrjInfo> prjinfos =  new ArrayList<PrjInfo>();
		//id:28943~90143
		//for(int i = 88943;i<90143;i++)
		for(int i = 90000;i<90003;i++)
		{
			
		String url="http://www.zrefs.cn/index.php?id="+Integer.toString(i);
		String html=pickData(url);
		PrjInfo prjinfo = analyzeHTMLByString(html);		
		if(prjinfo == null)		    
		   break;
		else		  
		   prjinfos.add(prjinfo);
		}
		return prjinfos;
	}

	/*
	 * ʹ��jsoup������ҳ��Ϣ
	 * */
	private static PrjInfo analyzeHTMLByString(String html) {
		String prjID="",prjName="",version="",area="",prjAddress="",cost="",invest="",category="",phase="",description="",beginTime="",
		endTime="",publishTime="",developer="",designer="",engineer="",builder="",subContractor="";
        Document document = Jsoup.parse(html);
       
        ArrayList<Element> tds=document.getElementsByTag("td");
        if(tds.size()>0)
        {
        prjID = tds.get(1).text().toString();
        prjName = tds.get(3).text().toString();
        version = tds.get(5).text().toString();
        area = tds.get(7).text().toString();
        prjAddress=tds.get(9).text().toString();
        cost = tds.get(11).text().toString();
        invest = tds.get(13).text().toString();
        category = tds.get(15).text().toString();
        phase = tds.get(17).text().toString();
        description = tds.get(19).text().toString();
        beginTime = tds.get(21).text().toString();
        endTime = tds.get(23).text().toString();
        publishTime = tds.get(25).text().toString();
        developer = tds.get(27).text().toString();
        designer = tds.get(29).text().toString();
        engineer = tds.get(31).text().toString();
        builder = tds.get(33).text().toString();
        subContractor = tds.get(35).text().toString();
        
        PrjInfo prjinfo=new PrjInfo(prjID,prjName,version,area,prjAddress,cost,invest,category,phase,description,beginTime,
        		endTime,publishTime,developer,designer,engineer,builder,subContractor);
        return prjinfo;
        }
        else
        {
        	return null;
        }
        
        
	}

	/*
	 * ��ȥ��ҳ��Ϣ
	 * */
	private static String pickData(String url) {
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpGet httpget = new HttpGet(url);
	            CloseableHttpResponse response = httpclient.execute(httpget);
	            try {
	                // ��ȡ��Ӧʵ��
	                HttpEntity entity = response.getEntity();
	                // ��ӡ��Ӧ״̬
	                if (entity != null) {
	                    return EntityUtils.toString(entity);
	                }
	            } finally {
	                response.close();
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            // �ر�����,�ͷ���Դ
	            try {
	                httpclient.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	}
}
