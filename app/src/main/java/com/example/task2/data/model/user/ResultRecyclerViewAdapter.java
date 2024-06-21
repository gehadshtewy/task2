package com.example.task2.data.model.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task2.R;
import com.example.task2.UsersActivity;
import com.example.task2.data.repasiyory.local.room.entity.user;
import com.example.task2.databinding.UsersItemBinding;

import java.util.List;

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultsViewHolder>{
    Context context;
    private List<user> dataSet;

    public ResultRecyclerViewAdapter(Context context, List<user> userList) {
        this.context = context;
        this.dataSet = userList;
    }

    @NonNull
    @Override
    public ResultRecyclerViewAdapter.ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater im = LayoutInflater.from(context);
       View view = im.inflate(R.layout.users_item,parent,false);
       return new ResultRecyclerViewAdapter.ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRecyclerViewAdapter.ResultsViewHolder holder, int position) {
        Log.d("jihad",dataSet.get(position).firstName);
        Glide.with(holder.itemView).load(dataSet.get(position).getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder)
                        .error(R.drawable.error))
                .into(holder.imageUrl);
        holder.firstName.setText(dataSet.get(position).firstName);
        holder.lastName.setText(dataSet.get(position).lastName);
        holder.city.setText(dataSet.get(position).city);
        holder.country.setText(dataSet.get(position).country);



    }

    @Override
    public int getItemCount() {
        return dataSet!=null? dataSet.size():0;
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{
        ImageView imageUrl;
        TextView firstName,lastName,city,country;
        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.imageViewUserIcon);
            firstName = itemView.findViewById(R.id.textViewUserFName);
            lastName = itemView.findViewById(R.id.textViewUserLName);
            city = itemView.findViewById(R.id.textViewUserCity);
            country = itemView.findViewById(R.id.textViewUserCuntry);
        }
    }
}
