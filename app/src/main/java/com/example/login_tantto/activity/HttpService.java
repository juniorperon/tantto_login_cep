package com.example.login_tantto.activity;

import android.os.AsyncTask;

import com.example.login_tantto.model.CEP;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//HttpService - doInBackground para nao travar o app.
//faz ligacao com o model CEP

public class HttpService extends AsyncTask<Void, Void, CEP> {

    //criacao de cep pelo constructor
    private final String cep;

    public HttpService(String cep) {
        this.cep = cep;
    }

    //doInBackground para realizar em background e nao travar o app
    @Override
    protected CEP doInBackground(Void... voids) {
        //criacao de builder
        StringBuilder resposta = new StringBuilder();

        //para rodar apenas quando inserido o cep
        if (cep != null && cep.length() == 8) {
            try {
                //consumir viacep
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");

                //fazendo conexao
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");//metodo para pegar as informacoes
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);//tempo maximo de pesquisa
                connection.connect();//conectar a URL

                //ler informacoes
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //utilizando Gson, uma biblioteca para facilitar a leitura -- convertendo JSON em CEP
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
