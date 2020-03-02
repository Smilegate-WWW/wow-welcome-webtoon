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

//주소 파싱하여 idx 알아오기
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(window.location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

var idx = getParameterByName('idx');

export default function Webtoon() {
    const [episodes, setEpisodes] = React.useState([]);
    const [webtoon_thumbnail, setWebtoon_thumbnail] = React.useState("");
    const [title, setTitle] = React.useState("");
    const [writer, setWriter] = React.useState("");
    const [plot, setPlot] = React.useState("");

    const classes = useStyles();

    React.useEffect(() => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("/episode/" + idx, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                setEpisodes(result.data.episodelist)
                setWebtoon_thumbnail(result.data.webtoon_thumbnail)
                setTitle(result.data.title)
                setWriter(result.data.writer)
                setPlot(result.data.plot)
            })
            .catch(error => console.log('error', error));
    }, []);

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

            <div style={{ border: '1px solid grey', minHeight: 600, }}>
                <div className={classes.title} style={{ display: "flex" }}>
                    <img src={webtoon_thumbnail} alt="thumbnail" style={{ margin: 10, height: 120, }} />
                    <div>
                        <h2>{title} ({writer})</h2>
                        <span>{plot}</span>
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
                                <TableRow key={episode.idx}>
                                    <TableCell align="center">
                                        <img src={episode.thumbnail} width="64" height="64"/>
                                    </TableCell>
                                    <TableCell align="left">
                                        <a href={"/webtoon/episode?idx="+idx+"&ep_no="+episode.ep_no+"&ep_idx="+episode.idx}>
                                            {episode.ep_no}화. {episode.title}
                                        </a>
                                    </TableCell>
                                    <TableCell align="center">
                                        <Box component="span" mb={0} borderColor="transparent">
                                            <Rating name="read-only" value={episode.starRating} readOnly />
                                        </Box>
                                    </TableCell>
                                    <TableCell align="center">{episode.created_date.slice(0, 10)}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </div>
    )
}