package to.marcus.classtab.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.R;
import to.marcus.classtab.data.model.Artist;

/**
 * Created by marcus on 7/8/2016
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>{

    private LinkedHashMap<Integer,Artist> mArtists;
    private final RecyclerViewArtistClickListener clickListener;

    public ArtistsAdapter(RecyclerViewArtistClickListener listener){
        this.clickListener = listener;
    }

    @Override
    public ArtistsAdapter.ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(ArtistsAdapter.ArtistsViewHolder holder, int position) {
        holder.artistNameView.setText(mArtists.get(position).getName());
        holder.artistDateView.setText(mArtists.get(position).getDate());
        Context context = holder.artistImageView.getContext();
        Picasso.with(context).load(mArtists.get(position).getUrl())
                .into(holder.artistImageView);
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    public void setArtists(LinkedHashMap<Integer,Artist> artists){
        this.mArtists = artists;
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
}
