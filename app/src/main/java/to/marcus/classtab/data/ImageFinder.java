package to.marcus.classtab.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by marcus on 7/4/2016
 */
public class ImageFinder {
    //// TODO: 7/5/2016 run one time only - get artist images via web search
    public static String FindImage(String question, String ua) {
        String finRes = "";

        try {
            String googleUrl = "https://www.google.com/search?tbm=isch&q=" + question.replace(",", "");
            Document doc1 = Jsoup.connect(googleUrl).userAgent(ua).timeout(10 * 1000).get();
            Element media = doc1.select("[data-src]").first();
            String finUrl = media.attr("abs:data-src");

            finRes= "<a href=\"http://images.google.com/search?tbm=isch&q=" + question + "\"><img src=\"" + finUrl.replace("&quot", "") + "\" border=1/></a>";

        } catch (Exception e) {
            System.out.println(e);
        }

        return finRes;
    }
}
