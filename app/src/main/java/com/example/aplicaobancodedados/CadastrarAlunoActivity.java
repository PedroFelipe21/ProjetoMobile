package com.example.aplicaobancodedados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class CadastrarAlunoActivity extends AppCompatActivity {

    EditText etNomeAluno, etEmailAluno, etDataNasc, etMatricula;
    Spinner spCursosAluno;
    Button btnGerarMatricula, btnSalvarAluno;
    SQLiteDatabase db;
    ArrayList<String> listaNomesCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);

        // Vinculando componentes
        etNomeAluno = findViewById(R.id.etNomeAluno);
        etEmailAluno = findViewById(R.id.etEmailAluno);
        etDataNasc = findViewById(R.id.etDataNasc);
        etMatricula = findViewById(R.id.etMatricula);
        spCursosAluno = findViewById(R.id.spCursosAluno);
        btnGerarMatricula = findViewById(R.id.btnGerarMatricula);
        btnSalvarAluno = findViewById(R.id.btnSalvarAluno);

        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);
            carregarCursosNoSpinner(); // Carrega os cursos do banco
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao iniciar banco: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btnGerarMatricula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                // Gera um número aleatório entre 100000 e 999999
                int numeroMatricula = rand.nextInt(900000) + 100000;
                etMatricula.setText(String.valueOf(numeroMatricula));
            }
        });


        btnSalvarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeAluno.getText().toString();
                String email = etEmailAluno.getText().toString();
                String data = etDataNasc.getText().toString();
                String matricula = etMatricula.getText().toString();

                // Se não houver curso cadastrado, o spinner pode vir nulo ou vazio
                String curso = (spCursosAluno.getSelectedItem() != null) ? spCursosAluno.getSelectedItem().toString() : "";

                if (nome.isEmpty() || email.isEmpty() || data.isEmpty() || matricula.isEmpty() || curso.isEmpty()) {
                    Toast.makeText(CadastrarAlunoActivity.this, "Preencha todos os campos! Cadastre um curso primeiro.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    db.execSQL("INSERT INTO alunos(nome, email, data_nasc, matricula, curso) " +
                            "VALUES('" + nome + "', '" + email + "', '" + data + "', '" + matricula + "', '" + curso + "')");

                    Toast.makeText(CadastrarAlunoActivity.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Limpa os campos
                    etNomeAluno.setText("");
                    etEmailAluno.setText("");
                    etDataNasc.setText("");
                    etMatricula.setText("");
                } catch (Exception e) {
                    Toast.makeText(CadastrarAlunoActivity.this, "Erro ao salvar aluno: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void carregarCursosNoSpinner() {
        listaNomesCursos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nome FROM cursos", null);

        if (cursor.moveToFirst()) {
            do {
                listaNomesCursos.add(cursor.getString(0)); // Adiciona o nome do curso na lista
            } while (cursor.moveToNext());
        } else {
            listaNomesCursos.add("Nenhum curso cadastrado");
        }
        cursor.close();

        // O Adapter padrão do Android joga a lista de Strings para dentro do Spinner visualmente
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNomesCursos);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCursosAluno.setAdapter(spinnerAdapter);
    }
}