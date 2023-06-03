package de.firstcoder.nugetexport.Settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

// import com.intellij.openapi.application.ApplicationManager
// import com.intellij.openapi.components.PersistentStateComponent
// import com.intellij.openapi.components.State
// import com.intellij.util.xmlb.XmlSerializerUtil
// import org.jetbrains.annotations.NotNull
// import org.jetbrains.annotations.Nullable


@State(name = "de.firstcoder.nugetexport.Settings.AppSettingsState", storages = arrayOf(Storage("FirstcoderNugetExportPlugin.xml")))
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var exportPath ="C:\\Windows\\Temp"

    @Nullable
    override fun getState(): AppSettingsState {
        return this
    }

    override fun loadState(@NotNull state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}