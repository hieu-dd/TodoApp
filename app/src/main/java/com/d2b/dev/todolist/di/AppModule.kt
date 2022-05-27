package com.d2b.dev.todolist.di

import com.d2b.dev.todolist.service.TaskManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideTaskManager(): TaskManager = TaskManager.Instance
}