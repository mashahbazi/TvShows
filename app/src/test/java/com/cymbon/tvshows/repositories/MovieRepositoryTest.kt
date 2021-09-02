package com.cymbon.tvshows.repositories

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.combyn.tvshows.CreateMovieMutation
import com.combyn.tvshows.model.Movie
import com.combyn.tvshows.repositories.movie.MovieRepository
import com.combyn.tvshows.type.CreateMovieFieldsInput
import com.combyn.tvshows.type.CreateMovieInput
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {
    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    /**
     * Should return movie id after create movie
     */
    @Test
    fun createMovie_return_created_movie_id() = testScope.runBlockingTest {
        val movie = Movie("title", null, null)
        val createMovieInput = CreateMovieInput(
            fields = Optional.Present(
                CreateMovieFieldsInput(
                    title = movie.title,
                    seasons = Optional.Present(movie.seasons),
                    releaseDate = Optional.Present(movie.releaseDate)
                )
            )
        )

        coEvery {
            apolloClient.mutate(CreateMovieMutation(createMovieInput))
        } returns
                ApolloResponse(
                    UUID.randomUUID(),
                    CreateMovieMutation(createMovieInput),
                    CreateMovieMutation.Data(
                        CreateMovieMutation.CreateMovie(
                            CreateMovieMutation.Movie(
                                "answerId"
                            )
                        )
                    )
                )

        val movieRepository = MovieRepository(apolloClient)
        val id = movieRepository.createMovie(movie)
        assertEquals("answerId", id)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}