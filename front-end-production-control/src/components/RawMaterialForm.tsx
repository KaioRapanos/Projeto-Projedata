import { useState } from 'react'
import { api } from '../services/api'

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
  onClose
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
        quantity: Number(quantity)
      }

      if (rawMaterial?.id) {
        await api.put(`/raw-materials/${rawMaterial.id}`, payload)
      } else {
        await api.post('/raw-materials', payload)
      }

      onSuccess()
      onClose()
    } catch (err: unknown) {
      const errorMessage = err && typeof err === 'object' && 'response' in err
        ? (err as { response?: { data?: string } }).response?.data || 'Something went wrong'
        : 'Something went wrong'
      setError(errorMessage)
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name:</label>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>

      <div>
        <label>Quantity In Stock:</label>
        <input
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
          min={0}
        />
      </div>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <button type="submit" disabled={loading}>
        {loading ? 'Saving...' : rawMaterial?.id ? 'Update' : 'Create'}
      </button>

      <button type="button" onClick={onClose}>Cancel</button>
    </form>
  )
}
