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
    ep_idx: 3,
    no: 1,
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
let comments = [
    {
        nickname: "감자돌이",
        comment: "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
        date: "2020.02.05",
        goodNum: 125,
        badNum: 5,
    }
]
let best_comments =[]
let comment_page=1;

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

function commentLoading() {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    //url 수정
    fetch("/episodes/"+episode.ep_idx+"/comments?page=1", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if(result.code==0){
                comments=result.data.comments;
                console.log(comments);
                comment_page = result.data.total_pages;
            }
            else if (result.code==20){
                alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
            else if (result.code ==23){
                alert("[ERROR 23] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
            /*
            else if(result.code ==44){
                //access token 재발급 api 접근
                //재발급 성공시 페이지 재 로딩
                //재발급 실패시 로그인 만료 -> direct "/login"
            }
            */
            else{
                alert("잘못된 접근입니다, 관리자에게 문의하세요.");
            }
        })
        .catch(error => console.log('error', error));
}

function bestCommentLoading() {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("/episodes/"+episode.ep_idx+"/comments/best", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if(result.code==0){
                best_comments=result.data;
                console.log(best_comments);
            }
            else if (result.code==20){
                alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
        })
        .catch(error => console.log('error', error));
}

export default function Episode() {

    React.useEffect(()=> {
        commentLoading();
        bestCommentLoading();
    },[]);

    const classes = useStyles();
    const [comment, setComment] = React.useState("");
    const [score, setScore] = React.useState("5");
    const [value, setValue] = React.useState(0);

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    }
    const handleScoreChange = (event) => {
        setScore(event.target.value);
    };
    const tabChange = (event, newValue) => {
        setValue(newValue);
    };
    const submitComment = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

        var raw = JSON.stringify({ "content": "댓글 내용" });

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/episodes/"+episode.ep_idx+"/comments", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }

    const handleRating = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("AUTHORIZATION",  localStorage.getItem("AUTHORIZATION"));

        var raw = JSON.stringify({ "rating": score});
        console.log(score);

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/episodes/"+episode.ep_idx+"/rating", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }

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

            <div style={{ borderTop: '1px solid grey', minHeight: 600, marginBottom: 30 }}>
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
                                    value={score}
                                    onChange={handleScoreChange}>
                                    <option value={5}>5</option>
                                    <option value={4}>4</option>
                                    <option value={3}>3</option>
                                    <option value={2}>2</option>
                                    <option value={1}>1</option>
                                </Select>
                            </FormControl>
                            <Button color="primary" variant="contained" onClick={handleRating} style={{ height: 30, marginTop: 20 }}>
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
                                value={comment}
                                onChange={handleCommentChange}
                                variant="outlined"
                                style={{ width: 800 }}
                            />
                            <Button color="primary" variant="contained" onClick={submitComment} style={{ height: 40, marginLeft: 10, marginTop: 70 }}>등록</Button>
                        </div>
                        <Paper elevation={3} style={{ marginTop: 30 }}>
                            <AppBar position="static" color="inherit">
                                <Tabs value={value} onChange={tabChange} aria-label="commentTabLabel">
                                    <Tab label="베스트 댓글"  {...a11yProps(0)} />
                                    <Tab label="전체 댓글" {...a11yProps(1)} />
                                </Tabs>
                            </AppBar>

                            <TabPanel value={value} index={0}>
                                {best_comments.map(comment => (
                                    <Comment cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                {comments.map(comment => (
                                    <Comment cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                            </TabPanel>
                        </Paper>
                    </div>
                </div>
            </div>
        </div>
    )
}