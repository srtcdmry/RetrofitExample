package com.info.retrofitexampleroom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi2 {
    @GET("posts/3/comments")
    Call<List<Comment>> getAllComments();
}
