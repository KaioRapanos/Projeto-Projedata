import { NavLink } from 'react-router-dom'
import styles from './styles/Layout.module.css'
import logo from '../../public/ChatGPT Image 18 de fev. de 2026, 18_13_50.png' // coloque o logo em /src/assets

interface LayoutProps {
  children: React.ReactNode
}

export default function Layout({ children }: LayoutProps) {
  return (
    <div className={styles.layout}>
      {/* Navbar com logo e links */}
      <nav className={styles.navbar}>
        <img src={logo} alt="Autoflex Logo" className={styles.logo} />
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? `${styles.navLink} ${styles.activeLink}` : styles.navLink
          }
        >
          Home
        </NavLink>
        <NavLink
          to="/products"
          className={({ isActive }) =>
            isActive ? `${styles.navLink} ${styles.activeLink}` : styles.navLink
          }
        >
          Products
        </NavLink>
        <NavLink
          to="/raw-materials"
          className={({ isActive }) =>
            isActive ? `${styles.navLink} ${styles.activeLink}` : styles.navLink
          }
        >
          Raw Materials
        </NavLink>
      </nav>

      {/* Container principal das páginas */}
      <div className={styles.container}>{children}</div>
    </div>
  )
}
