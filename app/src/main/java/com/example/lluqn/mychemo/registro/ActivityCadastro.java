package com.example.lluqn.mychemo.registro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lluqn.mychemo.R;
import com.example.lluqn.mychemo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityCadastro extends AppCompatActivity {

    //view
    private ViewFlipper viewFlipper;
    private int pag = -1;

    //butões
    private Button bt_prox;
    private Button bt_ant;

    // estágios timeline
    private TextView txt0;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;

    //dados pessoais
    private CircleImageView img_user;
    private static final int RESULT_IMAGE = 1;
    private static final int PERMISSAO_REQUEST = 2;

    private TextView et_nome;

    private NumberPicker k;
    private NumberPicker g;

    private NumberPicker m;
    private NumberPicker cm;

    private DatePicker dtpicker;
    private RadioGroup rg_sexo;
    private ProgressBar progressBar;

    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        user = new Usuario();

        // componentes da timeline
        txt0 = findViewById(R.id.status_1);
        txt1 = findViewById(R.id.status_2);
        txt2 = findViewById(R.id.status_3);
        txt3 = findViewById(R.id.status_4);
        txt4 = findViewById(R.id.status_5);

        viewFlipper = findViewById(R.id.ViewFlipper);
        viewFlipper.setDisplayedChild(5);

        //dados pessoais pag 1
        img_user = findViewById(R.id.img_user);
        et_nome = findViewById(R.id.et_nome);

        //dados pessoais pag 2
        dtpicker = findViewById(R.id.dataPicker);
        dtpicker.setMaxDate(Calendar.getInstance().getTimeInMillis());

        //dados pessoais pag 3
        rg_sexo = findViewById(R.id.rg_sexo);

        //dados pessoais pag 4
        k = findViewById(R.id.k);
        g = findViewById(R.id.g);
        k.setMinValue(0);k.setMaxValue(200);k.setValue(0);
        g.setMinValue(0);g.setMaxValue(9);g.setValue(0);

        //dados pessoais pag 5
        m = findViewById(R.id.m);
        cm = findViewById(R.id.cm);
        m.setMinValue(0);m.setMaxValue(9);m.setValue(0);
        cm.setMinValue(0);cm.setMaxValue(99);cm.setValue(0);

        progressBar = findViewById(R.id.progressbar);

        //botoes
        bt_prox = findViewById(R.id.bt_proximo);
        bt_ant = findViewById(R.id.bt_anterior);
    }

    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.bt_proximo){

            if(bt_prox.getText().toString().equals("FINALIZAR")){
                insertDatabase();

            }else if(validacao(pag)){
                if(pag<4){
                    pag++;
                    timeLineBlue(pag);
                    viewFlipper.setDisplayedChild(pag);
                    bt_ant.setVisibility(View.VISIBLE);
                }else{
                    viewFlipper.setDisplayedChild(6);
                    bt_prox.setText("FINALIZAR");
                }
            }

        }else if(id == R.id.bt_anterior){

            if(pag>0){
                timeLineWrite(pag);
                pag--;
                viewFlipper.setDisplayedChild(pag);
                bt_prox.setText("PRÓXIMO");
            }else{
                bt_ant.setVisibility(View.INVISIBLE);
            }

        }else if(id == R.id.img_user){

            //carregando as permissões necessárias
            if(ContextCompat.checkSelfPermission(
                    ActivityCadastro.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(
                        ActivityCadastro.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                } else{ActivityCompat.requestPermissions(
                        ActivityCadastro.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
                }
            }

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent,RESULT_IMAGE);

        }

    }

    //manipulando timeline
    public void timeLineBlue(int id){
        switch (id){
            case 0:
                txt0.setBackground(getDrawable(R.drawable.timeline_blue));
                txt0.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                txt1.setBackground(getDrawable(R.drawable.timeline_blue));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                txt2.setBackground(getDrawable(R.drawable.timeline_blue));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                txt3.setBackground(getDrawable(R.drawable.timeline_blue));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
                txt4.setBackground(getDrawable(R.drawable.timeline_blue));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    public void timeLineWrite(int id){
        switch (id){
            case 0:
                txt0.setBackground(getDrawable(R.drawable.timeline_write));
                txt0.setTextColor(getResources().getColor(R.color.colorTexto));
                break;
            case 1:
                txt1.setBackground(getDrawable(R.drawable.timeline_write));
                txt1.setTextColor(getResources().getColor(R.color.colorTexto));
                break;
            case 2:
                txt2.setBackground(getDrawable(R.drawable.timeline_write));
                txt2.setTextColor(getResources().getColor(R.color.colorTexto));
                break;
            case 3:
                txt3.setBackground(getDrawable(R.drawable.timeline_write));
                txt3.setTextColor(getResources().getColor(R.color.colorTexto));
                break;
            case 4:
                txt4.setBackground(getDrawable(R.drawable.timeline_write));
                txt4.setTextColor(getResources().getColor(R.color.colorTexto));
                break;
        }
    }

    //recuperar imagem da galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK) {
            img_user.setImageBitmap(carregarImagemGaleria(data));
            user.setImage(BitMapToString(carregarImagemGaleria(data)));
        }
    }

    //permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions , int[] grantResults) {
        if(requestCode== PERMISSAO_REQUEST) {
            if(grantResults.length> 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // A permissão foi concedida. Pode continuar}
            }else {
                // A permissão foi negada. Precisa ver o que deve ser desabilitado}
            }

            return;
        }
    }

    // salvando usuário no banco de dados realtime
    public void insertDatabase(){

        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuário");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(ActivityCadastro.this,"Salvo com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(ActivityCadastro.this,"Error: Falha ao registrar!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean validacao(int id){

        switch (id){
            case 0:

                user.setNome(et_nome.getText().toString().trim());

                if(user.getNome().isEmpty()){
                    et_nome.setError("campo obrigatorio");
                    et_nome.requestFocus();
                    return false;
                }

                break;
            case 1:
                user.setDataNasc( dtpicker.getDayOfMonth() + "/" + (dtpicker.getMonth() + 1) + "/" + dtpicker.getYear());
                break;
            case 2:
                switch (rg_sexo.getCheckedRadioButtonId()){
                    case R.id.rb_fem:
                        RadioButton rb_fem = findViewById(R.id.rb_fem);
                        user.setSexo(rb_fem.getText().toString().trim());
                        break;
                    case R.id.rb_masc:
                        RadioButton rb_masc = findViewById(R.id.rb_masc);
                        user.setSexo(rb_masc.getText().toString().trim());
                        break;
                }
                break;
            case 3:
                user.setPeso(Double.parseDouble(k.getValue() + "." + g.getValue()));
                break;
            case 4:
                user.setAltura(Double.parseDouble(m.getValue()  + "." + cm.getValue()));
                break;

            default:

        }
        return true;
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
}
