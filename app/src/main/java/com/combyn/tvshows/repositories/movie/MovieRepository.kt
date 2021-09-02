package com.combyn.tvshows.repositories.movie

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.apollographql.apollo3.ApolloClient
import com.combyn.tvshows.model.Movie

class MovieRepository(private val apolloClient: ApolloClient) {
    /**
     * Fetch all movies from apollo client.
     *
     * It will fetch them by paging and return them as live data
     */
    fun getAllMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(MovieDataSource.pageSize, enablePlaceholders = true),
            pagingSourceFactory = { MovieDataSource(apolloClient) }
        ).liveData
    }
}