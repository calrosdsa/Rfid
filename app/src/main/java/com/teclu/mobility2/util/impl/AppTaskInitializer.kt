package com.teclu.mobility2.util.impl

import android.app.Application
import com.teclu.mobility2.util.interfaces.TaskAppInitializer
import com.teclu.mobility2.util.interfaces.AppTasks
import javax.inject.Inject
import dagger.Lazy

class TaskAppTasksInitializer @Inject constructor(
    private val appTasks: Lazy<AppTasks>
) :TaskAppInitializer {
    override fun init() {
//        appTasks.get().sendMarcaciones()
        appTasks.get().getDataServer()
    }
}