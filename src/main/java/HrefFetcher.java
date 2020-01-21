import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class HrefFetcher {

    private HashSet<String> links = new HashSet<String>();
    Map<String, String> topics = new TreeMap<String,String>();
    String topicNames[]= new String[]{};


    public Map<String,String> getTopicLinks(String URL) {

        if (!links.contains(URL)) {
            try {
                if (links.add(URL)) {
//                    System.out.println(URL);
                }
                Document document = Jsoup.connect(URL).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                        .ignoreContentType(true)
                        .followRedirects(true)
                        .referrer("http://www.google.com")
                        .get();


//
                //print result
                //System.out.println(document);

                    Elements urls = document.select(".browse-by-list-item");

                for (Element e : urls) {
                    topics.put((e.select("button").text().trim()), (e.select("a").attr("href").trim()));
                }

//                for(Map.Entry entry : topics.entrySet()) {
//                    System.out.println(entry.getKey() + ":: "+ entry.getValue());
//                }

            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }

        }
        return  topics;
    }

}
