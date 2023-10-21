package com.anafthdev.composeresearch.research.navigate_with_result

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.composeresearch.research.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Screen2ViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<Screen2State>(savedStateHandle, Screen2State()) {

}