package com.example.checkincheckout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CheckInActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    String checkout_title, checkout_author, checkout_genre, checkout_book_type, checkout_isbn, checkout_front_cover, username, checkout_email;
    Integer checkout_stock, checkout_id, history_id;
    TextView title, author, genre, book_type, stock, isbn, email;
    ImageView front_cover;
    MaterialButton btn_yes, btn_no;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        btn_yes = (MaterialButton) findViewById(R.id.btn_yes);
        btn_no = (MaterialButton) findViewById(R.id.btn_no);
        checkout_id = (Integer) getIntent().getExtras().getInt("ID");
        history_id = (Integer) getIntent().getExtras().getInt("History Id");

        // Set Title
        title = (TextView) findViewById(R.id.text_title);
        checkout_title = (String) getIntent().getExtras().getString("Title");
        title.setText(checkout_title);

        // Set Author
        author = (TextView) findViewById(R.id.text_author);
        checkout_author = (String) getIntent().getExtras().getString("Author");
        author.setText(checkout_author);

        // Set Genre
        genre = (TextView) findViewById(R.id.text_genre);
        checkout_genre = (String) getIntent().getExtras().getString("Genre");
        genre.setText(checkout_genre);

        // Set Book Type
        book_type = (TextView) findViewById(R.id.text_book_type);
        checkout_book_type = (String) getIntent().getExtras().getString("Book Type");
        book_type.setText(checkout_book_type);

        // Set Stock
        stock = (TextView) findViewById(R.id.text_stock);
        checkout_stock = (Integer) getIntent().getExtras().getInt("Stock");
        stock.setText("Stock: " + checkout_stock);

        // Set ISBN
        isbn = (TextView) findViewById(R.id.text_isbn);
        checkout_isbn = (String) getIntent().getExtras().getString("ISBN");
        isbn.setText("ISBN: "+ checkout_isbn);

        // Set Front Cover
        front_cover = (ImageView) findViewById(R.id.cover_image);
        checkout_front_cover = (String) getIntent().getExtras().getString("Front Cover");
        Glide.with(front_cover.getContext()).load(checkout_front_cover).into(front_cover);

        // Set Email
        email = (TextView) findViewById(R.id.text_email);
        checkout_email = (String) getIntent().getExtras().getString("Email attached to book")   ;
        email.setText("Email attached to book: " + checkout_email);

        //User hits yes
        btn_yes.setOnClickListener(v -> checkInBook(checkout_id, history_id));

        // User hits no
        btn_no.setOnClickListener(v -> {
            Intent i = new Intent(CheckInActivity.this, AdminSearchActivity.class);
            startActivity(i);
            finish();
        });

    }

    private void checkInBook(Integer checkout_id, Integer history_id) {
        compositeDisposable.add(myAPI.checkInBook(checkout_id, history_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // do stuff with response
                    if (response.contains("Can't Check In!"))
                    {
                        Toast.makeText(CheckInActivity.this, "Can't Check In!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // book was successfully checked out (TEST)
                        Toast.makeText(CheckInActivity.this, "Check In Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CheckInActivity.this, AdminSearchActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
        );
    }

}