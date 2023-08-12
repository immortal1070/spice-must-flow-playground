import io.immortal.spicemustflow.domain.ingredient.IngredientId
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO for ingredients of a recipe")
class RecipeIngredientDto(
    val ingredientId: IngredientId,

    @Schema(description = "Amount of ingredient used in this recipe", example = "0.5")
    val amount: Double
)