package io.immortal.spicemustflow.application.validation

import jakarta.validation.Constraint
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