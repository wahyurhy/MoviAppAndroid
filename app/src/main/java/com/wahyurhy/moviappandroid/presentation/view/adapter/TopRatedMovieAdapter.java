package com.wahyurhy.moviappandroid.presentation.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.toprated.ResultsItemTopRatedMovie;

import java.util.ArrayList;
import java.util.List;

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.ViewHolder> {

    private final List<ResultsItemTopRatedMovie> resultsItemTopRatedMovieList;
    private final Context context;
    private ResultsItemTopRatedMovie resultsItemTopRatedMovie;
    private OnItemClickListener onItemClickListener;

    public TopRatedMovieAdapter( Context context) {
        this.context = context;
        resultsItemTopRatedMovieList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ResultsItemTopRatedMovie resultsItemTopRatedMovie, int position);
    }

    public void add(ResultsItemTopRatedMovie itemTopRatedMovie) {
        resultsItemTopRatedMovieList.add(itemTopRatedMovie);
        notifyItemInserted(resultsItemTopRatedMovieList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResultsItemTopRatedMovie> resultsItemTopRatedMovieList) {
        for (ResultsItemTopRatedMovie resultsItemTopRatedMovie : resultsItemTopRatedMovieList) {
            Log.d("TAG", "addAll: " + resultsItemTopRatedMovieList.get(0).getTitle());
            add(resultsItemTopRatedMovie);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemTopRatedMovieList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResultsItemTopRatedMovie itemTopRatedMovie) {
        int position = resultsItemTopRatedMovieList.indexOf(itemTopRatedMovie);
        if (position > -1) {
            resultsItemTopRatedMovieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResultsItemTopRatedMovie getItem() {
        return resultsItemTopRatedMovieList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_rated_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMovieAdapter.ViewHolder holder, int position) {
        resultsItemTopRatedMovie = resultsItemTopRatedMovieList.get(position);
        holder.bind(resultsItemTopRatedMovie, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemTopRatedMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private ANImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            //imageView = view.findViewById(R.id.rv_top_rated_movie);
        }

        public void bind(final ResultsItemTopRatedMovie resultsItemTopRatedMovie, final int position) {
            Log.d("TAG", "bind: " + resultsItemTopRatedMovie.getTitle());
        }

    }

}
