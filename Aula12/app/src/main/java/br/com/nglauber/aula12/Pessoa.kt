package br.com.nglauber.aula12

import org.parceler.Parcel

@Parcel
data class Pessoa (var uid: String = "",
                   var nome: String = "",
                   var redesocial: Int = RS_FACEBOOK) {

    companion object {
        val RS_FACEBOOK = 0
        val RS_TWITTER = 1
        val RS_GPLUS = 2
    }
}
