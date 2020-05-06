import React, { useEffect, RefObject, useRef } from 'react';

// IMAGENES
import Login from '../../Assets/login.jpg';
import viewer from '../../Assets/viewer.jpg';
import converter from '../../Assets/converter.jpg'
import Move from "../../Assets/ill.gif"

// ESTILOS
import './SubHeader.css'

const SubHeader: React.FC = () => {
  const screens: RefObject<HTMLDivElement> = useRef(null);
  const image: RefObject<HTMLImageElement> = useRef(null);

  useEffect(() => {
    window.addEventListener('scroll', () => {
      const scroll: number = window.scrollY;
      const translate: number = 400 - scroll;

      if (screens.current && translate > 0) {
        screens.current.style.transform = "translate(" + translate + "px)"
        screens.current.style.opacity = scroll * 0.003 + "";
      }

      if (image.current)
        image.current.style.opacity = scroll * 0.0025 + "";
    })
  }, [])

  return (
    <div id="secContent">
      <div id="secBanner">
        <h1>Descarga gratuita</h1>
        <p>Prueba la version de prueba totalmente gratis, incluye módulos de convertidor, editor y visor</p>
        <button className="blue btn waves-effect">Descargar <i className="material-icons">arrow_downward</i></button>
      </div>
      <div ref={screens} id="screens">
        <img src={Login} alt="Login" />
        <img src={viewer} alt="Viewer" />
        <img src={converter} alt="Converter" />
      </div>
      <img ref={image} id="secMove" src={Move} alt='ill' />
    </div>
  )
}

export default SubHeader;