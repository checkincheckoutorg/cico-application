package com.example.checkincheckout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView username, password, confirmedPassword, firstName, lastName;
    String typedUsername;
    MaterialButton btn_register;

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
        setContentView(R.layout.activity_register);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        username = (TextView) findViewById(R.id.email);

        //Set
        typedUsername = getIntent().getExtras().getString("Name");
        username.setText(typedUsername);

        //View
        username = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        confirmedPassword = (TextView) findViewById(R.id.confirm_password);
        firstName = (TextView) findViewById(R.id.first_name);
        lastName = (TextView) findViewById(R.id.last_name);
        btn_register = (MaterialButton) findViewById(R.id.btn_register);

        //Event
        btn_register.setOnClickListener(v -> registerUser(username.getText().toString(), password.getText().toString(), confirmedPassword.getText().toString(), firstName.getText().toString(), lastName.getText().toString()));
    }

    private void registerUser(String email, String password, String confirmedPassword, String firstName, String lastName) {
        if (!password.equals(confirmedPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match! " + password + " and " + confirmedPassword, Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(myAPI.registerUser(email,password, firstName, lastName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.contains("Register Successful!")){
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}