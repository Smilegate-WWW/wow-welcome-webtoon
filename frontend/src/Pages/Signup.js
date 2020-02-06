import React from 'react';
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

export default function Signup() {
    const classes = useStyles();
    const [gender, setGender] = React.useState('');

    const inputLabel = React.useRef(null);
    const [labelWidth, setLabelWidth] = React.useState(0);
    React.useEffect(() => {
        setLabelWidth(inputLabel.current.offsetWidth);
    }, []);


    const handleChange = event => {
        setGender(event.target.value);
    };

    return (
        <div className={classes.root}>
            <span style={{
                color: "#ff7043",
                fontSize: "64px",
                fontWeight: 700,
            }}>&emsp;WWW</span>

            <form className={classes.textField} noValidate autoComplete="off">
                <TextField id="userid" label="아이디" variant="outlined" />
                <TextField
                    id="pw"
                    label="비밀번호"
                    type="password"
                    autoComplete="current-password"
                    variant="outlined"
                />
                <TextField
                    id="pw_check"
                    label="비밀번호 재입력"
                    type="password"
                    autoComplete="current-password"
                    variant="outlined"
                />
            </form>

            <div className={classes.display}>
                <form className={classes.smallTextField} noValidate autoComplete="off">
                    <TextField id="name" label="이름" variant="outlined" />
                </form>

                <FormControl variant="outlined" className={classes.smallTextField}>

                    <InputLabel ref={inputLabel} id="gender_label">성별</InputLabel>
                    <Select
                        labelId="gender_label"
                        id="gender"
                        value={gender}
                        onChange={handleChange}
                        labelWidth={labelWidth}
                    >
                        <MenuItem value={"male"}>남</MenuItem>
                        <MenuItem value={"female"}>여</MenuItem>
                    </Select>
                </FormControl>
            </div>

            <form className={classes.textField} noValidate autoComplete="off">
                <TextField
                    id="birth"
                    label="생년월일"
                    type="date"
                    defaultValue="2000-01-01"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField id="email" label="이메일" variant="outlined" />
            </form>

            <div className={classes.loginButton}>
                <Button variant="contained" color="primary">
                    <span style={{ color: "#fafafa", fontWeight: "bold" }}>회원가입</span>
                </Button>
            </div>
        </div>
    );
}