package com.teclu.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    //    @Test
    private fun startup(mode:CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.teclu.mobility2",
        metrics = listOf(StartupTimingMetric()),
        iterations = 10,
        compilationMode = mode,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
        device.wait(Until.hasObject(By.res("ingreso")),5000)
        device.findObject(By.res("ingreso")).click()
        device.wait(Until.gone(By.res("ingreso")), 5000)
        device.wait(Until.hasObject(By.res("input")),1000)
//        val input =device.findObject(By.res("input"))
//        repeat(30){
////                input.text = ""
//            input.text = "${input.text}01"
////            input.text = "1"
//
//        }
        device.pressBack()
        device.waitForIdle()
    }

    @Test
    fun startupNoCompilation() = startup(CompilationMode.None())

    @Test
    fun startupBaselineProfile() = startup(CompilationMode.Partial())
}