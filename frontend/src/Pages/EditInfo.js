import React, { useState } from 'react';
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
            margin: theme.spacing(1, 1, 1, 1),
            width: 300,
            height: 40,
        },
    },
}));

function checkPw(pw) {
    var pattern_spc = /[#$%^&*()_+|<>?:{}]/; //특수 문자
    if (pattern_spc.test(pw)) {
        return true;
    }
}


export default function EditInfo() {
    const classes = useStyles();

    const [pw, setPw] = useState("");
    const [pwCheck, setPwCheck] = useState("");
    const [name, setName] = useState("");
    const [gender, setGender] = React.useState("");
    const [birth, setBirth] = useState("");

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

    const handleSubmit = () => {
        console.log(pw, pwCheck, name, gender, birth);
        if (pw === '' || pwCheck === '' || name === '' || gender === '' || birth === '') {
            alert("정보를 모두 입력해주세요!!");
            console.log(pw); console.log(pwCheck); console.log(name); console.log(gender);
        }
        else if (pw !== pwCheck) {
            alert("비밀번호가 일치하지 않습니다!!");
        }
        else if (pw.length < 8) {
            alert("비밀번호는 8자리 이상으로 설정해주세요!")
        }
        else if (checkPw(pw)) {
            alert("비밀번호에 가능한 특수문자는 ~!@ 입니다");
        }
        else {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
            myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

            var raw = JSON.stringify({ "pw": pw, "name": name, "birth": birth, "gender": gender });

            var requestOptions = {
                method: 'PUT',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            var user_idx = localStorage.getItem("USER_IDX")

            fetch("/users/" + user_idx, requestOptions)
                .then(response => response.json())
                .then(result => {
                    console.log(result)
                    if (result.code == 0) {
                        alert("재로그인 해주세요")
                        window.location.href = "/login";
                    }
                    else {
                        alert("실패했습니다. 다시 로그인 후 이용해주세요.");
                    }
                })
                .catch(error => console.log('error', error));
        }
    }

    const handleOut = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

        var raw = "";

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        var user_idx = localStorage.getItem("USER_IDX")
        
        fetch("/users/"+user_idx, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if(result.code==0){
                    alert("회원 탈퇴가 성공적으로 마무리되었습니다.")
                    localStorage.clear();
                    window.location.href="/";
                }
                else {
                    alert("실패했습니다. 다시 로그인 후 이용해주세요.");
                }
            })
            .catch(error => console.log('error', error));
    }

    const inputLabel = React.useRef(null);
    const [labelWidth, setLabelWidth] = React.useState(0);
    React.useEffect(() => {
        setLabelWidth(inputLabel.current.offsetWidth);
    }, []);

    return (
        <div className={classes.root}>
            <div
                style={{
                    color: "#ff7043",
                    fontSize: "64px",
                    fontWeight: 700,
                    cursor: "hand",
                }}
            >&emsp;WWW</div>

            <form className={classes.textField} noValidate autoComplete="off">
                <TextField
                    disabled
                    id="userid"
                    label="아이디"
                    defaultValue={localStorage.getItem("USERID")}
                    variant="outlined"
                />
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
                    <TextField
                        id="name"
                        label="이름"
                        value={name}
                        onChange={handleNameChange}
                        variant="outlined"
                        placeholder={localStorage.getItem("NAME")}
                    />
                </form>

                <FormControl variant="outlined" className={classes.smallTextField}>

                    <InputLabel ref={inputLabel} id="gender_label">성별</InputLabel>
                    <Select
                        labelId="gender_label"
                        id="gender"
                        value={gender}
                        onChange={handleGenderChange}
                        labelWidth={labelWidth}
                        placeholder={localStorage.getItem("GENDER")}
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
                    value={birth}
                    onChange={handleBirthChange}
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField
                    disabled
                    id="email"
                    label="이메일"
                    defaultValue={localStorage.getItem("EMAIL")}
                    variant="outlined" />
            </form>

            <div className={classes.loginButton}>
                <Button variant="contained" color="primary" onClick={handleSubmit}>
                    <span style={{ color: "#fafafa", fontWeight: "bold" }}>회원정보수정</span>
                </Button>
                <Button variant="contained" onClick={handleOut}>
                    <span style={{ fontWeight: "bold" }}>회원 탈퇴</span>
                </Button>
            </div>
        </div>
    );
}