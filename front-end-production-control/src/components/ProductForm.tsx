import { useState } from 'react'
import { api } from '../services/api'
import styles from './styles/Form.module.css'
import ProductComposition from './ProductComposition.tsx'

interface Product {
  id?: number
  name: string
  price: number
  quantity: number
}

interface ProductFormProps {
  product?: Product
  onSuccess: () => void
  onClose: () => void
}

export default function ProductForm({
  product,
  onSuccess,
  onClose,
}: ProductFormProps) {
  const [name, setName] = useState(product?.name || '')
  const [price, setPrice] = useState(product?.price.toString() || '')
  const [quantity, setQuantity] = useState(product?.quantity.toString() || '')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)

    try {
      // Convertemos para número aqui, só no submit
      const payload = {
        name,
        price: Number(price),
        quantity: Number(quantity),
      }

      if (product?.id) {
        await api.put(`/products/${product.id}`, payload)
      } else {
        await api.post('/products', payload)
      }

      onSuccess()
      onClose()
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} className={styles.formContainer}>
      <h3 className={styles.title}>
        {product?.id ? 'Edit Product' : 'New Product'}
      </h3>

      <div className={styles.formGroup}>
        <label className={styles.label}>Name:</label>
        <input
          className={styles.input}
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>
      <div className={styles.formGroup}>
        <label className={styles.label}>Price:</label>
        <input
          className={styles.input}
          type="number"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
          required
        />
      </div>
      <div className={styles.formGroup}>
        <label className={styles.label}>Quantity:</label>
        <input
          className={styles.input}
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
        />
      </div>

      <div className={styles.buttonGroup}>
        <button
          type="submit"
          disabled={loading}
          className={styles.primaryButton}
        >
          {loading ? 'Saving...' : product?.id ? 'Update' : 'Create'}
        </button>

        <button
          type="button"
          onClick={onClose}
          className={styles.secondaryButton}
        >
          Cancel
        </button>
      </div>

      {product?.id && <ProductComposition productId={product.id} />}
    </form>
  )
}
