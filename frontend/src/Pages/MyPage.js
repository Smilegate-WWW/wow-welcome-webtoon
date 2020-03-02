import React from 'react';
import Header from '../Components/Header';
//버튼
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//링크 관련
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';
//내 작품 관련
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import MyWebtoon from '../Components/MyWebtoon';
import PlusWebtoon from '../Components/PlusWebtoon';
import Paper from '@material-ui/core/Paper';
//스위치
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';

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
            margin: theme.spacing(5, 0, 10, 14),
        },

    },
    linkMerge: {
        '& > *': {
            marginRight: theme.spacing(3),
        },
    },
    gridRoot: {
        '& > *': {
            marginLeft: theme.spacing(14),
        },
    },
    gridList: {
        width: 1200,
        height: 500,
        justify: "center",
    },
    alarm: {
        marginLeft: theme.spacing(135)
    },
}));

export default function MyPage() {
    const classes = useStyles();

    const [myWebtoons,setMyWebtoons]=React.useState([]);

    React.useEffect(() => {
        var myHeaders = new Headers();
        myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));
        myHeaders.append("Accept", "application/json");

        var requestOptions = {
            cache: "default",
            headers: myHeaders,
            method: 'GET',
            redirect: 'follow',
        };

        fetch("/myTitleList?page=1", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    setMyWebtoons(result.data.webtoonlist);
                }
                else {
                    alert("세션이 만료되었습니다.");
                    window.location.href = "/login";
                }
            })
            .catch(error => console.log('error', error));
    },[]);

    return (
        <div>
            <Header />

            <div className={classes.menu} style={{ display: "flex" }}>
                <div className={classes.button}>
                    <Button variant="contained" href="/">
                        <span style={{ color: "#212121", fontWeight: 520 }}>도전만화</span>
                    </Button>

                    <Button variant="contained" color="primary" href="/mypage">
                        <span style={{ color: "#fafafa", fontWeight: 550 }}>마이페이지</span>
                    </Button>
                </div>
                <div style={{ marginLeft: 980, mariginTop: 30 }}>
                    <a href="/mypage/editInfo"
                        style={{
                            color: "black",
                        }}>
                        회원정보수정
                         </a>
                    <FormControlLabel
                        value="alarm"
                        control={<Switch color="primary" />}
                        label={<img src="/Icon/alarm.png" alt="icon" />}
                        labelPlacement="start"
                    />
                </div>


            </div>

            <div className={classes.link}>
                <Typography className={classes.linkMerge}>
                    <Link href="/mypage" color="primary" style={{
                        fontWeight: 700, fontSize: "20px",
                    }}>
                        내 작품
                    </Link>

                    <Link href="/mypage/comment" color="inherit" style={{
                        fontWeight: 500, fontSize: "18px",
                    }}>
                        내 댓글
                    </Link>
                </Typography>
            </div>

            <div className={classes.gridRoot}>
                <Paper>
                    <GridList cellHeight={300} className={classes.gridList} spacing={15} cols={4}>
                        {myWebtoons.map(myWebtoon => (
                            <GridListTile key={myWebtoon.title} item="true">
                                <MyWebtoon idx={myWebtoon.idx} title={myWebtoon.title} thumbnail={myWebtoon.thumbnail} created_date={myWebtoon.created_date} last_updated={myWebtoon.last_updated} />
                            </GridListTile>
                        ))}
                        <PlusWebtoon href="/mypage/register" />
                    </GridList>
                </Paper>
            </div>


        </div>
    )
};