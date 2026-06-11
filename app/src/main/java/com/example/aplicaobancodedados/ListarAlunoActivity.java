package com.example.aplicaobancodedados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class ListarAlunoActivity extends AppCompatActivity {

    ListView lvAlunos;
    SQLiteDatabase db;
    ArrayList<HashMap<String, String>> listaAlunos;
    AlunoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aluno);

        lvAlunos = findViewById(R.id.lvAlunos);

        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);

            Cursor cursor = db.rawQuery("SELECT * FROM alunos", null);
            listaAlunos = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> aluno = new HashMap<>();
                    aluno.put("id", cursor.getString(0));
                    aluno.put("nome", cursor.getString(1));
                    aluno.put("email", cursor.getString(2));
                    aluno.put("data_nasc", cursor.getString(3));
                    aluno.put("matricula", cursor.getString(4));
                    aluno.put("curso", cursor.getString(5));
                    listaAlunos.add(aluno);
                } while (cursor.moveToNext());
            }
            cursor.close();

            adapter = new AlunoAdapter(this, listaAlunos, db);
            lvAlunos.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Erro ao listar alunos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}