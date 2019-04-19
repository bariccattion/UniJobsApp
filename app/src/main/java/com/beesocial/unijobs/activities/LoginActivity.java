package com.beesocial.unijobs.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.beesocial.unijobs.R;
import com.beesocial.unijobs.api.Api;
import com.beesocial.unijobs.api.RetrofitClient;
import com.beesocial.unijobs.models.DefaultResponse;
import com.beesocial.unijobs.models.LoginResponse;
import com.beesocial.unijobs.models.User;
import com.beesocial.unijobs.models.UserLogin;
import com.beesocial.unijobs.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    DefaultResponse resposta = new DefaultResponse();
    UserLogin model_obj;
    User userComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Campo necessário");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("O email precisa ser válido");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Campo necessário");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("A senha tem que ter ao mínimo 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }

        model_obj = new UserLogin(email, password);

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(model_obj);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                Toast.makeText(LoginActivity.this, loginResponse.getToken(), Toast.LENGTH_LONG).show();

                Retrofit retrofit  = new Retrofit.Builder()
                        .baseUrl("https://micro-unijobs-user.felipetiagodecarli.now.sh/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api client = retrofit.create(Api.class);
                Call<DefaultResponse> calltargetResponce = client.getUser(loginResponse.getToken());
                calltargetResponce.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> calltargetResponce, retrofit2.Response<DefaultResponse> responsee) {
                        DefaultResponse UserResponse = responsee.body();
                        Log.d("respostaLogin","deu bom?");
                        Log.d("respostaLogin",UserResponse.getEmail());
                        userComplete=new User(UserResponse.getId(),UserResponse.getEmail(),UserResponse.getName(),UserResponse.getImage(),UserResponse.getPassword());
                        Log.d("respostaLogin",userComplete.getEmail());
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(userComplete);
                    }
                    @Override
                    public void onFailure(Call<DefaultResponse> calltargetResponce, Throwable t) {
                        Log.d("respostaLogin","deu ruim");
                    }
                });


                //checar se o usuario ja existe, essas coisas que a mensagem fala


                Log.d("tokeee",loginResponse.getToken());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                userLogin();
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.textViewRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }



}
