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

//주소 파싱하여 idx 알아오기
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(window.location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

var idx = getParameterByName('idx');
var ep_no = getParameterByName('ep_no');
var ep_idx=getParameterByName('ep_idx');

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
let best_comments = []
let comment_page = 1;

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
    fetch("/episodes/" + ep_idx + "/comments?page=1", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if (result.code == 0) {
                comments = result.data.comments;
                console.log(comments);
                comment_page = result.data.total_pages;
            }
            else if (result.code == 20) {
                alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
            else if (result.code == 23) {
                alert("[ERROR 23] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
            /*
            else if(result.code ==44){
                //access token 재발급 api 접근
                //재발급 성공시 페이지 재 로딩
                //재발급 실패시 로그인 만료 -> direct "/login"
            }
            */
            else {
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

    fetch("/episodes/" + ep_idx + "/comments/best", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if (result.code == 0) {
                best_comments = result.data;
                console.log(best_comments);
            }
            else if (result.code == 20) {
                alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
        })
        .catch(error => console.log('error', error));
}

export default function MyEpisode() {
    const [contents, setContents] = React.useState([]);
    const [thumbnail, setThumbnail] = React.useState("");
    const [title, setTitle] = React.useState("");
    const [author, setAuthor] = React.useState("");
    const [summary, setSummary] = React.useState("");
    const [rating_avg, setRating_avg] = React.useState("");

    React.useEffect(() => {
        // 회차 정보
        var myHeaders = new Headers();
        myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

        var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("/mydetail/" + idx + "/" + ep_no, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                setTitle(result.data.title)
                setAuthor(result.data.author)
                setSummary(result.data.summary)
                setThumbnail(result.data.thumbnail)
                setRating_avg(result.data.rating_avg)
                setContents(result.data.contents)

            })
            .catch(error => console.log('error', error));
        commentLoading();
        bestCommentLoading();
    }, []);

    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    const tabChange = (event, newValue) => {
        setValue(newValue);
    };

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
                    <img src={thumbnail} alt="thumbnail" style={{ margin: 10, height: 120, }} width="128" height="128" />
                    <div>
                        <h2>{title} ({author})</h2>
                        <body1>{summary}</body1>
                    </div>
                </div>

                <div className={classes.body} style={{ width: 950, borderTop: '1px solid grey' }}>
                    <div style={{ marginLeft: 30 }}>
                        <h4 style={{ marginTop: 20, marginBottom: 0 }}>{ep_no}화. {title}</h4>
                        <Box component="span" mb={0} borderColor="transparent" style={{ display: "flex", }}>
                            <Rating name="read-only" value={rating_avg} readOnly style={{ marginTop: 30 }} />
                            <body2 style={{ marginTop: 30 }}>&nbsp;({rating_avg})&ensp;</body2>
                        </Box>
                    </div>
                    <div style={{ width: 950, borderTop: '1px solid grey', paddingTop: 40, paddingBottom: 40 }} align="center">
                        {contents.map(content => (
                            <img src={content} alt="cut" key={content} />
                        ))}
                    </div>
                    <div style={{ width: 950, borderTop: '1px solid grey', borderBottom: '1px solid grey', paddingBottom: 20 }}>
                        <div style={{ marginLeft: 30 }}>
                            <h4>작가의 말</h4>
                            <body1>....</body1>
                        </div>
                    </div>
                    <div className={classes.comment}>
                        <Paper elevation={3} style={{ marginTop: 30 }}>
                            <AppBar position="static" color="inherit">
                                <Tabs value={value} onChange={tabChange} aria-label="commentTabLabel">
                                    <Tab label="베스트 댓글"  {...a11yProps(0)} />
                                    <Tab label="전체 댓글" {...a11yProps(1)} />
                                </Tabs>
                            </AppBar>

                            <TabPanel value={value} index={0}>
                                {best_comments.map(comment => (
                                    <Comment key={comment.idx} cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                {comments.map(comment => (
                                    <Comment key={comment.idx} cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                            </TabPanel>
                        </Paper>
                    </div>
                </div>
            </div>
        </div>
    )
}