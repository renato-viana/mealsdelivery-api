package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation

import org.springframework.web.multipart.MultipartFile
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FileContentTypeValidator : ConstraintValidator<FileContentType, MultipartFile?> {

    private lateinit var allowedContentTypes: List<String>

    override fun initialize(constraint: FileContentType) {
        allowedContentTypes = constraint.allowed.asList()
    }

    override fun isValid(multipartFile: MultipartFile?, context: ConstraintValidatorContext): Boolean {
        return (multipartFile == null || allowedContentTypes.contains(multipartFile.contentType))
    }

}