package to.marcus.classtab;

import android.app.Application;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

/**
 * Created by marcus on 6/17/2016
 */
public final class WebParser {
    private static final String ARTISTS_INDEX = "div[class=artists]";
    private static final String ARTIST_ELEMENT = "div[class=artist_";
    private static final String RECORD_SONG = "song";
    private static InputStream sInputStream;
    private static Document sDocument;
    private Application mContext;

    @Inject
    public WebParser(Application context){
        this.mContext = context;
    }

    /*
    Bootstrap process for creating database on first run
    Should only run once
     */

    private void setDocumentSource(){
        try{
            sInputStream = mContext.getAssets().open("index.htm");
            sDocument = Jsoup.parse(sInputStream, "UTF-8", "http://www.classtab.org");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeDocumentSource(){
        if(sInputStream != null){
            sInputStream = null;
        }
        if(sDocument != null){
            sDocument = null;
        }
    }

    public HashMap<String,String> getArtists() {
        HashMap<String,String> resultSet = new HashMap<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<HashMap<String,String>> result = es.submit(new Callable<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> call() throws Exception {
                setDocumentSource();
                HashMap<String,String> artistMap = new HashMap<>();
                int artistCount = 0;
                Elements elementDiv = sDocument.select(ARTISTS_INDEX);
                //get artist index A-Z
                for (char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
                    //get artist div
                    for (Element e : elementDiv.select(ARTIST_ELEMENT + indexLetter)) {
                        //get artist list
                        for (Element artist : e.select("ul")) {
                            for (Element title : artist.select("b")) {
                                //artist date
                                artistMap.put(indexLetter + String.valueOf(artistCount), title.text());
                                artistCount++;
                                break;
                            }
                        }
                    }
                }
                closeDocumentSource();
                return artistMap;
            }
        });
        try{
            resultSet = result.get();
        }catch (Exception e){
            Log.e("WEBPARSER", "could not get artists!");
        }
        es.shutdown();
        return resultSet;
    }


    public HashMap<String,String> getArtistDates() {
        HashMap<String,String> resultSet = new HashMap<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<HashMap<String,String>> result = es.submit(new Callable<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> call() throws Exception {
                setDocumentSource();
                HashMap<String,String> artistDateMap = new HashMap<>();
                int artistCount = 0;
                Elements elementDiv = sDocument.select(ARTISTS_INDEX);
                //get artist index A-Z
                for (char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
                    //get artist div
                    for (Element e : elementDiv.select(ARTIST_ELEMENT + indexLetter)) {
                        //get artist list
                        for (Element artist : e.select("ul")) {
                            for (Element title : artist.select("b")) {
                                //artist date
                                artistDateMap.put(indexLetter+String.valueOf(artistCount), artist.textNodes().get(1).text());
                                artistCount++;
                                break;
                            }
                        }
                    }
                }
                closeDocumentSource();
                return artistDateMap;
            }
        });
        try{
            resultSet = result.get();
        }catch (Exception e){
            Log.e("WEBPARSER", "could not get dates!");
        }
        es.shutdown();
        return resultSet;
    }

    public HashMap<String,String> getTabs(){
        HashMap<String,String> resultSet = new HashMap<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<HashMap<String,String>> result = es.submit(new Callable<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> call() throws Exception {
                setDocumentSource();
                HashMap<String,String> tabMap = new HashMap<>();
                int artistCount = 0;
                int totalArtists = 0;
                Elements elementDiv = sDocument.select(ARTISTS_INDEX);
                //get artist index A-Z
                for(char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
                    //get artist div
                    for (Element e : elementDiv.select(ARTIST_ELEMENT+indexLetter)) {
                        //get artist list
                        for (Element artist : e.select("ul")) {
                            for (Element title : artist.select("b")) {
                                totalArtists++;
                                break;
                            }
                            while (artistCount < totalArtists) {
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
                                                tabMap.put(idKey, links.get(i).attr("href"));
                                                recordType = "";
                                            }
                                        }
                                        artistCount++;//next artist
                                    }
                                }
                            }
                        }
                    } //end indexLetter
                }
                closeDocumentSource();
                return tabMap;
            }
        });
        try{
            resultSet = result.get();
        }catch (Exception e){
            Log.e("WEBPARSER", "could not get dates!");
        }
        es.shutdown();
        return resultSet;
    }

    public HashMap<String,String> getTabTitles(){
        HashMap<String,String> resultSet = new HashMap<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<HashMap<String,String>> result = es.submit(new Callable<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> call() throws Exception {
                setDocumentSource();
                HashMap<String,String> tabTitleMap = new HashMap<>();
                int artistCount = 0;
                int totalArtists = 0;
                Elements elementDiv = sDocument.select(ARTISTS_INDEX);
                //get artist index A-Z
                for(char indexLetter = 'a'; indexLetter <= 'z'; indexLetter++) {
                    //get artist div
                    for (Element e : elementDiv.select(ARTIST_ELEMENT+indexLetter)) {
                        //get artist list
                        for (Element artist : e.select("ul")) {
                            for (Element title : artist.select("b")) {
                                totalArtists++;
                                break;
                            }
                            while (artistCount < totalArtists) {
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
                                                tabTitleMap.put(idKey, links.get(i).childNode(0).toString());
                                                recordType = "";
                                            }
                                        }
                                        artistCount++;//next artist
                                    }
                                }
                            }
                        }
                    } //end indexLetter
                }
                closeDocumentSource();
                return tabTitleMap;
            }
        });
       try{
           resultSet = result.get();
       }catch (Exception e){
           Log.e("WEBPARSER", "could not get dates!");
       }
        es.shutdown();
        return resultSet;
    }

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
                                        }
                                        else if (links.get(i).toString().contains("MIDI")) {
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
    }

    private static String generateUID(char alphaIndex, int recordPtr){
        Random rand = new Random();
        return Character.toString(alphaIndex)+String.valueOf(recordPtr)+"-"+ String.valueOf(rand.nextInt());
    }

}
