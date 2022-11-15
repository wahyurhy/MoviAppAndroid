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
import com.wahyurhy.moviappandroid.core.data.remote.response.popularmovie.ResultsItemPopularMovie;

import java.util.ArrayList;
import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {

    private final List<ResultsItemPopularMovie> resultsItemPopularMovieList;
    private final Context context;
    private ResultsItemPopularMovie resultsItemPopularMovie;
    private OnItemClickListener onItemClickListener;

    public PopularMovieAdapter(Context context) {
        this.context = context;
        resultsItemPopularMovieList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ResultsItemPopularMovie resultsItemPopularMovie, int position);
    }

    public void add(ResultsItemPopularMovie itemTPopularActor) {
        resultsItemPopularMovieList.add(itemTPopularActor);
        notifyItemInserted(resultsItemPopularMovieList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResultsItemPopularMovie> resultsItemPopularMovieList) {
        for (ResultsItemPopularMovie resultsItemPopularMovie : resultsItemPopularMovieList) {
            Log.d("TAG", "addAll: " + resultsItemPopularMovieList.get(0).getTitle());
            add(resultsItemPopularMovie);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemPopularMovieList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResultsItemPopularMovie itemPopularActor) {
        int position = resultsItemPopularMovieList.indexOf(itemPopularActor);
        if (position > -1) {
            resultsItemPopularMovieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResultsItemPopularMovie getItem() {
        return resultsItemPopularMovieList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieAdapter.ViewHolder holder, int position) {
        resultsItemPopularMovie = resultsItemPopularMovieList.get(position);
        holder.bind(resultsItemPopularMovie, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemPopularMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ANImageView mPosterPath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterPath = itemView.findViewById(R.id.poster_path);
        }

        public void bind(final ResultsItemPopularMovie resultsItemPopularMovie, final int position) {
            mPosterPath.setDefaultImageResId(R.drawable.bg_popular_movie);
            mPosterPath.setImageUrl("https://image.tmdb.org/t/p/w300" + resultsItemPopularMovie.getPosterPath());
        }

    }

}
