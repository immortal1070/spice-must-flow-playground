import React, {ReactNode} from 'react'

export enum ButtonType {
  primary,
  secondary
}

interface ButtonProps {
  onClick: () => void
  type?: ButtonType
  children: ReactNode
  formId?: string
}

export default function Button({
  children,
  formId,
  onClick,
  type = ButtonType.primary
}: ButtonProps) {
  let buttonStyle
  switch (type) {
    case ButtonType.primary: {
      buttonStyle = 'bg-primary-500 text-white'
      break
    }
    case ButtonType.secondary: {
      buttonStyle = 'border border-current'
      break
    }
  }

  return (
    <button
      type={formId ? 'submit' : 'button'}
      {...(formId ? {form: formId} : {})}
      onClick={onClick}
      className={`${buttonStyle} p-2 w-20`}
    >
      {children}
    </button>
  )
}
