package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.R;

/**
 * Created by mplienegger on 7/21/2016
 */
public class TabActivity extends AppCompatActivity {

    @BindView (R.id.txt_tab_view) TextView mTabView;
    @Override

    //// TODO: 7/21/2016 make a clean textview that formats nicely 
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        String tab = new String(getIntent().getByteArrayExtra("TABEXTRA"));
        mTabView.setText(tab);
    }
}
