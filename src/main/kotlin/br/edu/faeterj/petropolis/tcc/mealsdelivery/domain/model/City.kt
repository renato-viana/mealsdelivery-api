package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Entity
class City(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotBlank
    @Column(nullable = false)
    var name: String? = null,

    @Valid
    @ManyToOne
    @JoinColumn(nullable = false)
    var state: State? = null

)