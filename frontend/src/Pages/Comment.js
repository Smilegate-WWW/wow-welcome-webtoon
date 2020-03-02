import React from 'react';
import Header from '../Components/Header';
//버튼
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//링크 관련
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';
//테이블 관련
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Checkbox from '@material-ui/core/Checkbox';

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
    link: {
        '& > *': {
            margin: theme.spacing(5, 0, 4, 14),
        },

    },
    linkMerge: {
        '& > *': {
            marginRight: theme.spacing(3),
        },
    },
    table: {
        width: 1300,
        marginBottom: 50
    },
    titleField: {
        maxWidth: 100,
    },
    commentField: {
        maxWidth: 600,
    },
    deleteButton: {
        '& > *': {
            marginTop: theme.spacing(2),
            marginLeft: theme.spacing(167),
        },
    },
}));

//comment 정보
let myComments = [];
let myComment_page=1;

/* 내 댓글 목록 조회 */
function MyCommentLoading() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

    var requestOptions = {
     method: 'GET',
      headers: myHeaders,
     redirect: 'follow'
    };

    //댓글 목록 페이지 추가 후 수정 필요!!!!!!!!!!!!!!
    fetch("/users/comments?page=1", requestOptions)
    .then(response => response.json())
     .then(result => {
        console.log(result)
        myComments=result.data.comments;
        console.log(myComments);
        myComment_page=result.data.total_pages;
        if(result.code==0){
        }
        else{
            alert("잘못된 접근입니다, 관리자에게 문의하세요.")
        }
    })
    .catch(error => console.log('error', error));
}


export default function Comment() {
    /* 댓글 로드 */
    React.useState(() => {
        MyCommentLoading();
    },[]);

    const classes = useStyles();
    const [checked, setChecked] = React.useState(true);

    const handleChange = event => {
        setChecked(event.target.checked);
    };

    /* 댓글 삭제 */
    const deleteComment = (idx) => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

        var raw = "";

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/comments/" + idx, requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }

    return (
        <div>
            <Header />

            <div className={classes.menu}>
                <div className={classes.button}>
                    <Button variant="contained" href="/">
                        <span style={{ color: "#212121", fontWeight: 520 }}>도전만화</span>
                    </Button>

                    <Button variant="contained" color="primary" href="/mypage">
                        <span style={{ color: "#fafafa", fontWeight: 550 }}>마이페이지</span>
                    </Button>
                </div>
            </div>

            <div className={classes.link}>
                <Typography className={classes.linkMerge}>
                    <Link href="/mypage" color="inherit" style={{
                        fontWeight: 500, fontSize: "18px",
                    }}>
                        내 작품
                    </Link>

                    <Link href="/mypage/comment" color="primary" style={{
                        fontWeight: 700, fontSize: "20px",
                    }}>
                        내 댓글
                    </Link>
                </Typography>
            </div>

            <div>
                <TableContainer component={Paper} backgroundColor>
                    <Table className={classes.table} aria-label="simple table" align="center">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center">이미지</TableCell>
                                <TableCell align="center">제목</TableCell>
                                <TableCell align="center">회차</TableCell>
                                <TableCell align="center">내 댓글</TableCell>
                                <TableCell align="center">좋아요</TableCell>
                                <TableCell align="center">싫어요</TableCell>
                                <TableCell align="center">등록일</TableCell>
                                <TableCell align="center">삭제</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {myComments.map(myComment => (
                                <TableRow key={myComment.content}>
                                    <TableCell align="center">{myComment.webtoon_thumbnail}</TableCell>
                                    <TableCell align="center">
                                        <div className={classes.titleField}>
                                            {myComment.webtoon_title}
                                        </div>
                                    </TableCell>
                                    <TableCell align="center">{myComment.ep_no}</TableCell>
                                    <TableCell align="center">
                                        <div className={classes.commentField}>
                                            {myComment.content}
                                        </div>
                                    </TableCell>
                                    <TableCell align="center">{myComment.like_cnt}</TableCell>
                                    <TableCell align="center">{myComment.dislike_cnt}</TableCell>
                                    <TableCell align="center">{myComment.created_date}</TableCell>
                                    <TableCell align="center">
                                        <Button variant="contained" color="primary" onClick={()=>deleteComment(myComment.idx)} >
                                            삭제
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </div>
    )
};