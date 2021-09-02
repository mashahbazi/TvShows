package com.combyn.tvshows.repositories.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.combyn.tvshows.MoviesQuery
import com.combyn.tvshows.model.Movie
import com.combyn.tvshows.type.MovieOrder

class MovieDataSource(
    private val apolloClient: ApolloClient,
    private val movieOrder: MovieOrder,
) :
    PagingSource<String, Movie>() {
    companion object {
        const val pageSize = 20
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<String, Movie>): String? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(-1)
        }
    }

    /**
     * Loads apollo client movies and convert them to app Movie model.
     */
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Movie> {
        val response = apolloClient.query(MoviesQuery(pageSize, listOf(movieOrder), params.key))
        val pageInfo = response.data?.movies?.pageInfo
        return LoadResult.Page(
            data = response.data?.movies?.edges?.mapNotNull { it?.node }
                ?.map { Movie(it.id, it.title, it.seasons, it.releaseDate) } ?: emptyList(),
            prevKey = null,
            nextKey = if (pageInfo?.hasNextPage == true) pageInfo.endCursor else null,
        )
    }
}