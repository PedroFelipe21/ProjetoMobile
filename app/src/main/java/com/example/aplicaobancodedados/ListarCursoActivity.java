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

public class ListarCursoActivity extends AppCompatActivity {

    ListView lvCursos;
    SQLiteDatabase db;
    ArrayList<HashMap<String, String>> listaCursos;
    CursoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_curso); // Seu XML da tela da lista de cursos

        lvCursos = findViewById(R.id.lvCursos);

        try {
            db = openOrCreateDatabase("mystudent", Context.MODE_PRIVATE, null);

            // Busca os dados do banco
            Cursor cursor = db.rawQuery("SELECT * FROM cursos", null);
            listaCursos = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> curso = new HashMap<>();
                    curso.put("id", cursor.getString(0));
                    curso.put("nome", cursor.getString(1));
                    curso.put("duracao", cursor.getString(2));
                    curso.put("turno", cursor.getString(3));
                    listaCursos.add(curso);
                } while (cursor.moveToNext());
            }
            cursor.close();

            // Liga os dados ao Adapter Customizado
            adapter = new CursoAdapter(this, listaCursos, db);
            lvCursos.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Erro ao carregar lista: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}