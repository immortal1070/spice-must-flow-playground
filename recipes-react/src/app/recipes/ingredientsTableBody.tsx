'use client'
import React, {memo} from 'react'
import {IngredientDto} from '@/client/ingredientsApi'
import {MdModeEditOutline, MdOutlineDelete} from 'react-icons/md'

export type IngredientsTableBodyProps = {
  ingredients: IngredientDto[] | undefined
  updateOnClick: (ingredient: IngredientDto) => void
  deleteOnClick: (ingredient: IngredientDto) => void
}

const IngredientsTableBody = memo(
  ({ingredients, updateOnClick, deleteOnClick}: IngredientsTableBodyProps) => {
    return (
      <tbody>
        {ingredients &&
          ingredients.map((ingredient) => (
            <tr key={ingredient.id} className="m-20">
              <td className="overflow-hidden text-ellipsis max-w-0 ">
                {ingredient.name}
              </td>
              <td className="max-w-0">{ingredient.id}</td>
              <td className="max-w-0">
                <button type="button" onClick={() => updateOnClick(ingredient)}>
                  <MdModeEditOutline className="w-6 h-6" />
                </button>
              </td>
              <td className="max-w-0">
                <button type="button" onClick={() => deleteOnClick(ingredient)}>
                  <MdOutlineDelete className="w-6 h-6" />
                </button>
              </td>
            </tr>
          ))}
      </tbody>
    )
  },
  (oldprops, newprops) => {
    return oldprops.ingredients == newprops.ingredients
  }
)

export default IngredientsTableBody
