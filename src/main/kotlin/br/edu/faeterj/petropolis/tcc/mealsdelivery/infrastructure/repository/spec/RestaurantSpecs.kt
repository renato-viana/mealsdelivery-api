package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository.spec

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

object RestaurantSpecs {

    fun withFreeShipping(): Specification<Restaurant> {
        return Specification<Restaurant> { root: Root<Restaurant>, _: CriteriaQuery<*>, builder: CriteriaBuilder ->
            builder.equal(
                root.get<Restaurant>("deliveryFee"),
                BigDecimal.ZERO
            )
        }
    }

    fun withSimilarName(name: String): Specification<Restaurant> {
        return Specification<Restaurant> { root: Root<Restaurant>, _: CriteriaQuery<*>, builder: CriteriaBuilder ->
            builder.like(
                root.get<String>("name"),
                "%$name%"
            )
        }
    }

}