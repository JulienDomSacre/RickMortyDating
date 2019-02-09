package com.juliensacre.rickmortydating.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl : IConnectivityInterceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("check connectivity and return exception if don't have") //To change body of created functions use File | Settings | File Templates.
    }

    //TODO add check connectivity
}