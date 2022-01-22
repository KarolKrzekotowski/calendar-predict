package com.example.calendar_predict

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    lateinit var tablayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        tablayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tablayout.addTab(tablayout.newTab().setText("Dzień"))
        tablayout.addTab(tablayout.newTab().setText("Kalendarz"))
        tablayout.addTab(tablayout.newTab().setText("Statystyki"))
        val adapter = MyPagerAdapter(this,supportFragmentManager,tablayout.tabCount)
        viewPager.adapter =  adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
        )

        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)

//        setContentView(R.layout.activity_firebase_ui)
    }
    fun openSettings(view: View){
        val myintent = Intent(this,Settings::class.java)
        startActivity(myintent)
    }
    fun openGoals(view: View){
        val intent = Intent(this,Goals::class.java)
        startActivity(intent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().currentUser!!

            val database = Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")
            userRef = database.getReference("users/" + (Firebase.auth.currentUser!!.email?.replace('.', ' ') ?: 0))
            auth = FirebaseAuth.getInstance()
            Toast.makeText(this, "Welcome " + user.displayName, Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun signIn(view: android.view.View) {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    companion object {
        private lateinit var userRef: DatabaseReference

        public fun getMyRef(): DatabaseReference
        {
            return userRef
        }
    }
}