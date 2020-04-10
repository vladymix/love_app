package com.altamirano.fabricio.vibrationlive.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoveApi {
    @POST("/love_api/generateCode")
    fun generateCode(@Body request:UserRequest): Call<CodeResponse>

    @POST("/love_api/checkPair")
    fun checkPair(@Body request:UserRequest): Call<PairResponse>

    @PUT("/love_api/pairDevice")
    fun pairDevice(@Body request:UserRequest): Call<PairDeviceResponse>

    @POST("/love_api/message")
    fun message(@Body request:MessageRequest): Call<MessageResponse>

}