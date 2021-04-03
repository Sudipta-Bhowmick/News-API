package com.sbsoft.firstapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

class News{
    String headLine;
    String linkToImage;
    String newsLink;
    String author;
    News(String headLine,String linkToImage,String newsLink,String author){
        this.headLine=headLine;
        this.linkToImage=linkToImage;
        this.newsLink=newsLink;
        this.author=author;
    }
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<News> list;
    LayoutInflater inflater;

    RecyclerAdapter(Context context, ArrayList<News> newsArrayList){
        this.context=context;
        this.list=newsArrayList;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root_view=inflater.inflate(R.layout.sanple_row,parent,false);
        root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return new MyViewHolder(root_view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView_headLine.setText(list.get(position).headLine);
        holder.tv_author.setText(list.get(position).author);
        Glide.with(context).load(list.get(position).linkToImage).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imageView_header);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }





    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_headLine;
        TextView tv_author;
        ImageView imageView_header;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_headLine=itemView.findViewById(R.id.tv_sampleText);
            tv_author=itemView.findViewById(R.id.tv_author);
            imageView_header=itemView.findViewById(R.id.image_header);
            progressBar=itemView.findViewById(R.id.progressbar);
        }
    }
}
