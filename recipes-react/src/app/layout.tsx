import './globals.css'
import type {Metadata} from 'next'
import {Inter} from 'next/font/google'
import React from 'react'
import {Providers} from '@/store/provider'
import Link from 'next/link'

const inter = Inter({subsets: ['latin']})

export const metadata: Metadata = {
  title: '"Spice Must Flow" recipes App',
  description: 'App is about recipes and ingredients'
}

export default function RootLayout({children}: {children: React.ReactNode}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <main className="flex min-h-screen flex-col items-stretch justify-between p-2">
          <div className="z-10 w-full items-center justify-between font-mono text-sm lg:flex">
            <p className="fixed left-0 top-0 flex w-full justify-center from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30">
              <Link href="/ingredients">Ingredients</Link>
            </p>
            <p className="fixed left-0 top-0 flex w-full justify-center from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30">
              <Link href="/recipes">Recipes</Link>
            </p>
            <div className="fixed ml-auto bottom-0 left-0 flex h-48 w-full items-end justify-center bg-gradient-to-t from-white via-white dark:from-black dark:via-black lg:static lg:h-auto lg:w-auto lg:bg-none">
              <div className="inline-block bg-secondary-500 m-2 rounded-full">
                <p className="text-white table-cell align-middle text-center h-10 w-10 p-2">
                  Sergei
                </p>
              </div>
            </div>
          </div>
          <Providers>{children}</Providers>
        </main>
      </body>
    </html>
  )
}
