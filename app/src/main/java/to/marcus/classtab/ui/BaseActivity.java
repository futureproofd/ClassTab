package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.R;

/**
 * Created by marcus on 7/4/2016
 */
public class BaseActivity extends AppCompatActivity {
    //// TODO: 7/8/2016 Nav framework components here
    @BindView(R.id.toolbar) Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        initToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initToolBar(){
        setSupportActionBar(mToolBar);
    }
}
