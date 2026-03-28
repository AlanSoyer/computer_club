package ru.rsue.computerclub.models

data class Computer(
    val id: Long,
    val computerName: String,
    val description: String?,
    val statusId: Long,
    val statusName: String?
)