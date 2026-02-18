import { useEffect, useState } from 'react'
import { api } from '../services/api'
import { CrudButton } from './CrudButton'
import RawMaterialForm from './RawMaterialForm'

interface RawMaterial {
  id: number
  name: string
  quantityInStock: number
}

export default function RawMaterialList() {

  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([])
  const [loading, setLoading] = useState(true)
  const [showForm, setShowForm] = useState(false)
  const [editRawMaterial, setEditRawMaterial] = useState<RawMaterial | null>(null)

  const fetchRawMaterials = async () => {
    setLoading(true)
    try {
      const res = await api.get('/raw-materials')
      setRawMaterials(res.data)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchRawMaterials()
  }, [])

  if (loading) return <p>Loading...</p>

  return (
    <div>

      {!showForm && (
        <>
          <h3>Raw Materials</h3>

          <button onClick={() => { setEditRawMaterial(null); setShowForm(true) }}>
            New Raw Material
          </button>

          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Quantity In Stock</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {rawMaterials.map((rm) => (
                <tr key={rm.id}>
                  <td>{rm.name}</td>
                  <td>{rm.quantityInStock}</td>
                  <td>
                    <button onClick={() => { setEditRawMaterial(rm); setShowForm(true) }}>
                      Edit
                    </button>

                    <CrudButton
                      id={rm.id}
                      entity="raw-materials"
                      type="delete"
                      onUpdate={fetchRawMaterials}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

      {showForm && (
        <RawMaterialForm
          rawMaterial={editRawMaterial || undefined}
          onSuccess={fetchRawMaterials}
          onClose={() => setShowForm(false)}
        />
      )}
    </div>
  )
}
