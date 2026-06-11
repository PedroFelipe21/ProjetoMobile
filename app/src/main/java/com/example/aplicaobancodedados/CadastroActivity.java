package com.example.aplicaobancodedados;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    Button btnConfirmar, btnVoltar;
    EditText etCadEmail, etCadSenha;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        // Vinculando os IDs do seu novo layout de cadastro
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        etCadEmail = (EditText) findViewById(R.id.etCadEmail);
        etCadSenha = (EditText) findViewById(R.id.etCadSenha);

        // Abre o banco unificado do sistema
        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);
        } catch(Exception e) {
            MostraMensagem("Erro ao abrir banco: " + e.toString());
        }

        // Ação de Confirmar o Cadastro
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String email = etCadEmail.getText().toString();
                String senha = etCadSenha.getText().toString();

                // Validação simples para não salvar em branco
                if (email.isEmpty() || senha.isEmpty()) {
                    MostraMensagem("Por favor, preencha todos os campos!");
                    return;
                }

                try {
                    // Insere na tabela 'usuarios' que criamos na abertura do app
                    db.execSQL("INSERT INTO usuarios(email, senha) VALUES('" + email + "','" + senha + "')");

                    MostraMensagem("Usuário cadastrado com sucesso!");



                } catch(Exception e) {
                    MostraMensagem("Erro ao inserir: " + e.toString());
                }
            }
        });


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(CadastroActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}