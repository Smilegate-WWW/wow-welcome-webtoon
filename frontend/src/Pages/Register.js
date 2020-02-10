import React from 'react';
import Header from '../Components/Header';
//버튼
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
// 텍스트 필드
import TextField from '@material-ui/core/TextField';
//라디오
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
//체크박스
import Checkbox from '@material-ui/core/Checkbox';
import { grey } from '@material-ui/core/colors';

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
    body: {
        height: 500,
        padding: theme.spacing(0, 15),
    },
    components: {
        padding: theme.spacing(0, 5)
    },
    thumbnail: {
        width: 100,
        height: 100,
        backgroundColor: grey[400],
    },
    buttons:{
        marginTop:theme.spacing(1),
        paddingLeft:theme.spacing(80),
        '& > *': {
            margin: theme.spacing(1),
        },
    },
}));

export default function Register({ authenticated, logout }) {
    const classes = useStyles();
    const [value, setValue] = React.useState('female');
    const [state, setState] = React.useState({
        daily: true,
        gag: true,
        fantasy: true,
        action: true,
        drama: true,
        pure: true,
        emotion: true,
    });

    const checkChange = name => event => {
        setState({ ...state, [name]: event.target.checked });
    };
    const handleChange = event => {
        setValue(event.target.value);
    };
    return (
        <div>
            <Header authenticated={authenticated} logout={logout} />

            <div className={classes.menu}>
                <div className={classes.button}>
                    <Button variant="contained" href="/">
                        <span style={{ color: "#212121", fontWeight: 520 }}>도전만화</span>
                    </Button>

                    <Button variant="contained" color="primary" href="/mypage">
                        <span style={{ color: "#fafafa", fontWeight: 550 }}>마이페이지</span>
                    </Button>
                </div>
            </div>

            <div className={classes.body} style={{ border: '1px solid grey' }}>
                <h4>새 작품 등록</h4>
                <div className={classes.components}>
                    <div style={{ display: "flex", height: 50, }}>
                        <h5 >작품 제목&emsp;</h5>
                        <TextField
                            id="title"
                            variant="outlined"
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex", height: 50, }}>
                        <h5 >형식&emsp;&emsp;&emsp;&emsp;</h5>
                        <RadioGroup aria-label="position" name="type" value={value} onChange={handleChange} row>
                            <FormControlLabel
                                value="episode"
                                control={<Radio color="primary" />}
                                label="에피소드"
                                labelPlacement="end"
                            />
                            <FormControlLabel
                                value="omnibus"
                                control={<Radio color="primary" />}
                                label="옴니버스"
                                labelPlacement="end"
                            />
                            <FormControlLabel
                                value="story"
                                control={<Radio color="primary" />}
                                label="스토리"
                                labelPlacement="end"
                            />
                        </RadioGroup>
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5>장르&emsp;&emsp;&emsp;&emsp;</h5>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.daily}
                                    onChange={checkChange('daily')}
                                    value="daily"
                                    color="primary"
                                />
                            }
                            label="일상"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.gag}
                                    onChange={checkChange('gag')}
                                    value="gag"
                                    color="primary"
                                />
                            }
                            label="개그"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.fantasy}
                                    onChange={checkChange('fantasy')}
                                    value="fantasy"
                                    color="primary"
                                />
                            }
                            label="판타지"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.action}
                                    onChange={checkChange('action')}
                                    value="action"
                                    color="primary"
                                />
                            }
                            label="액션"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.drama}
                                    onChange={checkChange('drama')}
                                    value="drama"
                                    color="primary"
                                />
                            }
                            label="드라마"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.pure}
                                    onChange={checkChange('pure')}
                                    value="pure"
                                    color="primary"
                                />
                            }
                            label="순정"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={state.emotion}
                                    onChange={checkChange('emotion')}
                                    value="emotion"
                                    color="primary"
                                />
                            }
                            label="감성"
                        />
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5 >작품 요약&emsp;</h5>
                        <TextField
                            id="summary"
                            variant="outlined"
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5 >줄거리&emsp;&emsp;</h5>
                        <TextField
                            id="summary"
                            variant="outlined"
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5 >썸네일&emsp;&emsp;</h5>
                        <div>
                            <Button className={classes.thumbnail} data-id="IMG">
                                <h5>434X330</h5>
                                
                            </Button>
                            <input type="file" id="IMG" name="IMG" style={{display:"none"}}/>
                        </div>
                        <p style={{
                            fontSize: 12,
                            padding: 5,
                            marginTop:80,
                            color:grey
                        }}> 파일용량 1MB 이하/ jpg만 업로드 가능</p>
                    </div>
                </div>
            </div>
            <div className={classes.buttons} style={{display:"flex"}}>
                <Button variant="contained" href="/mypage">
                    <span style={{fontWeight:550}}>취소</span>
                </Button>
                <Button variant="contained" color="primary" >
                    <span style={{color:"#fafafa",fontWeight:550}}>등록</span>
                </Button>
                <Button variant="contained" color="primary" href="/mypage/upload">
                    <span style={{color:"#fafafa",fontWeight:550}}>등록 후 1회 올리기</span>
                </Button>
            </div>
        </div >
    )
}