package com.praveen.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.praveen.newsapp.MyListner;
import com.praveen.newsapp.R;
import com.praveen.newsapp.model.ArticleModel;

import java.util.List;

public class MyTopHeadlinesNewAdapter extends RecyclerView.Adapter<MyTopHeadlinesNewAdapter.MyViewHolder>{
    List<ArticleModel> articleModelList;
    Context context;

    public MyTopHeadlinesNewAdapter(List<ArticleModel> articleModelList, Context context) {
        this.articleModelList = articleModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_new_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ArticleModel articleModel = articleModelList.get(position);
        holder.title.setText(articleModel.getTitle());
        holder.description.setText(articleModel.getDescription());
        holder.network.setText(articleModel.getSource().getName());
        Glide.with(context)
                .load(articleModel.getUrlToImage())
                .into(holder.newsImage);

        holder.linearrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyListner)context).callback(articleModelList.get(position).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,network;
        ImageView newsImage;
        LinearLayout linearrow;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txttitle);
            description = (TextView) itemView.findViewById(R.id.txtdesc);
            network = (TextView) itemView.findViewById(R.id.txtNetwork);
            newsImage = (ImageView) itemView.findViewById(R.id.newimage);
            linearrow = (LinearLayout) itemView.findViewById(R.id.linearrow);
        }

    }
}
