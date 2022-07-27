package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation

import org.springframework.util.unit.DataSize
import org.springframework.web.multipart.MultipartFile
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FileSizeValidator : ConstraintValidator<FileSize, MultipartFile?> {

    private lateinit var maxSize: DataSize

    override fun initialize(constraintAnnotation: FileSize) {
        maxSize = DataSize.parse(constraintAnnotation.max)
    }

    override fun isValid(value: MultipartFile?, context: ConstraintValidatorContext): Boolean {
        return value == null || value.size <= maxSize.toBytes()
    }

}