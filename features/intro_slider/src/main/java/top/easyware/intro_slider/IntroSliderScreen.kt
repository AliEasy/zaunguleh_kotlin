package top.easyware.intro_slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import top.easyware.core.util.UiText
import top.easyware.intro_slider.component.IntroSliderItem
import top.easyware.uikit.ButtonComponent
import top.easyware.uikit.ButtonComponentType

@Composable
fun IntroSliderScreen(
    viewModel: IntroSliderViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = {
            state.introSliderPages.size
        })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonComponent(
                        title = if (pagerState.currentPage == state.introSliderPages.size - 1) {
                            UiText.StringResource(
                                R.string.enter_app
                            ).asString()
                        } else {
                            UiText.StringResource(R.string.continue_label)
                                .asString()
                        },
                        onClick = {
                            if (pagerState.currentPage == state.introSliderPages.size - 1) {

                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        buttonType = ButtonComponentType.Filled,
                        modifier = Modifier.wrapContentWidth()
                    )
                    if (pagerState.currentPage != 0) {
                        ButtonComponent(
                            title = UiText.StringResource(R.string.previous).asString(),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            buttonType = ButtonComponentType.Text,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 23.dp, end = 23.dp, top = 15.dp, bottom = 15.dp),
                ) {
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        state = pagerState,
                    ) { index ->
                        IntroSliderItem(
                            state.introSliderPages[index]
                        )
                    }
                    //                HorizontalPagerIndicator(
                    //                    pagerState = pagerState,
                    //                    modifier = Modifier.padding(16.dp)
                    //                )
                }
            }
        }
    }
}