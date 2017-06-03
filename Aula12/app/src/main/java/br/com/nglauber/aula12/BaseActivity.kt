package br.com.nglauber.aula12

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


open class BaseActivity : AppCompatActivity() {

    val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    lateinit var mAuthListener: FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                finish()
                startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
            }
        }

    }

    public override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(mAuthListener)
    }

    public override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(mAuthListener)
    }
}
