import React, { Component } from 'react';
import Header from '../Components/Header';
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//별점
import Box from '@material-ui/core/Box';
import Rating from '@material-ui/lab/Rating';
//select
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
//댓글 다는 field
import TextField from '@material-ui/core/TextField';
//탭
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import PropTypes from 'prop-types';
//comment component
import Comment from '../Components/Comment';

const useStyles = makeStyles(theme => ({
    menu: {
        '& > *': {
            margin: theme.spacing(5, 0, 3, 8),
        },
    },
    button: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    title: {
        padding: theme.spacing(2, 35),
        margin: theme.spacing(0, 0, 5, 0),
        height: 100,
    },
    body: {
        margin: theme.spacing(0, 35),
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    comment: {
        margin: theme.spacing(0, 5),
    },
}));

//에피소드 정보
const episode = {
    no: 11,
    title: "은성이의 사랑",
    starRating: 4.5,
    cuts: [
        { cut: "http://placeimg.com/500/800/any" },
        { cut: "http://placeimg.com/500/1000/any" },
        { cut: "http://placeimg.com/500/300/any" },
        { cut: "http://placeimg.com/500/256/any" },
        { cut: "http://placeimg.com/500/800/any" },
        { cut: "http://placeimg.com/500/900/any" },
        { cut: "http://placeimg.com/500/800/any" },
    ],
    authorComment: "오늘하루최고~~",
}

//댓글 정보
const comments=[
    {
        nickname:"감자돌이",
        comment:"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
        date:"2020.02.05",
        goodNum:125,
        badNum:5,
    },
    {
        nickname:"감자돌이",
        comment:"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
        date:"2020.02.05",
        goodNum:125,
        badNum:5,
    },
    {
        nickname:"감자돌이",
        comment:"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
        date:"2020.02.05",
        goodNum:125,
        badNum:5,
    },
    {
        nickname:"감자돌이",
        comment:"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
        date:"2020.02.05",
        goodNum:125,
        badNum:5,
    },
]

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

export default function Episode({ authenticated, logout }) {
    const classes = useStyles();
    const [state, setState] = React.useState({
        score: '',
    });
    const [value, setValue] = React.useState(0);    
    
    const handleChange = name => event => {
        setState({
            ...state,
            [name]: event.target.value,
        });
    };
    const tabChange = (event, newValue) => {
        setValue(newValue);
    };
    return (
        <div>
            <Header authenticated={authenticated} logout={logout} />

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

            <div style={{ borderTop: '1px solid grey', minHeight: 600, marginBottom:30}}>
                <div className={classes.title} style={{ display: "flex" }}>
                    <img src="http://placeimg.com/128/128/any" alt="thumbnail" style={{ margin: 10, height: 120, }} />
                    <div>
                        <h2>웹툰 제목 (작가)</h2>
                        <body1>줄거리저ㅜㄹ거리줄ㄹ거리줄럭리줄러길줄럭리줄러기</body1>
                    </div>
                </div>

                <div className={classes.body} style={{ width: 950, borderTop: '1px solid grey' }}>
                    <div style={{ marginLeft: 30 }}>
                        <h5 style={{ marginTop: 20, marginBottom: 0 }}>{episode.no}화 {episode.title}</h5>
                        <Box component="span" mb={0} borderColor="transparent" style={{ display: "flex", }}>
                            <Rating name="read-only" value={episode.starRating} readOnly style={{ marginTop: 30 }} />
                            <body2 style={{ marginTop: 30 }}>&nbsp;({episode.starRating})&ensp;</body2>
                            <FormControl className={classes.formControl}>
                                <InputLabel htmlFor="star-score">별점</InputLabel>
                                <Select
                                    native
                                    value={state.score}
                                    onChange={handleChange('score')}

                                >
                                    <option value={1}>1</option>
                                    <option value={2}>2</option>
                                    <option value={3}>3</option>
                                    <option value={4}>4</option>
                                    <option value={5}>5</option>
                                </Select>
                            </FormControl>
                            <Button color="primary" variant="contained" style={{ height: 30, marginTop: 20 }}>
                                <span style={{ color: "#fafafa", fontWeight: 400 }}>제출</span>
                            </Button>
                        </Box>
                    </div>
                    <div style={{ width: 950, borderTop: '1px solid grey', paddingTop: 40, paddingBottom: 40 }} align="center">
                        {episode.cuts.map(cut => (
                            <img src={cut.cut} alt="cut" />
                        ))}
                    </div>
                    <div style={{ width: 950, borderTop: '1px solid grey', borderBottom: '1px solid grey', paddingBottom: 20 }}>
                        <div style={{ marginLeft: 30 }}>
                            <h4>작가의 말</h4>
                            <body1>{episode.authorComment}</body1>
                        </div>
                    </div>
                    <div className={classes.comment}>
                        <h4>의견쓰기</h4>
                        <div style={{ display: "flex" }}>
                            <TextField
                                id="comment"
                                multiline
                                rows="4"
                                placeholder="의견을 작성해주세요"
                                variant="outlined"
                                style={{ width: 800 }}
                            />
                            <Button color="primary" variant="contained" style={{ height: 40, marginLeft: 10, marginTop: 70 }}>등록</Button>
                        </div>
                        <Paper elevation={3} style={{marginTop:30}}>
                            <AppBar position="static" color="inherit">
                                <Tabs value={value} onChange={tabChange} aria-label="commentTabLabel">
                                    <Tab label="베스트 댓글"  {...a11yProps(0)} />
                                    <Tab label="전체 댓글" {...a11yProps(1)} />
                                </Tabs>
                            </AppBar>

                            <TabPanel value={value} index={0}>
                                {comments.map(comment=>(
                                    <Comment nickname={comment.nickname} comment={comment.comment} date={comment.date} goodNum={comment.goodNum} badNum={comment.badNum}/>
                                ))}
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                전체댓글
                            </TabPanel>
                        </Paper>
                    </div>
                </div>
            </div>
        </div>
    )
}