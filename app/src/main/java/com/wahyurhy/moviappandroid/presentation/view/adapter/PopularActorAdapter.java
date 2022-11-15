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
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.ResultsItemPopularActor;

import java.util.ArrayList;
import java.util.List;

public class PopularActorAdapter extends RecyclerView.Adapter<PopularActorAdapter.ViewHolder> {

    private final List<ResultsItemPopularActor> resultsItemPopularActorList;
    private final Context context;
    private ResultsItemPopularActor resultsItemPopularActor;
    private OnItemClickListener onItemClickListener;

    public PopularActorAdapter(Context context) {
        this.context = context;
        resultsItemPopularActorList = new ArrayList<>();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ResultsItemPopularActor resultsItemPopularActor, int position);
    }

    public void add(ResultsItemPopularActor itemTPopularActor) {
        resultsItemPopularActorList.add(itemTPopularActor);
        notifyItemInserted(resultsItemPopularActorList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResultsItemPopularActor> resultsItemPopularActorList) {
        for (ResultsItemPopularActor resultsItemPopularActor : resultsItemPopularActorList) {
            Log.d("TAG", "addAll: " + resultsItemPopularActorList.get(0).getName());
            add(resultsItemPopularActor);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        resultsItemPopularActorList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResultsItemPopularActor itemPopularActor) {
        int position = resultsItemPopularActorList.indexOf(itemPopularActor);
        if (position > -1) {
            resultsItemPopularActorList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResultsItemPopularActor getItem() {
        return resultsItemPopularActorList.get(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularActorAdapter.ViewHolder holder, int position) {
        resultsItemPopularActor = resultsItemPopularActorList.get(position);
        holder.bind(resultsItemPopularActor, position);
    }

    @Override
    public int getItemCount() {
        return resultsItemPopularActorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ANImageView mProfilePath;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProfilePath = itemView.findViewById(R.id.profile_path);
            mName = itemView.findViewById(R.id.name_actor);
        }

        public void bind(final ResultsItemPopularActor resultsItemPopularActor, final int position) {
            mProfilePath.setDefaultImageResId(R.drawable.bg_popular_actor_default);
            mProfilePath.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_popular_actor));
            mProfilePath.setImageUrl("https://image.tmdb.org/t/p/w300" + resultsItemPopularActor.getProfilePath());
            mName.setText(resultsItemPopularActor.getName());
        }

    }

}
