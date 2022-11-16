package com.wahyurhy.moviappandroid.presentation.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.KnownForItem;

import java.util.ArrayList;
import java.util.List;

public class PopularActorDetailAdapter extends RecyclerView.Adapter<PopularActorDetailAdapter.ViewHolder> {

    private final List<KnownForItem> resultsItemKnownForList;
    private final Context context;
    private KnownForItem resultsItemKnownFor;
    private OnItemClickListener onItemClickListener;

    public PopularActorDetailAdapter(Context context) {
        this.context = context;
        resultsItemKnownForList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, KnownForItem knownForItem, int position);
    }

    public void add(KnownForItem itemKnownFor) {
        resultsItemKnownForList.add(itemKnownFor);
        notifyItemInserted(resultsItemKnownForList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<KnownForItem> resultsItemKnownForList) {
        for (KnownForItem resultsItemKnownFor : resultsItemKnownForList) {
            Log.d("TAG", "addAll: " + resultsItemKnownForList.get(0).getName());
            add(resultsItemKnownFor);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemKnownForList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(KnownForItem itemKnownFor) {
        int position = resultsItemKnownForList.indexOf(itemKnownFor);
        if (position > -1) {
            resultsItemKnownForList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private KnownForItem getItem() {
        return resultsItemKnownForList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_known_for, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularActorDetailAdapter.ViewHolder holder, int position) {
        resultsItemKnownFor = resultsItemKnownForList.get(position);
        holder.bind(resultsItemKnownFor, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemKnownForList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ANImageView mPosterPath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterPath = itemView.findViewById(R.id.poster_path);
        }

        public void bind(final KnownForItem resultsItemKnownFor, final int position) {
            mPosterPath.setDefaultImageResId(R.drawable.bg_popular_movie);
            mPosterPath.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_popular_actor));
            mPosterPath.setImageUrl("https://image.tmdb.org/t/p/w300" + resultsItemKnownFor.getPosterPath());

            mPosterPath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, resultsItemKnownFor, position);
                }
            });
        }

    }

}
