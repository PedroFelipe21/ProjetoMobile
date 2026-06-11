package com.example.aplicaobancodedados;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Componentes da Tela de Login
    Button btnEntrar, btnCadastrar;
    EditText etEmail, etSenha;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Garanta que este é o XML da tela de login


        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);


        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);

            // Cria todas as tabelas silenciosamente se elas não existirem
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, senha TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS cursos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, duracao TEXT, turno TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS alunos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT, data_nasc TEXT, matricula TEXT, curso TEXT);");

        } catch(Exception e) {
            MostraMensagem("Erro ao inicializar o banco: " + e.toString());
        }

        // ====================================================================
        // AÇÃO DO BOTÃO ENTRAR (LOGIN)
        // ====================================================================
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                if (email.isEmpty() || senha.isEmpty()) {
                    MostraMensagem("Por favor, digite o e-mail e a senha!");
                    return;
                }

                try {

                    Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = '" + email + "' AND senha = '" + senha + "'", null);

                    if (cursor.moveToFirst()) {

                        cursor.close();


                        Intent intent = new Intent(MainActivity.this, TelaPrincipalActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        // Banco voltou vazio
                        MostraMensagem("E-mail ou senha incorretos.");
                        cursor.close();
                    }
                } catch (Exception e) {
                    MostraMensagem("Erro ao autenticar: " + e.toString());
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a CadastroActivity que acabamos de ajustar
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}