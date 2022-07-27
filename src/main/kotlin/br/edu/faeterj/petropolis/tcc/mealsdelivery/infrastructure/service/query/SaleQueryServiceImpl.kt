package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.query

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.OrderStatus
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response.DailySaleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SaleQueryService
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*

@Repository
class SaleQueryServiceImpl(

    @PersistenceContext
    private val manager: EntityManager

) : SaleQueryService {

    override fun consultDailySales(filter: DailySaleFilter?, timeOffset: String?): List<DailySaleModelResponse> {
        val builder: CriteriaBuilder = manager.criteriaBuilder
        val query: CriteriaQuery<DailySaleModelResponse> = builder.createQuery(DailySaleModelResponse::class.java)
        val root: Root<Order> = query.from(Order::class.java)
        val predicates = ArrayList<Predicate>()

        val functionConvertTzCreationDate: Expression<Date> = builder.function(
            "convert_tz", Date::class.java,
            root.get<Order>("creationDate"),
            builder.literal("+00:00"),
            builder.literal<String>(timeOffset)
        )

        val functionDateCreationDate: Expression<Date> =
            builder.function("date", Date::class.java, functionConvertTzCreationDate)

        val selection: CompoundSelection<DailySaleModelResponse> = builder.construct(
            DailySaleModelResponse::class.java,
            functionDateCreationDate,
            builder.count(root.get<Order>("id")),
            builder.sum(root.get("totalPrice"))
        )

        if (filter?.restaurantId != null) {
            predicates.add(builder.equal(root.get<Order>("restaurant"), filter.restaurantId))
        }


        if (filter?.creationDateStart != null) {
            predicates.add(
                builder.greaterThanOrEqualTo(
                    root.get("creationDate"),
                    filter.creationDateStart
                )
            )
        }

        if (filter?.creationDateEnd != null) {

            predicates.add(
                builder.lessThanOrEqualTo(
                    root.get("creationDate"),
                    filter.creationDateEnd
                )
            )
        }

        predicates.add(
            root.get<Order>("status").`in`(
                OrderStatus.CONFIRMED, OrderStatus.DELIVERED
            )
        )

        query.select(selection)
        query.where(*predicates.toTypedArray())
        query.groupBy(functionDateCreationDate)
        return manager.createQuery(query).resultList
    }

}