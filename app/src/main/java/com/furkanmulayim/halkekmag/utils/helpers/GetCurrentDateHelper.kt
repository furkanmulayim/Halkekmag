package com.furkanmulayim.halkekmag.utils.helpers

import java.text.SimpleDateFormat
import java.util.*

object GetCurrentDateHelper {

        fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

