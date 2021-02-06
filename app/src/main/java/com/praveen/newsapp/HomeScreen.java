package com.praveen.newsapp;


import android.app.SearchManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.praveen.newsapp.adapter.MyTopHeadlinesAdapter;
import com.praveen.newsapp.database.DatabaseClient;
import com.praveen.newsapp.database.EntityClass;
import com.praveen.newsapp.databinding.ActivityHomescreenBinding;
import com.praveen.newsapp.model.ResponseData;
import com.praveen.newsapp.network.APIClient;
import com.praveen.newsapp.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {

    ActivityHomescreenBinding activityHomescreenBinding;
    APIInterface apiService;
    public String TAG = HomeScreen.class.getSimpleName();
    private MyTopHeadlinesAdapter myTopHeadlinesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomescreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_homescreen);
        apiService =
                APIClient.getClient().create(APIInterface.class);
        getTopHighlights();
        getRecentNews();

        activityHomescreenBinding.bookmarkked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityClass entityClass=new EntityClass();
                entityClass.setTitle(activityHomescreenBinding.txttitle.getText().toString());
               //new SaveArticle(entityClass).execute();
              // new GetArticle().execute();
            }
        });


    }

    private void getRecentNews() {
        Call<ResponseData> call = apiService.getLatestNews();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                activityHomescreenBinding.txttitle.setText(response.body().getArticleModelList().get(1).getTitle());
                activityHomescreenBinding.txtdesc.setText(response.body().getArticleModelList().get(1).getDescription());
                activityHomescreenBinding.txtNetwork.setText(response.body().getArticleModelList().get(1).getSource().getName());
                Glide.with(HomeScreen.this)
                        .load(response.body().getArticleModelList().get(1).getUrlToImage())
                        .into(activityHomescreenBinding.imageNews);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getTopHighlights() {
        Call<ResponseData> call = apiService.getTopHeadLines();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d(TAG, response.body().getStatus());
                myTopHeadlinesAdapter = new MyTopHeadlinesAdapter(response.body().getArticleModelList(), HomeScreen.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                activityHomescreenBinding.rcTopNews.setLayoutManager(mLayoutManager);
                activityHomescreenBinding.rcTopNews.setItemAnimator(new DefaultItemAnimator());
                activityHomescreenBinding.rcTopNews.setAdapter(myTopHeadlinesAdapter);

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        MenuItem mSearch = menu.findItem(R.id.app_bar_search);
        MenuItem mBook = menu.findItem(R.id.bookmarkk);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class SaveArticle extends AsyncTask<Void, Void, Void> {

        EntityClass entityClass;
        public SaveArticle(EntityClass entityClass){
            this.entityClass=entityClass;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .articleDao()
                    .insert(entityClass);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Bookmarked", Toast.LENGTH_LONG).show();
        }
    }

    public class GetArticle extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            List<EntityClass> entityClassList=DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .articleDao()
                    .getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}