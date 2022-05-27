package com.d2b.dev.todolist

import com.d2b.dev.todolist.service.TaskManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before


abstract class BaseTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    protected open var taskManager = TaskManager.Instance

    @Before
    open fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        runBlocking {
            taskManager.clearTasks()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}