package top.easyware.zanguleh.features.home_widget.event_daily_counter.presentation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.screens.Screens
import top.easyware.zanguleh.features.daily_counter.presentation.components.ReminderItem

class EventDailyCounterWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = EventDailyCounterWidget()
}

class EventDailyCounterWidget: GlanceAppWidget() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface EventDailyCounterWidgetContentProviderEntryPoint {
        fun viewModel(): EventDailyCounterViewModel
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, EventDailyCounterWidgetContentProviderEntryPoint::class.java)

        val viewModel = hiltEntryPoint.viewModel()
        val eventList = viewModel.eventList.value

        provideContent {
            GlanceTheme() {
                EventList(eventList)
            }
        }
    }

    @Composable
    private fun EventList(eventList: List<ReminderModel>) {
        LazyColumn {
            items(eventList) {event ->
                ReminderItem(
                    modifier = Modifier.fillMaxWidth(),
                    reminder = event,
                    onTap = {

                    },
                )
            }
        }
    }

}