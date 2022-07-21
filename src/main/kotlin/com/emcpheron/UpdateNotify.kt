package com.emcpheron

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.NotificationType
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.emcpheron.settings.RocketLogSettings

class UpdateNotify : StartupActivity {
  private val plugin = PluginManagerCore.getPlugin(PluginId.getId("com.emcpheron"))!!

  override fun runActivity(project: Project) {
    val settings = RocketLogSettings.instance
    if (settings.version == "Unknown") {
      settings.version = plugin.version
    } else if (plugin.version != settings.version) {
      settings.version = plugin.version
      showUpdate(project)
    }
  }

  private val updateContent: String by lazy {
    //language=HTML
    """
    <br/>
    In this version you'll find the ability<br><br>
    👉 to insert the <b>filename</b> and <b>line number</b> into the log<br>
    See <a href='jetbrains://WebStorm/settings?name=Tools--RocketLog'>RocketLog Settings</a><br><br>
    👉 to remove RocketLog's logs from the <b>current file</b> or the <b>project</b><br>
    See RocketLog Remove command in <b>Code | Log It</b>
    """
  }

  private fun showUpdate(project: Project) {
    val notification = createNotification(
      "RocketLog plugin updated to version ${plugin.version}",
      updateContent,
      NotificationType.INFORMATION
    )
    showFullNotification(project, notification)
  }
}
