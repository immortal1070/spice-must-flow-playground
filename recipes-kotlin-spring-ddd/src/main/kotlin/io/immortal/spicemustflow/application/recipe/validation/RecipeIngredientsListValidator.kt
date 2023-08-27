package io.immortal.spicemustflow.application.recipe.validation

import io.immortal.spicemustflow.domain.recipe.RecipeIngredient
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

const val VALIDATION_RECIPE_INGREDIENT_LIST_ERROR = "One ingredient can be assigned to recipe only ones"

@Constraint(validatedBy = [RecipeIngredientsListValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidRecipeIngredientsList(
    val message: String = VALIDATION_RECIPE_INGREDIENT_LIST_ERROR,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

class RecipeIngredientsListValidator : ConstraintValidator<ValidRecipeIngredientsList?, List<RecipeIngredient>?> {
    override fun isValid(
        recipeIngredientList: List<RecipeIngredient>?,
        cxt: ConstraintValidatorContext?
    ): Boolean {
        return recipeIngredientList.isNullOrEmpty() ||
                recipeIngredientList.size == recipeIngredientList.distinctBy { it.ingredientId.uuid }.count()
    }
}