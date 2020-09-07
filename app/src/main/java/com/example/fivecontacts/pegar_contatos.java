package com.example.fivecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class pegar_contatos extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button bt_salvar;
    Button bt_buscar;
    EditText pesquisa;
    ListView listaContatosDisplay;
    Usuario user;
    String nomeContato;
    String numeroContato;
    List numeros = new ArrayList<String>();
    List nomes = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegar_contatos);
        bt_salvar = (Button)findViewById(R.id.btSalvar);
        bt_buscar =(Button)findViewById(R.id.buscar);
        bt_buscar.setOnClickListener(this);
        pesquisa =(EditText)findViewById(R.id.edtBusca);
        listaContatosDisplay = (ListView)findViewById(R.id.listaContatos);
        Bundle usuario = new Bundle();
        Intent lista = this.getIntent();
        usuario = lista.getExtras();
        user = (Usuario) usuario.get("user");
        listaContatosDisplay.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            String consulta = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
            String[] argumentosConsulta ={"%"+pesquisa.getText()+"%"};

            ContentResolver cr = getContentResolver();
            Cursor cursor= cr.query(ContactsContract.Contacts.CONTENT_URI,null,consulta,argumentosConsulta,null);

            while (cursor.moveToNext()) {
                int indiceNome= cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                nomes.add(cursor.getString(indiceNome));
                String nome =cursor.getString(indiceNome);


                int indiceColunaContato = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
                String contadoID =cursor.getString(indiceColunaContato);

                String consultaPhone = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = "+contadoID;
                Cursor phones =cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,consultaPhone,null,null);
                int i =0;
                while (phones.moveToNext()){
                    if(i==0){
                        String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        numeros.add(number);
                        Log.v("PDM","Numero: "+number +" Nome: "+nome );
                        i++;
                    }else{
                        i=0;
                    }
                }
            }

            ArrayAdapter<String> teste = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nomes);
            listaContatosDisplay.setAdapter(teste);
        }else{
            ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.READ_CONTACTS},1212);
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
                String consulta = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
                String[] argumentosConsulta ={"%"+pesquisa.getText()+"%"};
                ContentResolver cr = getContentResolver();
                Cursor cursor= cr.query(ContactsContract.Contacts.CONTENT_URI,null,consulta,argumentosConsulta,null);

                while (cursor.moveToNext()){

                    int indiceNome= cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                    nomes.add(cursor.getString(indiceNome));

                }
                ArrayAdapter<String> teste = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nomes);
                listaContatosDisplay.setAdapter(teste);
            }

        }
    }

    public void salvarContato(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED) {
            Intent paraListaContatos = new Intent(getApplicationContext(), listar_contatos.class);
            Contato contatoSelecionado = new Contato();
            contatoSelecionado.numero = numeroContato;
            contatoSelecionado.nome = nomeContato;
            if(user.contato.contains(contatoSelecionado)){
                Toast.makeText(this,"O contato "+nomeContato+" já está salvo na lista",Toast.LENGTH_LONG).show();
            }

            else if(user.QtdContatos>=5){
                Toast.makeText(this,"Limite de contatos salvos atingido, exclua algum contato",Toast.LENGTH_LONG).show();
            }
            else{
                user.contato.add(contatoSelecionado);
                user.QtdContatos++;
                Bundle param = new Bundle();
                param.putSerializable("user", user);
                paraListaContatos.putExtras(param);
                startActivity(paraListaContatos);
            }

        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            String numero = (String) numeros.get(i);
            String nome = (String) nomes.get(i);
            numeroContato = numero;
            nomeContato = nome;
            Toast.makeText(this,"Contato: "+nomeContato+" selecionado, toque em salvar contato",Toast.LENGTH_LONG).show();
        }
    }
}