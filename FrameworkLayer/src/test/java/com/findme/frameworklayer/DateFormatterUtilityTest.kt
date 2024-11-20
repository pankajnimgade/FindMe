package com.findme.frameworklayer

import com.findme.frameworklayer.utility.changeDateFrom
import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatterUtilityTest {

    @Test
    fun addition_isCorrect() {
        val currentDate = "2024-11-19T18:46:26-08:00"
        val changeDateFrom = currentDate.changeDateFrom()
        println("changeDateFrom : $changeDateFrom")
    }
}