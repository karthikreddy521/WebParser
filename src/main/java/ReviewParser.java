import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReviewParser {

    private HashSet<String> links = new HashSet<String>();
    List<ReviewModel> reviewModelList = new ArrayList<ReviewModel>();
    Document doc= null;
    boolean goToNextflag=true;

    BufferedWriter writer1;
    public List<ReviewModel> readReview(String reviewURL, String Topic) throws Exception{
        System.out.println("Please wait while reviews are being parsed...");

        try {
                Connection connection = Jsoup.connect(reviewURL);
                Connection.Response resp = connection.execute();
                Document document= resp.parse();

            Map<String,String> cookies = resp.cookies();
            while(goToNextflag) {
                Elements reviews = document.select(".search-results-item-body");

                for (Element ele : reviews) {
                    ReviewModel reviewModel = new ReviewModel();

                    reviewModel.setTitle(ele.select("a").first().text());
//                reviewModel.setURL(ele.select("a").attr("href"));
                    reviewModel.setURL(ele.select("a").first().absUrl("href"));
                    reviewModel.setAuthor(ele.select(".search-result-authors").text());
                    reviewModel.setTopic(Topic);

                    String dateOut = ele.select(".search-result-date").text();
                    DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
                    Date date = format.parse(dateOut);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date1 = sdf.format(date);
                    reviewModel.setDate(date1);

                    reviewModelList.add(reviewModel);
                }

                if(document.select(".pagination-next-link").text().equals("Next")) {
                  String nextPage = document.select(".pagination-next-link").select("a").first().absUrl("href");
                  Connection con = Jsoup.connect(nextPage).followRedirects(true);
                    Connection.Response res = con.execute();
                    document = Jsoup.connect(nextPage)
                                    .cookies(cookies)
                                    .get();
                }
                else goToNextflag =false;

            }

            } catch(IOException e){
                System.err.println("For '" + reviewURL + "': " + e.getMessage());
            }

        return reviewModelList;

    }

}
