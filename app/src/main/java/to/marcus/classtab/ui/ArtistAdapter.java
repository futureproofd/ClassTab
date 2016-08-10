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
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistsViewHolder>{

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
    public ArtistAdapter.ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View artistView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_artist,parent,false);
        final ArtistsViewHolder artistsViewHolder = new ArtistsViewHolder(artistView);
        artistView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickListener.onObjectClick(v,mArtists.get(artistsViewHolder.getAdapterPosition()).getId());
            }
        });
        return artistsViewHolder;
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistsViewHolder holder, int position) {
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

    /*
    Main entry point from Activity, to set the list of artists
     */
    public void setArtists(LinkedHashMap<Integer,Artist> artists){
        sortArtists(artists);
        resizePopularArtist();
    }

    class ArtistsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_artist_name)TextView artistNameView;
        @BindView(R.id.txt_artist_date) TextView artistDateView;
        @BindView(R.id.img_artist) ImageView artistImageView;
        public ArtistsViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //After sorting, the Latest accessed record should always be first
    private void resizePopularArtist(){
        mArtists.entrySet().iterator().next().getValue().setColumnSpan(2);
    }

    public int getItemColumnSpan(int position){
        return mArtists.get(position).getColumnSpan();
    }

    private void sortArtists(LinkedHashMap<Integer,Artist> artists){
        this.mArtists = (LinkedHashMap)mComparator.sortByAccessTime(artists);
        notifyDataSetChanged();
    }


}
