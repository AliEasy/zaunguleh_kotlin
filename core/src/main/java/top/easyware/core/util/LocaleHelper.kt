package top.easyware.core.util

import android.content.Context
import java.util.Locale

object LocaleHelper {
    fun wrapContext(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}