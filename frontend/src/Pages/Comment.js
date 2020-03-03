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
// 토큰 재발급
var ReToken = require("../AuthRoute");

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

export default function Comment() {
    const [myComments,setMyComments]=React.useState([]);
    //const [renderFlag,setRenderFlag]=React.useState(0);

    React.useEffect(() => {
        loadMyComments();
    },[]);
    
    const classes = useStyles();
    const [checked, setChecked] = React.useState(true);

    const handleChange = event => {
        setChecked(event.target.checked);
    };

    /* 내 댓글 목록 조회 */
    const loadMyComments = () => {

        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));
            
       var requestOptions = {
          method: 'GET',
          headers: myHeaders,
          redirect: 'follow'
        };
    
        fetch("/users/comments?page=1", requestOptions)
          .then(response => response.json())
          .then(result => {
              console.log(result)
              if(result.code==0){
                setMyComments(result.data.comments);
              }
              if(result.code==23){
                  alert("[ERROR 23] 유효하지 않은 페이지 요청 입니다.")
              }
              else if(result.code==42){
                alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
            }
            else if(result.code==44){
                ReToken.ReToken()
            }
        })
          .catch(error => console.log('error', error));
    }

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

        console.log("/comments/"+idx);
        fetch("/comments/" + idx, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if(result.code==0){
                    loadMyComments()
                }
                else if(result.code==21){
                    alert("존재하지 않는 댓글입니다.")
                }
                else if(result.code==22){
                    alert("이 댓글의 작성자가 아닙니다.")
                }
                else if(result.code==42){
                    alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else if(result.code==44){
                    ReToken.ReToken()
                }
            })
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
                                <TableRow key={myComment.idx}>
                                    <TableCell align="center"><img src={myComment.webtoon_thumbnail}/></TableCell>
                                    <TableCell align="center">
                                        <div className={classes.titleField}>
                                            {myComment.webtoon_title}
                                        </div>
                                    </TableCell>
                                    <TableCell align="center">{myComment.ep_no}화</TableCell>
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