package com.info.retrofitexampleroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.info.retrofitexampleroom.comment.CommentAdapter;
import com.info.retrofitexampleroom.comment.CommentRepository;
import com.info.retrofitexampleroom.comment.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private CommentRepository commentRepository;
    private RecyclerView recyclerView2;
    private JsonPlaceHolderApi2 jsonPlaceHolderApi2;

    private static final String URL_DATA = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        commentRepository = new CommentRepository(getApplication());
        commentAdapter = new CommentAdapter(getApplication(), commentList);
        commentList = new ArrayList<>();

        CommentViewModel commentViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(CommentViewModel.class);
        commentViewModel.getAllComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.getAllComments(commentList);
                recyclerView2.setAdapter(commentAdapter);
                Log.d("Main","onChanged:"+commentList);
            }
        });
        networkRequest2();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // burası http eklendikten sonra
                .build();

        jsonPlaceHolderApi2 = retrofit1.create(JsonPlaceHolderApi2.class);

    }
    private void networkRequest2(){
        Retrofit retrofit1=new Retrofit.Builder()
                .baseUrl(URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi2 api =retrofit1.create(JsonPlaceHolderApi2.class);
        Call<List<Comment>> call = api.getAllComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response1) {
                if(response1.isSuccessful()){
                    commentRepository.insert(response1.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(MainActivity2.this,"something went wrong...",Toast.LENGTH_SHORT).show();

            }
        });
    }
}