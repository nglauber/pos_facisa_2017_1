package br.com.nglauber.aula12

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var mGoogleApiClient : GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, object : GoogleApiClient.OnConnectionFailedListener {
                    override fun onConnectionFailed(p0: ConnectionResult) {
                        Toast.makeText(this@LoginActivity, "Erro ao conectar", Toast.LENGTH_SHORT).show()
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        btnSignIn.setOnClickListener { login() }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            firebaseAuthWithGoogle(result.signInAccount)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.d("NGVL", "firebaseAuthWithGoogle:" + acct?.id)

        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    Log.d("NGVL", "signInWithCredential:onComplete:" + task.isSuccessful)

                    if (task.isSuccessful) {
                        finish()
                        startActivity(Intent(this, ListaActivity::class.java))
                    } else {
                        Log.w("NGVL", "signInWithCredential", task.exception)
                        Toast.makeText(this@LoginActivity, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun login() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        val RC_SIGN_IN = 0
    }
}
