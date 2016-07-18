package to.marcus.classtab;

import android.app.Application;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

import to.marcus.classtab.data.local.ArtistRepositoryHelperImpl;
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllArtistsQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsQuery;

/**
 * Created by marcus on 6/17/2016
 */
public class WebParser {
    private static final String ARTISTS_INDEX = "div[class=artists]";
    private static final String ARTIST_ELEMENT = "div[class=artist_";
    private static final String RECORD_SONG = "song";
    private Application mContext;

    public WebParser(Application context){
        this.mContext = context;
    }

    /*
    Bootstrap process for creating database on first run
    Should only run once
     */
    public void InitDB(){
        InputStream inputStream;
        Document document;
        HashMap<String,String> artistMap = new HashMap<>();
        HashMap<String,String> artistDateMap = new HashMap<>();
        HashMap<String,String> songMap = new HashMap<>();
        HashMap<String,String> songTitleMap = new HashMap<>();
        HashMap<String,String> midiMap = new HashMap<>();
        HashMap<String,String> vidMap = new HashMap<>();
        int artistCount = 0;
        try {
            inputStream = mContext.getAssets().open("index.htm");
            document = Jsoup.parse(inputStream, "UTF-8", "http://www.classtab.org");
            Elements elementDiv = document.select(ARTISTS_INDEX);
            //get artist index A-Z
            for(char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
                //get artist div
                for (Element e : elementDiv.select(ARTIST_ELEMENT+indexLetter)) {
                    //get artist list
                    for (Element artist : e.select("ul")) {
                        for (Element title : artist.select("b")) {
                            //artist name and date
                            artistMap.put(indexLetter + String.valueOf(artistCount), title.text());
                            artistDateMap.put(indexLetter+String.valueOf(artistCount), artist.textNodes().get(1).text());
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
                                            songTitleMap.put(idKey, links.get(i).childNode(0).toString());
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

        //// TODO: 7/8/2016 Re-use these for one-time bootstrap process

        ArtistRepositoryHelperImpl artistRepositoryHelperImpl = new ArtistRepositoryHelperImpl(mContext);
        artistRepositoryHelperImpl.populateArtists(artistMap);
        artistRepositoryHelperImpl.populateArtistsDates(artistDateMap);

       //works but only needs to run once!
        /*
        TabRepositoryHelperImpl tabRepositoryHelperImpl = new TabRepositoryHelperImpl(this);
        tabRepositoryHelperImpl.populateTabs(songMap);
        HashMap<String,byte[]> tabs = new HashMap<>();
        tabs = tabRepositoryHelperImpl.query(new AllTabsQuery());
*/
    }

    private static String generateUID(char alphaIndex, int recordPtr){
        Random rand = new Random();
        return Character.toString(alphaIndex)+String.valueOf(recordPtr)+"-"+ String.valueOf(rand.nextInt());
    }

}
