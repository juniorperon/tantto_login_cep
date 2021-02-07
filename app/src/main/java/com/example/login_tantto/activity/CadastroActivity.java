package com.example.login_tantto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_tantto.R;
import com.example.login_tantto.helper.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

//Tela 1 - Tela de login, com usuário e senha. Sempre que o aplicativo for
//aberto será possível entrar com último usuário e senha digitados, ou -- fiz com que caisse na tela 2 direto (Dashboard)
//digitar um novo usuário e senha. Para essa tela deve ser utilizado um
//banco de dados local ou outra técnica de armazenamento local. -- utilizei o firebase

public class CadastroActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao; //autenticacao com firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponents();//identificando components da tela(esta no final dessa pagina)
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();//ligacao com o helper ConfiguracaoFirebase

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pegar os campos digitados quando clicar
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                //se nao estiver vazio rodar cadastro ou login(depende do switch) se nao erros.
                if( !email.isEmpty()) {
                    if(!senha.isEmpty()) {

                        //verifica switch
                        if( tipoAcesso.isChecked()) {//cadastro

                            autenticacao.createUserWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if( task.isSuccessful()) {

                                        Toast.makeText( CadastroActivity.this,
                                                "Cadastro feito com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        //Direcionar para a tela principal do app


                                    }else {

                                        String erroExcecao = "";
                                        //erros firebase cadastro
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            erroExcecao = "Digite uma senha mais forte";
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Digite um E-mail válido";
                                        } catch (FirebaseAuthUserCollisionException e) {
                                            erroExcecao = "Esta conta ja foi cadastrada";
                                        } catch (Exception e) {
                                            erroExcecao = "ao cadastrar usuario: " + e.getMessage();
                                            e.printStackTrace();
                                        }

                                        Toast.makeText( CadastroActivity.this,
                                                "Erro: " + erroExcecao,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {//login

                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if( task.isSuccessful() ) {

                                        Toast.makeText( CadastroActivity.this,
                                                "Logado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));// envia para a tela 2

                                    } else {

                                        Toast.makeText( CadastroActivity.this,
                                                "Erro ao fazer o login: " + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                        }
                    } else {
                        Toast.makeText( CadastroActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText( CadastroActivity.this,
                                    "Preencha o E-mail !",
                                    Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //components
    private void inicializaComponents() {
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);
    }
}