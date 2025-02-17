package top.easyware.intro_slider

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.easyware.core.screens.AppScreens
import top.easyware.core.util.UiText
import top.easyware.intro_slider.component.IntroSliderItem
import top.easyware.uikit.ButtonComponent
import top.easyware.uikit.ButtonComponentType

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IntroSliderScreen(
    navController: NavController,
    viewModel: IntroSliderViewModel = hiltViewModel(),
) {
    val state by viewModel.state
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = {
            state.introSliderPages.size
        })
    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                IntroSliderEvent.PermissionDenied -> {

                }
            }
        }
    }

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
                    if (pagerState.currentPage == 0) {
                        ButtonComponent(
                            title = UiText.StringResource(R.string.continue_label).asString(),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            buttonType = ButtonComponentType.Filled,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else if (pagerState.currentPage == 1) {
                        if (!notificationPermissionState.status.isGranted) {
                            ButtonComponent(
                                title = UiText.StringResource(R.string.permission_request)
                                    .asString(),
                                onClick = {
                                    notificationPermissionState.launchPermissionRequest()
                                },
                                buttonType = ButtonComponentType.Filled,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            ButtonComponent(
                                title = UiText.StringResource(R.string.enter_app)
                                    .asString(),
                                onClick = {
                                    viewModel.onIntent(IntroSliderIntent.EnterApp)
                                    navController.navigate(AppScreens.HomeScreen.route) {
                                        popUpTo(AppScreens.IntroSliderScreen.route) {inclusive = true}
                                    }
                                },
                                buttonType = ButtonComponentType.Filled,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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