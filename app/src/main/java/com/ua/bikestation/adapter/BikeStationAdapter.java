package com.ua.bikestation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ua.bikestation.BR;
import com.ua.bikestation.R;
import com.ua.bikestation.listener.ItemClickListener;
import com.ua.bikestation.model.BikeStation;

import java.util.ArrayList;
import java.util.List;

public class BikeStationAdapter extends RecyclerView.Adapter<BikeStationAdapter.StationViewHolder>  implements Filterable {
    private List<BikeStation> mStationList;
    private List<BikeStation> mFilteredStationList;
    private LayoutInflater inflater;
    private ItemClickListener mItemClickListener;

    public BikeStationAdapter(Context context, List<BikeStation> stations){
        inflater = LayoutInflater.from(context);
        this.mStationList = stations;
        this.mFilteredStationList = stations;
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_station_card,parent,false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        BikeStation station = mFilteredStationList.get(position);
        holder.setData(station,position);
    }

    @Override
    public int getItemCount() {
        return mFilteredStationList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    mFilteredStationList = mStationList;

                } else {

                    ArrayList<BikeStation> tempFilteredList = new ArrayList<>();

                    for (BikeStation station : mStationList) {
                        // search for station name
                        if (station.getStationName().toLowerCase().contains(searchString)) {
                            tempFilteredList.add(station);
                        }
                    }

                    mFilteredStationList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredStationList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredStationList = (ArrayList<BikeStation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding binding;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
           binding = DataBindingUtil.bind(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void setData(final BikeStation station, final int position) {
            binding.setVariable(BR.station,station);
            binding.executePendingBindings();
            itemView.setOnClickListener(v -> mItemClickListener.onItemClick(station,position));
        }
    }
}
