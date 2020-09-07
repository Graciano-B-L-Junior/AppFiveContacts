package com.example.fivecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class listar_contatos extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    List nome = new ArrayList<String>();
    List numero = new ArrayList<String>();
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contatos);
        lv = (ListView)findViewById(R.id.teste);
        Bundle listaContatos = new Bundle();
        Intent lista = this.getIntent();
        listaContatos = lista.getExtras();
        user = (Usuario) listaContatos.get("user");

        for (Contato t:user.contato) {
                nome.add(t.nome);
                numero.add(t.numero);
        }
        ArrayAdapter<String> test = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nome);
        lv.setAdapter(test);
        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String teste = (String) numero.get(i);
        Uri uri = Uri.parse("tel:"+teste);
        if(checarPermissao()){
            Intent ligar = new Intent(Intent.ACTION_CALL,uri);
            startActivity(ligar);
        }

    }

    protected boolean checarPermissao(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            String[] permissoes = {Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(this,permissoes,1212);
        }
        return false;
    }

    public void alterarDados(View view){
        Bundle alterar = new Bundle();
        Intent a = new Intent(getApplicationContext(),alterar_usuario.class);
        alterar.putSerializable("user",user);
        a.putExtras(alterar);
        startActivity(a);
    }

    public void voltarListaAgenda(View view){
        Bundle voltarAgenda = new Bundle();
        Intent voltar = new Intent(getApplicationContext(),pegar_contatos.class);
        voltarAgenda.putSerializable("user",user);
        voltar.putExtras(voltarAgenda);
        startActivity(voltar);
        finish();
    }

}