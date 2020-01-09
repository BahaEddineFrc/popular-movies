package com.verycreatives.popularmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.verycreatives.popularmovies.models.User


class LoginViewModel : ViewModel() {
    var TAG ="LoginViewModel"
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

    var connectedUser : MutableLiveData<FirebaseUser> = MutableLiveData()

    var email : ObservableField<String> = ObservableField()
    var password: ObservableField<String> = ObservableField()
    var password2 : ObservableField<String> = ObservableField()

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String>
        get() = _msg

    fun register(v: View){
        _isRefreshing.value=true
        //Log.d(TAG,"${email.get().toString()} + ${password.get().toString()}")
        if (!password.get().toString().equals(password2.get().toString())) _msg.value="Passwords do not match"
        else

        mAuth?.createUserWithEmailAndPassword(email.get().toString(), password.get().toString())?.addOnCompleteListener { task->
            if (task.isSuccessful) {
                val user = mAuth!!.currentUser
                Log.d(TAG, "createUserWithEmail:success -> $user")
                connectedUser.postValue(user)
            } else {
                Log.w(TAG, "createUserWithEmail:failure ${task.exception}", task.exception)
                //updateUI(null)
                _msg.value=task.exception!!.message
            }
            _isRefreshing.value=false
        }
    }

    fun login(v: View){
        _isRefreshing.value=true
        Log.d(TAG,"${email.get().toString()} + ${password.get().toString()}")
        mAuth?.signInWithEmailAndPassword(email.get().toString(), password.get().toString())?.addOnCompleteListener { task->
            if (task.isSuccessful) {
                val user = mAuth!!.currentUser
                Log.d(TAG, "signInWithEmailAndPassword:success -> $user")
                connectedUser.postValue(user)
                //updateUI(user)
            } else {
                Log.w(TAG, "signInWithEmailAndPassword:failure ${task.exception}", task.exception)
                //updateUI(null)
                _msg.value=task.exception!!.message
            }
            _isRefreshing.value=false
        }
    }

}