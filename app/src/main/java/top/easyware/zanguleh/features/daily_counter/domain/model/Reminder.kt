package top.easyware.zanguleh.features.daily_counter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    val title: String,
    @PrimaryKey val reminderId: Int? = null
)