package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.OffsetDateTime

@ApiModel("Problem")
@JsonInclude(JsonInclude.Include.NON_NULL)
class Problem(

    @ApiModelProperty(example = "400", position = 1)
    var status: Int? = null,

    @ApiModelProperty(example = "2022-04-20T12:09:46.175634Z", position = 5)
    var timestamp: OffsetDateTime? = null,

    @ApiModelProperty(example = "https://mealsdelivery.com.br/invalid-data", position = 10)
    var type: String? = null,

    @ApiModelProperty(example = "Invalid data", position = 15)
    var title: String? = null,

    @ApiModelProperty(
        example = "One or more fields are invalid. Please fill in correctly and try again.",
        position = 20
    )
    var detail: String? = null,

    @ApiModelProperty(
        example = "One or more fields are invalid. Please fill in correctly and try again.",
        position = 25
    )
    var userMessage: String? = null,

    @ApiModelProperty(value = "List of objects or fields that generated the error (optional)", position = 30)
    var objects: List<Any>? = null

) {

    constructor(builder: Builder) : this(
        builder.status,
        builder.timestamp,
        builder.type,
        builder.title,
        builder.detail,
        builder.userMessage,
        builder.objects
    )

    @ApiModel("ProblemObject")
    class Builder {

        @ApiModelProperty(example = "price")
        var name: String? = null

        @ApiModelProperty(example = "Product price is mandatory")
        var userMessage: String? = null

        var status: Int? = null

        var timestamp: OffsetDateTime? = null

        var type: String? = null

        var title: String? = null

        var detail: String? = null

        var objects: List<Any>? = null

        fun status(status: Int?) = apply { this.status = status }

        fun timestamp(timestamp: OffsetDateTime?) = apply { this.timestamp = timestamp }

        fun type(type: String?) = apply { this.type = type }

        fun title(title: String?) = apply { this.title = title }

        fun detail(detail: String?) = apply { this.detail = detail }

        fun objects(objects: List<Any>) = apply { this.objects = objects }

        fun userMessage(userMessage: String?) = apply { this.userMessage = userMessage }

        fun build() = Problem(this)

    }

}