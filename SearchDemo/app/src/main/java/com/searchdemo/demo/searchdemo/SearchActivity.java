package com.searchdemo.demo.searchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.searchdemo.demo.searchdemo.searchview.BcallBack;
import com.searchdemo.demo.searchdemo.searchview.IcallBack;
import com.searchdemo.demo.searchdemo.searchview.SearchView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnClickBackListener(new BcallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
        searchView.setOnClickSearchListener(new IcallBack() {
            @Override
            public void SearchAciton(String string) {
                Toast.makeText(getApplicationContext(),"当前搜索的是"+string,Toast.LENGTH_LONG).show();
            }
        });
    }

}
