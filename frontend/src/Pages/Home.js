import React, { Component } from 'react';
import Header from '../Components/Header';
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
        thumbnail: "http://placeimg.com/128/128/any",
        artist: "이동건",
        ep_rating_avg: 4
    },
    {
        title: "복학왕",
        thumbnail: "http://placeimg.com/128/128/any",
        artist: "기안84",
        ep_rating_avg: 3
    },
    {
        title: "신의탑",
        thumbnail: "http://placeimg.com/128/128/any",
        artist: "siu",
        ep_rating_avg: 5
    },
    {
        title: "여신강림",
        thumbnail: "http://placeimg.com/128/128/any",
        artist: "냥",
        ep_rating_avg: 4
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
                <h5>{this.state.date.getFullYear()}.{this.state.date.getMonth() + 1}.{this.state.date.getDate()}</h5>
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



export default function Home() {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const [list, setList] = React.useState([]);
    const [listByhits, setListByhits] = React.useState([]);
    const [listByep_rating_avg, setListByep_rating_avg] = React.useState([]);

    React.useEffect(() => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("/webtoonlist?page=1&sortBy=0", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    setList(result.data.webtoonlist);
                }
            })
            .catch(error => console.log('error', error));


        fetch("/webtoonlist?page=1&sortBy=1", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    setListByhits(result.data.webtoonlist);
                }
            })
            .catch(error => console.log('error', error));


        fetch("/webtoonlist?page=1&sortBy=2", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    setListByep_rating_avg(result.data.webtoonlist);
                }
            })
            .catch(error => console.log('error', error));
    }, [])

    return (
        <div>
            <Header />

            <div className={classes.menu}>
                <div className={classes.button}>
                    <Button variant="contained" color="primary" href="/">
                        <span style={{ color: "#fafafa", fontWeight: 550 }}>도전만화</span>
                    </Button>
                    <Button variant="contained" href="/mypage">
                        <span style={{ color: "#212121", fontWeight: 520 }}>마이페이지</span>
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
                            <Grid key={webtoon.title} item>
                                <Webtoon title={webtoon.title} thumbnail={webtoon.thumbnail} artist={webtoon.artist} ep_rating_avg={webtoon.ep_rating_avg} />
                            </Grid>
                        ))}
                    </Grid>
                </Paper>
            </div>

            <div className={classes.webtoonList}>
                <Paper elevation={3}>
                    <AppBar position="static" color="inherit">
                        <Tabs value={value} onChange={handleChange} aria-label="webtoonListTabLabel">
                            <Tab label="업데이트순"  {...a11yProps(0)} />
                            <Tab label="조회순" {...a11yProps(1)} />
                            <Tab label="별점순" {...a11yProps(2)} />
                        </Tabs>
                    </AppBar>

                    <TabPanel value={value} index={0}>
                        <div className={classes.gridRoot}>
                            <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
                                {list.map(webtoon => (
                                    <GridListTile key={webtoon.idx} item="true" >
                                        <Webtoon title={webtoon.title} thumbnail={webtoon.thumbnail} artist={webtoon.artist} ep_rating_avg={webtoon.ep_rating_avg} idx={webtoon.idx} />
                                    </GridListTile>
                                ))}
                            </GridList>
                        </div>
                    </TabPanel>
                    <TabPanel value={value} index={1}>
                        <div className={classes.gridRoot}>
                            <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
                                {listByhits.map(webtoon => (
                                    <GridListTile key={webtoon.idx} item="true" >
                                        <Webtoon title={webtoon.title} thumbnail={webtoon.thumbnail} artist={webtoon.artist} ep_rating_avg={webtoon.ep_rating_avg} idx={webtoon.idx}/>
                                    </GridListTile>
                                ))}
                            </GridList>
                        </div>
                    </TabPanel>
                    <TabPanel value={value} index={2}>
                        <div className={classes.gridRoot}>
                            <GridList cellHeight={250} className={classes.gridList} spacing={15} cols={5}>
                                {listByep_rating_avg.map(webtoon => (
                                    <GridListTile key={webtoon.idx} item="true" >
                                        <Webtoon title={webtoon.title} thumbnail={webtoon.thumbnail} artist={webtoon.artist} ep_rating_avg={webtoon.ep_rating_avg} idx={webtoon.idx}/>
                                    </GridListTile>
                                ))}
                            </GridList>
                        </div>
                    </TabPanel>
                </Paper>
            </div>
        </div>
    )
};