package top.easyware.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.domain.usecase.GetHomeLastVisitedTabUseCase
import top.easyware.domain.usecase.SaveHomeLastVisitedTabUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeLastVisitedTabUseCase: GetHomeLastVisitedTabUseCase,
    private val saveHomeLastVisitedTabUseCase: SaveHomeLastVisitedTabUseCase
) : ViewModel() {
    fun saveLastVisitedTab(tab: String) = saveHomeLastVisitedTabUseCase.invoke(tab)
    fun getLastVisitedTab() = getHomeLastVisitedTabUseCase.invoke()
}