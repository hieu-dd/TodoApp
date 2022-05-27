package com.d2b.dev.todolist.service

import com.d2b.dev.todolist.BaseTest
import com.d2b.dev.todolist.data.model.Task
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TaskManagerTest : BaseTest() {
    private lateinit var task: Task

    @Before
    override fun setup() {
        super.setup()
        task = Task(id = "1", name = "Hello", "Note", dueDate = Clock.System.now())
    }

    @Test
    fun `test TaskManager tasks is empty when init`() {
        assert(taskManager.tasks.value.isEmpty())
    }

    @Test
    fun `test TaskManager add Task correctly`() {
        with(taskManager) {
            assert(tasks.value.isEmpty())
            runBlocking {
                addNewTask(task)
                assert(tasks.value.size == 1)
                assert(tasks.value[0] == task)
            }
        }
    }

    @Test
    fun `test TaskManager update status Task correctly`() {
        with(taskManager) {
            assert(tasks.value.isEmpty())
            runBlocking {
                addNewTask(task)
                addNewTask(task.copy(id = "2"))
                assert(tasks.value.size == 2)
                assert(tasks.value[0].status == Task.TaskStatus.Incomplete)
                updateStatusTask("1", true)
                assert(tasks.value[0].status == Task.TaskStatus.Complete)
                assert(tasks.value[1].status == Task.TaskStatus.Incomplete)
            }
        }
    }
}