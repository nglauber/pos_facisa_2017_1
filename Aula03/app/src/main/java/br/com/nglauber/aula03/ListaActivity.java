package br.com.nglauber.aula03;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.com.nglauber.aula03.persistence.PessoaDb;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaActivity extends AppCompatActivity {

    private static final int REQ_CADASTRO = 0;

    private List<Pessoa> pessoas;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PessoaRecycleAdapter adapter;

    PessoaDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);

        db = new PessoaDb(this);

        pessoas = new ArrayList<>();

        adapter = new PessoaRecycleAdapter(pessoas, new PessoaRecycleAdapter.AoClicarNaPessoa() {
            @Override
            public void pessoaFoiClicada(Pessoa pessoa, int id) {
                Intent it = new Intent(ListaActivity.this, DetalheActivity.class);
                it.putExtra(DetalheActivity.EXTRA_PESSOA, Parcels.wrap(pessoa));
                it.putExtra(DetalheActivity.EXTRA_ID, id);
                startActivityForResult(it, REQ_CADASTRO);
            }
        });
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        configuraSwipe();

        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }
    }

    @OnClick(R.id.lista_fab_add)
    void onClick(){
        Intent it = new Intent(this, DetalheActivity.class);
        startActivityForResult(it, REQ_CADASTRO);
    }

    private void configuraSwipe() {
        ItemTouchHelper.SimpleCallback swipe =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        final int position = viewHolder.getAdapterPosition();
                        excluirPessoa(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipe);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void excluirPessoa(final int position) {

        final Pessoa pessoa = pessoas.get(position);
        db.excluir(pessoa);
        refresh();

        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.msg_pessoa_excluida, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_desfazer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.salvar(pessoa, true);
                        refresh();
                    }
                })
                .show();
    }

    private void refresh(){
        pessoas.clear();
        pessoas.addAll(db.listar());
        adapter.notifyDataSetChanged();
    }
}
