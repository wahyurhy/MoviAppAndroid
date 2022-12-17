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
import com.wahyurhy.moviappandroid.core.data.remote.response.searchmovie.ResultsItemSearchMovie;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {

    private final List<ResultsItemSearchMovie> resultsItemSearchMoviesList;
    private final Context context;
    private ResultsItemSearchMovie resultsItemSearchMovies;
    private OnItemClickListener onItemClickListener;

    public SearchMovieAdapter(Context context) {
        this.context = context;
        resultsItemSearchMoviesList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ResultsItemSearchMovie resultsItemSearchMovies, int position);
    }

    public void add(ResultsItemSearchMovie itemSearchMovie) {
        resultsItemSearchMoviesList.add(itemSearchMovie);
        notifyItemInserted(resultsItemSearchMoviesList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResultsItemSearchMovie> resultsItemSearchMoviesList) {
        for (ResultsItemSearchMovie resultsItemSearchMovies : resultsItemSearchMoviesList) {
            Log.d("TAG", "addAll: " + resultsItemSearchMoviesList.get(0).getTitle());
            add(resultsItemSearchMovies);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemSearchMoviesList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResultsItemSearchMovie itemSearchMovie) {
        int position = resultsItemSearchMoviesList.indexOf(itemSearchMovie);
        if (position > -1) {
            resultsItemSearchMoviesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResultsItemSearchMovie getItem() {
        return resultsItemSearchMoviesList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.ViewHolder holder, int position) {
        resultsItemSearchMovies = resultsItemSearchMoviesList.get(position);
        holder.bind(resultsItemSearchMovies, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemSearchMoviesList.size();
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
            mRelativeLayoutMain = itemView.findViewById(R.id.lyt_search_item);
        }

        public void bind(final ResultsItemSearchMovie resultsItemSearchMovies, final int position) {
            mPosterMovie.setDefaultImageResId(R.drawable.logo_app);
            mPosterMovie.setImageUrl("https://image.tmdb.org/t/p/w300" + resultsItemSearchMovies.getBackdropPath());
            mTitleMovie.setText(resultsItemSearchMovies.getTitle());
            mVoteAverage.setText(String.valueOf(resultsItemSearchMovies.getVoteAverage()));
            if (resultsItemSearchMovies.getReleaseDate().equals("")) {
                mYear.setText("");
            } else {
                mYear.setText(resultsItemSearchMovies.getReleaseDate().substring(0, 4));
            }

            mRelativeLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, resultsItemSearchMovies, position);
                }
            });
        }

    }

}
