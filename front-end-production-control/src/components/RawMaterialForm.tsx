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
  const [quantityInStock, setQuantityInStock] = useState(rawMaterial?.quantityInStock || 0)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)

    try {
      if (rawMaterial?.id) {
        await api.put(`/raw-materials/${rawMaterial.id}`, {
          name,
          quantityInStock
        })
      } else {
        await api.post('/raw-materials', {
          name,
          quantityInStock
        })
      }

      onSuccess()
      onClose()
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name:</label>
        <input value={name} onChange={(e) => setName(e.target.value)} required />
      </div>

      <div>
        <label>Quantity In Stock:</label>
        <input
          type="number"
          value={quantityInStock}
          onChange={(e) => setQuantityInStock(Number(e.target.value))}
          required
        />
      </div>

      <button type="submit" disabled={loading}>
        {loading ? 'Saving...' : rawMaterial?.id ? 'Update' : 'Create'}
      </button>

      <button type="button" onClick={onClose}>Cancel</button>
    </form>
  )
}
