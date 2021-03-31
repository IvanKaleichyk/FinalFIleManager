package project

import org.gradle.api.Plugin
import org.gradle.api.Project
import setup.AppDefaultConfig
import setup.BaseKotlinConvention
import setup.BuildTypes
import setup.CompileOptions

class AndroidAppConvention : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.run {
            apply("com.android.application")
            apply("kotlin-android")
            apply(AppDefaultConfig::class.java)
            apply(BuildTypes::class.java)
            apply(CompileOptions::class.java)
            apply(BaseKotlinConvention::class.java)
        }
    }
}