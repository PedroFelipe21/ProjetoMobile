package com.example.aplicaobancodedados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class CursoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> listaCursos;
    private SQLiteDatabase db;

    public CursoAdapter(Context context, ArrayList<HashMap<String, String>> listaCursos, SQLiteDatabase db) {
        this.context = context;
        this.listaCursos = listaCursos;
        this.db = db;
    }

    @Override public int getCount() { return listaCursos.size(); }
    @Override public Object getItem(int position) { return listaCursos.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_curso, parent, false);
        }

        final HashMap<String, String> curso = listaCursos.get(position);
        final String idCurso = curso.get("id");

        // Vincula elementos do item_curso.xml
        TextView txtNome = convertView.findViewById(R.id.txtNomeCursoItem);
        TextView txtDuracao = convertView.findViewById(R.id.txtDuracaoCursoItem);
        TextView txtTurno = convertView.findViewById(R.id.txtTurnoCursoItem);
        Button btnEditar = convertView.findViewById(R.id.btnEditarCursoItem);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluirCursoItem);

        // Seta os textos
        txtNome.setText(curso.get("nome"));
        txtDuracao.setText("Duração: " + curso.get("duracao"));
        txtTurno.setText(curso.get("turno"));


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.execSQL("DELETE FROM cursos WHERE id = " + idCurso);
                    listaCursos.remove(position); // Remove da lista local
                    notifyDataSetChanged();       // Atualiza a tela instantaneamente
                    Toast.makeText(context, "Curso excluído!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Erro ao deletar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Editar Curso");

                // Cria um container vertical para segurar os 3 campos organizados
                LinearLayout layoutEditar = new LinearLayout(context);
                layoutEditar.setOrientation(LinearLayout.VERTICAL);
                layoutEditar.setPadding(40, 20, 40, 20);

                final EditText inputNome = new EditText(context);
                inputNome.setText(curso.get("nome"));
                inputNome.setHint("Nome do Curso");
                layoutEditar.addView(inputNome);

                final EditText inputDuracao = new EditText(context);
                inputDuracao.setText(curso.get("duracao"));
                inputDuracao.setHint("Duração");
                layoutEditar.addView(inputDuracao);

                final EditText inputTurno = new EditText(context);
                inputTurno.setText(curso.get("turno"));
                inputTurno.setHint("Turno");
                layoutEditar.addView(inputTurno);

                builder.setView(layoutEditar);

                builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String novoNome = inputNome.getText().toString();
                        String novaDuracao = inputDuracao.getText().toString();
                        String novoTurno = inputTurno.getText().toString();

                        try {
                            // Atualiza todas as colunas no SQLite
                            db.execSQL("UPDATE cursos SET nome = '" + novoNome + "', duracao = '" + novaDuracao + "', turno = '" + novoTurno + "' WHERE id = " + idCurso);

                            // Atualiza a lista local (memória)
                            curso.put("nome", novoNome);
                            curso.put("duracao", novaDuracao);
                            curso.put("turno", novoTurno);

                            notifyDataSetChanged();      // Atualiza a tela instantaneamente
                            Toast.makeText(context, "Curso atualizado!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Erro ao atualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });

        return convertView;
    }
}