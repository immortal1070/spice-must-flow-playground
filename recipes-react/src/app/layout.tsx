import './globals.css'
import type {Metadata} from 'next'
import {Inter} from 'next/font/google'
import React from 'react'
import {Providers} from '@/store/provider'

const inter = Inter({subsets: ['latin']})

export const metadata: Metadata = {
  title: '"Spice Must Flow" recipes App',
  description: 'App is about recipes and ingredients'
}

export default function RootLayout({children}: {children: React.ReactNode}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <Providers>{children}</Providers>
      </body>
    </html>
  )
}
