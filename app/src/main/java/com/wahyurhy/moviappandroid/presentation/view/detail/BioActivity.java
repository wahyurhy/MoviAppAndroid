package com.wahyurhy.moviappandroid.presentation.view.detail;

import android.content.Intent;
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
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.KnownForItem;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.ResultsItemPopularActor;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactordetail.ResponsePopularActorDetail;
import com.wahyurhy.moviappandroid.presentation.view.adapter.PopularActorDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class BioActivity extends AppCompatActivity implements PopularActorDetailAdapter.OnItemClickListener {

    ResultsItemPopularActor resultsItemPopularActor;
    PopularActorDetailAdapter popularActorDetailAdapter;

    private ANImageView mProfilePath;
    private TextView mNameActor;
    private TextView mPopularityNumber;
    private TextView mBioContent;
    private TextView mTvKnownFor;
    private RecyclerView mRvKnownFor;
    private ImageButton mIbBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        initView();

        Gson gson = new Gson();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id_extra");
            resultsItemPopularActor = gson.fromJson(extras.getString("actor_extra"), ResultsItemPopularActor.class);

            loadData(id, resultsItemPopularActor);
        }
    }

    private void loadData(String id, ResultsItemPopularActor resultsItem) {
        AndroidNetworking.get("https://api.themoviedb.org/3/person/" + id)
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .setTag("loadData")
                .setPriority(Priority.HIGH)
                .build()
                .getAsOkHttpResponseAndObject(ResponsePopularActorDetail.class, new OkHttpResponseAndParsedRequestListener<ResponsePopularActorDetail>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponsePopularActorDetail response) {
                        mProfilePath.setImageUrl("https://image.tmdb.org/t/p/w300" + response.getProfilePath());
                        mNameActor.setText(response.getName());
                        mPopularityNumber.setText(String.valueOf(response.getPopularity()));
                        mBioContent.setText(response.getBiography());

                        if (resultsItem.getKnownFor().size() != 0) {
                            mRvKnownFor.setVisibility(View.VISIBLE);
                            popularActorDetailAdapter = new PopularActorDetailAdapter(BioActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BioActivity.this);
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            mRvKnownFor.setLayoutManager(linearLayoutManager);
                            mRvKnownFor.setHasFixedSize(true);
                            mRvKnownFor.setAdapter(popularActorDetailAdapter);

                            Gson gson = new Gson();

                            List<KnownForItem> dataResultItem;
                            String resultString = gson.toJson(resultsItem.getKnownFor());

                            dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<KnownForItem>>() {
                            }.getType());

                            popularActorDetailAdapter.addAll(dataResultItem);
                            popularActorDetailAdapter.setOnItemClickListener(BioActivity.this);

                        } else {
                            mTvKnownFor.setVisibility(View.GONE);
                            mRvKnownFor.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(BioActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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
        mProfilePath = findViewById(R.id.profile_path);
        mNameActor = findViewById(R.id.name_actor);
        mPopularityNumber = findViewById(R.id.popularity_number);
        mBioContent = findViewById(R.id.bio_content);
        mRvKnownFor = findViewById(R.id.rv_known_for);
        mTvKnownFor = findViewById(R.id.tv_known_for);
        mIbBack = findViewById(R.id.ib_back);

        mIbBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onItemClick(View view, KnownForItem knownForItem, int position) {
        Intent intentDetailActivity = new Intent(BioActivity.this, DetailMovieActivity.class);
        intentDetailActivity.putExtra("id_extra", String.valueOf(knownForItem.getId()));
        startActivity(intentDetailActivity);
    }
}