package com.karyadev.e_agendaprobolinggo.utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun localDateFormat(date: String, time: String): LocalDate {
        val dateStr = "$date $time"

        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun yearMonthFormat(date: String): YearMonth {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

}