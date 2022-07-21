package com.emcpheron

import com.intellij.find.FindModel
import com.intellij.find.FindUtil
import com.intellij.find.replaceInProject.ReplaceInProjectManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.emcpheron.settings.RocketLogSettings


class RocketLogRemove : AnAction("Remove RocketLog's Logs") {
  override fun actionPerformed(e: AnActionEvent) {
    // display the dialog
    val dlg = RocketLogRemoveDlg()
    if (!dlg.showAndGet()) return

    val project = e.getData(CommonDataKeys.PROJECT)!!
    val editor = e.getRequiredData(CommonDataKeys.EDITOR)

    val patternToReplace = ".*" + RocketLogSettings.instance.pattern.run {
      replace("\\", "\\\\")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("^", "\\^")
        .replace("+", "\\+")
        .replace("?", "\\?")
        .replace("|", "\\|")
        .replace(".", "\\.")
        .replace("*", "\\*")
        .replace("$$", ".*")
        .replace("{FN}", ".*")
        .replace("{FP}", ".*")
        .replace("{LN}", "\\d*")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("$", "\\$")
    } + "\n"

    val findModel = FindModel().apply {
      stringToFind = patternToReplace
      stringToReplace = ""
      isPromptOnReplace = false
      isRegularExpressions = true
      isGlobal = true
      isPromptOnReplace = false
    }

    when (dlg.scope) {
      Scope.CURRENT_FILE -> FindUtil.replace(project, editor, 0, findModel)
      Scope.PROJECT -> {
        ReplaceInProjectManager(project).replaceInPath(findModel)
      }
    }
  }
}

