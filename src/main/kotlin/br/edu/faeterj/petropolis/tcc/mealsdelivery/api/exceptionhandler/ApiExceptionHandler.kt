package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.ValidationException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityNotFoundException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime
import java.util.stream.Collectors

@ControllerAdvice
class ApiExceptionHandler(

    private val GENERIC_MESSAGE_SYSTEM_ERROR: String = ("Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato " + "com o administrador do sistema."),

    @Autowired
    private val messageSource: MessageSource

) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleUncaught(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val problemType = ProblemType.SYSTEM_ERROR
        val detail = GENERIC_MESSAGE_SYSTEM_ERROR
        logger.error(ex.message, ex)
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException, request: WebRequest): ResponseEntity<Any> {
        return handleValidationInternal(
            ex, ex.bindingResult, HttpHeaders(),
            HttpStatus.BAD_REQUEST, request
        )
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleEntityNotFound(ex: AccessDeniedException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.FORBIDDEN
        val problemType = ProblemType.ACCESS_DENIED
        val detail = ex.message
        val problem: Problem = createProblemBuilder(status, problemType, detail)
            .userMessage(detail)
            .userMessage("Você não possui permissão para executar essa operação.")
            .build()

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        return handleValidationInternal(ex, ex.bindingResult, headers, status, request)
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleValidationInternal(ex, ex.bindingResult, headers, status, request)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).headers(headers).build()
    }

    private fun handleValidationInternal(
        ex: Exception, bindingResult: BindingResult,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val problemType = ProblemType.INVALID_DATA
        val detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."
        val problemObjects: List<Any> = bindingResult.allErrors.stream()
            .map { objectError: ObjectError ->
                val message: String = messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
                var name: String = objectError.objectName

                if (objectError is FieldError) {
                    name = objectError.field
                }

                val objects = Problem.Builder()
                objects.name = name
                objects.userMessage = message
            }
            .collect(Collectors.toList())

        val problem: Problem =
            createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects).build()

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val problemType = ProblemType.RESOURCE_NOT_FOUND
        val detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.requestURL)
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        return if (ex is MethodArgumentTypeMismatchException) {
            handleMethodArgumentTypeMismatch(ex, headers, status, request)
        } else super.handleTypeMismatch(ex, headers, status, request)
    }

    private fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val problemType = ProblemType.INVALID_PARAMETER

        val detail = String.format(
            "O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor " +
                    "compatível com o tipo %s.", ex.name, ex.value, ex.requiredType?.simpleName
        )

        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val rootCause: Throwable = ExceptionUtils.getRootCause(ex)
        if (rootCause is InvalidFormatException) {
            return handleInvalidFormat(rootCause, headers, status, request)
        } else if (rootCause is PropertyBindingException) {
            return handlePropertyBinding(rootCause, headers, status, request)
        }
        val problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE
        val detail = "O corpo da requisição está inválido. Verifique erro de sintaxe."
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun handlePropertyBinding(
        ex: PropertyBindingException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val path = joinPath(ex.path)
        val problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE
        val detail = String.format(
            "A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path
        )
        val problem: Problem =
            createProblemBuilder(status, problemType, detail).userMessage(GENERIC_MESSAGE_SYSTEM_ERROR).build()


        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun handleInvalidFormat(
        ex: InvalidFormatException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val path = joinPath(ex.path)
        val problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE
        val detail = String.format(
            "A propriedade '%s' recebeu o valor '%s', "
                    + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            path, ex.value, ex.targetType.simpleName
        )
        val problem: Problem =
            createProblemBuilder(status, problemType, detail).userMessage(GENERIC_MESSAGE_SYSTEM_ERROR).build()

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.NOT_FOUND
        val problemType = ProblemType.RESOURCE_NOT_FOUND
        val detail: String = ex.message!!
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(EntityInUseException::class)
    fun handleEntityInUse(ex: EntityInUseException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.CONFLICT
        val problemType = ProblemType.ENTITY_IN_USE
        val detail: String = ex.message!!
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(ex: BusinessException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.BAD_REQUEST
        val problemType = ProblemType.BUSINESS_ERROR
        val detail: String = ex.message!!
        val problem: Problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build()

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    override fun handleExceptionInternal(
        ex: Exception, body: Any?, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        var body = body

        if (body == null) {
            body = Problem(
                timestamp = OffsetDateTime.now(),
                title = status.reasonPhrase,
                status = status.value(),
                userMessage = GENERIC_MESSAGE_SYSTEM_ERROR
            )
        } else if (body is String) {
            body = Problem(
                timestamp = OffsetDateTime.now(),
                title = body as String?,
                status = status.value(),
                userMessage = GENERIC_MESSAGE_SYSTEM_ERROR
            )
        }

        return super.handleExceptionInternal(ex, body, headers, status, request)
    }

    private fun createProblemBuilder(
        status: HttpStatus,
        problemType: ProblemType,
        detail: String?,
    ): Problem.Builder {
        return Problem.Builder()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .type(problemType.uri)
            .title(problemType.title)
            .detail(detail)
    }

    private fun joinPath(references: List<JsonMappingException.Reference>): String {
        return references.stream().map { obj: JsonMappingException.Reference -> obj.fieldName }
            .collect(Collectors.joining("."))
    }

}