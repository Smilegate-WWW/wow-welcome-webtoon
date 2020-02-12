import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
//아이디,비번 입력창
import TextField from '@material-ui/core/TextField';
//로그인 버튼
import Button from '@material-ui/core/Button';
// 성별 선택
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(8, 75),
    },
    textField: {
        '& > *': {
            margin: theme.spacing(1),
            width: 300,
        },
    },
    smallTextField: {
        margin: theme.spacing(1),
        width: 142,
    },
    display: {
        display: "flex",
    },
    loginButton: {
        '& > *': {
            margin: theme.spacing(1, 1, 2, 1),
            width: 300,
            height: 40,
        },
    },
}));

var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");

var raw = JSON.stringify({"userid":"exampleID","pw":"password","name":"백지수","birth":"1997-07-02","gender":1,"email":"bjisoo72@gmail.com"});

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: raw,
  redirect: 'follow'
};

fetch("localhost:8080/users", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));

export default function Signup() {
    const classes = useStyles();

    const [userId, setUserId] = useState('');
    const [pw,setPw] = useState('');
    const [pwCheck,setPwCheck] = useState('');
    const [name,setName]=useState('');
    const [gender, setGender] = React.useState('');
    const [birth,setBirth]=useState('');
    const [email,setEmail]=useState('');
    
    const handleIdChange = (e) => {
        setUserId(e.target.value);
    }
    const handlePwChange = (e) => {
        setPw(e.target.value);
    }
    const handlePwCheckChange = (e) => {
        setPwCheck(e.target.value);
    }
    const handleNameChange = (e) => {
        setName(e.target.value);
    }
    const handleGenderChange = (e) => {
        setGender(e.target.value);
    }
    const handleBirthChange = (e) => {
        setBirth(e.target.value);
    }
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    }

    const handleSubmit = () => {
        if(userId==='' || pw==='' || pwCheck==='' || name==='' || gender==='' || birth==='' || email===''){
            alert("정보를 모두 입력해주세요!!");
        }
        else if(pw!==pwCheck){
            alert("비밀번호가 일치하지 않습니다!!");
        }

        else{
        const axios = require('axios');
        const FormData = require('form-data');

        const form_data = new FormData();
        form_data.append('userid', userId);
        form_data.append('pw', pw);
        form_data.append('name',name);
        form_data.append('birth', birth);
        form_data.append('gender', gender);
        form_data.append('email', email);
        
        let url = 'localhost:8080/users';
        axios.post(url, form_data)
            .then(res => {
              console.log(res.data);
            })
            .catch(err => console.log(err))

        }
    }

    const inputLabel = React.useRef(null);
    const [labelWidth, setLabelWidth] = React.useState(0);
    React.useEffect(() => {
        setLabelWidth(inputLabel.current.offsetWidth);
    }, []);

    return (
        <div className={classes.root}>
            <span style={{
                color: "#ff7043",
                fontSize: "64px",
                fontWeight: 700,
            }}>&emsp;WWW</span>

            <form className={classes.textField} noValidate autoComplete="off">
                <TextField id="userid" label="아이디" value={userId} onChange={handleIdChange} variant="outlined" />
                <TextField
                    id="pw"
                    label="비밀번호"
                    value={pw}
                    onChange={handlePwChange}
                    type="password"
                    variant="outlined"
                />
                <TextField
                    id="pwCheck"
                    label="비밀번호 재입력"
                    value={pwCheck} 
                    onChange={handlePwCheckChange}
                    type="password"
                    variant="outlined"
                />
            </form>

            <div className={classes.display}>
                <form className={classes.smallTextField} noValidate autoComplete="off">
                    <TextField id="name" label="이름" value={name} onChange={handleNameChange} variant="outlined" />
                </form>

                <FormControl variant="outlined" className={classes.smallTextField}>

                    <InputLabel ref={inputLabel} id="gender_label">성별</InputLabel>
                    <Select
                        labelId="gender_label"
                        id="gender"
                        value={gender}
                        onChange={handleGenderChange}
                        labelWidth={labelWidth}
                    >
                        <MenuItem value={0}>남</MenuItem>
                        <MenuItem value={1}>여</MenuItem>
                    </Select>
                </FormControl>
            </div>

            <form className={classes.textField} noValidate autoComplete="off">
                <TextField
                    id="birth"
                    label="생년월일"
                    type="date"
                    defaultValue="2000-01-01"
                    value={birth} 
                    onChange={handleBirthChange}
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField id="email" label="이메일" value={email} onChange={handleEmailChange}variant="outlined" />
            </form>

            <div className={classes.loginButton}>
                <Button variant="contained" color="primary" onClick={handleSubmit}>
                    <span style={{ color: "#fafafa", fontWeight: "bold" }}>회원가입</span>
                </Button>
            </div>
        </div>
    );
}