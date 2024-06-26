package com.example.chatbottest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void onPressJoinPressed(View view) {
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtPassword = findViewById(R.id.txtPassword);

        Amplify.Auth.signUp(
                txtEmail.getText().toString(),
                txtPassword.getText().toString(),
                AuthSignUpOptions.builder().userAttribute(
                        AuthUserAttributeKey.email(),txtEmail.getText().toString()
                ).build(),
                this::onJoinSuccess,
                this::onJoinError

        );
    }

    private void onJoinSuccess(AuthSignUpResult authSignUpResult) {
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtPassword = findViewById(R.id.txtPassword);
        EditText txtName = findViewById(R.id.txtName);
        Intent intent = new Intent(this, EmailConfirmationActivity.class);
        intent.putExtra("email",txtEmail.getText().toString());
        intent.putExtra("password",txtPassword.getText().toString());
        intent.putExtra("name",txtName.getText().toString());
        startActivity(intent);

    }

    private void onJoinError(AuthException e) {
        this.runOnUiThread(()-> {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        });
    }


}