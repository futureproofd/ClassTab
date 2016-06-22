package to.marcus.classtab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private static final String ARTISTS_INDEX = "div[class=artists]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InputStream inputStream = null;
        Document document = null;
        String elementHolder = null;
        HashMap<String,String> artistMap = new HashMap<String,String>();
        HashMap<String,String> songMap = new HashMap<String,String>();
        HashMap<String,String> midiMap = new HashMap<String,String>();
        HashMap<String,String> vidMap = new HashMap<String,String>();
        int count = 0;
        try {
            inputStream = this.getAssets().open("index.htm");
            document = Jsoup.parse(inputStream, "UTF-8", "http://placeholder.com");
            Elements elementDiv = document.select(ARTISTS_INDEX);
            //get artist index A-Z
           for(char indexLetter = 'a'; indexLetter <= 'b'; indexLetter++) {
               //get artist div
               for (Element e : elementDiv.select("div[class=artist_"+indexLetter)) {
                   //get artist list
                   for (Element artist : e.select("ul")) {
                       for (Element title : artist.select("b")) {
                           System.out.println(title.text());
                           artistMap.put(indexLetter + String.valueOf(count), title.text());
                           break;
                       }
                       while (count < artistMap.size()) {
                           for (Element songData : artist.getAllElements()) {
                               Elements links = songData.getAllElements().select("a[href]");
                               if (links.size() == 0) {
                                   break;
                               } else {
                                   String recordType = "song";
                                   String idKey = "";
                                   for (int i = 0; i < links.size(); i++) {
                                       //todo: put id in correct place to associate records
                                       switch (recordType) {
                                           case "song":
                                               idKey = generateUID(indexLetter, count);
                                               break;
                                       }
                                       if (links.get(i).toString().contains(".txt")) {
                                           if (recordType != "song") {
                                               idKey = generateUID(indexLetter, count);
                                           }
                                           songMap.put(idKey, links.get(i).attr("href"));
                                           recordType = "";
                                       } else if (links.get(i).toString().contains("MIDI")) {
                                           midiMap.put(idKey, links.get(i).attr("href"));
                                       } else if (links.get(i).toString().contains("youtube.com")) {
                                           vidMap.put(idKey, links.get(i).attr("href"));
                                       }
                                   }
                                   count++;
                               }
                           }
                       }
                   }
               } //end indexLetter
           }
        }catch(IOException exception){
            exception.printStackTrace();
        }
        String stop = "stop";
    }

    private static String generateUID(char alphaIndex, int recordPtr){
        Random rand = new Random();
        return Character.toString(alphaIndex)+String.valueOf(recordPtr)+"-"+ String.valueOf(rand.nextInt());
    }
}
