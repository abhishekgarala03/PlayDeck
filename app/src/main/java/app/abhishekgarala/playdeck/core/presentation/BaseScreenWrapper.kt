@file:OptIn(ExperimentalMaterial3Api::class)

package app.abhishekgarala.playdeck.core.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import app.abhishekgarala.playdeck.core.utils.Constants.Companion.BUNDLE_ARG_KEY
import app.abhishekgarala.playdeck.core.viewModel.BaseViewModel
import app.abhishekgarala.playdeck.ui.components.LoadingDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <State, ScreenEvent> BaseScreenWrapper(
    navController: NavController,
    viewModel: BaseViewModel<State, ScreenEvent>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isShowLoading by remember { mutableStateOf(false) }
    var loadingMessage: String? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Success -> {
                    isShowLoading = false
                }

                is UiEvent.ShowToast -> {
                    isShowLoading = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.ShowSnackBar -> {
                    isShowLoading = false
                    scope.launch {
                        snackBarHostState.showSnackbar(it.message)
                    }
                }

                is UiEvent.ShowLoading -> {
                    isShowLoading = true
                    loadingMessage = it.message
                }

                is UiEvent.NavigateBack -> {
                    isShowLoading = false
                    navController.popBackStack()
                }

                is UiEvent.Navigate -> {
                    isShowLoading = false
                    it.data?.let { data ->
                        navController.navigate(it.route)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            it.key ?: BUNDLE_ARG_KEY,
                            data
                        )
                    } ?: navController.navigate(it.route)
                }

                is UiEvent.NavigateAndRemoveBackStack -> {
                    isShowLoading = false
                    it.data?.let { data ->
                        navController.navigate(it.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId)
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            it.key ?: BUNDLE_ARG_KEY,
                            data
                        )
                    } ?: navController.navigate(it.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            }
        }
    }
    if (isShowLoading) {
        LoadingDialog(message = loadingMessage)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }

}
