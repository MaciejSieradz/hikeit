package com.example.hikeit.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain

val apolloClient = ApolloClient.Builder()
    .serverUrl("http://10.0.2.2:8080/graphql")
    .addHttpInterceptor(AuthorizationInterceptor)
    .build()

object AuthorizationInterceptor : HttpInterceptor {

    var token: String = ""

    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        return chain.proceed(request.newBuilder().addHeader("Authorization", "Bearer $token").build())
    }
}