package cn.zxw.application.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlContent {

	public static void main(String[] args) {
		 String html = "<div layer1=\"text-s\">1</div><div class=\"tz-paragraph\">有没有换这种的</div><div layer1=\"text-e\">2</div><br>";  
         Document doc = Jsoup.parse(html);  
         System.out.println(doc.text());//过滤html标签取内容
         
         //Elements rows = doc.select("table[class=list]").get(0).select("tr");
         Elements eles=doc.getAllElements();
         System.out.println(eles.size());
         for(Element e:eles) {
        	 System.out.println("-"+e.text());
         }
         
	}
}
