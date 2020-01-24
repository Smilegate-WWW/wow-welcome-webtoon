import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core';
import green from '@material-ui/core/colors/green';

const theme = createMuiTheme({
    palette:{
        primary :{
            main:green[500]
        }
    }
})

ReactDOM.render(
<MuiThemeProvider theme={theme}>
    <App />
</MuiThemeProvider>, 
document.getElementById('root'));


serviceWorker.unregister();
