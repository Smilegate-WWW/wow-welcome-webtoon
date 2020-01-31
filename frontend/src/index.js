import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Root from './Client/Root';
import * as serviceWorker from './serviceWorker';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core';
import deepOrange from '@material-ui/core/colors/deepOrange';

const theme = createMuiTheme({
    palette:{
        primary :{
            main:deepOrange[400]
        }
    }
})

ReactDOM.render(
<MuiThemeProvider theme={theme}>
    <Root />
</MuiThemeProvider>, 
document.getElementById('root'));


serviceWorker.unregister();
