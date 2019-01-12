package com.example.lluqn.mychemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import com.example.lluqn.mychemo.conta.ActivityPerfil;
import com.example.lluqn.mychemo.registro.ActivityLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridLayout = findViewById(R.id.mainGrid);

    }

    public void clickButton(View view) {
        int id = view.getId();

        if (id == R.id.card_1) {
            startActivity(new
                    Intent(this, ActivityPerfil.class)
            );
            return;

        }else if(id == R.id.card_2) {
            return;

        }else if(id == R.id.card_3) {
            return;

        }else if(id == R.id.card_4) {
            return;

        }

        return;
    }

    @Override //inflando o menu da toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override //configurando opções
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            mAuth.signOut();
            startActivity(new Intent(this, ActivityLogin.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //verificar se ja existe usuário logado
        if (user == null) {
            startActivity(new Intent(this, ActivityLogin.class));
            finish();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
