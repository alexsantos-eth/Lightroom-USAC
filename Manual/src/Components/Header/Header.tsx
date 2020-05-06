import React, { useEffect } from 'react'

// ESTILOS
import './Header.css'

// IMAGENES
import Background from "../../Assets/header.jpg"
import Il from "../../Assets/header-image.png";

const Header: React.FC = () => {
  useEffect(() => {
    // SELECCIONAR ELEMENTOS
    const header: HTMLElement | null = document.getElementById("headerS");
    const mainInfo: HTMLElement | null = document.getElementById("mainInfo");

    // ANIMAR HEADER
    setTimeout(() => {
      if (header) header.style.width = "100%"
    }, 500);

    // ANIMAR TEXTOS
    setTimeout(() => {
      if (mainInfo) {
        mainInfo.style.opacity = "1"
        mainInfo.style.right = "0"
      }
    }, 1500);
  }, [])

  return (
    <header id="headerS">
      <img src={Background} alt="Header Background" />
      <div id="headerContent">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
          <path
            fill="#fff"
            fill-opacity="1"
            d="M0,128L48,154.7C96,181,192,235,288,234.7C384,235,480,181,576,138.7C672,96,768,64,864,69.3C960,75,1056,117,1152,128C1248,139,1344,117,1392,106.7L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
          </path>
        </svg>
        <div id="headerTexts">
          <img src={Il} alt="Illustration" />
          <h1 id="mainInfo">Software de edición
            <p>
              Edita fotos, aplica filtros convierte y modifica todo tipo de imágenes, es totalmente gratuito.
            </p>
            <button className="waves-effect waves-light btn">Iniciar recorrido <i className="material-icons">arrow_forward</i></button>
          </h1>
        </div>
      </div>
    </header >
  )
}

export default Header;