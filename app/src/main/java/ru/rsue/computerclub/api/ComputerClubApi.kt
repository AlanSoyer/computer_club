package ru.rsue.computerclub.api

import retrofit2.http.*
import ru.rsue.computerclub.models.*

interface ComputerClubApi {

    // Visitors
    @GET("visitorapi")
    suspend fun getVisitors(): List<Visitor>

    @POST("visitorapi")
    suspend fun addVisitor(@Body visitor: Visitor): Visitor

    @PUT("visitorapi/{id}")
    suspend fun updateVisitor(@Path("id") id: Long, @Body visitor: Visitor): Visitor

    @DELETE("visitorapi/{id}")
    suspend fun deleteVisitor(@Path("id") id: Long): Unit

    // Computers
    @GET("computerapi")
    suspend fun getComputers(): List<Computer>

    @POST("computerapi")
    suspend fun addComputer(@Body computer: Computer): Computer

    @PUT("computerapi/{id}")
    suspend fun updateComputer(@Path("id") id: Long, @Body computer: Computer): Computer

    @DELETE("computerapi/{id}")
    suspend fun deleteComputer(@Path("id") id: Long): Unit

    // Visits
    @GET("visitapi")
    suspend fun getVisits(): List<Visit>

    @POST("visitapi")
    suspend fun addVisit(@Body visit: Visit): Visit

    @PUT("visitapi/{id}")
    suspend fun updateVisit(@Path("id") id: Long, @Body visit: Visit): Visit

    @DELETE("visitapi/{id}")
    suspend fun deleteVisit(@Path("id") id: Long): Unit

    // Computer Statuses
    @GET("statusapi")
    suspend fun getComputerStatuses(): List<ComputerStatus>

    @POST("statusapi")
    suspend fun addComputerStatus(@Body status: ComputerStatus): ComputerStatus

    @PUT("statusapi/{id}")
    suspend fun updateComputerStatus(@Path("id") id: Long, @Body status: ComputerStatus): ComputerStatus

    @DELETE("statusapi/{id}")
    suspend fun deleteComputerStatus(@Path("id") id: Long): Unit
}