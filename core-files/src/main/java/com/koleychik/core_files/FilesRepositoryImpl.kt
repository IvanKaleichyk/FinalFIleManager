package com.koleychik.core_files

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.FileUriExposedException
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.core_files.extensions.audioProjections
import com.koleychik.core_files.extensions.documentsProjections
import com.koleychik.core_files.extensions.imagesProjections
import com.koleychik.core_files.extensions.videoProjections
import com.koleychik.injector.AppConstants.TAG
import com.koleychik.models.extensions.getSizeAbbreviation
import com.koleychik.models.extensions.toDocumentModel
import com.koleychik.models.extensions.toFolderModel
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.MusicModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.getTypeOfDocument
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel
import com.koleychik.models.type.getFileType
import java.io.File
import javax.inject.Inject


@SuppressLint("Recycle")
internal class FilesRepositoryImpl @Inject constructor(private val context: Context) :
    FilesRepository {

    private val contentUri = "external"

    override fun getImages(): List<ImageModel> {
        val uriExternal: Uri = ImagesStorage.EXTERNAL_CONTENT_URI
        val listRes = mutableListOf<ImageModel>()
        val sorterOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = queryContentResolver(
            uriExternal,
            imagesProjections,
            sortedOrder = sorterOrder
        ) ?: return emptyList()

        var id: Long
        while (cursor.moveToNext()) {
            id = cursor.getLong(0)
            listRes.add(
                ImageModel(
                    id = id,
                    name = cursor.getString(1),
                    uri = Uri.withAppendedPath(uriExternal, id.toString()),
                    sizeAbbreviation = context.getSizeAbbreviation(cursor.getLong(2)),
                    dateAdded = cursor.getLong(3),
                    mimeType = cursor.getString(4)
                )
            )
        }
        cursor.close()
        return listRes
    }

    override fun getDocuments(): List<DocumentModel> {
        val listRes = mutableListOf<DocumentModel>()
        val uriExternal: Uri = MediaStore.Files.getContentUri(contentUri)
        val cursor = queryContentResolver(uriExternal, documentsProjections) ?: return emptyList()

        while (cursor.moveToNext()) {
            val name = cursor.getString(1)
            val mimeType = cursor.getString(4) ?: ""
            listRes.add(
                DocumentModel(
                    name = name ?: "",
                    uri = Uri.withAppendedPath(uriExternal, cursor.getString(0)),
                    sizeAbbreviation = context.getSizeAbbreviation(cursor.getLong(2)),
                    dateAdded = cursor.getLong(3),
                    format = getTypeOfDocument(name ?: ""),
                    type = getFileType(mimeType),
                    mimeType = mimeType
                )
            )
        }
        cursor.close()
        return listRes
    }

    override fun getMusic(): List<MusicModel> {
        val uriExternal = AudioStorage.EXTERNAL_CONTENT_URI
        val listRes = mutableListOf<MusicModel>()
        val cursor = context.contentResolver.query(
            uriExternal,
            audioProjections,
            null,
            null,
            null
        ) ?: return emptyList()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            listRes.add(
                MusicModel(
                    id = id,
                    name = cursor.getString(1),
                    artist = cursor.getString(2),
                    title = cursor.getString(3),
                    album = cursor.getString(4),
                    duration = cursor.getLong(5),
                    uri = Uri.withAppendedPath(uriExternal, id.toString()),
                    sizeAbbreviation = context.getSizeAbbreviation(cursor.getLong(6)),
                    dateAdded = cursor.getLong(7),
                    mimeType = cursor.getString(8)
                )
            )
        }

        return listRes
    }

    override fun getVideo(): List<VideoModel> {
        val uriExternal = VideoStorage.EXTERNAL_CONTENT_URI
        val listRes = mutableListOf<VideoModel>()

        val cursor = queryContentResolver(uriExternal, videoProjections) ?: return emptyList()

        var id: Long

        while (cursor.moveToNext()) {
            id = cursor.getLong(0)
            listRes.add(
                VideoModel(
                    id = id,
                    name = cursor.getString(1),
                    uri = Uri.withAppendedPath(uriExternal, id.toString()),
                    duration = cursor.getLong(2),
                    sizeAbbreviation = context.getSizeAbbreviation(cursor.getLong(3)),
                    dateAdded = cursor.getLong(4),
                    mimeType = cursor.getString(5)
                )
            )
        }
        cursor.close()
        return listRes
    }

    override fun getFoldersAndFiles(path: String): List<FileCarcass> {
        val file = DocumentFile.fromFile(File(path))
//        val list = file.listFiles() ?: return emptyList()
        val list = file.listFiles()
        val listRes = mutableListOf<FileCarcass>()
        for (i in list) {
            if (i.isDirectory) listRes.add(i.toFolderModel())
            else {
                Log.d(TAG, "file.type = ${i.type}")
                listRes.add(i.toDocumentModel(context))
            }
        }
        return listRes
    }

    override fun openFile(model: FileCarcass) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(model.uri, model.mimeType)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                context.startActivity(intent)
            } catch (e: FileUriExposedException) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            }
        } else context.startActivity(intent)
    }

    override fun delete(model: FileCarcass) {
        val file = DocumentFile.fromSingleUri(context, model.uri)
        if (file == null) {
            Log.d(TAG, "file = null")
            return
        }
        if (file.exists()) {
            if (file.delete()) Log.d(TAG, "file was deleted")
            else Log.d(TAG, "cannot delete file")
        } else Log.d(TAG, "file doesn't exists")
    }

    private fun queryContentResolver(
        uri: Uri,
        projections: Array<out String>,
        selection: String? = null,
        selectionArgs: Array<out String>? = null,
        sortedOrder: String? = null
    ) = context.contentResolver.query(
        uri,
        projections,
        selection,
        selectionArgs,
        sortedOrder
    )
}