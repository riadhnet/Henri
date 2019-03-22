package net.riadh.henri.model


data class Question(
    val score: Int,
    val value: String,
    var userUid: String = ""
)