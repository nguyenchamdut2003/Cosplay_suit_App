package com.example.cosplay_suit_app.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.cosplay_suit_app.API;
import com.example.cosplay_suit_app.Activity.ChatActivity;
import com.example.cosplay_suit_app.Activity.Chitietsanpham;
import com.example.cosplay_suit_app.Activity.FavoriteActivity;
import com.example.cosplay_suit_app.Activity.LoginActivity;
import com.example.cosplay_suit_app.Activity.NewPasswordActivity;
import com.example.cosplay_suit_app.Activity.ProfileActivity;
import com.example.cosplay_suit_app.Activity.QlspActivity;
import com.example.cosplay_suit_app.Activity.Shopcuatoi_Activity;
import com.example.cosplay_suit_app.Adapter.DhWithoutCmtsAdapter;
import com.example.cosplay_suit_app.DTO.ItemDoneDTO;
import com.example.cosplay_suit_app.DTO.Shop;
import com.example.cosplay_suit_app.DTO.User;
import com.example.cosplay_suit_app.Interface_retrofit.CmtsInterface;
import com.example.cosplay_suit_app.Interface_retrofit.UserInterface;
import com.example.cosplay_suit_app.MainActivity;
import com.example.cosplay_suit_app.Package_bill.Activity.Danhgia_Activity;
import com.example.cosplay_suit_app.Package_bill.Activity.Giaohang_Activity;
import com.example.cosplay_suit_app.Package_bill.Activity.Layhang_Activity;
import com.example.cosplay_suit_app.Package_bill.Activity.xannhandon_Activity;
import com.example.cosplay_suit_app.Package_bill.donhang.Collection_adapter_bill;
import com.example.cosplay_suit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_profile extends Fragment {
    static String url = API.URL;
    static final String BASE_URL = url +"/user/api/";
    private TextView tv_fullname,tv_qlsp, tv_dky_shop, tv_donhangmua;

    private ImageView img_profile;
    private Button btn_login_profile;

    private AppCompatButton appCompatButton;

    private ProgressDialog progressDialog;

    Dialog dialog;
    String username_u, id_user;
    static String  role;
    static String id="";
    RelativeLayout rlRole, relative_newpass, rll_shopcuatoi;
    View idview5;
    SharedPreferences sharedPreferences;

    public Fragment_profile() {
    }

    public static Fragment_profile newInstance(){
        Fragment_profile fragmentProfile = new Fragment_profile();
        return fragmentProfile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewok = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView imgProfile = viewok.findViewById(R.id.img_profile);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", getContext().MODE_PRIVATE);
        username_u = sharedPreferences.getString("fullname", "");
        id = sharedPreferences.getString("id", "");

        // Set an OnClickListener for the ImageView
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to start the new activity (replace NewProfileActivity.class with your actual profile activity class)
                Intent intent = new Intent(getContext(), ProfileActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });

        return viewok;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_fullname = view.findViewById(R.id.tv_fullname_profile);
        btn_login_profile = view.findViewById(R.id.btn_login_profile);
        tv_dky_shop = view.findViewById(R.id.tv_dky_shop);
        img_profile = view.findViewById(R.id.img_profile);
        tv_donhangmua = view.findViewById(R.id.donhangmua);
        appCompatButton = view.findViewById(R.id.btn_login_profile);
        tv_qlsp = view.findViewById(R.id.tvQlsp);
        View view1 = view.findViewById(R.id.ivView);
        View view2 = view.findViewById(R.id.idview2);
        relative_newpass = view.findViewById(R.id.relative_newpass);
        idview5 = view.findViewById(R.id.idview5);
        rlRole = view.findViewById(R.id.rlRole);
        rll_shopcuatoi = view.findViewById(R.id.rll_shopcuatoi);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", getContext().MODE_PRIVATE);
        username_u = sharedPreferences.getString("fullname", "");
        id = sharedPreferences.getString("id", "");
//        if (id != null) {
//            getsoluongdg();
//        }
        role = sharedPreferences.getString("role", "");
        tv_fullname.setText(username_u);

        rll_shopcuatoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Shopcuatoi_Activity.class);
                startActivity(intent);
            }
        });

        tv_qlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QlspActivity.class);
                startActivity(intent);
            }
        });

        btn_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tv_dky_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.equalsIgnoreCase("")) {
                    Intent intent = new Intent();
                    String ed_nameshop = intent.getStringExtra("nameshop");
                    String ed_address = intent.getStringExtra("address");
                    showDialog(getContext(), ed_nameshop, ed_address);
                }else{
                    new AlertDialog.Builder(getContext()).setTitle("Thông Báo!!")
                            .setMessage("Bạn cần đăng nhập để có thể sử dụng chức năng!!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        });
        if (id.equalsIgnoreCase("")) {
            relative_newpass.setVisibility(View.INVISIBLE);
            idview5.setVisibility(View.INVISIBLE);
        } else {
            relative_newpass.setVisibility(View.VISIBLE);
            idview5.setVisibility(View.VISIBLE);
        }
        relative_newpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewPasswordActivity.class));
                getActivity().finish();
            }
        });

        if (!id.equalsIgnoreCase("")) {
            appCompatButton.setText("Sign Out");
            if (Objects.equals(role, "User")){
                rll_shopcuatoi.setVisibility(View.GONE);
                tv_qlsp.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
            }else {
                rlRole.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
            }
            appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", getContext().MODE_PRIVATE);

                    String userUID =  sharedPreferences.getString("id", "");
                    DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("userTokens").child(userUID);
                    tokenRef.removeValue();
                    sharedPreferences.edit().clear().commit();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            });
        } else {
            appCompatButton.setText("Sign In");
            rll_shopcuatoi.setVisibility(View.GONE);
            tv_qlsp.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);

            appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            });
        }
        view.findViewById(R.id.relative_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!id.equalsIgnoreCase("")) {
                    startActivity(new Intent(getContext(), FavoriteActivity.class));
                }else{
                    new AlertDialog.Builder(getContext()).setTitle("Thông Báo!!")
                            .setMessage("Bạn cần đăng nhập để xem sản phẩm đã thích.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        });

    }

    public void showDialog(Context context, String name, String address ) {
         dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register_shop);

        progressDialog = new ProgressDialog(getContext());
        sharedPreferences = getContext().getSharedPreferences("User", getContext().MODE_PRIVATE);
        id_user = sharedPreferences.getString("id", "");


        Button btn_regisshop = dialog.findViewById(R.id.btn_register_shop);
        EditText ed_nameshop = dialog.findViewById(R.id.signup_nameshop);
        EditText ed_address = dialog.findViewById(R.id.signup_address);

        btn_regisshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fname = ed_nameshop.getText().toString();
                String a = ed_address.getText().toString();


                Log.d("zzzz", "id_user: " + id_user);
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                Shop shop = new Shop();
                shop.setNameshop(fname);
                shop.setAddress(a);
                shop.setId_user(id_user);


                AddShop(shop);
                User us = new User();
                us.setRole("Salesman");

                UpdateRole(us);



            }
        });


        dialog.show();
    }

    void UpdateRole(User u) {
        progressDialog.show();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( gson))
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);

        Call<User> objCall = userInterface.udate_role(id_user,u);
        objCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (response.isSuccessful()){
                    dialog.dismiss();
                    sharedPreferences.edit().clear().commit();
                    progressDialog.dismiss();
                    if (u != null){
                        Toast.makeText(getContext(), "Bạn cần đăng nhập lại app!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                }else{
//                    Log.e("zzz", "onResponse: " +signUpUser.getMessage());
//                    Log.e(TAG, "onResponse: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Sign Up Fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("zzzz", t.getLocalizedMessage());

            }
        });

    }
    void AddShop(Shop s){

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( gson))
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);

        Call<Shop> objCall = userInterface.new_shop(s);
        objCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                Shop shop = response.body();

                if (response.isSuccessful()){

                    Log.e("zzzzz", "onResponse1: " +shop.getId_user());
                    Toast.makeText(getContext(), shop.getId(), Toast.LENGTH_SHORT).show();

                }else{
//                    Log.e("zzz", "onResponse: " +signUpUser.getMessage());
//                    Log.e(TAG, "onResponse: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(getContext(), "Sign Up Fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("zzzz", t.getLocalizedMessage());

            }
        });

    }
}
