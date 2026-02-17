import { useState } from 'react'
import { api } from '../services/api'

interface Product {
  id?: number
  name: string
  price: number
  quantity: number
}

interface ProductFormProps {
  product?: Product         // se passado, é edit; se não, é new
  onSuccess: () => void     // callback após criar/editar
  onClose: () => void       // fechar modal
}

export default function ProductForm({ product, onSuccess, onClose }: ProductFormProps) {
  const [name, setName] = useState(product?.name || '')
  const [price, setPrice] = useState(product?.price || 0)
  const [quantity, setQuantity] = useState(product?.quantity || 0)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)

    try {
      if (product?.id) {
        // Edit
        await api.put(`/products/${product.id}`, { name, price, quantity })
      } else {
        // New
        await api.post('/products', { name, price, quantity })
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
        <label>Price:</label>
        <input type="number" value={price} onChange={(e) => setPrice(Number(e.target.value))} required />
      </div>
      <div>
        <label>Quantity:</label>
        <input type="number" value={quantity} onChange={(e) => setQuantity(Number(e.target.value))} required />
      </div>
      <button type="submit" disabled={loading}>
        {loading ? 'Saving...' : product?.id ? 'Update' : 'Create'}
      </button>
      <button type="button" onClick={onClose}>Cancel</button>
    </form>
  )
}