package top.easyware.domain.repository

interface SharedPreferencesRepository {
    fun saveHomeLastVisitedTab(tab: String)

    fun getHomeLastVisitedTab(): String?
    fun setIntroSliderShowed()
    fun getIntroSliderShowed(): Boolean
}