package com.example.fivecontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nome;
    EditText usuario;
    Usuario user;
    int inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = (EditText) findViewById(R.id.edt_Nome);
        usuario = (EditText) findViewById(R.id.edt_Usuario);
        Bundle params2 = new Bundle();
        Intent teste = getIntent();
        if ((params2 = teste.getExtras()) == null){
            Log.v("PDM","Deu ruim");
        }else{
            params2 = teste.getExtras();
            user = (Usuario) params2.get("user");
            inicio=1;
        }


        if(inicio!=0){
            Intent t = this.getIntent();
            Bundle params = t.getExtras();
            user =(Usuario) params.get("user");
        }
        if(montarObjetoUserSemLogar()){
            Intent Contatos = new Intent(MainActivity.this,listar_contatos.class);
            Bundle param = new Bundle();
            param.putSerializable("user",user);
            Contatos.putExtras(param);
            startActivity(Contatos);
            inicio++;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(inicio!=0){
            Intent t = this.getIntent();
            Bundle params = t.getExtras();
            user =(Usuario) params.get("user");
        }

    }

    public void sendMessage(View view) {
        if(user!=null){
            if(nome.getText().toString().equals(user.nome) && usuario.getText().toString().equals(user.login)){

                if(user.QtdContatos==0){
                    Bundle param = new Bundle();
                    Intent t = new Intent(getApplicationContext(),pegar_contatos.class);
                    param.putSerializable("user",user);
                    t.putExtras(param);
                    startActivity(t);
                    finish();
                }else{
                    Bundle param = new Bundle();
                    Intent t = new Intent(getApplicationContext(),listar_contatos.class);
                    param.putSerializable("user",user);
                    t.putExtras(param);
                    startActivity(t);
                    finish();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Nome ou usuário incorretos",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Cadastre um novo usuário, toque no botão novo",Toast.LENGTH_LONG).show();
        }


    }

    public void testa(View v){
        Intent t = new Intent(getApplicationContext(),novo_usuario.class);
        startActivity(t);
    }

    private boolean montarObjetoUserSemLogar(){
        SharedPreferences usuario = getSharedPreferences("teste",Activity.MODE_PRIVATE);
        boolean manterLogado = usuario.getBoolean("manterLogado",false);
        return manterLogado;
        }
    }
