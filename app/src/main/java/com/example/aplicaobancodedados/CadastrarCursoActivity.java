package com.example.aplicaobancodedados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastrarCursoActivity extends AppCompatActivity {

    EditText etNomeCurso, etDuracaoCurso, etTurnoCurso;
    Button btnSalvarCurso;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_curso); // Seu XML de cadastro de curso

        // Vinculando os componentes
        etNomeCurso = findViewById(R.id.etNomeCurso);
        etDuracaoCurso = findViewById(R.id.etDuracaoCurso);
        etTurnoCurso = findViewById(R.id.etTurnoCurso);
        btnSalvarCurso = findViewById(R.id.btnSalvarCurso);

        // Abre o banco de dados unificado
        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao abrir banco: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Ação de Salvar o Curso
        btnSalvarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeCurso.getText().toString();
                String duracao = etDuracaoCurso.getText().toString();
                String turno = etTurnoCurso.getText().toString();

                if (nome.isEmpty() || duracao.isEmpty() || turno.isEmpty()) {
                    Toast.makeText(CadastrarCursoActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Executa o Insert na tabela 'cursos'
                    db.execSQL("INSERT INTO cursos(nome, duracao, turno) VALUES('" + nome + "', '" + duracao + "', '" + turno + "')");
                    Toast.makeText(CadastrarCursoActivity.this, "Curso cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Limpa os campos após salvar
                    etNomeCurso.setText("");
                    etDuracaoCurso.setText("");
                    etTurnoCurso.setText("");

                } catch (Exception e) {
                    Toast.makeText(CadastrarCursoActivity.this, "Erro ao salvar curso: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}