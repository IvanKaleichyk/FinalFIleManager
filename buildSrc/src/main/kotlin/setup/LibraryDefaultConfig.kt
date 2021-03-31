package setup

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryDefaultConfig : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(BaseExtension::class.java) { extension ->
            extension.run {
                compileSdkVersion(30)
                buildToolsVersion("30.0.3")
                defaultConfig { config ->
                    config.run {
                        minSdkVersion(21)
                        targetSdkVersion(30)
                        versionCode(1)
                        versionName("1.0")

                        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
                        consumerProguardFiles("consumer-rules.pro")
                    }
                }
            }
        }
    }
}