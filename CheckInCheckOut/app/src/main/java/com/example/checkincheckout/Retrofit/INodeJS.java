package com.example.checkincheckout.Retrofit;

import com.example.checkincheckout.Model.Book;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("firstName") String firstName,
                                    @Field("lastName") String lastName,
                                    @Field("password") String password,
                                    @Field("age") int age,
                                    @Field("admin") boolean admin);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                    @Field("password") String password);

    @GET("getAllBooks")
    Observable<List<Book>> getBookList();

    @POST("getBooksByFilter")
    @FormUrlEncoded
    Observable<List<Book>> searchBooks(@Field("search") String search,
                                       @Field("filter") String filter);


}
