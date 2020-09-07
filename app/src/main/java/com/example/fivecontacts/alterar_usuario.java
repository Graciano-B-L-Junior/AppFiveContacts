package com.example.fivecontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class alterar_usuario extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_alterar_usuario);
        Bundle param = new Bundle();
        Intent t = getIntent();
        param = t.getExtras();
        user = (Usuario) param.get("user");
        nome = (EditText)findViewById(R.id.edtNome);
        senha = (EditText)findViewById(R.id.edt_Pass2);
        usuario =(EditText)findViewById(R.id.edT_Login2);
        email =(EditText)findViewById(R.id.edEmail);
        bt_Criar = (Button)findViewById(R.id.btCriar);
        swLogado = (Switch)findViewById(R.id.swLogado);

        nome.setText(user.nome);
        senha.setText(user.senha);
        usuario.setText(user.login);
        email.setText(user.email);
        swLogado.setChecked(user.logarDireto);
        bt_Criar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle valoresAtualizados = new Bundle();
        Intent voltar = new Intent(getApplicationContext(),listar_contatos.class);
        user.logarDireto = swLogado.isChecked();
        user.nome = nome.getText().toString();
        user.email = email.getText().toString();
        user.senha = senha.getText().toString();
        user.login = usuario.getText().toString();
        valoresAtualizados.putSerializable("user",user);
        voltar.putExtras(valoresAtualizados);
        startActivity(voltar);
        finish();
    }



}