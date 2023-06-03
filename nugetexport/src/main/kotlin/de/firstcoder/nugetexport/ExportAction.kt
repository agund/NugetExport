package de.firstcoder.nugetexport

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import de.firstcoder.nugetexport.Models.Dependency
import de.firstcoder.nugetexport.Notifications.CopyNotification
import de.firstcoder.nugetexport.Settings.AppSettingsState
import java.io.File
import java.nio.file.Paths
import java.util.*

class ExportAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        val project = event.getRequiredData(CommonDataKeys.PROJECT)

        println("project-path: "+project.basePath)
        println("project-name: "+project.name)

        val files = project.basePath?.let { File(it).listFiles() }
        val existProjectFile = files?.filter { it.name.contains("csproj") }

        if ( existProjectFile?.count()!! <= 0)
        {
            val existProjectFolder = files.filter { it.name.contains(project.name) }
            if (existProjectFolder.isEmpty())
            {
                val arr = arrayOf("")
                Messages.showDialog("Can't found *.csproj file","Error",arr,1, Messages.getErrorIcon())
            } else {
                // var projectFolderFiles = File(Paths.get(project.basePath!!,project.name).toString()).listFiles()
                println("Project-Folder:"+Paths.get(project.basePath!!,project.name).toString() )
                val listDep = readDependencys(Paths.get(project.basePath!!,project.name,project.name+".csproj").toString())
                if(listDep.any())
                searchNugets(listDep)
            }
        } else {
            val listDep = readDependencys(Paths.get(project.basePath!!,project.name+".csproj").toString())
            if(listDep.any())
            searchNugets(listDep)

        }
    }

    private fun readDependencys(projectFile: String) : List<Dependency>
    {
        var result :List<Dependency> =  emptyList()
        val fileTxt = File(projectFile).readText()
        fileTxt.toRegex().pattern
        val regex = Regex("<PackageReference Include=\"(.*?)\" Version=\"(.*?)\"")
        val founds = regex.findAll(fileTxt)
        if(founds.any())
        {
            founds.forEach {
                val depValues = "\"(.*?)\"".toRegex()
                val depFounds = depValues.findAll(it.value)
                result = result.plus(Dependency(depFounds.first().value,depFounds.last().value))
            }
        }
        return  result
    }

    private fun searchNugets(listDep : List<Dependency>){
        val nugetPath = getNugetPath()
        listDep.forEach {
            val files = File(Paths.get(nugetPath,it.Name.replace("\"",""),it.Version.replace("\"","")).toString()).listFiles()

            var nugetFile = files?.filter { itf -> itf.name.length >= 6}
            nugetFile = nugetFile?.filter { itf -> itf.name.substring(itf.name.length-6,itf.name.length) == ".nupkg" }

            val settings: AppSettingsState = AppSettingsState.instance

            if(!File(Paths.get(settings.exportPath,nugetFile?.first()?.name).toString()).exists()) {
                File(Paths.get(nugetPath, it.Name.replace("\"", ""), it.Version.replace("\"", ""), nugetFile?.first()?.name).toString()).copyTo(File(Paths.get(settings.exportPath, nugetFile?.first()?.name).toString()))
                CopyNotification.notifyInfo(null,"COPY: " + Paths.get(nugetPath, it.Name.replace("\"", ""), it.Version.replace("\"", ""), nugetFile?.first()?.name).toString() + " to: " + Paths.get(settings.exportPath, nugetFile?.first()?.name).toString())
            } else {
                CopyNotification.notifyWarning(null,"File: " +Paths.get(settings.exportPath,nugetFile?.first()?.name).toString()+" is exist")
            }

        }
    }

    private fun getNugetPath(): String {
        val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
        val userHome = System.getProperty("user.home")
        return when {
            osName.contains("win") -> {
                Paths.get(userHome, ".nuget", "packages").toString()
            }
            osName.contains("nix") || osName.contains("nux") || osName.contains("aix") -> {
                Paths.get("/","home",userHome,".nuget", "packages").toString()
            }
            else -> ""
        }
    }


}
