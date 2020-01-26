import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Checkbox from '@material-ui/core/Checkbox';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles(theme=>({
  table: {
    minWidth: 650,
  },
  titleField: {
    maxWidth: 100,
  },
  commentField: {
    maxWidth: 1000,
  },
  root: {
    '& > *': {
      marginTop:theme.spacing(3),
      marginLeft: theme.spacing(173),
    },
  },
}));

const myComments = [
  {
    image: <img src="http://placeimg.com/64/64/any" />,
    title: "유미의 세포들",
    no: "12화",
    comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
  },
  {
    image: <img src="http://placeimg.com/64/64/any" />,
    title: "감자들의 감자 심는 이야기~~~",
    no: "12화",
    comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
  },
  {
    image: <img src="http://placeimg.com/64/64/any" />,
    title: "유미의 세포들",
    no: "12화",
    comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
  },
  {
    image: <img src="http://placeimg.com/64/64/any" />,
    title: "유미의 세포들",
    no: "12화",
    comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
  },
  {
    image: <img src="http://placeimg.com/64/64/any" />,
    title: "복학왕",
    no: "12화",
    comment: "풀이 없으면 인간은 사막이다 오아이스도 없는 사막이다 보이는 끝까지 찾아다녀도 목숨이 있는 때까지 방황하여도 보이는 것은 거친 모래뿐일 것이다 이상의 꽃이 없으면 쓸쓸한 인간에 남는 것은 영락과 부패 뿐이다 낙원을 장식하는 천자만홍이",
  }
];

export default function CommentPage() {
  const classes = useStyles();

  const [checked, setChecked] = React.useState(true);
  const handleChange = event => {
    setChecked(event.target.checked);
  };

  return (
    <div>
      <TableContainer component={Paper} backgroundColor>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">이미지</TableCell>
              <TableCell align="center">제목</TableCell>
              <TableCell align="center">회차</TableCell>
              <TableCell align="center">내 댓글</TableCell>
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
                <TableCell align="center">
                  <div>
                    <Checkbox value="uncontrolled" color="primary" inputProps={{ 'aria-label': 'uncontrolled-checkbox' }} />
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div className={classes.root}>
        <Button variant="contained" color="primary" >
          삭제
      </Button>
      </div>
    </div>
  );
}