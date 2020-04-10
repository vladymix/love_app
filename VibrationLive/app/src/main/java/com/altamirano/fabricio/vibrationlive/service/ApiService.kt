package com.altamirano.fabricio.vibrationlive.service

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val service: LoveApi
    private val retrofit: Retrofit

    private val emailSource = "vane_fabo8@gmail.com"

    private val emailTarget = "developer@outlook.com"

    init {
        val client = OkHttpClient.Builder()
        retrofit = Retrofit.Builder()
            .baseUrl("https://apiservice.vladymix.es")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        service = retrofit.create(LoveApi::class.java)
    }

    companion object {
        private var instance: ApiService? = null

        // Singleton pattern
        fun getInstance(): ApiService {
            if (instance == null) {
                instance = ApiService()
            }
            return instance as ApiService
        }
    }

    fun generateCode(result: (String?) -> Unit) {
        service.generateCode(UserRequest(emailSource)).enqueue(object : Callback<CodeResponse> {
            override fun onFailure(call: Call<CodeResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<CodeResponse>, response: Response<CodeResponse>) {
                result.invoke(response.body()?.code)
            }

        })
    }

    fun checkPair(code: String, result: (PairResponse?) -> Unit) {
        service.checkPair(UserRequest(emailSource, code)).enqueue(object : Callback<PairResponse> {
            override fun onFailure(call: Call<PairResponse>, t: Throwable) {
                result.invoke(null)
            }

            override fun onResponse(call: Call<PairResponse>, response: Response<PairResponse>) {
                result.invoke(response.body())
            }

        })
    }

    fun pairDevice(code: String, f: (PairDeviceResponse?) -> Unit) {
        service.pairDevice(UserRequest(emailTarget, code))
            .enqueue(object : Callback<PairDeviceResponse> {
                override fun onFailure(call: Call<PairDeviceResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<PairDeviceResponse>,
                    response: Response<PairDeviceResponse>
                ) {
                    f.invoke(response.body())
                }
            })
    }

    fun sendMessage(code: String, int: Int) {
        service.message(MessageRequest(code, int)).enqueue(object : Callback<MessageResponse> {
            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {

            }
        })
    }
}


