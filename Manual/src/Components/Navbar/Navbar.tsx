import React, { useEffect } from 'react';

// ROUTER
import { Link, BrowserRouter } from 'react-router-dom';

// ESTILOS
import './Navbar.css';

const Navbar: React.FC = () => {
  useEffect(() => {
    // SELECCIONAR NAV
    const nav: HTMLElement | null = document.querySelector(".nav");

    // ANIMAR NAV
    setTimeout(() => {
      if (nav) {
        nav.style.transform = "translate(-50%, 0)";
        nav.style.opacity = "1";
      }
    }, 1000);
  }, [])

  return (
    <nav className="nav">
      <div className="nav-wrapper">
        <a href="/" className="brand-logo left">
          <img src="https://alexsan-dev.github.io/Lightroom-USAC/Source/assets/icon.png" alt="Logo" />
          <h1>LightRoom USAC</h1>
        </a>

        <BrowserRouter>
          <ul id="nav-mobile" className="right">
            <li>
              <Link to="/project">
                <span className="waves-effect">Proyecto</span>
              </Link>
            </li>
            <li>
              <Link to="/docs">
                <span className="waves-effect">Documentación</span>
              </Link>
            </li>
            <li id="navMore">
              <Link to="/more">
                <span className="waves-effect">Ver más</span>
              </Link>
            </li>
          </ul>
        </BrowserRouter>
      </div>
    </nav>
  )
}

export default Navbar;