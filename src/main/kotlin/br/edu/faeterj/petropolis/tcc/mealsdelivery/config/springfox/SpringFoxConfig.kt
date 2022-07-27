package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.springfox

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.*
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model.*
import com.fasterxml.classmate.TypeResolver
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Links
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.context.request.ServletWebRequest
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.*
import springfox.documentation.schema.AlternateTypeRule
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.json.JacksonModuleRegistrar
import springfox.documentation.spring.web.plugins.Docket
import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.net.URLStreamHandler
import java.util.function.Consumer


@Configuration
@Import(BeanValidatorPluginsConfiguration::class)
class SpringFoxConfig(

    @Autowired
    private val typeResolver: TypeResolver

) {

    @Bean
    fun apiDocketV1(): Docket {
        return Docket(DocumentationType.OAS_30)
            .groupName("V1")
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.edu.faeterj.petropolis.tcc.mealsdelivery.api"))
            .paths(PathSelectors.ant("/v1/**"))
            .build()
            .useDefaultResponseMessages(false)
            .globalResponses(HttpMethod.GET, globalGetResponseMessages())
            .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
            .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
            .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
            .additionalModels(typeResolver.resolve(Problem::class.java))
            .ignoredParameterTypes(
                ServletWebRequest::class.java, URL::class.java, URI::class.java, URLStreamHandler::class.java,
                Resource::class.java, File::class.java, InputStream::class.java
            )
            .directModelSubstitute(Pageable::class.java, PageableModelResponseOpenApi::class.java)
            .directModelSubstitute(Links::class.java, LinksModelResponseOpenApi::class.java)
            .alternateTypeRules(*rules())
            .securitySchemes(listOf(authenticationScheme()))
            .securityContexts(listOf(securityContext()))
            .apiInfo(apiInfoV1())
            .tags(tags()[0], *tags())
    }

    @Bean
    fun springFoxJacksonConfig(): JacksonModuleRegistrar? {
        return JacksonModuleRegistrar { objectMapper: ObjectMapper -> objectMapper.registerModule(JavaTimeModule()) }
    }

    private fun securityScheme(): SecurityScheme {
        return OAuthBuilder()
            .name("MealsDelivery")
            .grantTypes(grantTypes())
            .scopes(scopes())
            .build()
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(securityReference()).build()
    }

    private fun securityReference(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

    private fun authenticationScheme(): HttpAuthenticationScheme {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build()
    }

    private fun grantTypes(): List<GrantType> {
        return listOf<GrantType>(ResourceOwnerPasswordCredentialsGrant("/oauth/token"))
    }

    private fun scopes(): List<AuthorizationScope> {
        return listOf(
            AuthorizationScope("READ", "Read access"),
            AuthorizationScope("WRITE", "Write access")
        )
    }

    private fun globalGetResponseMessages(): List<Response> {
        return listOf<Response>(
            ResponseBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value().toString())
                .description("Internal server error")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build(),
            ResponseBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value().toString())
                .description("Resource has no representation that could be accepted by the consumer")
                .build()
        )
    }

    private fun globalPostPutResponseMessages(): List<Response> {
        return listOf<Response>(
            ResponseBuilder()
                .code(HttpStatus.BAD_REQUEST.value().toString())
                .description("Invalid request (client error)")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build(),
            ResponseBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value().toString())
                .description("Internal server error")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build(),
            ResponseBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value().toString())
                .description("Resource has no representation that could be accepted by the consumer")
                .build(),
            ResponseBuilder()
                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value().toString())
                .description("Request refused because the body is in an unsupported format")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build()
        )
    }

    private fun globalDeleteResponseMessages(): List<Response> {
        return listOf<Response>(
            ResponseBuilder()
                .code(HttpStatus.BAD_REQUEST.value().toString())
                .description("Invalid request (client error)")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build(),
            ResponseBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value().toString())
                .description("Internal server error")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemModelReference())
                .build()
        )
    }

    private fun getProblemModelReference(): Consumer<RepresentationBuilder> {
        return Consumer<RepresentationBuilder> { r ->
            r.model { m ->
                m.name("Problem")
                    .referenceModel { ref ->
                        ref.key { k ->
                            k.qualifiedModelName { q ->
                                q.name("Problem")
                                    .namespace("br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler")
                            }
                        }
                    }
            }
        }
    }

    private fun tags(): Array<Tag> {
        return arrayOf(
            Tag("Cities", "Manage cities"),
            Tag("Cuisines", "Manage cusines"),
            Tag("Orders", "Manage orders"),
            Tag("Payment Methods", "Manage payment methods"),
            Tag("Permissions", "Manage permissions"),
            Tag("Products", "Manage products"),
            Tag("Restaurants", "Manage restaurants"),
            Tag("Roles", "Manage roles"),
            Tag("Root Entry Point", "Displays the catalog of resources available in the API"),
            Tag("States", "Manage states"),
            Tag("Statistics", "Meals Delivery statistics"),
            Tag("Users", "Manage users"),
        )
    }

    private fun rules(): Array<AlternateTypeRule> {
        return arrayOf(
            AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel::class.java, CuisineModelResponse::class.java),
                CuisinesModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(Page::class.java, OrderSummaryModelResponse::class.java),
                OrdersSummaryModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, CityModelResponse::class.java),
                CitiesModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, StateModelResponse::class.java),
                StatesModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, PaymentMethodModelResponse::class.java),
                PaymentMethodsModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, RoleModelResponse::class.java),
                RolesModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, PermissionModelResponse::class.java),
                PermissionsModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel::class.java, OrderSummaryModelResponse::class.java),
                OrdersSummaryModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, ProductModelResponse::class.java),
                ProductsModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, RestaurantSummaryModelResponse::class.java),
                RestaurantsSummaryModelResponseOpenApi::class.java
            ),
            AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel::class.java, UserModelResponse::class.java),
                UsersModelResponseOpenApi::class.java
            )
        )
    }

    fun apiInfoV1(): ApiInfo {
        return ApiInfoBuilder()
            .title("MealsDelivery API")
            .description("Open API for customers and restaurants")
            .version("1")
            .contact(
                Contact(
                    "Renato Borges Viana",
                    "https://github.com/renato-viana/meals-delivery-api.git",
                    "rviana@faeterj-petropolis.edu.br"
                )
            )
            .build()
    }

}