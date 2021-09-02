package com.combyn.tvshows.model

import android.util.Log
import com.combyn.tvshows.utils.DateFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Movie(
    val id: String?,
    val title: String,
    val seasons: Double?,
    val releaseDate: Any?
) {
    companion object {
        const val placeholder = "--"
    }

    constructor(
        title: String,
        seasons: Double?,
        releaseDate: Any?
    ) : this(null, title, seasons, releaseDate)

    /**
     * Convert {@param Movie.seasons} to a string without decimal.
     *
     * It will use {@param Movie.placeholder} if {@param Movie.seasons} is null
     */
    fun getSeasonsStr(): String = seasons?.toInt()?.toString() ?: placeholder

    /**
     * Convert {@param Movie.releaseDate} to a human read able string.
     *
     * It will use {@param Movie.placeholder} if {@param Movie.releaseDate} is null
     */
    fun humanReadableReleaseDate(local: Locale): String {
        (releaseDate ?: placeholder).let {
            return when (it) {
                placeholder -> placeholder
                is String -> {
                    val inputFormatter =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", local)
                    try {
                        val date = inputFormatter.parse(it)
                        if (date != null) {
                            return DateFormatter.formatDate(date, local)
                        }
                    } catch (e: ParseException) {
                        Log.e("Movie", "pars error on $it");
                    }
                    return placeholder
                }
                is Date -> {
                    DateFormatter.formatDate(it, local)
                }
                else -> placeholder
            }
        }
    }
}