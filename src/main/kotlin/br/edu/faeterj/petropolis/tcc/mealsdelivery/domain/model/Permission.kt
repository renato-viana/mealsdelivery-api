package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*

@Entity
class Permission(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String

)