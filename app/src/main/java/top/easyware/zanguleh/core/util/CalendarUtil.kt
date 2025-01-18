package top.easyware.zanguleh.core.util

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.Calendar

class CalendarUtil {
    companion object {
        fun convertJalaliToGregorian(jalaliDate: String, time: String): Calendar {
            val persianDateFormat = PersianDateFormat("yyyy/MM/dd")
            val persianDate = persianDateFormat.parse(jalaliDate)

            val (hour, minute) = time.split(":").map { it.toInt() }

            val calendar = Calendar.getInstance()
            calendar.set(
                persianDate.grgYear,
                persianDate.grgMonth - 1,
                persianDate.grgDay,
                hour,
                minute,
                0
            )

            return calendar
        }
    }
}