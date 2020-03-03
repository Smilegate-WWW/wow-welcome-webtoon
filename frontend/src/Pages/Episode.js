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
//paging
import Pagination from '@material-ui/lab/Pagination';
// 토큰 재발급
var ReToken = require("../AuthRoute");
import { render } from '@testing-library/react';

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
    paging: {
        '& > *': {
            marginTop: theme.spacing(2),
        },
    }
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
var ep_idx = getParameterByName('ep_idx');

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

export default function Episode() {

    const [contents, setContents] = React.useState([]);
    const [thumbnail, setThumbnail] = React.useState("");
    const [title, setTitle] = React.useState("");
    const [author, setAuthor] = React.useState("");
    const [summary, setSummary] = React.useState("");
    const [rating_avg, setRating_avg] = React.useState("");
    const [webtoon_title, setWebtoon_title] = React.useState("");
    const [author_comment, setAuthor_comment] = React.useState("");
       
    //댓글 관련
    const [comments,setComments]=React.useState([]);
    const [bestComments,setBestComments]=React.useState([]);
    const [cmt_page,setCmtPage]=React.useState(1);

    //first rendering
    React.useEffect(() => {
        // 회차 정보
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("/detail/" + idx + "/" + ep_no, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                setTitle(result.data.title)
                setAuthor(result.data.author)
                setSummary(result.data.summary)
                setThumbnail(result.data.thumbnail)
                setRating_avg(result.data.rating_avg)
                setContents(result.data.contents)
                setAuthor_comment(result.data.author_comment)
                setWebtoon_title(result.data.webtoon_title)
            })
            .catch(error => console.log('error', error));
        // 댓글 로드
        loadComment(1);

        // 베스트 댓글 로드
        fetch("/episodes/" + ep_idx + "/comments/best", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if (result.code == 0) {
                setBestComments(result.data);
            }
            else if (result.code == 20) {
                alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
            }
        })
        .catch(error => console.log('error', error));       
    }, []);

    const classes = useStyles();
    const [comment, setComment] = React.useState("");
    const [score, setScore] = React.useState("5");
    const [value, setValue] = React.useState(0);

    const handlePaging = (event, value) => {
        loadComment(value);
    };
    const handleCommentChange = (e) => {
        setComment(e.target.value);
    }
    const handleScoreChange = (event) => {
        setScore(event.target.value);
    };
    const tabChange = (event, newValue) => {
        setValue(newValue);
    };

    /* 댓글 조회 */
    const loadComment = (page) => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };
    
        fetch("/episodes/" + ep_idx + "/comments?page=" + page, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    setComments(result.data.comments);
                    setCmtPage(result.data.total_pages);
                }
                else if (result.code == 20) {
                    alert("[ERROR 20] 잘못된 접근입니다, 관리자에게 문의하세요.");
                }
                else if (result.code == 23) {
                    alert("[ERROR 23] 잘못된 접근입니다, 관리자에게 문의하세요.");
                }
                else {
                    alert("잘못된 접근입니다, 관리자에게 문의하세요.");
                }
            })
            .catch(error => console.log('error', error));
    }

    /* 댓글 등록 */
    // 로그인 체크 필요 + token 유효시간 체크 필요
    const submitComment = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

        if(comment.length!=0){
        var raw = JSON.stringify({ "content": comment });

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/episodes/" + ep_idx + "/comments", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    alert("댓글 등록이 완료되었습니다.")
                    setComment("")
                    loadComment(1)
                }
                else if(result.code==27){
                    alert("댓글 글자수 200자 제한을 초과하였습니다.")
                }
                else if(result.code==44){
                    ReToken.ReToken()
                }
                else if(result.code==42){
                    alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else{
                    alert("잘못된 접근입니다, 관리자에게 문의하세요.")
                }
            })
            .catch(error => console.log('error', error));
        }
        else{
            alert("댓글 내용을 입력해주세요.");
        }
    }

    /* 별점 주기 */
    // 로그인 체크 필요 + token 유효시간 체크 필요
    const handleRating = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

        var raw = JSON.stringify({ "rating": score });
        console.log(score);

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/episodes/"+ep_idx+"/rating", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if(result.code==0){
                    alert("별점 등록이 완료되었습니다.");
                }
                else if(result.code==20){
                    alert("존재하지 않는 회차입니다. 관리자에게 문의하세요.");
                }
                else if(result.code==26){
                    alert("이미 별점 주기에 참여하셨습니다.")
                }
                else if(result.code==44){
                    ReToken.ReToken()
                }
                else if(result.code==42){
                    alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
            })
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
                    <img src={thumbnail} alt="thumbnail img" style={{ margin: 10, height: 120, }} width="128" height="128" />
                    <div>
                        <h2>{webtoon_title} ({author})</h2>
                        <body1>{summary}</body1>
                    </div>
                </div>

                <div className={classes.body} style={{ width: 950, borderTop: '1px solid grey' }}>
                    <div style={{ marginLeft: 30 }}>
                        <h5 style={{ marginTop: 20, marginBottom: 0 }}>{ep_no}화 {title}</h5>
                        <Box component="span" mb={0} borderColor="transparent" style={{ display: "flex", }}>
                            <Rating name="read-only" value={rating_avg} readOnly style={{ marginTop: 30 }} />
                            <body2 style={{ marginTop: 30 }}>&nbsp;({rating_avg})&ensp;</body2>
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
                        {contents.map(content => (
                            <img src={content} alt="webtoon img" key={content}/>
                        ))}
                    </div>
                    <div style={{ width: 950, borderTop: '1px solid grey', borderBottom: '1px solid grey', paddingBottom: 20 }}>
                        <div style={{ marginLeft: 30 }}>
                            <h4>작가의 말</h4>
                            <body1>{author_comment}</body1>
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
                        <Paper elevation={3} style={{ marginTop: 30, paddingBottom: 5}}>
                            <AppBar position="static" color="inherit">
                                <Tabs value={value} onChange={tabChange} aria-label="commentTabLabel">
                                    <Tab label="베스트 댓글"  {...a11yProps(0)} />
                                    <Tab label="전체 댓글" {...a11yProps(1)} />
                                </Tabs>
                            </AppBar>

                            <TabPanel value={value} index={0}>
                                {bestComments.map(comment => (
                                    <Comment cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                {comments.map(comment => (
                                    <Comment key={comment.idx} cmt_idx={comment.idx} nickname={comment.user_id} comment={comment.content} date={comment.created_date} goodNum={comment.like_cnt} badNum={comment.dislike_cnt} />
                                ))}
                                <div className={classes.paging}>
                                    <Pagination count={cmt_page} color="primary" onChange={handlePaging}/>
                                </div>
                            </TabPanel>

                        </Paper>
                    </div>
                </div>
            </div>
        </div>
    )
}