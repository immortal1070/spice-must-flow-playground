'use client'
import React from 'react'
import {IngredientDto} from '@/client/ingredientsApi'
import Confirm from '@/components/confirm'

interface IngredientModalProps {
  open: boolean
  ingredient?: IngredientDto
  onOk: () => void
  onCancel: () => void
}

const IngredientDeleteConfirm = ({
  open,
  ingredient,
  onOk,
  onCancel
}: IngredientModalProps) => {
  return (
    <Confirm
      open={open}
      title="Delete ingredient?"
      onSubmit={onOk}
      onCancel={onCancel}
    >
      {`Are you sure you want to delete an ingredient ${
        ingredient && ingredient.name
      }?`}
    </Confirm>
  )
}

export default IngredientDeleteConfirm
