package net.riadh.henri.model


data class Room(
    val name: String,
    val password: String,
    val subjects: List<Subject>,
    var uuid: String = ""
)