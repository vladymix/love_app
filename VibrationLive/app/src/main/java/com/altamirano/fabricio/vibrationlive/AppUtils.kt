package com.altamirano.fabricio.vibrationlive

object AppUtils {

    fun getEquals():LongArray{
        val list = LongArray(3)
        list[0]=0
        list[1]=5000
        list[2]=800
        return list
    }

    fun getPower():LongArray{
        val list = LongArray(3)
        list[0]=0
        list[1]=60000
        list[2]=0
        return list
    }

    fun getTow():LongArray{
        val list = LongArray(7)
        list[0]=0
        list[1]=3000
        list[2]=600
        list[3]=1500
        list[4]=600
        list[5]=1500
        list[6]=600
        return list
    }

    fun getBest():LongArray{
        val list = LongArray(9)
        list[0]=0
        list[1]=200
        list[2]=50
        list[3]=200
        list[4]=50
        list[5]=200
        list[6]=500
        list[7]=3000
        list[8]=50
        return list
    }
}