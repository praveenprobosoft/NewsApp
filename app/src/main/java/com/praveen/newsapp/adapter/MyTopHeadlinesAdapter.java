package com.praveen.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.praveen.newsapp.R;
import com.praveen.newsapp.activity.DetailScreen;
import com.praveen.newsapp.model.ArticleModel;

import java.util.List;

public class MyTopHeadlinesAdapter extends RecyclerView.Adapter<MyTopHeadlinesAdapter.MyViewHolder>{
    List<ArticleModel> articleModelList;
    Context context;

    public MyTopHeadlinesAdapter(List<ArticleModel> articleModelList, Context context) {
        this.articleModelList = articleModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row, parent, false);

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

        holder.linearrow.setOnClickListener(v -> {
            Toast.makeText(context,articleModelList.get(position).getSource().getName(),Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, DetailScreen.class);
            intent.putExtra("articleurl",articleModelList.get(position).getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleModelList.size();
    }
/*


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleModelList = articleModelList;
                } else {

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleModelList = (List<ArticleModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/

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
