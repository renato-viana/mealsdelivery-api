package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import javax.persistence.*

@Entity
@javax.persistence.Table(name = "\"Role\"")
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "role_permission",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: MutableSet<Permission> = mutableSetOf()

) {

    fun removePermission(permission: Permission): Boolean = permissions.remove(permission)

    fun addPermission(permission: Permission): Boolean = permissions.add(permission)

}