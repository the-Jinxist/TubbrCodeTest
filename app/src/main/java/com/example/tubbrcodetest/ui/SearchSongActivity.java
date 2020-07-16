package com.example.tubbrcodetest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.tubbrcodetest.Constants;
import com.example.tubbrcodetest.R;
import com.example.tubbrcodetest.new_model.RequestModel;
import com.example.tubbrcodetest.new_model.ItemsItem;
import com.example.tubbrcodetest.repo.SharedprefsRepo;
import com.example.tubbrcodetest.viewmodel.SpotifyViewModel;

import java.util.List;

import static android.view.View.GONE;

public class SearchSongActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchSongActivity";
    private String type;

    private ImageButton backButton;
    private EditText searchEditText;
    private ImageButton searchButton;
    private ImageView searchDummy;
    private ProgressBar searchProgress;
    private RecyclerView searchRecycler;

    private SpotifyViewModel viewModel;
    private SearchAdapter adapter;

    private String ACCESS_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song);

        ACCESS_TOKEN = SharedprefsRepo.getAccessToken(this);

        Intent startedIntent  = getIntent();
        type = startedIntent.getStringExtra("type");

        backButton = findViewById(R.id.search_song_back);
        searchEditText = findViewById(R.id.search_song_edit_text);
        searchButton = findViewById(R.id.search_song_search_button);
        searchDummy = findViewById(R.id.search_song_dummy);
        searchProgress = findViewById(R.id.search_song_progress);
        searchRecycler = findViewById(R.id.search_song_recycler);

        backButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (textView != null){
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    String searchText = textView.getText().toString();
                    searchSongs(searchText);
                }
            }
            return true;
        });

        viewModel = new ViewModelProvider(this).get(SpotifyViewModel.class);
        adapter = new SearchAdapter();

        searchRecycler.setHasFixedSize(true);

        bindData(viewModel.getSongData());
    }

    private void bindData(MutableLiveData<RequestModel> songData) {

        Log.d(TAG, "onCreate: Entry type: " + type);
        if (type.equals(Constants.SEARCH_NORMAL)){
            searchEditText.setHint(R.string.search_any_song);
        }else if(type.equals(Constants.SEARCH_20S)){
            searchEditText.setHint(R.string.search_any_20s_song);
        }

        songData.observe(this, requestModel -> {
            switch (requestModel.getState()){
                case IDLE:
                    searchProgress.setVisibility(GONE);
                    searchRecycler.setVisibility(GONE);
                    searchDummy.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    searchProgress.setVisibility(View.VISIBLE);
                    searchRecycler.setVisibility(GONE);
                    searchDummy.setVisibility(GONE);
                    break;
                case SUCCESS:
                    searchProgress.setVisibility(GONE);
                    searchRecycler.setVisibility(View.VISIBLE);
                    searchDummy.setVisibility(GONE);

                    loadDataIntoRecycler(requestModel.getTracks().getTracks().getItems());

                    break;

            }
        });

    }

    private void loadDataIntoRecycler(List<ItemsItem> items) {
       adapter.setItem(items);
       searchRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backButton){
            finish();
        }else if(view == searchButton){
            String text = searchEditText.getText().toString();
            searchSongs(text);
        }
    }

    private void searchSongs(String text){
        if (!text.isEmpty()){
            if (type.equals(Constants.SEARCH_NORMAL)){
                viewModel.loadSongs(text, ACCESS_TOKEN);
            }else {
                viewModel.load20sSongs(text, ACCESS_TOKEN);
            }

        }else{
            searchProgress.setVisibility(GONE);
            searchRecycler.setVisibility(GONE);
            searchDummy.setVisibility(View.VISIBLE);
        }
    }
}
