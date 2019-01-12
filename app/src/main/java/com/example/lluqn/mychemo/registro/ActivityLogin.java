package com.example.lluqn.mychemo.registro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lluqn.mychemo.MainActivity;
import com.example.lluqn.mychemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class ActivityLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    private Button bt_login;

    private TextView txt_novoUser;
    private TextView txt_reset;

    private EditText txt_email;
    private EditText txt_senha;

    String[] msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        msg = new String[] {"USUÁRIO NÃO ENCONTRADO!",
                "ERRO DE AUTENTICAÇÃO!",
                "CAMPO OBRIGATÓRIO",
                "E-MAIL INVÁLIDO"};

        progressBar = findViewById(R.id.progressBar);

        txt_email = findViewById(R.id.txt_email);
        txt_senha = findViewById(R.id.txt_senha);

        bt_login = findViewById(R.id.bt_login);

        txt_novoUser = findViewById(R.id.txt_novoUser);
        txt_reset = findViewById(R.id.txt_reset);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_login) {

            try {

                String email = txt_email.getText().toString().trim();
                String senha = txt_senha.getText().toString().trim();

                if (validaDados(email, senha))
                    autenticar(email, senha);

            } catch (Exception e) {
                Toast.makeText(this, msg[0], Toast.LENGTH_SHORT).show();
            }

        }else if (id == R.id.txt_novoUser) {

            startActivity(new Intent(ActivityLogin.this, ActivityNovo.class));

            return;

        }else if (id == R.id.txt_reset) {

            startActivity(new Intent(ActivityLogin.this, ActivityReset.class));

            return;

        }
    }

    private void autenticar(String email, String senha){

        progressBar.setVisibility(View.VISIBLE);

        try {

            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(ActivityLogin.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });

        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, msg[1], Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validaDados(String email, String senha){

        if(email.isEmpty()){
            txt_email.setError(msg[2]);
            txt_email.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_email.setError(msg[3]);
            txt_email.requestFocus();
            return false;
        }

        if(senha.isEmpty()){
            txt_senha.setError(msg[2]);
            txt_senha.requestFocus();
            return false;
        }

        if(senha.length()<6){
            txt_senha.setError(msg[4]);
            txt_senha.requestFocus();
            return false;
        }

        return true;
    }
}
