import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
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
                Document document = Jsoup.connect(URL).get();
                 Elements urls = document.select(".browse-by-list-item");

                for (Element e : urls) {
                    topics.put((e.select("button").text().trim()), (e.select("a").attr("href").trim()));
                }

            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }

        }
        return  topics;
    }

}
