package com.altamirano.fabricio.vibrationlive.service

data class UserRequest(var email:String, var code: String?=null)

data class CodeResponse(var status:Int,var code:String)

data class PairResponse(var status:Int,var with_user:String?, var connected:Int?=null, var is_active:Int?=null)

data class PairDeviceResponse(var status:Int,var with_user:String, var code: String)

data class MessageRequest(var code:String,var is_active:Int)
data class MessageResponse(var status:Int)