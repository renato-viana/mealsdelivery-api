package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.FileContentType
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.FileSize
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@NoArg
data class ProductImageModelRequest(

    @ApiModelProperty(hidden = true)
    @field:NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    val file: MultipartFile? = null,

    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    @field:NotBlank
    val description: String? = null

)