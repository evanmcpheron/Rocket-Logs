package com.emcpheron.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

internal const val DEFAULT_LOGIT_PATTERN = """console.log("🚀 ~ file: {FN} ~ line {LN} ~ $$: ", $$);"""

@State(name = "RocketLogSettings", storages = [(Storage("log_it.xml"))])
class RocketLogSettings : PersistentStateComponent<RocketLogSettings> {

  var pattern: String = DEFAULT_LOGIT_PATTERN
  var version = "Unknown"

  companion object {
    val instance: RocketLogSettings
      get() = ApplicationManager.getApplication().getService(RocketLogSettings::class.java)
  }

  @Nullable
  override fun getState(): RocketLogSettings = this

  override fun loadState(state: RocketLogSettings) {
    XmlSerializerUtil.copyBean(state, this)
  }
}
