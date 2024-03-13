package com.oguz.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oguz.retrofitjava.R;
import com.oguz.retrofitjava.adapter.RecyclerViewAdapter;
import com.oguz.retrofitjava.model.CryptoModel;
import com.oguz.retrofitjava.servis.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    ArrayList<CryptoModel> cryptoModelArrayList;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView recyclerView;

    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        //Retrofit & JSON


        recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getDataFromAPI();

    }

    private void getDataFromAPI(){

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class); //Böylece servisi oluşturduk artık veriyi çekmeye hazırız

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

       /* //Call method u ile veriyi çekeceğiz
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {//Burada asekron şekilde alıyoruz ana mainthread i bloklamadan arka planda yapıyoruz.
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    cryptoModelArrayList = new ArrayList<>(responseList);//Liste yi arrayliste çevirdik

                    //RecyclerView-- Listeyi aldıktan sonra recycler view i gösteriyoruz.
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModelArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);

                   for (CryptoModel cryptoModel : cryptoModelArrayList){
                        System.out.println(cryptoModel.currency);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        */
    }
    private void handleResponse(List<CryptoModel> cryptoModelsList){

        cryptoModelArrayList = new ArrayList<>(cryptoModelsList);//Liste yi arrayliste çevirdik

        //RecyclerView-- Listeyi aldıktan sonra recycler view i gösteriyoruz.
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModelArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}