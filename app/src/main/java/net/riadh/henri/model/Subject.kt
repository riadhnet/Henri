package net.riadh.henri.model


data class Subject(
    val question: Question,
    val answer: Question,
    var uuid: String = ""
)