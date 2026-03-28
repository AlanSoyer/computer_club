package ru.rsue.computerclub.models

data class Visitor(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val identityDocument: String?,
    val address: String?,
    val phone: String
)