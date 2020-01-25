import React, {Component} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import Webtoon from './Components/Webtoon';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';

const useStyles = makeStyles(theme => ({
  paper: {
    flexGrow:1,
    MozBorderBottomColors: 'gray',
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(5),
      marginLeft:theme.spacing(18),
      width: theme.spacing(150),
      height: theme.spacing(43),
    },
  },
  sortMenu:{
    width:"100%",
    height:"10%",
    marginLeft:theme.spacing(18),
  },
  titleMargin:{
      marginLeft:theme.spacing(3),
  },
  gridRoot: {
    maxWidth:200,
    backgroundColor: theme.palette.background.paper,
  },
  gridList: {
    width: 1200,
    height: 800,
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
    title:"신의탑",
    poster:"http://placeimg.com/128/128/any",
    artist:"siu",
    rating:5
  },
  {
    title:"여신강림",
    poster:"http://placeimg.com/128/128/any",
    artist:"냥",
    rating:4
  }
]

class Clock extends Component {
  constructor(props) {
    super(props)
    this.state = {date: new Date()}
  } 
  render() {
      return (
          <div>
          <body2>{this.state.date.getFullYear()}.{this.state.date.getMonth()+1}.{this.state.date.getDate()}</body2>
          </div>
      )
  }
}

export default function DojunPage() {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);
  
  const handleClick = event => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div className={classes.paper}>
        <Paper elevation={3} >
          <div className={classes.titleMargin}>
              <h4>오늘의 인기 도전 만화</h4>
              <Clock/>
          </div>
          <Grid  container justify="center" direction="row" spacing={8}>
              {webtoons.map(webtoon => (
                <Grid key={webtoon} item>
                  <Webtoon title={webtoon.title} poster={webtoon.poster} artist={webtoon.artist} rating={webtoon.rating}/> 
                </Grid>
              ))}
          </Grid>
      </Paper>
      <div className={classes.sortMenu}>
        <Button aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick} variant="contained" color="primary">
          정렬순
        </Button>
        <Menu
          id="simple-menu"
          anchorEl={anchorEl}
          keepMounted
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          <MenuItem onClick={handleClose}>별점순</MenuItem>
          <MenuItem onClick={handleClose}>조회수순</MenuItem>
          <MenuItem onClick={handleClose}>업로드순</MenuItem>
        </Menu>
        </div>
      <div className={classes.gridRoot}>
        <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
          {webtoons.map(webtoon => (
            <GridListTile key={webtoon} item>
              <Webtoon title={webtoon.title} poster={webtoon.poster} artist={webtoon.artist} rating={webtoon.rating}/>
            </GridListTile>
          ))}
        </GridList>
      </div>
    </div>
  );
}