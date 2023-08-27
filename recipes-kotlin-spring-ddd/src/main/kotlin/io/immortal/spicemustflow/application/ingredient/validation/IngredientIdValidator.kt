package io.immortal.spicemustflow.application.recipe.validation

import io.immortal.spicemustflow.common.exception.ObjectNotFoundException
import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.immortal.spicemustflow.domain.ingredient.IngredientRepository
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

const val VALIDATION_INGREDIENT_ID_ERROR = "Ingredient doesn't exist"

@Constraint(validatedBy = [IngredientIdValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidIngredientId(
    val message: String = VALIDATION_INGREDIENT_ID_ERROR,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

class IngredientIdValidator(private val repository: IngredientRepository) :
    ConstraintValidator<ValidIngredientId?, IngredientId?> {
    override fun isValid(
        ingredientId: IngredientId?,
        cxt: ConstraintValidatorContext?
    ): Boolean {
        if (ingredientId != null && repository.findById(ingredientId) != null) {
            return true;
        }
        throw ObjectNotFoundException("Ingredient", ingredientId)
    }
}