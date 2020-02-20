import React, { Component } from 'react';
import Header from '../Components/Header';
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//테이블
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
//별점
import Box from '@material-ui/core/Box';
import Rating from '@material-ui/lab/Rating';

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
    table: {
        width: 1000,
    },
}));

const episodes = [
    {
        thumbnail: "http://placeimg.com/64/64/any",
        title: "11화. 은성이의 사랑",
        starRating: 5,
        updateDate: "2020.02.04",
    },
    {
        thumbnail: "http://placeimg.com/64/64/any",
        title: "11화. 은성이의 사랑",
        starRating: 5,
        updateDate: "2020.02.04",
    },
    {
        thumbnail: "http://placeimg.com/64/64/any",
        title: "11화. 은성이의 사랑",
        starRating: 5,
        updateDate: "2020.02.04",
    },
    {
        thumbnail: "http://placeimg.com/64/64/any",
        title: "11화. 은성이의 사랑",
        starRating: 5,
        updateDate: "2020.02.04",
    },
    {
        thumbnail: "http://placeimg.com/64/64/any",
        title: "11화. 은성이의 사랑",
        starRating: 5,
        updateDate: "2020.02.04",
    },
];

export default function Webtoon() {
    const classes = useStyles();
    return (
        <div>
            <Header/>

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

            <div style={{ border: '1px solid grey', minHeight: 600, }}>
                <div className={classes.title} style={{ display: "flex" }}>
                    <img src="http://placeimg.com/128/128/any" alt="thumbnail" style={{ margin: 10, height: 120, }} />
                    <div>
                        <h2>웹툰 제목 (작가)</h2>
                        <body1>줄거리저ㅜㄹ거리줄ㄹ거리줄럭리줄러길줄럭리줄러기</body1>
                    </div>
                </div>
                <TableContainer component={Paper} align="center">
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center">이미지</TableCell>
                                <TableCell align="center">회차</TableCell>
                                <TableCell align="center">별점</TableCell>
                                <TableCell align="center">등록일</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {episodes.map(episode => (
                                <TableRow key={episode.title}>
                                    <TableCell align="center">
                                        <img src={episode.thumbnail}/>
                                    </TableCell>
                                    <TableCell align="left">
                                        <a href="/webtoon/episode" style={{}}>
                                        {episode.title}
                                        </a>
                                    </TableCell>
                                    <TableCell align="center">
                                        <Box component="span" mb={0} borderColor="transparent">
                                            <Rating name="read-only" value={episode.starRating} readOnly />
                                        </Box>
                                    </TableCell>
                                    <TableCell align="center">{episode.updateDate}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </div>
    )
}