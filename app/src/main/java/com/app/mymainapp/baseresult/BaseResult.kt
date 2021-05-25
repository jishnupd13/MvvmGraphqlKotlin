package com.app.mymainapp.baseresult

/** Created by Jishnu P Dileep on 25-05-2021 */

data class BaseResult<out T>(
    val status: Status,
    val data: T?,
    val message:String?
){
    companion object{

        fun <T> success(data:T?): BaseResult<T>{
            return BaseResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg:String, data:T?): BaseResult<T>{
            return BaseResult(Status.ERROR, data, msg)
        }

        fun <T> loading(data:T?): BaseResult<T>{
            return BaseResult(Status.LOADING, data, null)
        }

    }
}