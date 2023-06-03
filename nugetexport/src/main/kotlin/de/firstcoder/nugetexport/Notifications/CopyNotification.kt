package de.firstcoder.nugetexport.Notifications

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.Nullable


class CopyNotification {
    companion object {
        fun notifyInfo(
            @Nullable project: Project?,
            content: String
        ) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("NugetExport Notification")
                .createNotification(content, NotificationType.INFORMATION)
                .notify(project)
        }

        fun notifyWarning(
            @Nullable project: Project?,
            content: String
        ) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("NugetExport Notification")
                .createNotification(content, NotificationType.WARNING)
                .notify(project)
        }

        fun notifyError(
            @Nullable project: Project?,
            content: String
        ) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("NugetExport Notification")
                .createNotification(content, NotificationType.ERROR)
                .notify(project)
        }
    }
}

