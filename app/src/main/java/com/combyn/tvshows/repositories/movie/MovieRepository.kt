package com.combyn.tvshows.repositories.movie

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.combyn.tvshows.CreateMovieMutation
import com.combyn.tvshows.model.Movie
import com.combyn.tvshows.type.CreateMovieFieldsInput
import com.combyn.tvshows.type.CreateMovieInput

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

    /**
     * Create a movie in apollo client using {@param movie} data and return
     * created movie id.
     */
    suspend fun createMovie(movie: Movie): String? {
        val createMovieInput = CreateMovieInput(
            fields = Optional.Present(
                CreateMovieFieldsInput(
                    title = movie.title,
                    seasons = Optional.Present(movie.seasons),
                    releaseDate = Optional.Present(movie.releaseDate)
                )
            )
        )
        val result = apolloClient.mutate(CreateMovieMutation(createMovieInput))
        return result.data?.createMovie?.movie?.id
    }
}