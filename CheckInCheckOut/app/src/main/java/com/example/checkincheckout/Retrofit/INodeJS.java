package com.example.checkincheckout.Retrofit;

import com.example.checkincheckout.Model.Book;
import com.example.checkincheckout.Model.CheckedOutBook;

import java.util.List;

import io.reactivex.Completable;
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

    @POST("checkoutBook")
    @FormUrlEncoded
    Observable<String> checkoutBook(@Field("id") Integer id,
                                    @Field("email") String user);

    @GET("getDroppedOffBooks")
    Observable<List<CheckedOutBook>> getDroppedOffBooks();

    @POST("checkInBook")
    @FormUrlEncoded
    Observable<String> checkInBook(@Field("book_id") Integer book_id,
                                   @Field("history_id") Integer history_id);


    @POST("getCheckedOutBooksByUser")
    @FormUrlEncoded
    Observable<List<Book>> getCheckedOutBooksByUser(@Field("email") String email);
}
