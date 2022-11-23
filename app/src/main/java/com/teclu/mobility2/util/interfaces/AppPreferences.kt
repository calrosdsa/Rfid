package com.teclu.mobility2.util.interfaces

import kotlinx.coroutines.flow.StateFlow

interface AppPreferences {
    val passwordStream:StateFlow<String>
    var password:String
    val urlServidorStream:StateFlow<String>
    var urlServidor:String
    val initialScreenStream:StateFlow<String>
    var initialScreen:String
    val accessPinStream:StateFlow<String>
    var accessPin:String
    val tableNameStream:StateFlow<String>
    var tableName : String
    val marcacionManualStream:StateFlow<String>
    var marcacionManual:String
}