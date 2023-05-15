package com.chat.chatapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPasswoord: EditText
    private lateinit var edtName: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_Name)
        edtEmail = findViewById(R.id.edt_email)
        edtPasswoord = findViewById(R.id.edt_Password)
        btnSignUp = findViewById(R.id.btn_SignUp)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPasswoord.text.toString()

            signUp(name,email,password)
        }

    }

    private fun signUp(name: String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid!!).setValue(User(name,email,uid))

    }


}