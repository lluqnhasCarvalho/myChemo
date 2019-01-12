package com.example.lluqn.mychemo.conta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lluqn.mychemo.R;
import com.example.lluqn.mychemo.adapter.AdapterPerfilOp;
import com.example.lluqn.mychemo.model.Usuario;
import com.example.lluqn.mychemo.registro.ActivityCadastro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityPerfil extends AppCompatActivity {
    private static final int RESULT_IMAGE_FRENTE = 1;
    private static final int RESULT_IMAGE_FUNDO = 2;

    private Usuario user;
    private AdapterPerfilOp adapter;

    //componentes do cabeçalho
    private TextView txtNome;
    private TextView alterarImg;
    private ImageView imgfrente;

    private ImageView img_perfil;
    private ImageView img_fundo;

    //componentes do corpo da view
    private TextView txtSexo;
    private TextView txtPeso;
    private TextView txtIdade;
    private TextView txtAltura;
    private TextView txtEmail;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        img_perfil = findViewById(R.id.imgfrente_user);
        img_fundo = findViewById(R.id.imgfundo_user);

        // componentes cabeçalho da view
        txtNome = findViewById(R.id.txtnome_user);
        imgfrente = findViewById(R.id.add_imgfrente);
        alterarImg = findViewById(R.id.alterar_img);

        //componentes de dados
        txtSexo = findViewById(R.id.txtsexo_paciente);
        txtPeso = findViewById(R.id.txtpeso_paciente);
        txtIdade = findViewById(R.id.txtidade_parente);
        txtAltura = findViewById(R.id.txtaltura_paciente);
        txtEmail = findViewById(R.id.txtemail_paciente);

        //componentes itens
        carregarItens();

    }

    public void onClick(View view){
        int id = view.getId();

        if(id == R.id.imgfundo_user){

            Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intentPegaFoto,RESULT_IMAGE_FRENTE);

        }else if(id == R.id.alterar_img){

            Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intentPegaFoto,RESULT_IMAGE_FUNDO);

        }
    }

    public void atualizar(){

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuário");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActivityPerfil.this,"Salvo com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ActivityPerfil.this,"Error: Falha ao registrar!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public Bitmap carregarImagemGaleria(Intent data){
        InputStream stream = null;
        Bitmap bitmap = null;

        try {

            stream = getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_IMAGE_FRENTE && resultCode == RESULT_OK) {
            img_perfil.setImageBitmap(carregarImagemGaleria(data));
            user.setImage(BitMapToString(carregarImagemGaleria(data)));
        }else if (requestCode == RESULT_IMAGE_FUNDO && resultCode == RESULT_OK) {
            img_fundo.setImageBitmap(carregarImagemGaleria(data));
            user.setImage_fundo(BitMapToString(carregarImagemGaleria(data)));
        }

        atualizar();

    }

    public void carregarItens(){

        ArrayList<Integer> listImagens = new ArrayList<>();
        listImagens.add(R.drawable.img_grafico);
        listImagens.add(R.drawable.img_status);
        listImagens.add(R.drawable.img_chat);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Gráfico");
        animalNames.add("Status");
        animalNames.add("Chat");

        RecyclerView recyclerView = findViewById(R.id.recycle_opcoes);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new AdapterPerfilOp(this, listImagens, animalNames);

        adapter.setOnItemClickListener(new AdapterPerfilOp.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if(position == 0){
                    startActivity(
                            new Intent(ActivityPerfil.this, ActivityStatus.class)
                    );
                }else if(position == 1){
                    startActivity(
                            new Intent(ActivityPerfil.this, ActivityStatus.class)
                    );
                }else if(position == 2){
                    startActivity(
                            new Intent(ActivityPerfil.this, ActivityStatus.class)
                    );
                }
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(ActivityPerfil.this, "onItemLongClick pos = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("usuário")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            try {

                                user = dataSnapshot.getValue(Usuario.class);
                                if(user.getNome() == null){
                                    startActivity(
                                            new Intent(ActivityPerfil.this, ActivityCadastro.class)
                                    );
                                }else{
                                    txtNome.setText(user.getNome());
                                    txtEmail.setText(firebaseUser.getEmail());
                                    txtIdade.setText(user.getDataNasc());
                                    txtPeso.setText(String.valueOf(user.getPeso()) + " kg");
                                    txtAltura.setText(String.valueOf(user.getAltura()) + " cm");
                                    txtSexo.setText(user.getSexo());

                                    img_perfil.setImageBitmap(StringToBitMap(user.getImage()));
                                    img_fundo.setImageBitmap(StringToBitMap(user.getImage_fundo()));
                                }

                            } catch (Exception e) {
                                Toast.makeText(ActivityPerfil.this, "IMPOSSIVEL ACESSAR O BANCO DE DADOS", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
  }
}
