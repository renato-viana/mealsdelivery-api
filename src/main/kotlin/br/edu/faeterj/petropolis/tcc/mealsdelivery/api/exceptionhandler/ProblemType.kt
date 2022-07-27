package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler

enum class ProblemType(path: String, title: String) {
    ACCESS_DENIED("/access-denied", "Access denied"),
    INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    SYSTEM_ERROR("/system-error", "System error"),
    INVALID_DATA("/invalid-data", "Invalid data");

    var title: String
    var uri: String

    init {
        uri = "https://mealsdelivery.com$path"
        this.title = title
    }

}