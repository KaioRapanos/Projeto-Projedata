import { api } from './api'

export interface Product {
  id: number
  name: string
  price: number
  quantity: number
}

export const productService = {
  async getAll(): Promise<Product[]> {
    const response = await api.get<Product[]>('/products')
    return response.data
  },
}