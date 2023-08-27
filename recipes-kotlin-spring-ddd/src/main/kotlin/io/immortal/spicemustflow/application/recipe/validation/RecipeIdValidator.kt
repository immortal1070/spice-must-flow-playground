package io.immortal.spicemustflow.application.recipe.validation

import io.immortal.spicemustflow.common.exception.ObjectNotFoundException
import io.immortal.spicemustflow.domain.recipe.RecipeId
import io.immortal.spicemustflow.domain.recipe.RecipeRepository
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

const val VALIDATION_RECIPE_ID_ERROR = "Recipe doesn't exist"

@Constraint(validatedBy = [RecipeIdValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidRecipeId(
    val message: String = VALIDATION_RECIPE_ID_ERROR,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

class RecipeIdValidator(private val repository: RecipeRepository) :
    ConstraintValidator<ValidRecipeId?, RecipeId?> {
    override fun isValid(
        recipeId: RecipeId?,
        cxt: ConstraintValidatorContext?
    ): Boolean {
        if (recipeId != null && repository.findById(recipeId) != null) {
            return true;
        }
        throw ObjectNotFoundException("Recipe", recipeId)
    }
}