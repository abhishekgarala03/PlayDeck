package app.abhishekgarala.playdeck.core.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.abhishekgarala.playdeck.core.presentation.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

abstract class BaseViewModel<State, ScreenEvent> : ViewModel() {
    protected abstract fun defaultState(): State

    private val _state: MutableStateFlow<State> = MutableStateFlow(this.defaultState())
    val state = _state.asStateFlow()

    private val _uiEventChannel = Channel<UiEvent>()
    val uiEvent = _uiEventChannel.receiveAsFlow()

    abstract fun onEvent(event: ScreenEvent)

    protected fun sendEvent(event: UiEvent) = async {
        _uiEventChannel.send(event)
    }

    protected inline fun async(crossinline block: suspend () -> Unit) = with(viewModelScope) {
        launch { block() }
    }

    protected inline fun  asyncWithState(crossinline block: suspend State.() -> Unit) =
        async { block(state.value) }

    protected inline fun <reified T> asyncWithStateThenReturn(crossinline block: suspend State.() -> T) =
        runBlocking { suspendReturn(block) }

    protected suspend inline fun <reified T> suspendReturn(
        crossinline block: suspend State.() -> T
    ): T = viewModelScope.async { block(state.value) }.await()


    protected suspend inline fun <T> await(
        crossinline block: suspend () -> T
    ): T = withContext(Dispatchers.Default) { block() }

    protected inline fun asyncFlow(crossinline block: suspend () -> Unit) = async {
        withContext(Dispatchers.Default) { block() }
    }

    fun runOnMainThread(cb: suspend CoroutineScope.() -> Unit) = async {
        withContext(Dispatchers.Main, block = cb)
    }

    private fun commit(state: State) { _state.tryEmit(state) }

    fun commit(state: State.() -> State) { _state.tryEmit(state(_state.value)) }

    fun resetState() { commit(this.defaultState()) }

}