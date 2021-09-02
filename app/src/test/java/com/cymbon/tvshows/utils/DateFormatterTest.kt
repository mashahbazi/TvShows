package com.cymbon.tvshows.utils

import com.combyn.tvshows.utils.DateFormatter
import org.junit.Assert
import org.junit.Test
import java.util.*

class DateFormatterTest {

    /**
     * Should return correct human readable string based on local
     */
    @Test
    fun formatDate_to_correct_string_in_local() {
        val c = Calendar.getInstance()
        c.set(2222, 2, 2)
        Assert.assertEquals("02-03-2222", DateFormatter.formatDate(c.time,Locale.UK))
        Assert.assertEquals("02-03-2222", DateFormatter.formatDate(c.time,Locale.US))
        Assert.assertEquals("02-03-2222", DateFormatter.formatDate(c.time,Locale.JAPAN))
    }
}