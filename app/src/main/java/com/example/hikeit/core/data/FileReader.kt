package com.example.hikeit.core.data

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FileReader(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun uriToFileInfo(contentUri: Uri) : FileInfo {
        return withContext(ioDispatcher) {
            val bytes = context
                .contentResolver
                .openInputStream(contentUri)
                ?.use { stream ->
                    stream.readBytes()
                } ?: byteArrayOf()

            val fileName = UUID.randomUUID().toString()
            val mimeType = context.contentResolver.getType(contentUri) ?: ""

            FileInfo(
                name = fileName,
                mimeType = mimeType,
                bytes = bytes
            )
        }
    }
}

class FileInfo(
    name: String,
    mimeType: String,
    bytes: ByteArray
)