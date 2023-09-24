import React from 'react'
import styles from './loading.module.css'

export default function Loading() {
  return (
    <div className={styles['loader-holder']}>
      <span className={styles.loader}></span>
    </div>
  )
}
