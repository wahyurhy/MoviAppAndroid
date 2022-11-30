package com.wahyurhy.moviappandroid.presentation.view.detail;

import static com.wahyurhy.moviappandroid.utils.Utils.setSystemBarFitWindow;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.widget.ANImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.detailmovie.ResponseDetailMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.videos.ResponseVideos;
import com.wahyurhy.moviappandroid.core.data.remote.response.videos.ResultsItemVideos;
import com.wahyurhy.moviappandroid.presentation.view.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class DetailMovieActivity extends AppCompatActivity implements VideoAdapter.OnItemClickListener {

    private ANImageView mIvDetailMovie;
    private ImageButton mIbBack;
    private TextView mTvVoteAverage;
    private TextView mTvTitle;
    private TextView mTvRuntime;
    private TextView mVoteAverage;
    private TextView mGenre1;
    private TextView mGenre2;
    private TextView mGenre3;
    private TextView mTvOverviewContent;
    private RecyclerView mRvVideoTrailer;

    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initialize();
        initView();
        initExtras();
    }

    private void loadDataVideo(String id) {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/" + id + "/videos")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .setTag("loadDataVideo")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponseAndObject(ResponseVideos.class, new OkHttpResponseAndParsedRequestListener<ResponseVideos>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponseVideos response) {
                        Gson gson = new Gson();
                        List<ResultsItemVideos> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        videoAdapter = new VideoAdapter(DetailMovieActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailMovieActivity.this);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        mRvVideoTrailer.setLayoutManager(linearLayoutManager);
                        mRvVideoTrailer.setHasFixedSize(true);
                        mRvVideoTrailer.setAdapter(videoAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemVideos>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            videoAdapter.addAll(dataResultItem);
                            videoAdapter.setOnItemClickListener(DetailMovieActivity.this);
                        } else {
                            mRvVideoTrailer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DetailMovieActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                            // get parsed error object (If ApiError is your class)

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }

    private void initExtras() {
        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) {
            String id = extras.getString("id_extra");
            loadDataDetailMovie(id);
            loadDataVideo(id);
        }
    }

    private void loadDataDetailMovie(String id) {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/" + id)
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .setTag("loadDataDetailMovie")
                .setPriority(Priority.HIGH)
                .build()
                .getAsOkHttpResponseAndObject(ResponseDetailMovie.class, new OkHttpResponseAndParsedRequestListener<ResponseDetailMovie>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponseDetailMovie response) {
                        mIvDetailMovie.setImageUrl("https://image.tmdb.org/t/p/w780" + response.getBackdropPath());
                        mTvTitle.setText(response.getTitle());

                        String resultRuntime = convertToHour(response);

                        mTvRuntime.setText(resultRuntime);
                        mVoteAverage.setText(String.valueOf(response.getVoteAverage()).substring(0, 3));

                        setGenres(response);

                        mTvOverviewContent.setText(response.getOverview());
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DetailMovieActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                            // get parsed error object (If ApiError is your class)

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }

    private String convertToHour(ResponseDetailMovie response) {
        double runtimeToHour = Double.parseDouble(String.valueOf(response.getRuntime())) / 60;
        String hour = String.valueOf(runtimeToHour).charAt(0) + getString(R.string.hour_in_short);
        char firstCharOfHour = String.valueOf(runtimeToHour).charAt(0);
        int minute = Integer.parseInt(String.valueOf(response.getRuntime())) - (Integer.parseInt(String.valueOf(firstCharOfHour)) * 60);

        String resultRuntime = hour + " " + minute + getString(R.string.minute_in_short);
        return resultRuntime;
    }

    private void setGenres(ResponseDetailMovie response) {
        if (response.getGenres().size() <= 1) {
            mGenre1.setText(response.getGenres().get(0).getName());
            mGenre1.setVisibility(View.VISIBLE);
        } else if (response.getGenres().size() <= 2) {
            mGenre1.setText(response.getGenres().get(0).getName());
            mGenre2.setText(response.getGenres().get(1).getName());
            mGenre1.setVisibility(View.VISIBLE);
            mGenre2.setVisibility(View.VISIBLE);
        } else if (response.getGenres().size() <= 3) {
            mGenre1.setText(response.getGenres().get(0).getName());
            mGenre2.setText(response.getGenres().get(1).getName());
            mGenre3.setText(response.getGenres().get(2).getName());
            mGenre1.setVisibility(View.VISIBLE);
            mGenre2.setVisibility(View.VISIBLE);
            mGenre3.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mIvDetailMovie = findViewById(R.id.iv_detail_movie);
        mIbBack = findViewById(R.id.ib_back);
        mTvVoteAverage = findViewById(R.id.tv_vote_average);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRuntime = findViewById(R.id.tv_runtime);
        mVoteAverage = findViewById(R.id.vote_average);
        mGenre1 = findViewById(R.id.genre1);
        mGenre2 = findViewById(R.id.genre2);
        mGenre3 = findViewById(R.id.genre3);
        mTvOverviewContent = findViewById(R.id.tv_overview_content);
        mRvVideoTrailer = findViewById(R.id.rv_video_trailer);

        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initialize() {
        setSystemBarFitWindow(this);
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void onItemClick(View view, ResultsItemVideos resultsItemVideos, int position) {
        Toast.makeText(this, resultsItemVideos.getName(), Toast.LENGTH_SHORT).show();
        watchYoutubeVideo(this, String.valueOf(resultsItemVideos.getKey()));
    }
}