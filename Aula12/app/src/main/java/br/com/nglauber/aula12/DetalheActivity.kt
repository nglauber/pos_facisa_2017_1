package br.com.nglauber.aula12

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detalhe.*
import org.parceler.Parcels

class DetalheActivity : BaseActivity() {

    val db : DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child(firebaseAuth.currentUser?.uid)
    }

    var pessoa: Pessoa? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        pessoa = Parcels.unwrap<Pessoa>(intent.getParcelableExtra<Parcelable>(EXTRA_PESSOA))
        id = intent.getIntExtra(EXTRA_ID, -1)
        if (pessoa == null) {
            pessoa = Pessoa()
        } else {
            preencherCampos()
        }
        btnSalvar.setOnClickListener { onSalvarClick() }
    }

    private fun preencherCampos() {
        edtNome.setText(pessoa?.nome)
        when (pessoa?.redesocial) {
            Pessoa.RS_FACEBOOK -> rgrSocial.check(R.id.detalhe_radio_fb)
            Pessoa.RS_GPLUS -> rgrSocial.check(R.id.detalhe_radio_gplus)
            Pessoa.RS_TWITTER -> rgrSocial.check(R.id.detalhe_radio_tw)
        }
    }

    internal fun onSalvarClick() {
        pessoa?.nome = edtNome.text.toString()
        when (rgrSocial.checkedRadioButtonId) {
            R.id.detalhe_radio_fb -> pessoa?.redesocial = Pessoa.RS_FACEBOOK
            R.id.detalhe_radio_tw -> pessoa?.redesocial = Pessoa.RS_TWITTER
            R.id.detalhe_radio_gplus -> pessoa?.redesocial = Pessoa.RS_GPLUS
        }
        val it = Intent()
        it.putExtra(EXTRA_PESSOA, Parcels.wrap<Pessoa>(pessoa))
        it.putExtra(EXTRA_ID, id)
        setResult(Activity.RESULT_OK, it)

        if (pessoa?.uid.isNullOrEmpty()) {
            val ref = db.push()
            pessoa?.uid = ref.key
            ref.setValue(pessoa)
        } else {
            db.child(pessoa?.uid).setValue(pessoa)
        }

        finish()
    }

    companion object {
        val EXTRA_PESSOA = "pessoa"
        val EXTRA_ID = "id"
    }
}
