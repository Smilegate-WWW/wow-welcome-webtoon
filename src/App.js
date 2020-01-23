import React,{Component} from 'react';
import logo from './logo.svg';
import Logo from './Components/Logo';
import './App.css';
import {withStyles} from '@material-ui/core/styles';

const styles=theme => ({
  root:{
    width: '100%',
    marginTop: theme.spacing.unit*3,
    overflowX:"auto"
  }
})


function App() {
  return (
    <div>
      <Logo></Logo>
    </div>
  );
}

export default withStyles(styles) (App);
