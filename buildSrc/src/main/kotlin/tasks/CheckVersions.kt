package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CheckVersions: DefaultTask() {

    @TaskAction
    fun checkVersion(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}