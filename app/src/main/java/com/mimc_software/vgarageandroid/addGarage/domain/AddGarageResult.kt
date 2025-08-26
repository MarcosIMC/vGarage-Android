package com.mimc_software.vgarageandroid.addGarage.domain

sealed class AddGarageResult {
    data class Success(val garageId: Long) : AddGarageResult()
    object Duplicate : AddGarageResult()
    data class Error(val exception: Throwable) : AddGarageResult()
}