package com.healyks.app.di

import com.healyks.app.data.use_cases.UpdateUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ReminderReceiverEntryPoint {
    fun updateUseCase(): UpdateUseCase
}