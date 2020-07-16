package com.example.tubbrcodetest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tubbrcodetest.Constants;
import com.example.tubbrcodetest.R;
import com.example.tubbrcodetest.repo.SharedprefsRepo;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logoutButton;
    private Button searchButton;
    private Button search20sButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        logoutButton = findViewById(R.id.logout_button);
        searchButton = findViewById(R.id.search_button);
        search20sButton = findViewById(R.id.search_20s_button);

        logoutButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        search20sButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       if(view == logoutButton){
           SharedprefsRepo.logout(this);

       }else if (view == searchButton){
           proceed(Constants.SEARCH_NORMAL);

       }else if (view == search20sButton){
           proceed(Constants.SEARCH_20S);
       }
    }

    private void proceed(String type){
        Intent intent = new Intent(this, SearchSongActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}
