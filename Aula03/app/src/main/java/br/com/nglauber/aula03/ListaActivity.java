package br.com.nglauber.aula03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaActivity extends AppCompatActivity {

    private static final int REQ_CADASTRO = 0;

    private List<Pessoa> pessoas;

    @BindView(R.id.lista_listview)
    ListView listView;

    ArrayAdapter<Pessoa> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);
        pessoas = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, pessoas);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == RESULT_OK) {
                Pessoa p = (Pessoa)data.getSerializableExtra("pessoa");
                pessoas.add(p);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.lista_fab_add)
    void onClick(){
        Intent it = new Intent(this, DetalheActivity.class);
        startActivityForResult(it, REQ_CADASTRO);
    }

}
