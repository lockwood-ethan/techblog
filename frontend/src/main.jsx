import { StrictMode } from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router'
import Home from './Home.jsx'
import SignInPage from './SignInPage.jsx'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import RegistrationPage from "./RegistrationPage.jsx";

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signin" element={<SignInPage />} />
          <Route path="/register" element={<RegistrationPage />} />
      </Routes>
  </BrowserRouter>
)
