package com.combyn.tvshows.services.networking

import com.apollographql.apollo3.ApolloClient

object ApolloClientFactory {
    private const val serverUrl = "https://tv-show-manager.combyne.com/graphql"

    /**
     * A lazy loaded instance of apollo client to use all over app.
     */
    val apolloClient: ApolloClient by lazy {
        ApolloClient(serverUrl, interceptors = listOf(ApolloClientAuthInterceptor()))
    }
}