package com.example.aplicaobancodedados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class AlunoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> listaAlunos;
    private SQLiteDatabase db;

    public AlunoAdapter(Context context, ArrayList<HashMap<String, String>> listaAlunos, SQLiteDatabase db) {
        this.context = context;
        this.listaAlunos = listaAlunos;
        this.db = db;
    }

    @Override public int getCount() { return listaAlunos.size(); }
    @Override public Object getItem(int position) { return listaAlunos.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_aluno, parent, false);
        }

        final HashMap<String, String> aluno = listaAlunos.get(position);
        final String idAluno = aluno.get("id");

        TextView txtNome = convertView.findViewById(R.id.txtNomeItem);
        TextView txtEmail = convertView.findViewById(R.id.txtEmailItem);
        TextView txtMatricula = convertView.findViewById(R.id.txtMatriculaItem);
        TextView txtCurso = convertView.findViewById(R.id.txtCursoItem);
        Button btnEditar = convertView.findViewById(R.id.btnEditarItem);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluirItem);

        txtNome.setText(aluno.get("nome"));
        txtEmail.setText(aluno.get("email"));
        txtMatricula.setText("Mat: " + aluno.get("matricula"));
        txtCurso.setText(aluno.get("curso"));


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.execSQL("DELETE FROM alunos WHERE id = " + idAluno);
                    listaAlunos.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Aluno excluído!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Erro ao deletar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Editar Aluno");

                // Container vertical para organizar os 5 campos do aluno
                LinearLayout layoutEditar = new LinearLayout(context);
                layoutEditar.setOrientation(LinearLayout.VERTICAL);
                layoutEditar.setPadding(40, 20, 40, 20);

                final EditText inputNome = new EditText(context);
                inputNome.setText(aluno.get("nome"));
                inputNome.setHint("Nome");
                layoutEditar.addView(inputNome);

                final EditText inputEmail = new EditText(context);
                inputEmail.setText(aluno.get("email"));
                inputEmail.setHint("Email");
                layoutEditar.addView(inputEmail);

                final EditText inputData = new EditText(context);
                inputData.setText(aluno.get("data_nasc"));
                inputData.setHint("Data de Nascimento");
                layoutEditar.addView(inputData);

                final EditText inputMatricula = new EditText(context);
                inputMatricula.setText(aluno.get("matricula"));
                inputMatricula.setHint("Matrícula");
                layoutEditar.addView(inputMatricula);

                // Substituído o EditText por um Spinner para listar os cursos criados
                final Spinner spinnerCurso = new Spinner(context);
                spinnerCurso.setPadding(0, 20, 0, 20);

                // Carrega a lista de cursos direto do SQLite
                ArrayList<String> listaCursos = new ArrayList<>();
                Cursor cursor = db.rawQuery("SELECT nome FROM cursos", null);
                if (cursor.moveToFirst()) {
                    do {
                        listaCursos.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                } else {
                    listaCursos.add(aluno.get("curso")); // Caso não existam cursos, mantém o atual
                }
                cursor.close();

                // Define as opções do Spinner
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaCursos);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCurso.setAdapter(spinnerAdapter);

                // Pré-seleciona o curso que o aluno já pertencia
                int posicaoAtual = listaCursos.indexOf(aluno.get("curso"));
                if (posicaoAtual != -1) {
                    spinnerCurso.setSelection(posicaoAtual);
                }

                layoutEditar.addView(spinnerCurso);

                builder.setView(layoutEditar);

                builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String novoNome = inputNome.getText().toString();
                        String novoEmail = inputEmail.getText().toString();
                        String novaData = inputData.getText().toString();
                        String novaMatricula = inputMatricula.getText().toString();

                        // Pega o curso selecionado no Spinner
                        String novoCurso = (spinnerCurso.getSelectedItem() != null) ? spinnerCurso.getSelectedItem().toString() : aluno.get("curso");

                        try {
                            // Executa o UPDATE de todas as colunas no SQLite pelo ID do aluno
                            db.execSQL("UPDATE alunos SET nome = '" + novoNome + "', email = '" + novoEmail +
                                    "', data_nasc = '" + novaData + "', matricula = '" + novaMatricula +
                                    "', curso = '" + novoCurso + "' WHERE id = " + idAluno);

                            // Atualiza os dados na lista local da memória
                            aluno.put("nome", novoNome);
                            aluno.put("email", novoEmail);
                            aluno.put("data_nasc", novaData);
                            aluno.put("matricula", novaMatricula);
                            aluno.put("curso", novoCurso);

                            notifyDataSetChanged(); // Força a atualização da tela na mesma hora
                            Toast.makeText(context, "Dados updated!", Toast.LENGTH_SHORT).show();
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