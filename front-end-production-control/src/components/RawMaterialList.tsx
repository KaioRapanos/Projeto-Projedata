import { useEffect, useState } from 'react'
import { api } from '../services/api'
import { CrudButton } from './CrudButton'
import RawMaterialForm from './RawMaterialForm'
import styles from './styles/Table.module.css'


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

      // **Mapeando quantity para quantityInStock**
      const mappedData = res.data
      .map((rm: { id: number; name: string; quantity: number }) => ({
        id: rm.id,
        name: rm.name,
        quantityInStock: rm.quantity
      }))
      .sort((a: RawMaterial, b: RawMaterial) => a.name.localeCompare(b.name)) // ordena alfabeticamente

    setRawMaterials(mappedData)
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
          <h3 className={styles.title}>
            Raw Materials
          </h3>

          <button 
            className={styles.buttonPrimary}
            onClick={() => { setEditRawMaterial(null); setShowForm(true) }}
          >
            New Raw Material
          </button>

          <table className={styles.table}>
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
                  <td className={styles.actionButtons}>
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
