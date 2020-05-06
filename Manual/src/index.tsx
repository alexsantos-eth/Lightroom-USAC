import React from 'react';
import ReactDOM from 'react-dom';

// ESTILOS
import './index.css';

// COMPONENTES
import App from './Components/App/App';

// SERVICE WORKER
import * as serviceWorker from './serviceWorker';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.register();
