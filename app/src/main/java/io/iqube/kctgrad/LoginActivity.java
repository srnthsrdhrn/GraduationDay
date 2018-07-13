package io.iqube.kctgrad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.iqube.kctgrad.ConnectionService.ServiceGenerator;
import io.iqube.kctgrad.Home.DrawerActivity;
import io.iqube.kctgrad.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.iqube.kctgrad.KCTApplication.PREF_DEPT;
import static io.iqube.kctgrad.KCTApplication.PREF_NAME;
import static io.iqube.kctgrad.KCTApplication.PREF_ROLL;
import static io.iqube.kctgrad.KCTApplication.PREF_SESSION;
import static io.iqube.kctgrad.KCTApplication.SHARED_PREF_NAME;

public class LoginActivity extends AppCompatActivity {

    EditText regNoEt;
    Button login;
    String regNo;
    String regPattern = "[1]{1}[2,3,4]{1}[A-Z,a-z]{3}[0-9]{3}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regNoEt = (EditText)findViewById(R.id.regNo);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regNo = regNoEt.getText().toString();

                if(regNo.matches(regPattern))
                {
                    ServiceGenerator.KCTClient client=ServiceGenerator.createService(ServiceGenerator.KCTClient.class);

                    client.getUser("user/"+regNo+"/").enqueue(new Callback<List<User>>() {

                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                            if(response.code()==200)
                            {
                                if(response.body().size()!=0) {
                                    SharedPreferences user = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = user.edit();
                                    editor.putString(PREF_NAME, response.body().get(0).getName());
                                    editor.putString(PREF_DEPT, response.body().get(0).getDeptName());
                                    editor.putString(PREF_ROLL, response.body().get(0).getRollNo());
                                    editor.putInt(PREF_SESSION, response.body().get(0).getSession());
                                    editor.apply();
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                                }else{
                                    Toast.makeText(LoginActivity.this,"This Roll Number does not Exist",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Error with server please Try again!!", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Failure Connect To The Internet !! ", Toast.LENGTH_LONG).show();
                        }


                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Valid Registration Number !!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
