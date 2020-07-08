package com.developer.nfcproject

sealed class Failure

data class GeneralFailure(val title: String?, val message: String?) : Failure()

data class AudioProcessingFailure( val message: String?) : Failure()

object NoNetworkFailure : Failure()

object NeedPinFailure : Failure()
