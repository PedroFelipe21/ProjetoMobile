package com.example.aplicaobancodedados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipalActivity extends AppCompatActivity {

    Button btnMenuCadAluno, btnMenuCadCurso, btnMenuVerAlunos, btnMenuVerCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal); // Seu XML do menu principal

        // Vinculando os botões ao XML
        btnMenuCadAluno = findViewById(R.id.btnMenuCadAluno);
        btnMenuCadCurso = findViewById(R.id.btnMenuCadCurso);
        btnMenuVerAlunos = findViewById(R.id.btnMenuVerAlunos);
        btnMenuVerCursos = findViewById(R.id.btnMenuVerCursos);

        // 1. Botão para abrir o Cadastro de Aluno
        btnMenuCadAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipalActivity.this, CadastrarAlunoActivity.class);
                startActivity(intent);
            }
        });

        // 2. Botão para abrir o Cadastro de Curso
        btnMenuCadCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipalActivity.this, CadastrarCursoActivity.class);
                startActivity(intent);
            }
        });

        // 3. Botão para abrir a Lista de Alunos
        btnMenuVerAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipalActivity.this, ListarAlunoActivity.class);
                startActivity(intent);
            }
        });

        // 4. Botão para abrir a Lista de Cursos
        btnMenuVerCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipalActivity.this, ListarCursoActivity.class);
                startActivity(intent);
            }
        });
    }
}