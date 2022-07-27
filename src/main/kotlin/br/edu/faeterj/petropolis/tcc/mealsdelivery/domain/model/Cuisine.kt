package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*

@Entity
class Cuisine(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @Column(nullable = false)
    var name: String = "",

    @OneToMany(mappedBy = "cuisine")
    var restaurants: MutableList<Restaurant> = mutableListOf()

)