package com.wahyurhy.moviappandroid.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.toprated.ResponseTopRatedMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.toprated.ResultsItemTopRatedMovie;
import com.wahyurhy.moviappandroid.presentation.view.adapter.TopRatedMovieAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvTopRatedMovie;
    private TopRatedMovieAdapter topRatedMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    private void loadData() {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/top_rated")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1")
                .setTag("loadData")
                .setPriority(Priority.HIGH)
                .build()
                .getAsOkHttpResponseAndObject(ResponseTopRatedMovie.class, new OkHttpResponseAndParsedRequestListener<ResponseTopRatedMovie>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponseTopRatedMovie response) {
                        Gson gson = new Gson();
                        List<ResultsItemTopRatedMovie> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        topRatedMovieAdapter = new TopRatedMovieAdapter(MainActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        mRvTopRatedMovie.setLayoutManager(linearLayoutManager);
                        mRvTopRatedMovie.setHasFixedSize(true);
                        mRvTopRatedMovie.setAdapter(topRatedMovieAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemTopRatedMovie>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            topRatedMovieAdapter.addAll(dataResultItem);
                        } else {
                            mRvTopRatedMovie.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MainActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        mRvTopRatedMovie = findViewById(R.id.rv_top_rated_movie);
    }
}