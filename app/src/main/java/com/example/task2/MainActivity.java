package com.example.task2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.task2.data.model.user.Info;
import com.example.task2.data.model.user.Picture;
import com.example.task2.data.model.user.Result;
import com.example.task2.data.model.user.Results;
import com.example.task2.data.model.user.Root;
import com.example.task2.data.repasiyory.local.room.db.database;
import com.example.task2.data.repasiyory.local.room.entity.user;
import com.example.task2.data.repasiyory.user.ResultApiClient;
import com.example.task2.data.repasiyory.user.ResultService;
import com.example.task2.databinding.ActivityMainBinding;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
   ActivityMainBinding binding;
   database db;
   private String imageUrl;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=database.getInstance(this);

        handler = new Handler(Looper.getMainLooper());


        binding.nextUser.setOnClickListener(nextUserOnClickListener);
        binding.seeCollection.setOnClickListener(seeCollectionOnClickListener);
        binding.AddUserToCollection.setOnClickListener(AddUserToCollectionOnClickListener);
        binding.buttonDelete.setOnClickListener(DeleteAllUsersFromDatabase);
    }

    private void setButtonsEnabled(boolean enabled) {
        binding.nextUser.setEnabled(enabled);
        binding.seeCollection.setEnabled(enabled);
        binding.AddUserToCollection.setEnabled(enabled);
        binding.buttonDelete.setEnabled(enabled);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = ResultApiClient.getClient();
        ResultService resultService = retrofit.create(ResultService.class);
        Call<Root> callAsync = resultService.getResults();
        callAsync.enqueue(resultsCallAsyncCallBack);
    }

    Callback<Root> resultsCallAsyncCallBack = new Callback<Root>() {
        @Override
        public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
            Root root = response.body();
            String fname=root.results.get(0).name.first;
            String lname = root.results.get(0).getName().last;
            String age = String.valueOf(root.results.get(0).dob.age);
            String city = root.results.get(0).getLocation().city;
            String country = root.results.get(0).getLocation().country;
            String email = root.results.get(0).getEmail();
             imageUrl = root.results.get(0).picture.large;
            binding.textViewFName.setText(fname);
            binding.textViewLName.setText(lname);
            binding.textView7.setText(age);
            binding.textViewCity.setText(city);
            binding.textViewCuntry.setText(country);
            binding.textViewEmail.setText(email);

            Glide.with(MainActivity.this)
                    .load(imageUrl)
                    .into(binding.imageView);

        }




        @Override
        public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
            Log.e(TAG,"OnFailure: ",throwable);

        }
    };

    View.OnClickListener nextUserOnClickListener = view -> {
        setButtonsEnabled(false);
        Retrofit retrofit = ResultApiClient.getClient();
        ResultService resultService = retrofit.create(ResultService.class);
        Call<Root> callAsync = resultService.getResults();
        callAsync.enqueue(resultsCallAsyncCallBack);

        handler.postDelayed(() -> setButtonsEnabled(true), 2000);

    };

    View.OnClickListener seeCollectionOnClickListener = view -> {
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        startActivity(intent);
    };

    View.OnClickListener AddUserToCollectionOnClickListener = view -> {


        String firstName = binding.textViewFName.getText().toString();
        String lastName = binding.textViewLName.getText().toString();
        int age;
        try {
            age = Integer.parseInt(binding.textView7.getText().toString());
        } catch (NumberFormatException e) {
            age = 0;
        }
        String city = binding.textViewCity.getText().toString();
        String country = binding.textViewCuntry.getText().toString();
        String email = binding.textViewEmail.getText().toString();
       // String imageUrl = "";
      //  imageUrl = getImageUrlFromImageView(binding.imageView);

        user user1 = new user();
        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        user1.setAge(age);
        user1.setCity(city);
        user1.setCountry(country);
        user1.setEmail(email);
        user1.setImageUrl(imageUrl);

            new Thread(() -> {
                user existingUser = db.userDao().getUserByEmail(email);
                if (existingUser == null) {
                    db.userDao().insert(user1);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "User added to collection", Toast.LENGTH_SHORT).show());

                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "User already exists in collection", Toast.LENGTH_SHORT).show());

                }
            }).start();

    };
    View.OnClickListener DeleteAllUsersFromDatabase = view -> {
        new Thread(() -> {
            db.userDao().deleteAllUsers();
            runOnUiThread(() -> Toast.makeText(MainActivity.this, "All users deleted from database", Toast.LENGTH_SHORT).show());
        }).start();
    };
        private String getImageUrlFromImageView(ImageView imageView) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            }
            return "";
        }
    };

