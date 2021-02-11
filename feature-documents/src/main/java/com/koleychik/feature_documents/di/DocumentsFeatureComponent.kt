package com.koleychik.feature_documents.di

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.feature_documents.di.modules.ViewModelModule
import com.koleychik.feature_documents.ui.DocumentsFragment
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModel
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [DocumentsFeatureDependencies::class]
)
@PerFeature
interface DocumentsFeatureComponent : DocumentsFeatureApi {

    fun inject(fragment: DocumentsFragment)

    companion object {
        fun initAndGet(dependencies: DocumentsFeatureDependencies): DocumentsFeatureComponent {
            return DaggerDocumentsFeatureComponent.builder()
                .documentsFeatureDependencies(dependencies).build()
        }
    }

}