package com.example.cosplay_suit_app.bill.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.cosplay_suit_app.API;
import com.example.cosplay_suit_app.DTO.DTO_CartOrder;
import com.example.cosplay_suit_app.Interface_retrofit.CartOrderInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cart_controller {
    String TAG ="Cart_controller";
    static String url = API.URL;
    static final String BASE_URL_CARTORDER = url + "/bill/";
    Context context;

    public Cart_controller(Context context) {
        this.context = context;
    }

    public void AddCart(DTO_CartOrder objcart) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_CARTORDER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CartOrderInterface billInterface = retrofit.create(CartOrderInterface.class);
        Call<DTO_CartOrder> objCall = billInterface.addcart(objcart);

        objCall.enqueue(new Callback<DTO_CartOrder>() {
            @Override
            public void onResponse(Call<DTO_CartOrder> call, Response<DTO_CartOrder> response) {
                if (response.isSuccessful()) {
                    // Sử dụng mContext để hiển thị Toast
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "nguyen1: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DTO_CartOrder> call, Throwable t) {
                // Sử dụng mContext để hiển thị thông báo lỗi
                Toast.makeText(context, "Lỗi: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "nguyen2: " + t.getLocalizedMessage());
            }
        });
    }

}
