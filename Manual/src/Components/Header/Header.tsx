import React, { useEffect, RefObject, useRef } from 'react'

// ESTILOS
import './Header.css'

// IMAGENES
import Background from "../../Assets/header.jpg"
import Il from "../../Assets/header-image.png";

const Header: React.FC = () => {
  // REFERENCIAS
  const header: RefObject<HTMLElement> = useRef(null);
  const mainInfo: RefObject<HTMLHeadingElement> = useRef(null);
  const ill: RefObject<HTMLImageElement> = useRef(null);
  const back: RefObject<HTMLImageElement> = useRef(null);
  const btnWave: RefObject<SVGSVGElement> = useRef(null);


  useEffect(() => {
    // ANIMAR HEADER
    setTimeout(() => {
      if (header.current) {
        header.current.style.width = "100%"
      }
    }, 500);

    // ANIMAR TEXTOS
    setTimeout(() => {
      if (mainInfo.current) {
        mainInfo.current.style.opacity = "1"
        mainInfo.current.style.transform = "translateX(0)"
      }
    }, 1500);

    // QUITAR ANIMACION
    setTimeout(() => {
      if (mainInfo.current) mainInfo.current.style.transition = "none";
      if (ill.current) ill.current.style.transition = "none";
    }, 2000)

    window.addEventListener('scroll', (e: Event) => {
      const scrollValue: number = window.scrollY;

      if (mainInfo.current) mainInfo.current.style.transform = "translateX(" + scrollValue + "px)";
      if (ill.current) ill.current.style.transform = "translateX(-" + scrollValue * 0.2 + "%)";
      if (back.current) back.current.style.transform = "translateY(-" + scrollValue * 0.12 + "px)";
    })
  }, [])

  // EVENTOS
  const startTrial = () => {
    window.scrollTo({ top: 600, behavior: 'smooth' })
  }

  return (
    <header ref={header}>
      <img ref={back} src={Background} alt="Header Background" />
      <div id="headerContent">
        <svg ref={btnWave} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
          <path
            fill="#fff"
            d="M0,128L48,154.7C96,181,192,235,288,234.7C384,235,480,181,576,138.7C672,96,768,64,864,69.3C960,75,1056,117,1152,128C1248,139,1344,117,1392,106.7L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
          </path>
        </svg>
        <div id="headerTexts">
          <img ref={ill} src={Il} alt="Illustration" />
          <h1 ref={mainInfo}>Software de edición
            <p>
              Edita fotos, aplica filtros convierte y modifica todo tipo de imágenes, es totalmente gratuito.
            </p>
            <button onClick={() => startTrial()} className="waves-effect btn">Iniciar recorrido <i className="material-icons">arrow_downward</i></button>
          </h1>
        </div>

      </div>
    </header >
  )
}

export default Header;