//package io.immortal.spicemustflow
//
//import io.immortal.spicemustflow.infrastructure.ingredient.IngredientModel
//import io.immortal.spicemustflow.domain.ingredient.IngredientRepository
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
////@WithDatabase
//class RecipeJpaTests @Autowired constructor(
//    val entityManager: TestEntityManager,
//    val ingredientRepository: IngredientRepository
//) {
//
//    @Test
//    fun `When findByLogin then return User`() {
//        val potato = IngredientModel(name = "potato")
//        entityManager.persist(potato)
//        entityManager.flush()
//        val foundPotato = ingredientRepository.findByName(potato.name)
////        assertThat(foundPotato).isEqualTo(potato)
//        println(foundPotato?.id)
//
//        val missingPotato = ingredientRepository.findByName("not existing")
//        assertThat(missingPotato).isEqualTo(null)
//
////        foundPotato?.updatedBy = "me!!!!"
//        entityManager.flush()
//    }
//}