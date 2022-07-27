package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.ProductImageModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductImageModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.ProductImageModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.ProductImageControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ImageStorageService.RecoveredImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ProductImageService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/restaurants/{restaurantId}/products/{productId}/image"])
class ProductImageController(

    @Autowired
    private val productImageService: ProductImageService,

    @Autowired
    private val productService: ProductService,

    @Autowired
    private val productImageModelResponseAssembler: ProductImageModelResponseAssembler,

    @Autowired
    private val imageStorageService: ImageStorageService

) : ProductImageControllerOpenApi {

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable restaurantId: Long?, @PathVariable productId: Long?): ProductImageModelResponse {
        val productImage: ProductImage = productImageService.fetchOrFail(restaurantId, productId)
        return productImageModelResponseAssembler.toModel(productImage)
    }

    @GetMapping(produces = [MediaType.ALL_VALUE])
    @Throws(HttpMediaTypeNotAcceptableException::class)
    override fun fetchImage(
        @PathVariable restaurantId: Long,
        @PathVariable productId: Long,
        @RequestHeader(name = "accept") acceptHeader: String
    ): ResponseEntity<*> {
        return try {
            val productImage: ProductImage = productImageService.fetchOrFail(restaurantId, productId)

            val mediaTypeImage: MediaType = MediaType.parseMediaType(productImage.contentType!!)
            val mediaTypesAccepted = MediaType.parseMediaTypes(acceptHeader)

            checkMediaTypeCompatibility(mediaTypeImage, mediaTypesAccepted)

            val recoveredImage: RecoveredImage = imageStorageService.recover(productImage.fileName)

            if (recoveredImage.hasUrl()) {
                ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, recoveredImage.url)
                    .build<Any>()
            } else {
                ResponseEntity.ok()
                    .contentType(mediaTypeImage)
                    .body<InputStreamResource>(InputStreamResource(recoveredImage.inputStream!!))
            }
        } catch (e: EntityNotFoundException) {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @Throws(HttpMediaTypeNotAcceptableException::class)
    private fun checkMediaTypeCompatibility(mediaTypeImage: MediaType?, mediaTypesAccepted: List<MediaType>) {
        val compatible = mediaTypesAccepted.stream()
            .anyMatch { mediaTypeAccepted: MediaType -> mediaTypeAccepted.isCompatibleWith(mediaTypeImage) }

        if (!compatible) {
            throw HttpMediaTypeNotAcceptableException(mediaTypesAccepted)
        }
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(IOException::class)
    override fun updateImage(
        @PathVariable restaurantId: Long, @PathVariable productId: Long,
        @Valid productImageModelRequest: ProductImageModelRequest,
        @RequestPart(required = true) file: MultipartFile
    ): ProductImageModelResponse {
        val product: Product = productService.fetchOrFail(restaurantId, productId)

        val image = ProductImage()
        image.product = product
        image.description = productImageModelRequest.description
        image.contentType = file.contentType!!
        image.size = file.size
        image.fileName = file.originalFilename!!

        val savedImage: ProductImage = productImageService.save(image, file.inputStream)

        return productImageModelResponseAssembler.toModel(savedImage)
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable restaurantId: Long, @PathVariable productId: Long) {
        productImageService.delete(restaurantId, productId)
    }

}