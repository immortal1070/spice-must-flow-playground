package io.immortal.spicemustflow.domain.recipe

import io.immortal.spicemustflow.util.logging.MethodLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

//TODO add caching on service level + events
@Service
@MethodLogging
class RecipeService(private val recipeRepository: RecipeRepository) {
    fun findById(id: UUID): Recipe? = recipeRepository.findById(id)
    fun findByName(name: String?): Recipe? = recipeRepository.findByName(name)
    fun create(name: String): Recipe = recipeRepository.create(name)
}