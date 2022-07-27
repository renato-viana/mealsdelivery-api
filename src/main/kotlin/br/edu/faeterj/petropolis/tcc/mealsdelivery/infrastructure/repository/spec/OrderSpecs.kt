package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository.spec

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.OrderFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

object OrderSpecs {

    fun usingFilter(filter: OrderFilter?): Specification<Order> {
        return Specification { root: Root<Order>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->

            if (Order::class.java == query.resultType) {
                root.fetch<Any, Any>("restaurant").fetch<Any, Any>("cuisine")
                root.fetch<Any, Any>("customer")
            }

            val predicates = ArrayList<Predicate>()

            if (filter?.customerId != null) {
                predicates.add(builder.equal(root.get<Order>("customer"), filter.customerId))
            }

            if (filter?.restaurantId != null) {
                predicates.add(builder.equal(root.get<Order>("restaurant"), filter.restaurantId))
            }

            if (filter?.creationDateStart != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.creationDateStart))
            }
            if (filter?.creationDateEnd != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.creationDateEnd))
            }

            builder.and(*predicates.toTypedArray())
        }
    }

}