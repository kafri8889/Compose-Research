package com.anafthdev.composeresearch.research.base

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 *  kelas dasar (base class) untuk view model.
 *  Ini berarti kelas ini memberikan kerangka dasar untuk view model
 *  yang akan diturunkan (derived) oleh kelas-kelas lain.
 *
 *  @param savedStateHandle savedStateHandle yang digunakan untuk menyimpan state
 *  @param defaultState default state
 *
 *  @author kafri8889
 */
abstract class BaseViewModel<STATE: Parcelable>(
    private val savedStateHandle: SavedStateHandle,
    private val defaultState: STATE
): ViewModel() {

    // Key yang digunakan untuk menyimpan dan mengambil state di savedStateHandle
    private val KEY_STATE = "state"

    val state: StateFlow<STATE> = savedStateHandle.getStateFlow(KEY_STATE, defaultState)

    /**
     * Function yang digunakan untuk memperbarui state dari [savedStateHandle]
     *
     * @param newState parameter ini akan memberikan state saat ini sebagai `this`.
     */
    protected fun updateState(newState: STATE.() -> STATE) {
        // get current state
        savedStateHandle.get<STATE>(KEY_STATE)?.let { state ->
            // simpan state baru ke savedStateHandle
            savedStateHandle[KEY_STATE] = newState(state)
        }
    }

}