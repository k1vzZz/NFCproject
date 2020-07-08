package com.developer.nfcproject

sealed class ServiceResult<T>

data class ServiceSuccessResult<T>(val result: T) : ServiceResult<T>()

data class ServiceFailureResult<T>(val failureResult: Failure) : ServiceResult<T>()
