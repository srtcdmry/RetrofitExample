package com.info.retrofitexampleroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.info.retrofitexampleroom.comment.CommentAdapter;
import com.info.retrofitexampleroom.comment.CommentRepository;
import com.info.retrofitexampleroom.comment.CommentViewModel;
import com.info.retrofitexampleroom.post.PostAdapter;
import com.info.retrofitexampleroom.post.PostRepesitory;
import com.info.retrofitexampleroom.post.PostViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    // private CommentAdapter commentAdapter;
    //private List<Comment> commentList;
    private List<Post> postList;
    //private CommentRepository commentRepository;
    private PostRepesitory postRepesitory;
    private static final String URL_DATA = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        postRepesitory = new PostRepesitory(getApplication());
        //   commentRepository=new CommentRepository(getApplication());
        postAdapter = new PostAdapter(this, postList);
        //  commentAdapter=new CommentAdapter(this,commentList);
        postList = new ArrayList<>();
        // commentList=new ArrayList<>();


        // PostViewModel postViewModel= new ViewModelProvider(this).get(PostViewModel.class);
        PostViewModel postViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {

            @Override
            public void onChanged(List<Post> postList) {
                //Toast.makeText(MainActivity.this,"Working ",Toast.LENGTH_SHORT).show();
                postAdapter.getAllPosts(postList);
                recyclerView.setAdapter(postAdapter);
                Log.d("main", "onChanged:" + postList);
            }
        });

        networkRequest();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // burası http eklendikten sonra
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
        // getPosts();
        //getComments();
        //createPost();
        //updatePost();
        //deletePost();
    }


    private void networkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = api.getAllPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postRepesitory.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPosts() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        // Call<List<Post>> call = jsonPlaceHolderApi.getPosts(1,4,"id","desc");
        // Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{2,3,6},"id","desc");  // userİd dizi olarak tanımlandığında
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters); // hashmap ile
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        // Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments"); // url ile çağırma

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");
        fields.put("body", "New Text");
        //Post post = new Post(23,"New Title","New Text");
        // Call<Post> call = jsonPlaceHolderApi.createPost(post);
        //Call<Post> call = jsonPlaceHolderApi.createPost(23,"New Title","New Text");  //formurlenco tanımlaması için
        Call<Post> call = jsonPlaceHolderApi.createPost(fields);  // fields tanımlaması için geçerli
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";  // code 201 Created üretir
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    private void updatePost() {
        Post post = new Post(12, null, "New Text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);  // put olduğunda içerik tamamen girildiği şekilde değişir. girilmeyenler null olur
        // Call<Post> call = jsonPlaceHolderApi.patchPost(5,post); // patch olduğunda ise içerikte sadece girilen değerler değişir. girilmeyenler eskiden neyse o kalır

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";  // code 200 OK üretir
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}