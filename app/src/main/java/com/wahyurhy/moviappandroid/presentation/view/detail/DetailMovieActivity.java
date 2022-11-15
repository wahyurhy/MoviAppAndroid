package com.wahyurhy.moviappandroid.presentation.view.detail;

import static com.wahyurhy.moviappandroid.utils.Utils.setSystemBarFitWindow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.widget.ANImageView;
import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.core.data.remote.response.detailmovie.ResponseDetailMovie;

import okhttp3.Response;

public class DetailMovieActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initialize();
        initView();
        initExtras();

    }

    private void initExtras() {
        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) {
            String id = extras.getString("id_extra");
            loadDataDetailMovie(id);
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
        String minute = String.valueOf(runtimeToHour).charAt(2) + getString(R.string.minute_in_short);

        String resultRuntime = hour + " 0" + minute;
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
}