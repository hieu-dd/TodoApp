package com.d2b.dev.todolist.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@RunWith(JUnit4::class)
class DateTimeTest {
    private lateinit var testTime: Instant

    @Before
    fun setup() {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2022
        cal[Calendar.MONTH] = Calendar.APRIL
        cal[Calendar.DAY_OF_MONTH] = 1
        testTime = Instant.fromEpochMilliseconds(cal.timeInMillis)
    }

    @Test
    fun `test func formatDate return correctly with default pattern`() {
        val formatText = testTime.formatDate()
        assert(formatText == "01/04/2022")
    }

    @Test
    fun `test func formatDate return correctly with pattern yy-MM-dd`() {
        val formatText = testTime.formatDate("yy/MM/dd")
        assert(formatText == "22/04/01")
    }

    @Test
    fun `test func isOutDate return true when testTime in the pass`() {
        assert(testTime.isOutDate())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `test func isOutDate return false when testTime in the future`() {
        val testTime = Clock.System.now().apply {
            plus(duration = Duration.days(1))
        }
        assert(!testTime.isOutDate())
    }

    @Test
    fun `test func getDay return correctly`() {
        assert(testTime.getDay() == 1)
    }

    @Test
    fun `test func getMonth return correctly`() {
        assert(testTime.getMonth() == 4)
    }

    @Test
    fun `test func getYear return correctly`() {
        assert(testTime.getYear() == 2022)
    }
}