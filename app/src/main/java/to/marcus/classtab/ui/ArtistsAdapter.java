package to.marcus.classtab.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public ArtistsAdapter.ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View artistView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_artist,parent,false);
        return new ArtistsViewHolder(artistView);
    }

    @Override
    public void onBindViewHolder(ArtistsAdapter.ArtistsViewHolder holder, int position) {
        holder.artistNameView.setText(mArtists.get(position).getName());
        holder.artistDateView.setText(mArtists.get(position).getDate());
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
        public ArtistsViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
