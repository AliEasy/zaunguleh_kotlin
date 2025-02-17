package top.easyware.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.domain.usecase.home_last_visited_tab.HomeLastVisitedTabUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeLastVisitedTabUseCase: HomeLastVisitedTabUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state = _state

    init {
        homeLastVisitedTabUseCase.getHomeLastVisitedTabUseCase.invoke().let {
            _state.value = _state.value.copy(
                lastVisitedTab = it
            )
        }
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SaveLastVisitedTab -> {
                homeLastVisitedTabUseCase.saveHomeLastVisitedTabUseCase.invoke(intent.tab)
            }
        }
    }
}