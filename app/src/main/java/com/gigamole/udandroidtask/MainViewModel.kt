package com.gigamole.udandroidtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _onPasscodeConfirmed = MutableSharedFlow<Unit>()
    private val _onPasscodeRejected = MutableSharedFlow<Unit>()

    private val _activeStep = MutableStateFlow(Step.Create)
    private val _filledDots = MutableStateFlow(0)

    private var createPasscode: StringBuilder = StringBuilder()
    private var confirmPasscode: StringBuilder = StringBuilder()

    val onPasscodeConfirmed = _onPasscodeConfirmed.asSharedFlow()
    val onPasscodeRejected = _onPasscodeRejected.asSharedFlow()

    val activeStep = _activeStep.asStateFlow()
    val filledDots = _filledDots.asStateFlow()

    init {
        resetData()
    }

    private fun emitActiveStep(activeStep: Step) = viewModelScope.launch {
        _activeStep.emit(activeStep)
    }

    private fun emitFilledDots(filledDots: Int) = viewModelScope.launch {
        _filledDots.emit(filledDots)
    }

    private fun emitOnPasscodeConfirmed() = viewModelScope.launch {
        _onPasscodeConfirmed.emit(Unit)
    }

    private fun emitOnPasscodeRejected() = viewModelScope.launch {
        _onPasscodeRejected.emit(Unit)
    }

    private fun resetData() {
        emitActiveStep(Step.Create)
        emitFilledDots(0)

        createPasscode.clear()
        confirmPasscode.clear()
    }

    fun enterKey(key: String) {
        if (_filledDots.value >= PASSCODE_LENGTH) {
            return
        }

        emitFilledDots(
            if (_activeStep.value == Step.Create) {
                createPasscode.append(key)

                createPasscode.length
            } else {
                confirmPasscode.append(key)

                confirmPasscode.length
            }
        )

        if (_filledDots.value == PASSCODE_LENGTH) {
            if (_activeStep.value == Step.Create) {
                emitActiveStep(Step.Confirm)
                emitFilledDots(0)
            } else {
                if (createPasscode.toString() == confirmPasscode.toString()) {
                    resetData()

                    emitOnPasscodeConfirmed()
                } else {
                    emitOnPasscodeRejected()
                    // on wrong shake and show alert and then reset state
                }
            }
        }
    }

    fun deleteKey() {
        _filledDots.tryEmit(
            if (_activeStep.value == Step.Create) {
                if (createPasscode.isNotEmpty()) {
                    createPasscode.deleteAt(createPasscode.length - 1)
                }

                createPasscode.length
            } else {
                if (confirmPasscode.isNotEmpty()) {
                    confirmPasscode.deleteAt(confirmPasscode.length - 1)
                }

                confirmPasscode.length
            }
        )
    }

    fun deleteAllKeys() {
        if (_activeStep.value == Step.Create) {
            createPasscode.clear()
        } else {
            confirmPasscode.clear()
        }

        emitFilledDots(0)
    }

    fun restart() = resetData()

    enum class Step(var index: Int) {
        Create(0),
        Confirm(1)
    }

    companion object {

        const val STEPS_COUNT = 2
        const val PASSCODE_LENGTH = 6
    }
}
