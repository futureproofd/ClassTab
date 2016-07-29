package to.marcus.classtab.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.R;
import to.marcus.classtab.data.model.Tab;

/**
 * Created by mplienegger on 7/21/2016
 */
public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder>{
    private LinkedHashMap<Integer,Tab> mTabs;
    private final RecyclerViewTabClickListener clickListener;

    public TabAdapter(RecyclerViewTabClickListener listener){
        this.clickListener = listener;
        mTabs = new LinkedHashMap<>();
    }

    @Override
    public TabAdapter.TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View tabView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_tab,parent,false);
        final TabViewHolder tabViewHolder = new TabViewHolder(tabView);
        tabView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickListener.onObjectClick(v,mTabs.get(tabViewHolder.getAdapterPosition()).getFile());
            }
        });
        return tabViewHolder;
    }

    @Override
    public void onBindViewHolder(TabAdapter.TabViewHolder holder, int position) {
        holder.tabNameView.setText(mTabs.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mTabs.size();
    }

    public void setTabs(LinkedHashMap<Integer,Tab> tabs){
        this.mTabs = tabs;
    }

    class TabViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_tab_name)TextView tabNameView;
        @BindView(R.id.img_tab)
        ImageView artistImageView;
        public TabViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
