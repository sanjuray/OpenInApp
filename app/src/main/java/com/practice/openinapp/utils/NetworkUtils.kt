package com.practice.openinapp.utils

import com.practice.openinapp.model_class.TopLinks
import com.practice.openinapp.networking.RetrofitAPI
import retrofit2.Response

object NetworkUtils {

    val openInAppService = RetrofitAPI.apiService

    fun makeNetworkCall(
        response: Response<TopLinks>,
        onErrorCallback: (String) -> Unit,
        postProcessingCallback: (Any) -> Unit
    ){
        /**
         * 0 -> error in request and request parameters are a problem(parsing issue)
         * 1 -> error in api-end-point
         */
        try {
            CommonObjects.networkLogs(CommonObjects.makeNetworkLogMessageFormat(response.code(),response.message()))
            if(response.isSuccessful){
                if(response.code() == 401){
                    onErrorCallback("error in request and request parameters are a problem(parsing issue)")
                }else if(response.code() == 400){
                    onErrorCallback("error in api-end-point")
                }else{
                    postProcessingCallback(response.body()!!)
                }
            }else{
                onErrorCallback("Request not succeeded.")
            }
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

}
