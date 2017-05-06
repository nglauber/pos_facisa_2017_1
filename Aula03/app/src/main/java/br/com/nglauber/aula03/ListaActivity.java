package br.com.nglauber.aula03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class ListaActivity extends AppCompatActivity {

    private static final int REQ_CADASTRO = 0;
    public static final String EXTRA_PESSOAS = "pessoas";

    private ArrayList<Pessoa> pessoas;

    @BindView(R.id.lista_listview)
    ListView listView;

    ArrayAdapter<Pessoa> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            pessoas = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_PESSOAS));
        } else {
            pessoas = new ArrayList<>();
        }

        adapter = new PessoaAdapter(this, pessoas);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_PESSOAS, Parcels.wrap(pessoas));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == RESULT_OK) {
                Pessoa p = Parcels.unwrap(data.getParcelableExtra(DetalheActivity.EXTRA_PESSOA));
                int id = data.getIntExtra(DetalheActivity.EXTRA_ID, -1);
                if (id == -1) {
                    pessoas.add(p);
                } else {
                    pessoas.set(id, p);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnItemClick(R.id.lista_listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, pessoas.get(position).nome, Toast.LENGTH_SHORT).show();;

        Pessoa p = pessoas.get(position);
        Intent it = new Intent(this, DetalheActivity.class);
        it.putExtra(DetalheActivity.EXTRA_PESSOA, Parcels.wrap(p));
        it.putExtra(DetalheActivity.EXTRA_ID, position);
        startActivityForResult(it, REQ_CADASTRO);
    }

    @OnClick(R.id.lista_fab_add)
    void onClick(){
        Intent it = new Intent(this, DetalheActivity.class);
        startActivityForResult(it, REQ_CADASTRO);
    }

}
