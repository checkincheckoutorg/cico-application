package com.example.checkincheckout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkincheckout.Adapter.DroppedOffBookAdapter;
import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    DroppedOffBookAdapter adapter;

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
        setContentView(R.layout.activity_admin_search);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        //user = (String) getIntent().getExtras().getString("Name");

        recycler_search = (RecyclerView) findViewById(R.id.recycler_search2);
        recycler_search.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        getAllBooks();

        //Set

    }

    private void getAllBooks() {
        compositeDisposable.add(myAPI.getDroppedOffBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(books -> {
                    adapter = new DroppedOffBookAdapter(getBaseContext(), books);
                    recycler_search.setAdapter(adapter);
                }, throwable -> Toast.makeText(AdminSearchActivity.this, "Not Found", Toast.LENGTH_SHORT).show()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}