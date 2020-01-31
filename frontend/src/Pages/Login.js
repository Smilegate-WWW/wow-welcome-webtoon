import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
//아이디,비번 입력창
import TextField from '@material-ui/core/TextField';
//로그인 버튼
import Button from '@material-ui/core/Button';

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(10, 75),
    },
    textField: {
        '& > *': {
            margin: theme.spacing(1),
            width: 300,
        },
    },
    loginButton: {
        '& > *': {
            margin: theme.spacing(3, 1, 2, 1),
            width: 300,
            height: 40,
        },
    },
    signup:{
        
        '& > *': {
            margin: theme.spacing(0, 0, 0, 16),
        },
    },
}));

export default function Login() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <h1 style={{
                color: "#ff7043",
                fontSize: "64px",
            }}>&emsp;WWW</h1>


            <form className={classes.textField} noValidate autoComplete="off">
                <TextField id="outlined-basic" label="아이디를 입력해주세요" variant="outlined" />
                <TextField
                    id="outlined-password-input"
                    label="비밀번호를 입력해주세요"
                    type="password"
                    autoComplete="current-password"
                    variant="outlined"
                />
            </form>

            <div className={classes.loginButton}>
                <Button variant="contained" color="primary">
                    <span style={{ color: "#fafafa", fontWeight: "bold" }}>로그인</span>
                </Button>
            </div>

            <div className={classes.signup}>
                <a href="/login/signup">회원가입</a>
            </div>
        </div>
    );
}