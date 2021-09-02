package com.cymbon.tvshows.model

import com.combyn.tvshows.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * This is test class for [Movie]
 */
class MovieTest {

    /**
     * Should return place holder if season is null
     */
    @Test
    fun getSeasonsStr_placeholder_result() {
        val movie = Movie("", null, null)
        assertEquals(Movie.placeholder,movie.getSeasonsStr())
    }

    /**
     * Should return string without decimal value
     */
    @Test
    fun getSeasonsStr_return_int_string() {
        val movie = Movie("", 1.23, null)
        assertEquals("1",movie.getSeasonsStr())
    }

    /**
     * Should return placeholder when date is null
     */
    @Test
    fun humanReadableReleaseDate_placeholder_for_null() {
        val movie = Movie("", null, null)
        assertEquals(Movie.placeholder,movie.humanReadableReleaseDate(Locale("en")))
    }

    /**
     * Should return placeholder when release date is string but can't convert to date
     */
    @Test
    fun humanReadableReleaseDate_placeholder_for_non_date_string() {
        val movie = Movie("", null, "null")
        assertEquals(Movie.placeholder,movie.humanReadableReleaseDate(Locale.UK))
    }

    /**
     * Should return human readable string based on user local for correct format of string release date
     */
    @Test
    fun humanReadableReleaseDate_correct_value_for_string() {
        val movie = Movie("", null, "2222-05-01T00:00:00.000Z")
        assertEquals("01-05-2222",movie.humanReadableReleaseDate(Locale.UK))
        assertEquals("01-05-2222",movie.humanReadableReleaseDate(Locale.US))
        assertEquals("01-05-2222",movie.humanReadableReleaseDate(Locale.JAPAN))
    }

    /**
     * Should return human readable string based on user local for correct format date of release date
     */
    @Test
    fun humanReadableReleaseDate_correct_value_for_date() {
        val c = Calendar.getInstance()
        c.set(2222, 2, 2)
        val movie = Movie("", null, c.time)
        assertEquals("02-03-2222",movie.humanReadableReleaseDate(Locale.UK), )
        assertEquals("02-03-2222",movie.humanReadableReleaseDate(Locale.US), )
        assertEquals("02-03-2222",movie.humanReadableReleaseDate(Locale.JAPAN), )
    }

}