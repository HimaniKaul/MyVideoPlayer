package com.example.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Holder>
{
    private final ArrayList<VideoModel> videos;
    private final Context context;
    Activity activity;

    public VideoAdapter(Context applicationContext, ArrayList<VideoModel> arrayListCat,Activity activity)
    {
        this.context = applicationContext;
        this.videos = arrayListCat;
        this.activity=activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_video, parent, false);
        return new Holder(convertView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int i)
    {
        String s=videos.get(i).getStr_thum();
        /*Picasso.with(context)
                .load(s)
                .into(holder.imageView);*/

        Glide.with(context)
                .load(s)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent= new Intent(context,VideoActivity.class);
                 intent.putExtra("video",videos.get(i).getStr_path());
                 activity.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}