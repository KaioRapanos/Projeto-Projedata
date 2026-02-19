import { useState } from 'react'
import { api } from '../services/api'
import styles from './styles/Form.module.css'

interface RawMaterial {
  id?: number
  name: string
  quantityInStock: number
}

interface RawMaterialFormProps {
  rawMaterial?: RawMaterial
  onSuccess: () => void
  onClose: () => void
}

export default function RawMaterialForm({
  rawMaterial,
  onSuccess,
  onClose,
}: RawMaterialFormProps) {
  const [name, setName] = useState(rawMaterial?.name || '')
  const [quantity, setQuantity] = useState<string>(
    rawMaterial?.quantityInStock?.toString() || ''
  )
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError(null)

    try {
      const payload = {
        name: name.trim(),
        quantity: Number(quantity),
      }

      if (rawMaterial?.id) {
        await api.put(`/raw-materials/${rawMaterial.id}`, payload)
      } else {
        await api.post('/raw-materials', payload)
      }

      onSuccess()
      onClose()
    } catch (err: unknown) {
      const errorMessage =
        err && typeof err === 'object' && 'response' in err
          ? (err as { response?: { data?: string } }).response?.data ||
            'Something went wrong'
          : 'Something went wrong'
      setError(errorMessage)
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} className={styles.formContainer}>
      <h3 className={styles.title}>
        {rawMaterial?.id ? 'Edit Raw Material' : 'New Raw Material'}
      </h3>

      <div className={styles.formGroup}>
        <label className={styles.label}>Name</label>
        <input
          className={styles.input}
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>

      <div className={styles.formGroup}>
        <label className={styles.label}>Quantity In Stock</label>
        <input
          className={styles.input}
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
          min={0}
        />
      </div>

      {error && (
        <p style={{ color: '#d32f2f', marginBottom: '10px' }}>{error}</p>
      )}

      <div className={styles.buttonGroup}>
        <button
          type="submit"
          disabled={loading}
          className={styles.primaryButton}
        >
          {loading ? 'Saving...' : rawMaterial?.id ? 'Update' : 'Create'}
        </button>

        <button
          type="button"
          onClick={onClose}
          className={styles.secondaryButton}
        >
          Cancel
        </button>
      </div>
    </form>
  )
}
