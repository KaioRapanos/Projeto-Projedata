import ProductionSuggestions from '../components/ProductionSuggestions'
import styles from './styles/Home.module.css'

function Home() {
  return (
    <div className={styles.home}>
      <h1>Home</h1>
      <div className={styles.suggestions}>
        <ProductionSuggestions />
      </div>
    </div>
  )
}

export default Home
