package com.wahyurhy.moviappandroid.presentation.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.ResponsePopularActor;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularactor.ResultsItemPopularActor;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularmovie.ResponsePopularMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.popularmovie.ResultsItemPopularMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.searchmovie.ResponseSearchMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.searchmovie.ResultsItemSearchMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.toprated.ResponseTopRatedMovie;
import com.wahyurhy.moviappandroid.core.data.remote.response.toprated.ResultsItemTopRatedMovie;
import com.wahyurhy.moviappandroid.databinding.FragmentHomeBinding;
import com.wahyurhy.moviappandroid.presentation.view.adapter.PopularActorAdapter;
import com.wahyurhy.moviappandroid.presentation.view.adapter.PopularMovieAdapter;
import com.wahyurhy.moviappandroid.presentation.view.adapter.SearchMovieAdapter;
import com.wahyurhy.moviappandroid.presentation.view.adapter.TopRatedMovieAdapter;
import com.wahyurhy.moviappandroid.presentation.view.detail.BioActivity;
import com.wahyurhy.moviappandroid.presentation.view.detail.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class HomeFragment extends Fragment implements TopRatedMovieAdapter.OnItemClickListener, PopularMovieAdapter.OnItemClickListener, PopularActorAdapter.OnItemClickListener, SearchMovieAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;

    private TopRatedMovieAdapter topRatedMovieAdapter;
    private SearchMovieAdapter searchMovieAdapter;
    private PopularActorAdapter popularActorAdapter;
    private PopularMovieAdapter popularMovieAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLoadData();
        initSearch();
    }

    private void initLoadData() {
        loadDataTopRated();
        loadDataPopularActor();
        loadDataPopularMovie();
    }

    private void initSearch() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    loadDataSearchView(newText);
                    binding.lytTopRatedMovie.setVisibility(View.GONE);
                    binding.lytResultSearchView.setVisibility(View.VISIBLE);
                    binding.lytResultNotFoundSearchView.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    private void loadDataSearchView(String query) {
        AndroidNetworking.get("https://api.themoviedb.org/3/search/movie")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1")
                .addQueryParameter("query", query)
                .setTag("loadDataSearchView")
                .setPriority(Priority.HIGH)
                .build()
                .getAsOkHttpResponseAndObject(ResponseSearchMovie.class, new OkHttpResponseAndParsedRequestListener<ResponseSearchMovie>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponseSearchMovie response) {
                        Gson gson = new Gson();
                        List<ResultsItemSearchMovie> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        searchMovieAdapter = new SearchMovieAdapter(getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        binding.rvSearchMovie.setLayoutManager(linearLayoutManager);
                        binding.rvSearchMovie.setHasFixedSize(true);
                        binding.rvSearchMovie.setAdapter(searchMovieAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemSearchMovie>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            binding.rvSearchMovie.setVisibility(View.VISIBLE);
                            searchMovieAdapter.addAll(dataResultItem);
                            searchMovieAdapter.setOnItemClickListener(HomeFragment.this);
                        } else {
                            binding.rvSearchMovie.setVisibility(View.INVISIBLE);
                            binding.lytResultNotFoundSearchView.setVisibility(View.VISIBLE);
                            loadDataTopRated();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        binding.lytTopRatedMovie.setVisibility(View.VISIBLE);
                        binding.lytResultSearchView.setVisibility(View.GONE);
                        binding.lytResultNotFoundSearchView.setVisibility(View.GONE);
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

    private void loadDataTopRated() {
        binding.rvTopRatedMovie.setVisibility(View.VISIBLE);
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/top_rated")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1")
                .setTag("loadDataTopRated")
                .setPriority(Priority.HIGH)
                .build()
                .getAsOkHttpResponseAndObject(ResponseTopRatedMovie.class, new OkHttpResponseAndParsedRequestListener<ResponseTopRatedMovie>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponseTopRatedMovie response) {
                        Gson gson = new Gson();
                        List<ResultsItemTopRatedMovie> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        topRatedMovieAdapter = new TopRatedMovieAdapter(getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        binding.rvTopRatedMovie.setLayoutManager(linearLayoutManager);
                        binding.rvTopRatedMovie.setHasFixedSize(true);
                        binding.rvTopRatedMovie.setAdapter(topRatedMovieAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemTopRatedMovie>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            topRatedMovieAdapter.addAll(dataResultItem);
                            topRatedMovieAdapter.setOnItemClickListener(HomeFragment.this);
                        } else {
                            binding.rvTopRatedMovie.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(requireContext(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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

    private void loadDataPopularActor() {
        AndroidNetworking.get("https://api.themoviedb.org/3/person/popular")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1")
                .setTag("loadDataPopularActor")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsOkHttpResponseAndObject(ResponsePopularActor.class, new OkHttpResponseAndParsedRequestListener<ResponsePopularActor>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponsePopularActor response) {
                        Gson gson = new Gson();
                        List<ResultsItemPopularActor> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        popularActorAdapter = new PopularActorAdapter(getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        binding.rvPopularActor.setLayoutManager(linearLayoutManager);
                        binding.rvPopularActor.setHasFixedSize(true);
                        binding.rvPopularActor.setAdapter(popularActorAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemPopularActor>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            popularActorAdapter.addAll(dataResultItem);
                            popularActorAdapter.setOnItemClickListener(HomeFragment.this);
                        } else {
                            binding.rvPopularActor.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(requireContext(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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

    private void loadDataPopularMovie() {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/popular")
                .addQueryParameter("api_key", "f3aa39c23e3c246fc689906fcddb40b5")
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1")
                .setTag("loadDataPopularActor")
                .setPriority(Priority.LOW)
                .build()
                .getAsOkHttpResponseAndObject(ResponsePopularMovie.class, new OkHttpResponseAndParsedRequestListener<ResponsePopularMovie>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponsePopularMovie response) {
                        Gson gson = new Gson();
                        List<ResultsItemPopularMovie> dataResultItem;
                        String resultString = gson.toJson(response.getResults());

                        popularMovieAdapter = new PopularMovieAdapter(getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        binding.rvPopularMovie.setLayoutManager(linearLayoutManager);
                        binding.rvPopularMovie.setHasFixedSize(true);
                        binding.rvPopularMovie.setAdapter(popularMovieAdapter);

                        dataResultItem = new Gson().fromJson(resultString, new TypeToken<ArrayList<ResultsItemPopularMovie>>() {
                        }.getType());

                        if (dataResultItem.size() != 0) {
                            popularMovieAdapter.addAll(dataResultItem);
                            popularMovieAdapter.setOnItemClickListener(HomeFragment.this);
                        } else {
                            binding.rvPopularMovie.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(requireContext(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(View view, ResultsItemTopRatedMovie resultsItemTopRatedMovie, int position) {
        Intent intentDetailActivity = new Intent(requireContext(), DetailMovieActivity.class);
        intentDetailActivity.putExtra("id_extra", String.valueOf(resultsItemTopRatedMovie.getId()));
        startActivity(intentDetailActivity);
    }

    @Override
    public void onItemClick(View view, ResultsItemPopularMovie resultsItemPopularMovie, int position) {
        Intent intentDetailActivity = new Intent(requireContext(), DetailMovieActivity.class);
        intentDetailActivity.putExtra("id_extra", String.valueOf(resultsItemPopularMovie.getId()));
        startActivity(intentDetailActivity);
    }

    @Override
    public void onItemClick(View view, ResultsItemPopularActor resultsItemPopularActor, int position) {
        Gson gson = new Gson();

        Intent intentBioActivity = new Intent(requireContext(), BioActivity.class);
        intentBioActivity.putExtra("id_extra", String.valueOf(resultsItemPopularActor.getId()));
        intentBioActivity.putExtra("actor_extra", gson.toJson(resultsItemPopularActor));
        startActivity(intentBioActivity);
    }

    @Override
    public void onItemClick(View view, ResultsItemSearchMovie resultsItemSearchMovies, int position) {
        Intent intentDetailActivity = new Intent(requireContext(), DetailMovieActivity.class);
        intentDetailActivity.putExtra("id_extra", String.valueOf(resultsItemSearchMovies.getId()));
        startActivity(intentDetailActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}