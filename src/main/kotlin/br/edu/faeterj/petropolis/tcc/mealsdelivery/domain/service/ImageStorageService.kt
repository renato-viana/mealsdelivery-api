package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import java.io.InputStream
import java.util.*

interface ImageStorageService {

    fun recover(fileName: String?): RecoveredImage

    fun store(newImage: NewImage)

    fun remove(fileName: String?)

    fun replace(oldFileName: String?, newImage: NewImage) {
        store(newImage)

        if (oldFileName != null) {
            remove(oldFileName)
        }
    }

    fun generateFileName(fileName: String?): String? {
        return UUID.randomUUID().toString() + "_" + fileName
    }


    data class NewImage(

        var fileName: String?,

        var contentType: String?,

        var inputStream: InputStream

    )

    data class RecoveredImage(

        var inputStream: InputStream? = null,

        var url: String? = null,

        ) {

        fun hasUrl(): Boolean = url != null

        fun hasInputStream(): Boolean = inputStream != null

    }

}