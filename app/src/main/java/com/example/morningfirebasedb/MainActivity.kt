package com.example.morningfirebasedb

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIDNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView: Button
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.medtName)
        edtEmail =findViewById(R.id.medtEmail)
        edtIDNumber =findViewById(R.id.mIDNumber)
        btnSave =findViewById(R.id.mbtnSave)
        btnView = findViewById(R.id.mbtnView)
        progressDialog =  ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email =edtEmail.text.toString().trim()
            var idNumber = edtIDNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            if(name.isEmpty()){
                edtName.setError("Please fill in the fields... ")
                edtName.requestFocus()
            }else if(email.isEmpty()){
                edtEmail.setError("Please fill in the fields")
                edtEmail.requestFocus()

            }else if(idNumber.isEmpty()){
                edtIDNumber.setError("Please fill in the fields")
                edtIDNumber.requestFocus()
            }else{
                //Proceed to save
                var user =User(name,email,idNumber,id)
                //Create a reference in the firebase database
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                //Show the progress to start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                   //Dismiss the progress abd check if the task was ssuccessful
                    task->
                    progressDialog.dismiss()
                    if (task.isSuccessful){
                      Toast.makeText(this,"User saved successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Saving Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        btnView.setOnClickListener {

        }
    }
}