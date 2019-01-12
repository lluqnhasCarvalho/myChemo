package com.example.lluqn.mychemo.registro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lluqn.mychemo.MainActivity;
import com.example.lluqn.mychemo.R;
import com.example.lluqn.mychemo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityNovo extends AppCompatActivity {

    private EditText et_email;
    private EditText et_senha;
    private EditText et_confsenha;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        firebaseAuth = FirebaseAuth.getInstance();

        et_email = (EditText) findViewById(R.id.et_email);
        et_senha = (EditText) findViewById(R.id.et_senha);
        et_confsenha = (EditText) findViewById(R.id.et_confSenha);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    public void clickButton(View view) {
        int id = view.getId();

        if(id == R.id.bt_salvar){
            if(validacao())
                registrar(et_email.getText().toString().trim(),et_senha.getText().toString().trim());

            return;
        }

        if(id == R.id.bt_cancelar){
            finish();
            return;
        }
    }

    private void registrar(String arg, String arg1) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(arg, arg1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("usuário")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new Usuario()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(ActivityNovo.this,"Salvo com sucesso!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ActivityNovo.this,MainActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(ActivityNovo.this,"Error: Falha ao registrar!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(ActivityNovo.this, "Usuário já cadastrado", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private boolean validacao() {

        String email = et_email.getText().toString().trim();
        String senha = et_senha.getText().toString().trim();
        String confSenha = et_confsenha.getText().toString().trim();


        if(email.isEmpty()){
            et_email.setError("campo obrigatorio");
            et_email.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("e-mail invalido");
            et_email.requestFocus();
            return false;
        }

        if(senha.isEmpty()){
            et_senha.setError("campo obrigatorio");
            et_senha.requestFocus();
            return false;
        }

        if(senha.length()<6){
            et_senha.setError("minimo de 6 digitos");
            et_senha.requestFocus();
            return false;
        }

        if(confSenha.isEmpty()){
            et_confsenha.setError("campo obrigatorio");
            et_confsenha.requestFocus();
            return false;
        }

        if(confSenha.length()<6){
            et_confsenha.setError("minimo de 6 digitos");
            et_confsenha.requestFocus();
            return false;
        }

        if(!senha.equals(confSenha)) {

            et_senha.setError("senhas diferentes");
            et_senha.setText("");

            et_confsenha.setError("senhas diferentes");
            et_confsenha.setText("");

            et_senha.requestFocus();

            return false;
        }

        return true;
    }

}
