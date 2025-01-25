package top.easyware.zanguleh.core.util

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

        fun addMonthsToJalali(jalaliDate: String, monthsToAdd: Int): String {
            val (year, month, day) = jalaliDate.split("/").map { it.toInt() }

            // اضافه کردن ماه
            var newYear = year
            var newMonth = month + monthsToAdd

            // مدیریت تغییر سال
            while (newMonth > 12) {
                newYear += 1
                newMonth -= 12
            }

            // بررسی تعداد روزهای ماه جدید
            val daysInNewMonth = getDaysInJalaliMonth(newYear, newMonth)
            val newDay = if (day > daysInNewMonth) daysInNewMonth else day

            return "%04d/%02d/%02d".format(newYear, newMonth, newDay)
        }

        private fun getDaysInJalaliMonth(year: Int, month: Int): Int {
            return when (month) {
                1, 2, 3, 4, 5, 6 -> 31
                7, 8, 9, 10, 11 -> 30
                12 -> if (isJalaliLeapYear(year)) 30 else 29
                else -> throw IllegalArgumentException("Invalid month")
            }
        }

        private fun isJalaliLeapYear(year: Int): Boolean {
            // محاسبه سال کبیسه در تقویم شمسی
            val mod = (year - 474) % 2820
            return ((mod + 474 + 38) * 682 % 2816) < 682
        }

        fun addDaysToJalali(jalaliDate: String, days: Int): String {
            val persianDateFormat = PersianDateFormat("yyyy/MM/dd")
            val persianDate = persianDateFormat.parse(jalaliDate)
            val addedPersianDate = persianDate.addDays(days)
            return "${addedPersianDate.shYear}/${addedPersianDate.shMonth.toString().padStart(2, '0')}/${addedPersianDate.shDay.toString().padStart(2, '0')}"
        }
    }
}