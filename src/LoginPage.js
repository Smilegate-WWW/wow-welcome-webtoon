import React from 'react';
import {makeStyles} from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  root:{
      padding:theme.spacing(20,30),
  },
  logo:{
    padding:theme.spacing(0,5),
  }
}));

export default function LoginPage(){
    const classes = useStyles();
    return(
        <div className={classes.root}>
            <div classNmae={classes.logo}>
                <h1 color="primary">WWW</h1>
            </div>
        </div>
    );
}