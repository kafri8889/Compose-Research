package com.anafthdev.composeresearch.research.navigate_with_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.composeresearch.research.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Screen1ViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
): BaseViewModel<Screen1State>(savedStateHandle, Screen1State()) {

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow("text", "").collectLatest {
                updateState {
                    copy(
                        text = it
                    )
                }
            }
        }
    }

}