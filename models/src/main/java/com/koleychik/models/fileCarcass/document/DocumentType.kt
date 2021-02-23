package com.koleychik.models.fileCarcass.document

import com.koleychik.models.R

sealed class DocumentType(
    open val id: Int,
    open val nameRes: Int,
    val nameFormat: String,
    var isSelected: Boolean
) {
    object TXT : DocumentType(0, R.string.txt, "txt", true)
    object PDF : DocumentType(1, R.string.pdf, "pdf", true)
    object EPUB : DocumentType(2, R.string.epub, "epub", true)
    object B2 : DocumentType(3, R.string.b2, "b2", true)
    object DOC : DocumentType(4, R.string.doc, "doc", true)
    object DOCX : DocumentType(5, R.string.docx, "docx", true)
    object PPT : DocumentType(6, R.string.ppt, "ppt", true)
    object OTHER : DocumentType(7, R.string.other, "", false)
}

val listAllDocumentTypes = listOf(
    DocumentType.TXT,
    DocumentType.PDF,
    DocumentType.EPUB,
    DocumentType.DOC,
    DocumentType.DOCX,
    DocumentType.PPT,
    DocumentType.OTHER
)
