package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*

@Embeddable
class Address(

    @Column(name = "address_zipCode")
    var zipCode: String? = null,

    @Column(name = "address_street")
    var street: String? = null,

    @Column(name = "address_number")
    var number: String? = null,

    @Column(name = "address_complement")
    var complement: String? = null,

    @Column(name = "address_district")
    var district: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    var city: City? = null

)