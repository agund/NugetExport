package de.firstcoder.nugetexport.Settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import org.jetbrains.annotations.NotNull
import javax.swing.JComponent
import javax.swing.JPanel


class AppSettingsComponent {
    val panel: JPanel
    private val extensionExportPath = JBTextField()

    //private val myIdeaUserStatus = JBCheckBox("Do you use IntelliJ IDEA? ")

    init {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Enter export path: "), extensionExportPath, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = extensionExportPath

    @get:NotNull
    var exportPath: String?
        get() = extensionExportPath.text
        set(newText) {
            extensionExportPath.text = newText
        }



}