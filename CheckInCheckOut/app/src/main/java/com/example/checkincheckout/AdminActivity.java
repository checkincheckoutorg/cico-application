package com.example.checkincheckout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView username, password;
    String typedUsername;
    MaterialButton btn_dropped_off_books, btn_logout;

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
        setContentView(R.layout.activity_admin_home);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        username = (TextView) findViewById(R.id.email);
        btn_logout = (MaterialButton) findViewById(R.id.btn_logout);
        btn_dropped_off_books = (MaterialButton) findViewById(R.id.btn_dropped_off_books);

        //Set
        typedUsername = getIntent().getExtras().getString("Name");
        username.setText(typedUsername);

        //view
        username = (TextView) findViewById(R.id.email);

        //Logout button
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(AdminActivity.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        //Books ready to be checked in
        btn_dropped_off_books.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, AdminSearchActivity.class);
            i.putExtra("Name", username.getText().toString());
            startActivity(i);
        });

    }
}