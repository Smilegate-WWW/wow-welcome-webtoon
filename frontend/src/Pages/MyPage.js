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

//내 작품 목록
const MyWebtoons = [
    {
        title: "유미의 세포들",
        poster: "http://placeimg.com/128/128/any",
        artist: "이동건",
        rating: 4,
        register_date: "2020.01.10",
        update_date: "2020.02.10",
    },
    {
        title: "복학왕",
        poster: "http://placeimg.com/128/128/any",
        artist: "기안84",
        rating: 3,
        register_date: "2020.01.10",
        update_date: "2020.02.10",
    },
    {
        title: "여신강림",
        poster: "http://placeimg.com/128/128/any",
        artist: "냥",
        rating: 4,
        register_date: "2020.01.10",
        update_date: "2020.02.10",
    }
]

function getWebtoon() {

    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("/myTitleList?page=1", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));


}

export default function MyPage() {
    const classes = useStyles();

    getWebtoon();

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
                        {MyWebtoons.map(myWebtoon => (
                            <GridListTile key={myWebtoon} item>
                                <MyWebtoon title={myWebtoon.title} poster={myWebtoon.poster} artist={myWebtoon.artist} rating={myWebtoon.rating} register_date={myWebtoon.register_date} update_date={myWebtoon.update_date} />
                            </GridListTile>
                        ))}
                        <PlusWebtoon href="/mypage/register" />
                    </GridList>
                </Paper>
            </div>

        </div>
    )
};