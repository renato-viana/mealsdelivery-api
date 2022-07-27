package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@javax.persistence.Table(name = "\"User\"")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var email: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    var registrationDate: OffsetDateTime? = null,

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()

) {

    fun isNew(): Boolean = id == null

    fun removeRole(role: Role): Boolean = roles.remove(role)

    fun addRole(role: Role): Boolean = roles.add(role)

}