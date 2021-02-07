package com.example.login_tantto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_tantto.R;
import com.example.login_tantto.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

//Tela 2 - : Painel de controle (Dashboard), com um botão chamando a tela 3. -- utilizei um Menu em cima do app para chamar tela3

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    //criação do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //criação das opções do menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //diferença de menu caso esteja logado ou deslogado
        if( autenticacao.getCurrentUser() == null ) {//usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);

        } else {//usuario logado
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    //menu selecionado -- mais informacoes estao no menu_main.xml
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch( item.getItemId()) {
            case R.id.menu_cadastrar://cadastrar ou login
                startActivity( new Intent(getApplicationContext(), CadastroActivity.class));
                break;
            case R.id.menu_sair://para deslogar
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_config:// um menu de configurações. para desenvolver futuro.
                Toast.makeText( DashboardActivity.this,
                        "Ainda não desenvolvemos... Estamos trabalhando com isso. ",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.VerificarCep://menu que envia para tela 3 -- para verificar o cep
                startActivity( new Intent(getApplicationContext(), BuscarActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}