package com.example.checkincheckout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView username, password;
    String typedUsername;
    MaterialButton btn_login, btn_register;

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
        setContentView(R.layout.activity_login);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        btn_login = (MaterialButton) findViewById(R.id.btn_signin);
        btn_register = (MaterialButton) findViewById(R.id.btn_register);

        username = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);

        //Event
        btn_login.setOnClickListener(v -> loginUser(username.getText().toString(), password.getText().toString()));

        btn_register.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            i.putExtra("Name", username.getText().toString());
            startActivity(i);
        });

/*        TextView username = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton btn_login = (MaterialButton) findViewById(R.id.btn_signin);*/

        /*// test
        btn_login.setOnClickListener(v -> {
            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
            {
                //correct
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                // incorrect
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email,password)
        .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(s -> {
                if(s.contains("encrypted_password")) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    // Maybe here is where we can move to next activity after login
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("Name", username.getText().toString());
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    // Maybe here is where we can move user to Register
                }
            })
        );
    }
}
