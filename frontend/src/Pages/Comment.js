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
const myComments = [
    {
        image: <img src="http://placeimg.com/64/64/any" />,
        title: "유미의 세포들",
        no: "12화",
        comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
        good_cnt: 12,
        bad_cnt: 3,
        register_date: "2020.02.08",
    },
    {
        image: <img src="http://placeimg.com/64/64/any" />,
        title: "감자들의 감자 심는 이야기~~~",
        no: "12화",
        comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
        good_cnt: 12,
        bad_cnt: 3,
        register_date: "2020.02.08",
    },
    {
        image: <img src="http://placeimg.com/64/64/any" />,
        title: "유미의 세포들",
        no: "12화",
        comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
        good_cnt: 12,
        bad_cnt: 3,
        register_date: "2020.02.08",
    },
    {
        image: <img src="http://placeimg.com/64/64/any" />,
        title: "유미의 세포들",
        no: "12화",
        comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
        good_cnt: 12,
        bad_cnt: 3,
        register_date: "2020.02.08",
    },
    {
        image: <img src="http://placeimg.com/64/64/any" />,
        title: "복학왕",
        no: "12화",
        comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
        good_cnt: 12,
        bad_cnt: 3,
        register_date: "2020.02.08",
    },
];


export default function Comment() {
    const classes = useStyles();
    const [checked, setChecked] = React.useState(true);

    const handleChange = event => {
        setChecked(event.target.checked);
    };

    const deleteComment = () => {
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

        fetch("/comments/22", requestOptions)
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
                                <TableRow key={myComment.comment}>
                                    <TableCell align="center">{myComment.image}</TableCell>
                                    <TableCell align="center">
                                        <div className={classes.titleField}>
                                            {myComment.title}
                                        </div>
                                    </TableCell>
                                    <TableCell align="center">{myComment.no}</TableCell>
                                    <TableCell align="center">
                                        <div className={classes.commentField}>
                                            {myComment.comment}
                                        </div>
                                    </TableCell>
                                    <TableCell align="center">{myComment.good_cnt}</TableCell>
                                    <TableCell align="center">{myComment.bad_cnt}</TableCell>
                                    <TableCell align="center">{myComment.register_date}</TableCell>
                                    <TableCell align="center">
                                        <Button variant="contained" color="primary" onClick={deleteComment} >
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