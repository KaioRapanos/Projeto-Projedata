import { useEffect, useState } from 'react'
import { api } from '../services/api'
import styles from './styles/ProductComposition.module.css'

interface RawMaterial {
  id: number
  name: string
}

interface ProductRawMaterial {
  id: number
  rawMaterial: RawMaterial
  quantity: number
}

interface Props {
  productId: number
}

export default function ProductComposition({ productId }: Props) {
  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([])
  const [relations, setRelations] = useState<ProductRawMaterial[]>([])
  const [selectedRawMaterial, setSelectedRawMaterial] = useState<number>()
  const [quantity, setQuantity] = useState(0)

  useEffect(() => {
    const loadData = async () => {
      const rm = await api.get('/raw-materials')
      const rel = await api.get(`/product-materials/product/${productId}`)

      setRawMaterials(rm.data)
      setRelations(rel.data)
    }

    loadData()
  }, [productId])

  const handleAdd = async () => {
    if (!selectedRawMaterial || quantity <= 0) return

    await api.post('/product-materials', {
      productId,
      rawMaterialId: selectedRawMaterial,
      quantity,
    })

    setQuantity(0)
    setSelectedRawMaterial(undefined)

    // Recarrega dados manualmente
    const rm = await api.get('/raw-materials')
    const rel = await api.get(`/product-materials/product/${productId}`)

    setRawMaterials(rm.data)
    setRelations(rel.data)
  }

  return (
    <div className={styles.container}>
      <h4 className={styles.title}>Composition</h4>

      <div className={styles.controls}>
        <select
          className={styles.select}
          value={selectedRawMaterial || ''}
          onChange={(e) => setSelectedRawMaterial(Number(e.target.value))}
        >
          <option value="">Select raw material</option>
          {rawMaterials.map((rm) => (
            <option key={rm.id} value={rm.id}>
              {rm.name}
            </option>
          ))}
        </select>

        <input
          className={styles.input}
          type="number"
          placeholder="Quantity"
          value={quantity}
          onChange={(e) => setQuantity(Number(e.target.value))}
        />

        <button className={styles.addButton} onClick={handleAdd}>
          Add
        </button>
      </div>

      <ul className={styles.list}>
        {relations.map((rel, index) => (
          <li
            key={`${rel.id}-${rel.rawMaterial.id}-${index}`}
            className={styles.listItem}
          >
            {rel.rawMaterial.name} - {rel.quantity}
          </li>
        ))}
      </ul>
    </div>
  )
}
