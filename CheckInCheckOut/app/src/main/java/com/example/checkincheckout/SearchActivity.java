package com.example.checkincheckout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkincheckout.Adapter.BookAdapter;
import com.example.checkincheckout.Adapter.DroppedOffBookAdapter;
import com.example.checkincheckout.Retrofit.INodeJS;
import com.example.checkincheckout.Retrofit.RetrofitClient;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    BookAdapter adapter;
    DroppedOffBookAdapter co_adapter;
    Spinner spinner;
    String user;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

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
        setContentView(R.layout.activity_search);

        // Spinner
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(this, R.array.filters, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(filterAdapter);
        spinner.setOnItemSelectedListener(this);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        user = (String) getIntent().getExtras().getString("Name");

        recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        recycler_search.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        materialSearchBar.setCardViewElevation(10);

        // add suggest list
        addSuggestList();

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search_term:suggestList)
                    if(search_term.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search_term);
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    getAllBooks();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString(), spinner.getSelectedItem().toString().toLowerCase());
                System.out.println(spinner.getSelectedItem().toString().toLowerCase());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        getAllBooks();

        //Set

    }

    private void startSearch(String query, String filter) {
        compositeDisposable.add(myAPI.searchBooks(query, filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(books -> {
                    adapter = new BookAdapter(SearchActivity.this, getBaseContext(), books);
                    recycler_search.setAdapter(adapter);
                }, throwable -> Toast.makeText(SearchActivity.this, "Not Found", Toast.LENGTH_SHORT).show()));
    }

    private void getAllBooks() {
        compositeDisposable.add(myAPI.getBookList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(books -> {
            adapter = new BookAdapter(SearchActivity.this, getBaseContext(), books);
            recycler_search.setAdapter(adapter);
        }, throwable -> Toast.makeText(SearchActivity.this, "Not Found", Toast.LENGTH_SHORT).show()));
    }

    private void addSuggestList() {
        // load suggest list
        suggestList.add("Harry Potter");
        suggestList.add("Great Gatsby");
        suggestList.add("Lord");

        materialSearchBar.setLastSuggestions(suggestList);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String filter = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), filter, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getUsername(){
        user = (String) getIntent().getExtras().getString("Name");
        return user;
    }
}