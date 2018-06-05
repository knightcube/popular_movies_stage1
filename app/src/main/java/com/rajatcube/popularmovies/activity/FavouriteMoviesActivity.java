package com.rajatcube.popularmovies.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.adapter.MoviesAdapter;
import com.rajatcube.popularmovies.database.MoviesRoomDatabase;
import com.rajatcube.popularmovies.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private static final String TAG = FavouriteMoviesActivity.class.getSimpleName();
    private RecyclerView favouritesRv;
    private MoviesAdapter moviesAdapter;
    private MoviesRoomDatabase moviesRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favouritesRv = (RecyclerView) findViewById(R.id.favourite_movies_rv);
        favouritesRv.setLayoutManager(new GridLayoutManager(FavouriteMoviesActivity.this, 2));
        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
    //        Log.i(TAG, "onCreate: " + moviesRoomDatabase.moviesDAO().getAllMovies().size());
//        for (Movies currentMovie : moviesRoomDatabase.moviesDAO().getAllMovies()) {
//            Log.i(TAG, "onCreate: " + currentMovie.getTitle());
//            Log.i(TAG, "onCreate: " + currentMovie.getId());
//            Log.i(TAG, "onCreate: " + currentMovie.getPosterPath());
//        }

        retrieveMovies();
    }

    private void retrieveMovies() {
        LiveData<List<Movies>> movieLiveData = moviesRoomDatabase.moviesDAO().getAllMovies();
        movieLiveData.observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                moviesAdapter = new MoviesAdapter(movies, FavouriteMoviesActivity.this, FavouriteMoviesActivity.this);
                favouritesRv.setAdapter(moviesAdapter);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex, List<Movies> moviesList) {
        Intent intent = new Intent(FavouriteMoviesActivity.this, MovieDetails.class);
        intent.putExtra("clicked_movie", moviesList.get(clickedItemIndex));
        startActivity(intent);
    }
}
