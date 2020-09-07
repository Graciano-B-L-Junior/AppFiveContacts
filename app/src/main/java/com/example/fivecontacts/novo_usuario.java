package com.example.fivecontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;

public class novo_usuario extends AppCompatActivity {
    EditText nome;
    EditText senha;
    EditText usuario;
    EditText email;
    Button bt_Criar;
    Switch swLogado;
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
        nome = (EditText)findViewById(R.id.edtNome);
        senha = (EditText)findViewById(R.id.edt_Pass2);
        usuario =(EditText)findViewById(R.id.edT_Login2);
        email =(EditText)findViewById(R.id.edtEmail);
        bt_Criar = (Button)findViewById(R.id.btCriar);
        swLogado = (Switch)findViewById(R.id.swLogado);

        bt_Criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nome.getText().toString() !="" || senha.getText().toString() != "" ||
                   usuario.getText().toString() !="" || email.getText().toString() != ""){
                    SharedPreferences preferencias = getSharedPreferences("teste",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    boolean logado = swLogado.isChecked();
                    editor.putBoolean("manterLogado",logado);
                     user = new Usuario(nome.getText().toString(),usuario.getText().toString()
                    ,senha.getText().toString(),email.getText().toString());
                     user.logarDireto = logado;
                    editor.commit();
                    Intent t = new Intent(getApplicationContext(),MainActivity.class);
                    Bundle userIntent = new Bundle();
                    userIntent.putSerializable("user", user);
                    t.putExtras(userIntent);
                    int inicioValido =1;
                    userIntent.putSerializable("inicio",inicioValido);
                    startActivity(t);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha todos os dados",Toast.LENGTH_LONG);
                }
            }
        });
    }
}