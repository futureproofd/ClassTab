package to.marcus.classtab.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by marcus on 7/8/2016
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>{

    private HashMap<String,String> mArtists;

    @Override
    public ArtistsAdapter.ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ArtistsAdapter.ArtistsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ArtistsViewHolder extends RecyclerView.ViewHolder{

        public ArtistsViewHolder(View view){
            super(view);

        }
    }
}
