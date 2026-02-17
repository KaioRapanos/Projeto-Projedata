import { useEffect, useState } from 'react'
import { api } from '../services/api'
import { CrudButton } from './CrudButton'

interface Product {
  id: number
  name: string
  price: number
  quantity: number
}

export default function ProductList() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)

  const fetchProducts = async () => {
    setLoading(true)
    try {
      const res = await api.get('/products')
      setProducts(res.data)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchProducts()
  }, [])

  if (loading) return <p>Loading...</p>

  return (
    <div>
      <h3>Products</h3>
      <button onClick={() => console.log('Open new product form')}>New Product</button>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>${p.price}</td>
              <td>{p.quantity}</td>
              <td>
                <CrudButton id={p.id} entity="products" type="edit" onUpdate={fetchProducts} />
                <CrudButton id={p.id} entity="products" type="delete" onUpdate={fetchProducts} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}