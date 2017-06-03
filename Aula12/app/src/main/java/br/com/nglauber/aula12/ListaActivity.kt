package br.com.nglauber.aula12

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_lista.*
import org.parceler.Parcels


class ListaActivity : BaseActivity() {

    val db: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child(firebaseAuth.currentUser?.uid)
    }

    var adapter: PessoaFbAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        adapter = PessoaFbAdapter(db, this::pessoaFoiClicada)

        recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        configuraSwipe()

        fabAdd.setOnClickListener { onClick() }
    }

    fun pessoaFoiClicada(pessoa: Pessoa) {
        val it = Intent(this, DetalheActivity::class.java)
        it.putExtra(DetalheActivity.EXTRA_PESSOA, Parcels.wrap(pessoa))
        it.putExtra(DetalheActivity.EXTRA_ID, pessoa.uid)
        startActivity(it)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_signout -> FirebaseAuth.getInstance().signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClick() {
        val it = Intent(this, DetalheActivity::class.java)
        startActivity(it)
    }

    private fun configuraSwipe() {
        val swipe = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                excluirPessoa(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun excluirPessoa(position: Int) {
        val pessoa = adapter?.getItem(position)
        db.child(pessoa?.uid).removeValue()

        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.msg_pessoa_excluida, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_desfazer) {
                    val ref = db.push()
                    pessoa?.uid = ref.key
                    ref.setValue(pessoa)
                }
                .show()
    }
}
