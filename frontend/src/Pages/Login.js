import React, { useState } from 'react';
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
    signup: {

        '& > *': {
            margin: theme.spacing(0, 0, 0, 16),
        },
    },
}));

export default function Login() {
    const [userid, setUserid] = useState('');
    const [pw, setPw] = useState('');

    const classes = useStyles();

    const handleClick = () => {
        if (userid === '' || pw === '') {
            alert("아이디와 비밀번호를 모두 입력하세요");
        }
        else {

            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({ "userid": userid, "pw": pw });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/users/token", requestOptions)
                .then(response => {
                    console.log(response);
                    response.json().then(
                        result => {
                            console.log(result);
                            if (result.code == 0) {
                                localStorage.setItem("AUTHORIZATION", response.headers.get('Authorization'))
                                var temp = localStorage.getItem("AUTHORIZATION")
                                var jwt_decode = require('jwt-decode')
                                var decodeToken = jwt_decode(temp.replace("bearer ", ""))
                                localStorage.setItem("USER_IDX", decodeToken.user_idx)

                                localStorage.setItem("USERID", result.data.userid);
                                localStorage.setItem("NAME", result.data.name);
                                localStorage.setItem("BIRTH", result.data.birth);
                                localStorage.setItem("GENDER", result.data.gender);
                                localStorage.setItem("EMAIL", result.data.email);
                                localStorage.setItem("REFRESHTOKEN", result.data.token);
                                
                                if (window.history.state == null) {
                                    window.location.href = "/"
                                }
                                else {
                                    const history = (window.history.state.state.from.pathname);
                                    window.location.href = history;
                                }
                                
                            }

                            else if (result.code == 2) {
                                alert("아이디, 비밀번호가 일치하지 않습니다!")
                            }
                            else if(result.code==42){
                                alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                            }
                            else{
                                alert("잘못된 접근입니다, 관리자에게 문의하세요.")
                            }
                        }
                    )
                })
                .catch(error => console.log('error', error))


        }
    };

    return (
        <div className={classes.root}>
            <h1
                style={{
                    color: "#ff7043",
                    fontSize: "64px",
                }}>&emsp;WWW</h1>


            <form className={classes.textField} noValidate autoComplete="off">
                <TextField
                    id="userid"
                    label="아이디를 입력해주세요"
                    variant="outlined"
                    value={userid}
                    onChange={({ target: { value } }) => setUserid(value)}
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