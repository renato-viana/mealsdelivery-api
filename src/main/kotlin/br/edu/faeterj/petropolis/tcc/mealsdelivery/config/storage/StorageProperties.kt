package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.storage

import com.amazonaws.regions.Regions
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
@ConfigurationProperties("mealsdelivery.storage")
class StorageProperties {

    var local: Local = Local()
    var s3 = S3()
    var type = StorageType.LOCAL

    enum class StorageType {
        LOCAL, S3
    }

    class Local {
        lateinit var imageDirectory: Path
    }

    data class S3(
        var idAccessKey: String? = null,
        var secretAccessKey: String? = null,
        var bucket: String? = null,
        var region: Regions? = null,
        var imageDirectory: String? = null,
    )

}