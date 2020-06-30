package com.developer.nfcproject.ui.operations

import androidx.navigation.NavDirections

data class Mode(
    val titleRes: Int,
    val descriptionRes: Int,
    val logoRes: Int,
    val navDirections: NavDirections
)