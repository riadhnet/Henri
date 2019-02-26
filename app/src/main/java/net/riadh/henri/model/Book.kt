package net.riadh.henri.model


data class Book(
    val cover: String,
    val isbn: String,
    val price: Int,
    val synopsis: List<String>,
    val title: String
)