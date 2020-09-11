package com.example.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.gadsleaderboard.network.GoogleFormApi;

import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitionActivity extends AppCompatActivity {

    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText githubLinkET;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);

        Toolbar submitAppToolbar = findViewById(R.id.submit_app_toolbar);
        setSupportActionBar(submitAppToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firstNameET = findViewById(R.id.first_name_et);
        lastNameET = findViewById(R.id.last_name_et);
        emailAddressET = findViewById(R.id.email_et);
        githubLinkET = findViewById(R.id.github_link_et);
        submitBtn = findViewById(R.id.submission_form_submit_btn);

        submitBtn.setOnClickListener(
            (view) -> {
                Dialog dialog = new Dialog(this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.confirm_submission_dialog);
                Button exitBtn = dialog.findViewById(R.id.exit_btn);
                exitBtn.setOnClickListener(
                    (view1) -> {
                        dialog.dismiss();
                    }
                );
                Button yesBtn = dialog.findViewById(R.id.yes_btn);
                yesBtn.setOnClickListener(
                    (view2) -> {
                        submitData();
                        dialog.dismiss();
                    }
                );
                dialog.show();
            }
        );
    }

    void submitData() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(GoogleFormApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GoogleFormApi client =  retrofit.create(GoogleFormApi.class);
        Call<Void> call = client.submitProject(
                firstNameET.getText().toString(),
                lastNameET.getText().toString(),
                emailAddressET.getText().toString(),
                githubLinkET.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() > 299) {
                    displayErrorDialog();
                } else {
                    Dialog dialog = new Dialog(SubmitionActivity.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.correct_submission_dialog);
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                displayErrorDialog();
            }
        });
    }

    void displayErrorDialog() {
        Dialog dialog = new Dialog(SubmitionActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wrong_submission_dialog);
        dialog.show();
    }
}
