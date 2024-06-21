package com.example.task2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.data.model.user.ResultRecyclerViewAdapter;
import com.example.task2.data.repasiyory.local.room.db.database;
import com.example.task2.data.repasiyory.local.room.entity.user;
import com.example.task2.databinding.ActivityUsersBinding;

import java.util.List;

public class UsersActivity extends AppCompatActivity {
    ActivityUsersBinding binding;
    private ResultRecyclerViewAdapter resultRecyclerViewAdapter;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = database.getInstance(this);
        List<user> userList = db.userDao().getAllUsers();
        Log.d("mahmod", userList.get(0).imageUrl);
        RecyclerView recyclerView = binding.recyclerviewUsers;
        resultRecyclerViewAdapter = new ResultRecyclerViewAdapter(this,userList);
        recyclerView.setAdapter(resultRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}