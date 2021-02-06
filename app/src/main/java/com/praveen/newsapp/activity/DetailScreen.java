package com.praveen.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.praveen.newsapp.HomeScreen;
import com.praveen.newsapp.MyListner;
import com.praveen.newsapp.R;
import com.praveen.newsapp.adapter.MyTopHeadlinesAdapter;
import com.praveen.newsapp.adapter.MyTopHeadlinesNewAdapter;
import com.praveen.newsapp.databinding.ActivityDetailScreenBinding;
import com.praveen.newsapp.model.ResponseData;
import com.praveen.newsapp.network.APIClient;
import com.praveen.newsapp.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScreen extends AppCompatActivity implements MyListner {

    ActivityDetailScreenBinding activityDetailScreenBinding;
    APIInterface apiService;
    public String TAG = DetailScreen.class.getSimpleName();
    private MyTopHeadlinesNewAdapter myTopHeadlinesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        apiService =
                APIClient.getClient().create(APIInterface.class);
        getTopHighlights();
        String url = getIntent().getExtras().getString("articleurl");
        activityDetailScreenBinding.txtHeader.setText(url);
        loadWeb(url);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadWeb(String url){
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);
    }

    @Override
    public void callback(String url) {
        loadWeb(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void getTopHighlights() {
        Call<ResponseData> call = apiService.getTopHeadLines();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d(TAG, response.body().getStatus());
                myTopHeadlinesAdapter = new MyTopHeadlinesNewAdapter(response.body().getArticleModelList(), DetailScreen.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                activityDetailScreenBinding.rcTopNews.setLayoutManager(mLayoutManager);
                activityDetailScreenBinding.rcTopNews.setItemAnimator(new DefaultItemAnimator());
                activityDetailScreenBinding.rcTopNews.setAdapter(myTopHeadlinesAdapter);
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}