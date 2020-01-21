import java.util.Date;

public class ReviewModel {

    private String URL;
    private String Topic;
    private String Title;
    private String Author;
    private String date;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return   URL + " | " +
                 Topic + " | " +
                 Title + " | " +
                 Author + " | " +
                 date + "\n"
                ;
    }
}
