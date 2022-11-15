package com.wahyurhy.moviappandroid.presentation.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public TopRatedMovieAdapter(Context context) {
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

        private ANImageView mPosterMovie;
        private TextView mTitleMovie, mVoteAverage, mYear;
        private RelativeLayout mRelativeLayoutMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterMovie = itemView.findViewById(R.id.poster_image);
            mTitleMovie = itemView.findViewById(R.id.tv_title);
            mVoteAverage = itemView.findViewById(R.id.tv_vote_average);
            mYear = itemView.findViewById(R.id.tv_year);
            mRelativeLayoutMain = itemView.findViewById(R.id.lyt_top_rated_movie_item);
        }

        public void bind(final ResultsItemTopRatedMovie resultsItemTopRatedMovie, final int position) {
            mPosterMovie.setDefaultImageResId(R.drawable.logo_app);
            mPosterMovie.setImageUrl("https://image.tmdb.org/t/p/w300" + resultsItemTopRatedMovie.getBackdropPath());
            mTitleMovie.setText(resultsItemTopRatedMovie.getTitle());
            mVoteAverage.setText(String.valueOf(resultsItemTopRatedMovie.getVoteAverage()));
            mYear.setText(resultsItemTopRatedMovie.getReleaseDate().substring(0, 4));

            mRelativeLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, resultsItemTopRatedMovie, position);
                }
            });
        }

    }

}
