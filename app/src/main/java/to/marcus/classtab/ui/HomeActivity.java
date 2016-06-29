package to.marcus.classtab.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.data.local.ArtistDataHelper;
import to.marcus.classtab.R;

public class HomeActivity extends AppCompatActivity {

    private static final String ARTISTS_INDEX = "div[class=artists]";
    private static final String ARTIST_ELEMENT = "div[class=artist_";
    private static final String RECORD_SONG = "song";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InputStream inputStream;
        Document document;
        HashMap<String,String> artistMap = new HashMap<>();
        HashMap<String,String> songMap = new HashMap<>();
        HashMap<String,String> midiMap = new HashMap<>();
        HashMap<String,String> vidMap = new HashMap<>();
        int artistCount = 0;
        try {
            inputStream = this.getAssets().open("index.htm");
            document = Jsoup.parse(inputStream, "UTF-8", "http://www.classtab.org");
            Elements elementDiv = document.select(ARTISTS_INDEX);
            //get artist index A-Z
           for(char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
               //get artist div
               for (Element e : elementDiv.select(ARTIST_ELEMENT+indexLetter)) {
                   //get artist list
                   for (Element artist : e.select("ul")) {
                       for (Element title : artist.select("b")) {
                           artistMap.put(indexLetter + String.valueOf(artistCount), title.text());
                           break;
                       }
                       while (artistCount < artistMap.size()) {
                           //extract per artist
                           for (Element songData : artist.getAllElements()) {
                               Elements links = songData.getAllElements().select("a[href]");
                               if (links.size() == 0) {
                                   break;
                               } else {
                                   String recordType = RECORD_SONG;
                                   String idKey = "";
                                   for (int i = 0; i < links.size(); i++) {
                                       switch (recordType) {
                                           case RECORD_SONG:
                                               idKey = generateUID(indexLetter, artistCount);
                                               break;
                                       }
                                       if (links.get(i).toString().contains(".txt")) {
                                           if (recordType != RECORD_SONG) {
                                               idKey = generateUID(indexLetter, artistCount);
                                           }
                                           songMap.put(idKey, links.get(i).attr("href"));
                                           //// TODO: 6/29/2016 add a songTitleMap
                                           recordType = "";
                                       } else if (links.get(i).toString().contains("MIDI")) {
                                           midiMap.put(idKey, links.get(i).attr("href"));
                                       } else if (links.get(i).toString().contains("youtube.com")) {
                                           vidMap.put(idKey, links.get(i).attr("href"));
                                       }
                                   }
                                   artistCount++;//next artist
                               }
                           }
                       }
                   }
               } //end indexLetter
           }
        }catch(IOException exception){
            exception.printStackTrace();
        }
        ArtistDataHelper artistDataHelper = new ArtistDataHelper(this);
        artistDataHelper.populateArtists(artistMap);
        ArrayList<Artist> artists = new ArrayList<>();
        artists = artistDataHelper.getAllArtists();
    }

    private static String generateUID(char alphaIndex, int recordPtr){
        Random rand = new Random();
        return Character.toString(alphaIndex)+String.valueOf(recordPtr)+"-"+ String.valueOf(rand.nextInt());
    }
}
