package com.example.medicalexample

class User (
    val username: String = "",
    var password: String = "",
) {
    override fun toString(): String {
        return "$username  $password"
    }
}