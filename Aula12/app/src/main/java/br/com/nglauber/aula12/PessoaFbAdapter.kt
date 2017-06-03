package br.com.nglauber.aula12

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.item_pessoa.view.*

class PessoaFbAdapter(ref: DatabaseReference, val callback : (Pessoa)->Any) : FirebaseRecyclerAdapter<Pessoa, PessoaFbAdapter.PessoaVH>(
        Pessoa::class.java, R.layout.item_pessoa, PessoaVH::class.java, ref
) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PessoaVH {
        val vh = super.onCreateViewHolder(parent, viewType)

        vh.itemView.setOnClickListener { callback(getItem(vh.adapterPosition)) }

        return vh
    }

    override fun populateViewHolder(viewHolder: PessoaVH?, model: Pessoa?, position: Int) {
        val image: Int
        when (model?.redesocial) {
            Pessoa.RS_FACEBOOK -> image = R.drawable.logo_face
            Pessoa.RS_GPLUS -> image = R.drawable.logo_gplus
            else -> image = R.drawable.logo_twitter
        }
        viewHolder?.itemView?.imgLogo?.setImageResource(image)
        viewHolder?.itemView?.txtNome?.text = model?.nome
    }

    class PessoaVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}
