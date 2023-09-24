'use client'
import React, {useEffect, useState} from 'react'
import Dialog, {DialogStyle} from '@/components/dialog'
import {IngredientDto} from '@/client/ingredientsApi'
import Button, {ButtonType} from '@/components/button'

export interface IngredientModalProps {
  open: boolean
  currentIngredient?: IngredientDto
  title: string
  onSubmit: (formData: IngredientFormData) => void
  onCancel: () => void
  okLink?: string
  cancelLink?: string
  ingredientFormId: string
}

export type IngredientFormData = {
  id?: string
  name?: string
}

const IngredientModal = ({
  open,
  title,
  onSubmit,
  onCancel,
  currentIngredient,
  ingredientFormId
}: IngredientModalProps) => {
  const [formData, setFormData] = useState<IngredientFormData>({
    name: ''
  })
  useEffect(() => {
    setFormData({
      ...currentIngredient
    })
  }, [currentIngredient])
  return (
    <Dialog
      open={open}
      title={title}
      onCancel={onCancel}
      style={DialogStyle.big}
      buttons={
        <>
          <Button onClick={onCancel} type={ButtonType.secondary}>
            Cancel
          </Button>
          {/* TODO add padding in dialog */}
          <div className="w-2"></div>
          <Button formId={ingredientFormId} onClick={() => {}}>
            OK
          </Button>
        </>
      }
    >
      <form
        id={ingredientFormId}
        method="post"
        onSubmit={() => onSubmit(formData)}
      >
        <label>
          Name:{' '}
          <input
            className="text-black"
            name="name"
            onChange={(event) => {
              setFormData({...formData, name: event.target.value})
            }}
            value={formData.name || ''}
          />
        </label>
      </form>
    </Dialog>
  )
}

export default IngredientModal
