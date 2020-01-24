import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import IconButton from '@material-ui/core/IconButton';

const useStyles = makeStyles(theme => ({
  paper: {
    color:'primary',
    MozBorderBottomColors: 'gray',
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(10),
      marginLeft:theme.spacing(18),
      width: theme.spacing(150),
      height: theme.spacing(35),
    },
  },
  titleMargin:{
      marginLeft:theme.spacing(3),
  },
  gridList: {
    flexWrap: 'nowrap',
    // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
    transform: 'translateZ(0)',
  },
}));

export default function DojunPage() {
  const classes = useStyles();

  return (
    <div className={classes.paper}>
        <Paper elevation={3}>
            <div className={classes.titleMargin}>
                <h4>오늘의 인기 도전 만화</h4>
            </div>
        </Paper>
    
      <GridList className={classes.gridList} cols={2.5}>
        {tileData.map(tile => (
          <GridListTile key={tile.img}>
            <img src={tile.img} alt={tile.title} />
          </GridListTile>
          ))}
      </GridList>
    </div>
  );
}