package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RootEntryPointControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.RepresentationModel
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RootEntryPointController(

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RootEntryPointControllerOpenApi {

    @GetMapping
    override fun root(): RootEntryPointModelResponse {
        val rootEntryPointModelResponse = RootEntryPointModelResponse()

        if (security.canConsultCuisines()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToCuisines("cuisines"))
        }

        if (security.canSearchOrders()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToOrders("orders"))
        }

        if (security.canConsultRestaurants()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToRestaurants("restaurants"))
        }

        if (security.canConsultUsersRolesPermissions()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToRoles("roles"))
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToUsers("users"))
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToPermissions("permissions"))
        }

        if (security.canConsultPaymentMethods()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToPaymentMethods("payment-methods"))
        }

        if (security.canConsultStates()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToStates("states"))
        }

        if (security.canConsultCities()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToCities("cities"))
        }

        if (security.canConsultStatistics()) {
            rootEntryPointModelResponse.add(resourceLinkHelper.linkToStatistics("statistics"))
        }

        return rootEntryPointModelResponse
    }

    class RootEntryPointModelResponse : RepresentationModel<RootEntryPointModelResponse>()

}