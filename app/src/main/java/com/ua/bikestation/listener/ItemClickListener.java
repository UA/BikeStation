package com.ua.bikestation.listener;

import com.ua.bikestation.model.BikeStation;

public interface ItemClickListener {
    void onItemClick(BikeStation station, int position);
}