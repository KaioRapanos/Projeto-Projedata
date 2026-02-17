import { api } from '../services/api'

interface CrudButtonProps {
  id: number
  entity: 'products' | 'raw-materials'
  type: 'delete' | 'edit'
  onUpdate?: () => void
}

export function CrudButton({ id, entity, type, onUpdate }: CrudButtonProps) {
  const handleDelete = () => {
    if (confirm('Are you sure?')) {
      api.delete(`/${entity}/${id}`).then(() => onUpdate && onUpdate())
    }
  }

  const handleEdit = () => {
    // Aqui você pode abrir um modal ou navegar para tela de edição
    console.log(`Edit ${entity} ${id}`)
  }

  return (
    <>
      {type === 'delete' && <button onClick={handleDelete}>Delete</button>}
      {type === 'edit' && <button onClick={handleEdit}>Edit</button>}
    </>
  )
}