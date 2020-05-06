import React, { RefObject, useRef, useEffect } from 'react';

import './Code.css'

import CodeGIF from '../../Assets/space.gif'

const Code: React.FC = () => {
  const image: RefObject<HTMLImageElement> = useRef(null);

  useEffect(() => {
    window.addEventListener('scroll', () => {
      const scroll: number = window.scrollY - 500;
      if (image.current && scroll < 1000) image.current.style.transform = "translate(" + scroll * 0.2 + "px)";
    })
  }, []);

  return (
    <div id="manual">

      <img ref={image} src={CodeGIF} alt='alt' />
      <div id="Mtexts">
        <h1>Documentación</h1>
        <p>Detalle de todos los métodos utilizados, lógica de cada vista y módulos ademas de un manual técnico</p>
        <button className="btn waves-effect">
          Ver Documentación <i className="material-icons">book</i>
        </button>
      </div>
    </div>
  )
}

export default Code;