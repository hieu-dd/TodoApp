package com.d2b.dev.todolist.ui.screen.add

import com.d2b.dev.todolist.BaseTest
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@RunWith(JUnit4::class)
class AddTaskViewModelTest : BaseTest() {
    private val viewModel = AddTaskViewModel(taskManager)

    @Test
    fun `test send Error when add Task with empty name`() {
        runTest {
            viewModel.addTask("", "Note", Clock.System.now(), {})
            viewModel.error.first().let {
                assert(it?.message == "Name can't empty")
            }
        }
    }

    @Test
    fun `test send Error when add Task with empty note`() {
        runTest {
            viewModel.addTask("111", "", Clock.System.now(), {})
            viewModel.error.first().let {
                assert(it?.message == "Note can't empty")
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `test send Error when add Task with due in the pass`() {
        runTest {
            viewModel.addTask("111", "note", Clock.System.now().minus(duration = Duration.days(1)), {})
            viewModel.error.first().let {
                assert(it?.message == "Due can't be in pass")
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `test call success when add Task success`() {
        runTest {
            val onSuccess = {}
            viewModel.addTask("111", "note", Clock.System.now().minus(duration = Duration.days(1)), onSuccess)
            verify { onSuccess.invoke() }
        }
    }
}