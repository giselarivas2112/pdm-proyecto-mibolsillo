package com.pdm0126.mibolsillo.view.screens.screenviewfixedpayments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentApiRepository
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.FixedPayment
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
    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    init {
        getFixedPayments()
    }

    fun getFixedPayments(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loading.value = true

            repository.getFixedPayments()
                .onSuccess { _fixedPayments.value = it }
                .onFailure { e -> _error.value = e.message }

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }
}