package com.example.login_tantto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_tantto.R;
import com.example.login_tantto.model.CEP;

import java.util.concurrent.ExecutionException;

/*Tela 3 -  a) Buscar CEP digitado e mostrar retorno da tela. Usar o seguinte
                webservice para consulta http://viacep.com.br/ .
            b) Ao digitar um dos CEP abaixo, o sistema não deverá fazer a busca no
                webservice, e deve apresentar a seguinte mensagem:
                “Este CEP está na lista negra”.
                Lista: 18010-001, 18010-082, 18013-001 e 18055-131.
            c) CEP do estado de MG devem retornar erro.
            d) Qualquer bairro começado com “s” ou “S” também deve retornar erro. */

public class BuscarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        //identificando caixas de texto e botoes na tela do app.
        final EditText cep = findViewById(R.id.txtCep);
        final TextView resposta = findViewById(R.id.txtResposta);

        Button buscarCep = findViewById(R.id.buttonBuscar);

        //quando clicar no botao buscar
        buscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ceps = cep.getText().toString();//pegar so o valor inserido

                //caso nao digite por completo - erro
                if (ceps.length() < 8) {
                    Toast.makeText(BuscarActivity.this,
                            "Erro: Digite um CEP",
                            Toast.LENGTH_SHORT).show();

                    //b) verifica lista negra antes de usar viacep
                    } else if (ceps.equals("18010001") || ceps.equals("18010082") || ceps.equals("18013001") || ceps.equals("18055131")) {
                        Toast.makeText(BuscarActivity.this,
                                "Erro: Este CEP está na lista negra \n" +
                                        "Lista: 18010-001, 18010-082, 18013-001 e 18055-131",
                                Toast.LENGTH_LONG).show();
                    } else {//buscar CEP
                        try {
                            //a) utilizando viacep -- utiliza o model CEP e HttpService para pegar informacoes inseridas.
                            CEP retorno = new HttpService(cep.getText().toString()).execute().get();
                            if( retorno.getUf().startsWith("MG")) {//c) qualquer CEP com estado MG da erro
                                Toast.makeText(BuscarActivity.this,
                                        "Erro: estado não permitido(MG)",
                                        Toast.LENGTH_SHORT).show();
                                resposta.setText("");//apagar mensagem anterior

                                //d) qualquer CEP com bairro começado com s ou S da erro
                                } else if ( retorno.getBairro().startsWith("s") || retorno.getBairro().startsWith("S")) {
                                    Toast.makeText(BuscarActivity.this,
                                            "Erro: Este bairro começa com 's' ou 'S' ",
                                            Toast.LENGTH_SHORT).show();
                                    resposta.setText("");//apagar mensagem anterior

                                    //erro caso não encontre o cep no viacep
                                    } else if( retorno.getUf() == "") {
                                        Toast.makeText(BuscarActivity.this,
                                                "Erro: Nenhum CEP encontrado!",
                                                Toast.LENGTH_SHORT).show();
                                        resposta.setText("");//apagar mensagem anterior

                                } else
                                    resposta.setText(retorno.toString());//mostrar todas informacoes existentes(do viacep)

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        }
                    });
                }

    //menu criado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //opçoes do menu -- apenas volta para o dashboard
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.group_voltar, true);
        return super.onPrepareOptionsMenu(menu);
    }

    //voltar dashboard se selecionar(tela 2)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
