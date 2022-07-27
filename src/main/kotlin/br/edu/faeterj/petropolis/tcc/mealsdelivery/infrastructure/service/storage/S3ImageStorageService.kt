package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.storage

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.storage.StorageProperties
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService.NewImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService.RecoveredImage
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import java.net.URL

open class S3ImageStorageService : ImageStorageService {

    @Autowired
    lateinit var amazonS3: AmazonS3

    @Autowired
    lateinit var storageProperties: StorageProperties

    override fun recover(fileName: String?): RecoveredImage {
        val filePath = getFilePath(fileName)
        val url: URL? = amazonS3.getUrl(storageProperties.s3.bucket, filePath)
        return RecoveredImage(url = url.toString())
    }

    override fun store(newImage: NewImage) {
        try {
            val filePath = getFilePath(newImage.fileName)

            val objectMetadata = ObjectMetadata()
            objectMetadata.contentType = newImage.contentType

            val putObjectRequest: PutObjectRequest? = PutObjectRequest(
                storageProperties.s3.bucket,
                filePath,
                newImage.inputStream,
                objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead)

            amazonS3.putObject(putObjectRequest)
        } catch (e: Exception) {
            throw StorageException("Não foi possível enviar arquivo para Amazon S3", e)
        }
    }

    private fun getFilePath(fileName: String?): String {
        return "${storageProperties.s3.imageDirectory}/$fileName"
    }

    override fun remove(fileName: String?) {
        try {
            val filePath = getFilePath(fileName)

            val deleteObjectRequest = DeleteObjectRequest(
                storageProperties.s3.bucket, filePath
            )

            amazonS3.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            throw StorageException("Não foi possível excluir arquivo na Amazon S3.", e)
        }
    }

}