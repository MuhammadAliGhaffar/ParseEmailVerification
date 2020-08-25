package com.example.parseemailverification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_login_username,edt_login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_login_username=findViewById(R.id.edt_login_username);
        edt_login_password=findViewById(R.id.edt_login_password);

    }
    public void loginIsPressed(View btnView){

        ParseUser.logInInBackground(edt_login_username.getText().toString(), edt_login_password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseuser, ParseException e) {
                if(parseuser != null) {
                    if(parseuser.getBoolean("emailVerified")){
                        alertDisplayer("Login Sucessful","Welcome" + edt_login_username.getText().toString() + "!" ,false);
                    }else {
                        ParseUser.logOut();
                        alertDisplayer("Login Fail","Please verify your email first",true);
                    }
                }else{
                    ParseUser.logOut();
                    alertDisplayer("Login Fail",e.getMessage()+"Please re-try",true);

                }
            }
        });




    }
    private void alertDisplayer(String title,String message,final boolean error){
        AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent =new Intent(LoginActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                });
        AlertDialog ok = builder.create();
        ok.show();

    }
}