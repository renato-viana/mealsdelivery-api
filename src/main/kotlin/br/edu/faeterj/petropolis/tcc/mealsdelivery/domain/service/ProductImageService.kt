package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.ProductImageNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.InputStream

@Service
class ProductImageService(

    @Autowired
    private val productRepository: ProductRepository,

    @Autowired
    private val imageStorageService: ImageStorageService

) {

    @Transactional
    fun save(image: ProductImage, data: InputStream): ProductImage {
        val restaurantId: Long? = image.getRestaurantId()
        val productId: Long? = image.product?.id
        val newFileName = imageStorageService.generateFileName(image.fileName)
        var existingFileName: String? = null
        val existingImage: ProductImage? = productRepository.findImageById(restaurantId, productId)

        if (existingImage != null) {
            existingFileName = existingImage.fileName
            productRepository.delete(existingImage)
        }

        image.fileName = newFileName!!
        val currentImage = productRepository.save(image)!!
        productRepository.flush()

        val newImage: ImageStorageService.NewImage = ImageStorageService.NewImage(
            currentImage.fileName, currentImage.contentType, data
        )

        imageStorageService.replace(existingFileName, newImage)

        return currentImage
    }

    @Transactional
    fun delete(restaurantId: Long, productId: Long) {
        val image: ProductImage = fetchOrFail(restaurantId, productId)
        productRepository.delete(image)
        productRepository.flush()
        imageStorageService.remove(image.fileName)
    }

    fun fetchOrFail(restaurantId: Long?, productId: Long?): ProductImage = productRepository
        .findImageById(restaurantId, productId) ?: throw ProductImageNotFoundException(restaurantId, productId)

}