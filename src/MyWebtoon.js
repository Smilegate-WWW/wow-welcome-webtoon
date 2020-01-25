import React, {Component} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import Webtoon from './Components/Webtoon';
import Grid from '@material-ui/core/Grid';
import PlusWebtoon from './Components/PlusWebtoon';

const useStyles = makeStyles(theme => ({
    gridRoot: {
      maxWidth:200,
      backgroundColor: theme.palette.background.paper,
    },
    gridList: {
      width: 1200,
      height: 500,
    },
    
}));

const webtoons=[
    {
      title:"유미의 세포들",
      poster:"http://placeimg.com/128/128/any",
      artist:"이동건",
      rating:4
    },
    {
      title:"복학왕",
      poster:"http://placeimg.com/128/128/any",
      artist:"기안84",
      rating:3
    },
    {
      title:"여신강림",
      poster:"http://placeimg.com/128/128/any",
      artist:"냥",
      rating:4
    }
  ]


export default function MyWebtoon(){
    const classes = useStyles();
    return(
        <div className={classes.gridRoot}>
            <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
                {webtoons.map(webtoon => (
                    <GridListTile key={webtoon} item>
                    <Webtoon title={webtoon.title} poster={webtoon.poster} artist={webtoon.artist} rating={webtoon.rating}/>
                    </GridListTile>
                ))}
                <PlusWebtoon/>
            </GridList>
        </div>
    )
}