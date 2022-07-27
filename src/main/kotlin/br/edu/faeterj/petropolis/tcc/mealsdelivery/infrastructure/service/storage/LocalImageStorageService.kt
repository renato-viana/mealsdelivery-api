package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.storage

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.storage.StorageProperties
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService.NewImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService.RecoveredImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.FileCopyUtils
import java.nio.file.Files
import java.nio.file.Path

open class LocalImageStorageService : ImageStorageService {

    @Autowired
    lateinit var storageProperties: StorageProperties

    override fun recover(fileName: String?): RecoveredImage {
        return try {
            val filePath = getFilePath(fileName)

            RecoveredImage(Files.newInputStream(filePath))

        } catch (e: Exception) {
            throw StorageException("Não foi possível recuperar o arquivo.", e)
        }
    }

    override fun store(newImage: NewImage) {
        try {
            val filePath = getFilePath(newImage.fileName)

            FileCopyUtils.copy(newImage.inputStream, Files.newOutputStream(filePath))

        } catch (e: Exception) {
            throw StorageException("Não foi possível armazenar o arquivo.", e)
        }
    }

    override fun remove(fileName: String?) {
        try {
            val filePath = getFilePath(fileName)

            Files.deleteIfExists(filePath)

        } catch (e: Exception) {
            throw StorageException("Não foi possível excluir o arquivo.", e)
        }
    }

    private fun getFilePath(fileName: String?): Path {
        return storageProperties.local.imageDirectory.resolve(Path.of(fileName.toString()))
    }

}