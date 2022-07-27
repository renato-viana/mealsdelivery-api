package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.storage

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.storage.LocalImageStorageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.storage.S3ImageStorageService
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StorageConfig(

    @Autowired
    private val storageProperties: StorageProperties

) {

    @Bean
    @ConditionalOnProperty(name = ["mealsdelivery.storage.type"], havingValue = "s3")
    fun amazonS3(): AmazonS3 {
        val credentials = BasicAWSCredentials(
            storageProperties.s3.idAccessKey,
            storageProperties.s3.secretAccessKey
        )
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(storageProperties.s3.region)
            .build()
    }

    @Bean
    fun imageStorageService(): ImageStorageService {
        return if (StorageProperties.StorageType.S3 == storageProperties.type) {
            S3ImageStorageService()
        } else {
            LocalImageStorageService()
        }
    }

}