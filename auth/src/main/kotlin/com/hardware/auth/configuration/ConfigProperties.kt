package com.hardware.auth.configuration

import java.util.Properties
import java.io.FileInputStream

/**
 * Singleton object for loading configuration properties from a file.
 */
object ConfigProperties {
    private val properties = Properties()
    private const val properties_path = "config.properties"

    /**
     * Initializes the [properties] object with the properties file at [properties_path].
     */
    init {
        val inputStream = FileInputStream(properties_path)
        properties.load(inputStream)
    }

    /**
     * Returns the value of the property with the given [key].
     * @param key the key of the property to retrieve as a [String].
     * @return the value of the property as a [String].
     */
    fun getProperty(key: String): String {
        return properties.getProperty(key)
    }
}
