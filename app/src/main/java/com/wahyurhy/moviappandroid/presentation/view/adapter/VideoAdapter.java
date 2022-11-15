package com.wahyurhy.moviappandroid.presentation.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.videos.ResultsItemVideos;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final List<ResultsItemVideos> resultsItemVideosList;
    private final Context context;
    private ResultsItemVideos resultsItemVideos;
    private OnItemClickListener onItemClickListener;

    public VideoAdapter(Context context) {
        this.context = context;
        resultsItemVideosList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ResultsItemVideos resultsItemVideos, int position);
    }

    public void add(ResultsItemVideos itemVideo) {
        resultsItemVideosList.add(itemVideo);
        notifyItemInserted(resultsItemVideosList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResultsItemVideos> resultsItemVideosList) {
        for (ResultsItemVideos resultsItemVideos : resultsItemVideosList) {
            Log.d("TAG", "addAll: " + resultsItemVideosList.get(0).getName());
            add(resultsItemVideos);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemVideosList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResultsItemVideos itemPopularActor) {
        int position = resultsItemVideosList.indexOf(itemPopularActor);
        if (position > -1) {
            resultsItemVideosList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResultsItemVideos getItem() {
        return resultsItemVideosList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        resultsItemVideos = resultsItemVideosList.get(position);
        holder.bind(resultsItemVideos, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemVideosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ANImageView mIbVideoTrailer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIbVideoTrailer = itemView.findViewById(R.id.ib_video_trailer);
        }

        public void bind(final ResultsItemVideos resultsItemVideos, final int position) {
            mIbVideoTrailer.setDefaultImageResId(R.drawable.bg_popular_actor_default);
            mIbVideoTrailer.setImageUrl("https://img.youtube.com/vi/" + resultsItemVideos.getKey() + "/sddefault.jpg");

            mIbVideoTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, resultsItemVideos, position);
                }
            });
        }

    }

}
