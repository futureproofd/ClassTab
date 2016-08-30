package to.marcus.classtab.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.R;
import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.data.model.ArtistSorter;

/**
 * Created by marcus on 7/8/2016
 */
public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_BIG_ARTIST = 0;
    private static final int VIEW_NORMAL_ARTIST = 1;
    private static final int VIEW_LOADING = -1;
    private LinkedHashMap<Integer,Artist> mArtists;
    private final RecyclerViewArtistClickListener clickListener;
    private final int mNumColumns;
    private ArtistSorter.ArtistComparator mComparator;

    public ArtistAdapter(RecyclerViewArtistClickListener listener, int numColumns){
        this.clickListener = listener;
        mNumColumns = numColumns;
        mComparator = new ArtistSorter.ArtistComparator();
        mArtists = new LinkedHashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_BIG_ARTIST:
                return createBigArtistViewHolder(parent);
            case VIEW_NORMAL_ARTIST:
                return createNormalArtistViewHolder(parent);
            case VIEW_LOADING:
                //return new LoadingViewHolder(
                        //inflate
              //  );
        }
        return null;
        }

    private NormalArtistViewHolder createNormalArtistViewHolder(ViewGroup parent) {
        final View artistView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_artist,parent,false);
        final NormalArtistViewHolder artistsViewHolder = new NormalArtistViewHolder(artistView);
        artistView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickListener.onObjectClick(v,mArtists.get(artistsViewHolder.getAdapterPosition()).getId());
            }
        });
        return artistsViewHolder;
    }

    private BigArtistViewHolder createBigArtistViewHolder(ViewGroup parent) {
        final View artistView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_big_artist,parent,false);
        final BigArtistViewHolder artistsViewHolder = new BigArtistViewHolder(artistView);
        artistView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickListener.onObjectClick(v,mArtists.get(artistsViewHolder.getAdapterPosition()).getId());
            }
        });
        return artistsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_BIG_ARTIST:
                bindBigArtistView((BigArtistViewHolder)holder, position); //setText etc
                break;
            case VIEW_NORMAL_ARTIST:
                bindNormalArtistView((NormalArtistViewHolder)holder,position);
                break;
            case VIEW_LOADING:
                //bindLoadingView;
                break;
        }
    }

    private void bindBigArtistView(final BigArtistViewHolder holder, int position){
        holder.artistBigNameView.setText(mArtists.get(position).getName());
        holder.artistBigDateView.setText(mArtists.get(position).getDate());
        Context context = holder.artistBigImageView.getContext();
        Picasso.with(context).load(mArtists.get(position).getUrl())
                .placeholder(R.drawable.user96)
                .into(holder.artistBigImageView);
    }

    private void bindNormalArtistView(final NormalArtistViewHolder holder, int position){
        holder.artistNameView.setText(mArtists.get(position).getName());
        holder.artistDateView.setText(mArtists.get(position).getDate());
        Context context = holder.artistImageView.getContext();
        Picasso.with(context).load(mArtists.get(position).getUrl())
                .placeholder(R.drawable.user96)
                .into(holder.artistImageView);
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    @Override
    public int getItemViewType(int position){
        if(mArtists.get(position).getColumnSpan() == 2){
            return VIEW_BIG_ARTIST;
        }else if(position > 0){
            return VIEW_NORMAL_ARTIST;
        }else{
            return VIEW_LOADING;
        }
    }

    /*
    Main entry point from Activity, to set the list of artists
     */
    public void setArtists(LinkedHashMap<Integer,Artist> artists){
        sortArtists(artists);
        resizePopularArtist();
    }

    //After sorting, the Latest accessed record should always be first
    private void resizePopularArtist(){
        if(mArtists.size() > 0)
            mArtists.entrySet().iterator().next().getValue().setColumnSpan(2);
    }

    public int getItemColumnSpan(int position){
        return mArtists.get(position).getColumnSpan();
    }

    private void sortArtists(LinkedHashMap<Integer,Artist> artists){
        this.mArtists = (LinkedHashMap)mComparator.sortByAccessTime(artists);
        notifyDataSetChanged();
    }

    class NormalArtistViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_artist_name)TextView artistNameView;
        @BindView(R.id.txt_artist_date) TextView artistDateView;
        @BindView(R.id.img_artist) ImageView artistImageView;
        public NormalArtistViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class BigArtistViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_big_artist_name)TextView artistBigNameView;
        @BindView(R.id.txt_big_artist_date)TextView artistBigDateView;
        @BindView(R.id.img_big_artist) ImageView artistBigImageView;
        public BigArtistViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }

}
