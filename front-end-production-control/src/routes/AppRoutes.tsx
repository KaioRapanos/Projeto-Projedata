import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Layout from './Layout'

import Home from '../pages/Home'
import Products from '../pages/Products'
import RawMaterials from '../pages/RawMaterials'

function AppRoutes() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<Products />} />
          <Route path="/raw-materials" element={<RawMaterials />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  )
}

export default AppRoutes