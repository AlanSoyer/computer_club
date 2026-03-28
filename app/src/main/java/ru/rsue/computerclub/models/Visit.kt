package ru.rsue.computerclub.models

data class Visit(
    val id: Long,
    val visitorId: Long,
    val computerId: Long,
    val visitorFullName: String?,
    val computerName: String?,
    val visitDate: String,  // ← String, а не LocalDate
    val duration: Int,
    val payment: Double
)