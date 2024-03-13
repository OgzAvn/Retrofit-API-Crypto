package com.oguz.retrofitjava.servis;

import com.oguz.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET (Veri almak), POST(Veri yazmak) , UPDATE , DELETE bu tarz işlemlerimiz olabilir.

    //URL BASE -> www.website.com
    // GET -> price?key=xxx


    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Observable<List<CryptoModel>> getData();
    //Call<List<CryptoModel>> getData(); //Bütün bu method un adına getData dedik.

}
