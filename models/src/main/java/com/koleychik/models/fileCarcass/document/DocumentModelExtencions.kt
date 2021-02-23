package com.koleychik.models.fileCarcass.document

fun getTypeOfDocument(name: String): DocumentType {
    for (type in listAllDocumentTypes) if (name.endsWith(type.nameFormat, true)) return type
    return DocumentType.OTHER
}