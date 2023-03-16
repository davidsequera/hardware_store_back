package com.hardware.auth.configuration

import java.util.Properties
import java.io.FileInputStream

object ConfigProperties {
    private val properties = Properties()
    private const val properties_path = "config.properties"
    init {
        val inputStream = FileInputStream(properties_path)
        properties.load(inputStream)
    }

    fun getProperty(key: String): String {
        return properties.getProperty(key)
    }
}
