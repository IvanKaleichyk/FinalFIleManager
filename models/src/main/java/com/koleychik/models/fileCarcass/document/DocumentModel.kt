package com.koleychik.models.fileCarcass.document

import android.net.Uri
import com.koleychik.models.R
import com.koleychik.models.fileCarcass.FileCarcass


data class DocumentModel(
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long,
    val format: DocumentType,
//    private val formatSerializer: Serializable,
    val imgRes: Int = R.drawable.documents_icon_48_black
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded) {
    //    fun getDocumentType() = formatSerializer as DocumentType
    fun getDocumentType() = DocumentType.DOC
}