package com.combyn.tvshows.services.networking

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.withHttpHeader
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow

class ApolloClientAuthInterceptor : ApolloInterceptor {
    /**
     * This will add headers to all apollo client requests.
     * Headers are authentications parameters for graph ql.
     */
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val authorizedRequest = request.withHttpHeader(
            "X-Parse-Client-Key",
            "yiCk1DW6WHWG58wjj3C4pB/WyhpokCeDeSQEXA5HaicgGh4pTUd+3/rMOR5xu1Yi",
        ).withHttpHeader(
            "X-Parse-Application-Id",
            "AaQjHwTIQtkCOhtjJaN/nDtMdiftbzMWW5N8uRZ+DNX9LI8AOziS10eHuryBEcCI"
        )
        return chain.proceed(authorizedRequest)
    }
}