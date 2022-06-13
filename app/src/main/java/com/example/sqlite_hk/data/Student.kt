package com.example.sqlite_hk.data

import java.util.*

data class Student(
    val id: Int = getRandomId(),
    val fullName: String = "",
    val address: String
) {
    companion object {
        fun getRandomId(): Int {
            return Random().nextInt(1000)
        }
        const val TableDB_Name :String="tb_student"
        const val Column_ID :String="id"
        const val Column_FullName :String="fullName"
        const val Column_Address :String="address"
    }
}