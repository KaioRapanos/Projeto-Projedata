import { useEffect, useState } from 'react'
import { api } from '../services/api'
import { CrudButton } from './CrudButton'
import ProductForm from './ProductForm'

interface Product {
  id: number
  name: string
  price: number
  quantity: number
}

export default function ProductList() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)
  const [showForm, setShowForm] = useState(false)
  const [editProduct, setEditProduct] = useState<Product | null>(null)

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
      {/* Botão para adicionar novo produto */}
      <button onClick={() => { setEditProduct(null); setShowForm(true) }}>
         New Product
      </button>
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
                {/* Botão Edit */}
                <button onClick={() => { setEditProduct(p); setShowForm(true) }}>
                  Edit
                </button>
                {/* Botão Delete */}
                <CrudButton
                  id={p.id}
                  entity="products"
                  type="delete"
                  onUpdate={fetchProducts}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Formulário condicional para New/Edit */}
      {showForm && (
        <ProductForm
          product={editProduct || undefined}
          onSuccess={fetchProducts}
          onClose={() => setShowForm(false)}
        />
      )}
    </div>
  )
}