package top.easyware.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.domain.usecase.home_last_visited_tab.GetHomeLastVisitedTabUseCase
import top.easyware.domain.usecase.home_last_visited_tab.HomeLastVisitedTabUseCase
import top.easyware.domain.usecase.home_last_visited_tab.SaveHomeLastVisitedTabUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeLastVisitedTabUseCase: HomeLastVisitedTabUseCase
) : ViewModel() {
    fun saveLastVisitedTab(tab: String) =
        homeLastVisitedTabUseCase.saveHomeLastVisitedTabUseCase.invoke(tab)

    fun getLastVisitedTab() = homeLastVisitedTabUseCase.getHomeLastVisitedTabUseCase.invoke()
}