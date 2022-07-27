package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*

@Entity
class ProductImage(

    @Id
    @Column(name = "product_id")
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    var product: Product? = null,

    @Column(name = "file_name")
    var fileName: String? = null,

    var description: String? = null,

    var contentType: String? = null,

    var size: Long? = null

) {

    fun getRestaurantId(): Long? = product?.restaurant?.id

}