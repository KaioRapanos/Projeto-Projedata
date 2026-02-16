import type { ReactNode } from 'react'

interface LayoutProps {
  children: ReactNode
}

function Layout({ children }: LayoutProps) {
  return (
    <div>
      <header>
        <h2>Production Control</h2>
      </header>

      <main>{children}</main>
    </div>
  )
}

export default Layout