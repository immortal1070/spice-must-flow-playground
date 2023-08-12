package io.immortal.spicemustflow.application.validation

import io.immortal.spicemustflow.domain.recipe.RecipeIngredient
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class RecipeIngredientsListValidator : ConstraintValidator<ValidRecipeIngredientsList?, List<RecipeIngredient>?> {
    override fun isValid(
        recipeIngredientList: List<RecipeIngredient>?,
        cxt: ConstraintValidatorContext?
    ): Boolean {
        return recipeIngredientList.isNullOrEmpty() ||
                recipeIngredientList.size == recipeIngredientList.distinctBy { it.ingredientId.uuid }.count()
    }
}