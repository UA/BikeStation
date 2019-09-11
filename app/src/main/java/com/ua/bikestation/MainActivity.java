package com.ua.bikestation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ua.bikestation.api.Services;
import com.ua.bikestation.adapter.BikeStationAdapter;
import com.ua.bikestation.listener.ItemClickListener;
import com.ua.bikestation.model.BikeStation;
import com.ua.bikestation.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    Services services;
    RecyclerView recyclerView;
    BikeStationAdapter adapter;
    List<BikeStation> list = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recyclerView);
        swipeRefreshLayout= findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        adapter = new BikeStationAdapter(this, list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setItemClickListener(this);

        getData();
    }

    private void getData() {

        services = ApiClient.createService(Services.class);
        Call<List<BikeStation>> call = services.getBikeStation();

        call.enqueue(new Callback<List<BikeStation>>() {
            @Override
            public void onResponse(Call<List<BikeStation>> call, Response<List<BikeStation>> response) {
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                assert response.body() != null;
                for (BikeStation station:response.body()) {
                    list.add(station);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BikeStation>> call, Throwable t) {
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(BikeStation station, int position) {
        Toast.makeText(this,station.getStationName() + " Clicked.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        list.clear();
        getData();
    }
}
