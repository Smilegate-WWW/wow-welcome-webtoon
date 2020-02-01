import React,{useState} from 'react';
import { Redirect } from 'react-router-dom';
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

export default function Login({ authenticated, login, location }) {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    
    const classes = useStyles();

    const handleClick = () => {
        try {
          login({ id, pw });
        } catch (e) {
          alert('Failed to login');
          setId('');
          setPw('');
        }
    };

    //이전에 접근하려고 했던 페이지로 리다이렉션
    const { from } = location.state || { from: { pathname: "/" } };
    if (authenticated) return <Redirect to={from} />;

    return (
        <div className={classes.root}>
            <h1 style={{
                color: "#ff7043",
                fontSize: "64px",
            }}>&emsp;WWW</h1>


            <form className={classes.textField} noValidate autoComplete="off">
                <TextField 
                    id="userid" 
                    label="아이디를 입력해주세요" 
                    variant="outlined" 
                    value={id}
                    onChange={({target:{value} }) =>setId(value)}   
                />
                <TextField
                    id="pw"
                    label="비밀번호를 입력해주세요"
                    type="password"
                    autoComplete="current-password"
                    value={pw}
                    onChange={({ target: { value } }) => setPw(value)}
                    variant="outlined"
                />
            </form>

            <div className={classes.loginButton}>
                <Button variant="contained" color="primary" onClick={handleClick}>
                    <span style={{ color: "#fafafa", fontWeight: "bold" }}>로그인</span>
                </Button>
            </div>

            <div className={classes.signup}>
                <a href="/login/signup">회원가입</a>
            </div>
        </div>
    );
}