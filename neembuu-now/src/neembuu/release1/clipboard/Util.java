/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.release1.clipboard;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import neembuu.release1.httpclient.NHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Shashank Tulsyan
 */
public class Util {
    //Pull all links from the body for easy retrieval

    public static ArrayList<String> pullLinks(String text) {
        ArrayList<String> links = new ArrayList<>();

        //String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        //String regex = "\\(?\\b(https?://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        //Pattern p = Pattern.compile(regex);
        Pattern p = Patterns.WEB_URL;
        
        Matcher m = p.matcher(text);
        while (m.find()) {
            String urlStr = m.group();
            /*if (urlStr.startsWith("(")
                    && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }*/
            if(! (urlStr.startsWith("http://") || urlStr.startsWith("https://"))){
                urlStr = "http://"+urlStr;
            }
            links.add(urlStr);
        }
        return links;
    }
    
    public static void main(String[] args) throws Exception{
        String text = "http://rapidshare.com/files/183020068/S5E93_-_J2_Revised.part2.rar asassa\n" +
                "\nsadsada " +
                "https://rapidshare.com/files/979681925/test120k.rmvb\n asdasdsa" +
                "\n asdasdsa " +
                "upd0.appwork.org/jcgi/JDownloader/alpha\n sadsadas" +
                "asdasd upd0.appwork.org/jcgi/JDownloader asdasd";
        System.out.println(text);
        
        System.out.println(text);
        ArrayList<String> a = pullLinks(text);
        for (String url : a) {
            System.out.println("lnk->"+url);
        }
    }
}
