package com.pdm0126.mibolsillo.view.screens.viewscreenfixedpayments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentApiRepository
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.model.FixedPayment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.onFailure


class FixedPaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: FixedPaymentRepository = FixedPaymentApiRepository(sessionManager)

    private val _fixedPayments = MutableStateFlow<List<FixedPayment>>(emptyList())
    val fixedPayments = _fixedPayments.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        getFixedPayments()
    }

    fun getFixedPayments() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getFixedPayments()
                .onSuccess { _fixedPayments.value = it }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }
}