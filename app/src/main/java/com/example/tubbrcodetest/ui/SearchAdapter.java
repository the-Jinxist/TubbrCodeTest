package com.example.tubbrcodetest.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tubbrcodetest.R;
import com.example.tubbrcodetest.new_model.ItemsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholders> {

    private List<ItemsItem> item;

    public void setItem(List<ItemsItem> collectedItem){
        item = collectedItem;
        notifyDataSetChanged();
    }

    class Viewholders extends RecyclerView.ViewHolder{

        private TextView artistName;
        private TextView songTitle;
        private TextView releasedDate;

        Viewholders(@NonNull View itemView) {
            super(itemView);

            artistName = itemView.findViewById(R.id.search_item_artist_name);
            songTitle = itemView.findViewById(R.id.search_item_name);
            releasedDate = itemView.findViewById(R.id.search_item_released_date);
        }

        void bindData(ItemsItem itemsItem){
            //Gets first artist, in most case the lead artist
            artistName.setText(itemsItem.getArtists().get(0).getName());
            releasedDate.setText(itemsItem.getAlbum().getReleaseDate());
            songTitle.setText(itemsItem.getName());

        }
    }

    @NonNull
    @Override
    public SearchAdapter.Viewholders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.Viewholders holder, int position) {
        ItemsItem currentItem = item.get(position);
        holder.bindData(currentItem);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
