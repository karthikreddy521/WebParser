import java.io.*;
import java.util.*;

public class CochraneLibraryParser {

    private HashSet<String> links;
    private HrefFetcher hrefFetcher;
    private ReviewParser reviewParser;
    private Map<String, String> topics = new TreeMap<String,String>();
    private List<ReviewModel> reviewModelList = new ArrayList<ReviewModel>();
    private String selectedTopic="Allergy & intolerance";

    public CochraneLibraryParser() {
        this.hrefFetcher = new HrefFetcher();
        this.reviewParser = new ReviewParser();
    }


    public void getReviews(String URL) throws Exception{

        topics = hrefFetcher.getTopicLinks(URL);
        selectedTopic = chooseTopic(topics);
        reviewModelList = reviewParser.readReview(topics.get(selectedTopic).trim(), selectedTopic);
        generateFile(reviewModelList);

    }

    public void generateFile(List<ReviewModel> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./cochrane_reviews.txt"));

        for (ReviewModel review: list) {

            writer.write(review.toString());

        }
        writer.close();
    }

    public  String chooseTopic(Map<String, String> map) {

        int counter = 1;
        for (Map.Entry entry : topics.entrySet()) {
            System.out.println("\t\t" + counter + " : " + entry.getKey());
            counter++;
        }
        System.out.println("Please select a topic from above. Default option is 1.");
        Scanner input = new Scanner(System.in);
//        String in = input.nextLine();
        String in = input.nextLine().trim();
        int inputChoice;
        try {
            if (in.equals("")) {
                System.out.println("Invalid choice. Running default stats for : " + selectedTopic);
                return selectedTopic;
            } else {
                inputChoice = Integer.parseInt(in);
                if (inputChoice <= 0 || inputChoice > counter - 1) {
                    System.out.println("Invalid Choice. Running default stats for : " + selectedTopic);
                    return selectedTopic;
                } else
                    System.out.println("Running stats for : " + (String) topics.keySet().toArray()[inputChoice - 1]);
                return (String) topics.keySet().toArray()[inputChoice - 1];
            }
        }
        catch (NumberFormatException ex) {
            System.out.println("Please enter a number");
            System.exit(0); return "";
        }
    }

    public static void main(String[] args) throws Exception {

        new CochraneLibraryParser().getReviews("https://www.cochranelibrary.com/cdsr/reviews/topics");
        System.out.println("Successfully Completed. Results were written to cochrane_reviews.txt file.");

    }
}
