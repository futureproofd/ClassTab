package to.marcus.classtab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private static final String ARTISTS_INDEX = "div[class=artists]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //open local index.htm for parsing
        //notification service will determine when a new version is available from classtab
            //then replace file and rebuild
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
            //get artist div
            for(Element e : elementDiv.select("div[class=artist_a")){
                //get artist list
                for(Element artist : e.select("ul")){
                    for(Element title : artist.select("b")){
                        System.out.println(title.text());
                        artistMap.put("a"+ String.valueOf(count),title.text());
                        break;
                    }

                    while(count < artistMap.size()){
                        for(Element songData : artist.getAllElements()){
                            Elements links = songData.getAllElements().select("a[href]");
                            String idKey = "a" + String.valueOf(count) + String.valueOf(Math.random());
                            if(links.size()==0){
                                break;
                            }else{
                                for(int i=0; i<links.size();i++){
                                    songMap.put(idKey, links.get(i).attr("href"));
                                    midiMap.put(idKey, links.get(i+1).attr("href"));
                                    vidMap.put(idKey, links.get(i+2).attr("href"));
                                }
                            }
                        }
                        count++;
                    }
                }
                String stop = "stop";
            }
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
