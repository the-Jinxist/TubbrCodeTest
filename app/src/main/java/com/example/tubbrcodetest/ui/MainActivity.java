package com.example.tubbrcodetest.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tubbrcodetest.R;
import com.example.tubbrcodetest.repo.SharedprefsRepo;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.tubbrcodetest.Constants.CLIENT_ID;
import static com.example.tubbrcodetest.Constants.REDIRECT_URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedprefsRepo.isLoggedIn(this)){
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        }

        loginText = findViewById(R.id.login_text);
        progressBar = findViewById(R.id.progress_main);

        loginText.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == loginText){
            startLogin();
        }
    }

    private void startLogin() {

        loginText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginInBrowser(this, request);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    String accessToken  = response.getAccessToken();
                    SharedprefsRepo.putAccessToken(this, accessToken);

                    startActivity(new Intent(this, SearchActivity.class));
                    finish();

                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(this, "Sorry, an error occurred: " + response.getError(), Toast.LENGTH_SHORT).show();
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    loginText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
            }

        }

    }
}
