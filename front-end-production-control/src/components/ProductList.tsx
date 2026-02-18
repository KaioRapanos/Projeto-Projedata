import { useEffect, useState } from 'react'
import { api } from '../services/api'
import { CrudButton } from './CrudButton'
import ProductForm from './ProductForm'

interface RawMaterial {
  id: number
  name: string
  quantity: number
}

interface Product {
  id: number
  name: string
  price: number
  quantity: number
  rawMaterials: RawMaterial[]
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
      const sorted = res.data.sort((a: Product, b: Product) =>
        a.name.localeCompare(b.name)
      )
      setProducts(sorted)
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
      {/* Mostrar lista apenas se form não estiver ativo */}
      {!showForm && (
        <>
          <h3>Products</h3>
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
                    {p.rawMaterials.map((rm, index) => (
                      <div key={`${rm.id}-${index}`}> {/* garante unicidade */}
                        {rm.name} ({rm.quantity})
                      </div>
                    ))}
                  </td>
                  <td>
                    <button onClick={() => { setEditProduct(p); setShowForm(true) }}>
                      Edit
                    </button>
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
        </>
      )}

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