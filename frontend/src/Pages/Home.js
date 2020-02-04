import React, { Component } from 'react';
import Header from '../Components/Header';
//라우터
import {Route as Router} from 'react-router-dom';
// 버튼 관련
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
//오늘의 인기도전
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Webtoon from '../Components/Webtoon';
//탭
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';
import PropTypes from 'prop-types';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box';
//웹툰 리스트 출력
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';

const useStyles = makeStyles(theme => ({
    menu: {
        '& > *': {
            margin: theme.spacing(5, 0, 0, 8),
        },
    },
    button: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    todayBest: {
        flexGrow: 1,
        MozBorderBottomColors: 'gray',
        display: 'flex',
        flexWrap: 'wrap',
        '& > *': {
            margin: theme.spacing(5),
            marginLeft: theme.spacing(18),
            width: theme.spacing(150),
            height: theme.spacing(43),
        },
    },
    titleMargin: {
        marginLeft: theme.spacing(3),
        marginBottom: theme.spacing(1),
    },
    webtoonList: {
        width: 1200,
        height: 600,
        margin: theme.spacing(5, 18),
    },
    gridList: {
        width: 1200,
        height: 800,
    },
}));

//오늘 인기 웹툰 정보
const webtoons = [
    {
        title: "유미의 세포들",
        poster: "http://placeimg.com/128/128/any",
        artist: "이동건",
        rating: 4
    },
    {
        title: "복학왕",
        poster: "http://placeimg.com/128/128/any",
        artist: "기안84",
        rating: 3
    },
    {
        title: "신의탑",
        poster: "http://placeimg.com/128/128/any",
        artist: "siu",
        rating: 5
    },
    {
        title: "여신강림",
        poster: "http://placeimg.com/128/128/any",
        artist: "냥",
        rating: 4
    }
]

//오늘 날짜 불러오는 함수
class Clock extends Component {
    constructor(props) {
        super(props)
        this.state = { date: new Date() }
    }
    render() {
        return (
            <div>
                <body2>{this.state.date.getFullYear()}.{this.state.date.getMonth() + 1}.{this.state.date.getDate()}</body2>
            </div>
        )
    }
}

//탭 관련
function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <Typography
            component="div"
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box p={2}>{children}</Box>}
        </Typography>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}


export default function Home({ authenticated, login,logout}) {
    
    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <div>
            <Header authenticated={authenticated} logout={logout}/>

            <div className={classes.menu}>
                <div className={classes.button}>
                    <Button variant="contained" color="primary" href="/">
                    <span style={{color:"#fafafa",fontWeight:550}}>도전만화</span>
                </Button>
                    <Button variant="contained" href="/mypage">
                    <span style={{color:"#212121",fontWeight:520}}>마이페이지</span>
                </Button>
                </div>
            </div>
            
            <div className={classes.todayBest}>
                <Paper elevation={3} >
                    <div className={classes.titleMargin}>
                        <h4>오늘의 인기 도전 만화</h4>
                        <Clock />
                    </div>
                    <Grid container justify="center" direction="row" spacing={8}>
                        {webtoons.map(webtoon => (
                            <Grid key={webtoon} item>
                                <Webtoon title={webtoon.title} poster={webtoon.poster} artist={webtoon.artist} rating={webtoon.rating} />
                            </Grid>
                        ))}
                    </Grid>
                </Paper>
            </div>

            <div className={classes.webtoonList}>
                <Paper elevation={3}>
                    <AppBar position="static" color="inherit">
                        <Tabs value={value} onChange={handleChange} aria-label="simple tabs example">
                            <Tab label="업데이트순"  {...a11yProps(0)} />
                            <Tab label="조회순" {...a11yProps(1)} />
                            <Tab label="별점순" {...a11yProps(2)} />
                        </Tabs>
                    </AppBar>

                    <TabPanel value={value} index={0}>
                        <div className={classes.gridRoot}>
                            <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
                                {webtoons.map(webtoon => (
                                    <GridListTile key={webtoon} item >
                                        <Webtoon title={webtoon.title} poster={webtoon.poster} artist={webtoon.artist} rating={webtoon.rating} />
                                    </GridListTile>
                                ))}
                            </GridList>
                        </div>
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        조회순
                    </TabPanel>
                    <TabPanel value={value} index={2}>
                        별점순
                    </TabPanel>
                </Paper>
            </div>
        </div>
    )
};