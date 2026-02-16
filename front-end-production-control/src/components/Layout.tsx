import type { ReactNode } from 'react'
import { Link } from 'react-router-dom'

interface LayoutProps {
  children: ReactNode
}

function Layout({ children }: LayoutProps) {
  return (
    <div>
      <header>
        <h2>Production Control</h2>

        <nav>
          <Link to="/">Home</Link> |{' '}
          <Link to="/products">Products</Link> |{' '}
          <Link to="/raw-materials">Raw Materials</Link>
        </nav>
      </header>

      <main>{children}</main>
    </div>
  )
}

export default Layout