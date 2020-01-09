package com.verycreatives.popularmovies.models

import java.io.Serializable

class User(

    val uid: String,
    val name: String,
    val email: String

) : Serializable{
    var isAuthenticated: Boolean = false
    var isNew: Boolean = false
    var isCreated: Boolean = false
    override fun toString(): String {
        return "User(uid='$uid', name='$name', email='$email', isAuthenticated=$isAuthenticated, isNew=$isNew, isCreated=$isCreated)"
    }


}