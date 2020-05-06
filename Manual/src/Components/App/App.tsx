import React from 'react';

// MATERIALIZE
import 'materialize-css/dist/css/materialize.min.css'
import 'materialize-css/dist/js/materialize.min.js'

// ESTILOS
import './App.css';
import '../../Icons/style.css'

// COMPONENTES
import Navbar from "../Navbar/Navbar"
import Header from '../Header/Header';

function App() {
  return (
    <>
      <Navbar />
      <Header />
    </>
  );
}

export default App;
