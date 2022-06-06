package org.verse.ssbc.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File

class Config(
  showMilliseconds: Boolean = true,
  includeDlc1: Boolean = true,
  includeDlc2: Boolean = true
) {

  companion object {

    private val configFileLocation = "${System.getProperty("user.home")}/ssbc/config.yml"
    private var config: Config? = null

    private val objectMapper: ObjectMapper = YAMLMapper()
      .registerModule(
        KotlinModule.Builder()
          .withReflectionCacheSize(512)
          .configure(KotlinFeature.NullToEmptyCollection, false)
          .configure(KotlinFeature.NullToEmptyMap, false)
          .configure(KotlinFeature.NullIsSameAsDefault, false)
          .configure(KotlinFeature.SingletonSupport, false)
          .configure(KotlinFeature.StrictNullChecks, false)
          .build()
      )

    fun load(): Config {
      config?.let {
        return it
      }

      val configFile = File(configFileLocation)

      if (!configFile.exists()) {
        config = Config()
        config!!.save()
      } else {
        config = objectMapper.readValue(configFile, Config::class.java)
      }

      return config!!
    }

  }

  var showMilliseconds: Boolean by mutableStateOf(showMilliseconds)

  var includeDlc1: Boolean by mutableStateOf(includeDlc1)

  var includeDlc2: Boolean by mutableStateOf(includeDlc2)

  fun save() {
    objectMapper.writeValue(File(configFileLocation), config)
  }

}