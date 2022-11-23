package com.teclu.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generateBaselineProfile() = rule.collectBaselineProfile("com.teclu.mobility2") {
        startActivityAndWait()
        device.wait(Until.hasObject(By.res("ingreso")),5000)
        device.findObject(By.res("ingreso")).click()
        device.wait(Until.gone(By.res("ingreso")), 5_000)
        device.wait(Until.hasObject(By.res("input")),1000)
//        val input =device.findObject(By.res("input"))
//        repeat(30){
////                input.text = ""
//            input.text = "${input.text}01"
//        }
////        device.waitForIdle(3000)
        device.pressBack()
        device.waitForIdle()
    }

//    @Test
//    fun generate():Unit = rule.collectBaselineProfile(
//        "com.teclu.mobility2"
//    ){
//        startActivityAndWait()
//        device.wait(Until.hasObject(By.res("ingreso")),5000)
//        device.findObject(By.res("ingreso")).click()
//        device.wait(Until.gone(By.res("ingreso")), 5_000)
//        device.wait(Until.hasObject(By.res("input")),1000)
//        val input =device.findObject(By.res("input"))
//        repeat(30){
////                input.text = ""
//            input.text = "${input.text}01"
//        }
//        device.waitForIdle(3000)
////        device.wait(Until.hasObject(By.res("logo")))
////        device.wait(Until.hasObject(By.text(" Ingresar Código")),30_000)
//    }
}