package top.easyware.zanguleh.features.home_widget.event_daily_counter.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.fillMaxWidth
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

class EventDailyCounterWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = EventDailyCounterWidget()
}

class EventDailyCounterWidget : GlanceAppWidget() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface EventDailyCounterWidgetContentProviderEntryPoint {
        fun viewModel(): EventDailyCounterViewModel
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                EventDailyCounterWidgetContentProviderEntryPoint::class.java
            )

        val viewModel = hiltEntryPoint.viewModel()
        val eventList = viewModel.eventList.value
//        Log.d("EventDailyCounterWidget", id.toString())
//        val eventList = listOf(
//            ReminderModel(
//                reminderId = 1,
//                title = "etetet",
//                reminderType = "occasion",
//                reminderDueDate = "",
//                reminderDueDatePersian = ""
//            ),
//            ReminderModel(
//                reminderId = 1,
//                title = "fwefewfq wfw ",
//                reminderType = "occasion",
//                reminderDueDate = "",
//                reminderDueDatePersian = ""
//            ),
//        )

        provideContent {
            GlanceTheme() {
                EventList(eventList)
            }
        }
    }

    @Composable
    private fun EventList(eventList: List<ReminderModel>) {
        LazyColumn {
            items(
                eventList.size,
                itemContent = {
                    val event = eventList[it]
                    ReminderItemWidget(
                        modifier = GlanceModifier.fillMaxWidth(),
                        reminder = event,
                        onTap = {

                        },
                    )
                },
            )
        }
    }

}