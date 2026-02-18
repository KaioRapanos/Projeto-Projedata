import { useEffect, useState } from 'react'
import { api } from '../services/api'

interface ProductProductionDTO {
  id: number
  name: string
  maxQuantity: number
  totalValue: number
}

export default function ProductionSuggestions() {
  const [suggestions, setSuggestions] = useState<ProductProductionDTO[]>([])
  const [loading, setLoading] = useState(true)

  const fetchSuggestions = async () => {
    setLoading(true)
    try {
      const res = await api.get('/production/suggestions')
      setSuggestions(res.data)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchSuggestions()
  }, [])

  if (loading) return <p>Loading production suggestions...</p>

  if (suggestions.length === 0) return <p>No production possible with current stock.</p>

  return (
    <div>
      <h3>Production Suggestions</h3>
      <table>
        <thead>
          <tr>
            <th>Product</th>
            <th>Max Quantity</th>
            <th>Total Value</th>
          </tr>
        </thead>
        <tbody>
          {suggestions.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>{p.maxQuantity}</td>
              <td>${p.totalValue}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
