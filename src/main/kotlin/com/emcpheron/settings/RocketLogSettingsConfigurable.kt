package com.emcpheron.settings

import com.intellij.openapi.options.ConfigurableBase

class RocketLogSettingsConfigurable : ConfigurableBase<RocketLogConfigurableUI, RocketLogSettings>("org.lso.logit", "RocketLog", "") {

  override fun getSettings(): RocketLogSettings {
    return RocketLogSettings.instance
  }

  override fun createUi(): RocketLogConfigurableUI {
    return RocketLogConfigurableUI(settings)
  }
}
