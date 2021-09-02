package com.combyn.tvshows.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    companion object {
        /**
         * Converts @{link date} to local time of @{local} and formate it to a human readable string
         */
        fun formatDate(date: Date, local: Locale): String {
            val outputFormatter =
                SimpleDateFormat("dd-MM-yyy", local)
            return outputFormatter.format(date)
        }
    }
}