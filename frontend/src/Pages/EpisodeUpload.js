import React from 'react';
import Header from '../Components/Header';
//버튼
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//텍스트 필드
import TextField from '@material-ui/core/TextField';
//색상
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
    thumbnail: {
        width: 100,
        height: 100,
        backgroundColor: grey[400],
        margin: theme.spacing(1),
    },
    fontStyle: {
        fontSize: 12,
        padding: 5,
        color: "grey",
        marginLeft: 10
    },
    buttons:{
        marginTop:theme.spacing(1),
        paddingLeft:theme.spacing(85),
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    upload:{
        variant:"contained", 
        height:30, 
        marginTop:20,
        marginRight:10
    },
}));

export default function Upload({ authenticated, logout }) {
    const classes = useStyles();
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

            <div className={classes.body} style={{ borderTop: '1px solid grey' ,borderBottom:'1px solid grey'}}>
                <h4>회차 등록</h4>
                <div style={{ marginLeft: 5 }}>
                    <div style={{ display: "flex", height: 50, }}>
                        <h5 >자동 회차 No&emsp;&emsp;</h5>
                        <TextField
                            id="episodeNo"
                            variant="outlined"
                            size="small"
                            label="5"
                            style={{ width: 100 }}
                            InputProps={{
                                readOnly: true,
                            }}
                        />

                        <p className={classes.fontStyle}>
                            회차 No는 순차적으로 자동 지정되기 때문에 임의로 설정이 불가능합니다.
                        </p>
                    </div>

                    <div style={{ display: "flex" }}>
                        <h5>썸네일 이미지&emsp;</h5>
                        <Button className={classes.thumbnail}>
                            <h5>202X120</h5>
                        </Button>
                        <Button className={classes.thumbnail}>
                            <h5>600x315</h5>
                        </Button>
                        <div style={{ marginTop: 60,}}>
                            <body1 className={classes.fontStyle}>
                                썸네일을 업로드 하지 않으면 자동 생성 됩니다.
                            </body1>
                            <p className={classes.fontStyle} style={{marginTop:0}}>
                                600X315는 SNS 등에 회차 공유시 노출되는 이미지입니다.
                            </p>
                        </div>
                    </div>

                    <div style={{display:"flex"}}>
                        <h5>원고 등록&emsp;&emsp;&emsp;&emsp;</h5>
                        <input 
                            type="file" 
                            name="file" 
                            onChange={null} 
                            className={classes.upload}
                        />
                        <Button variant="contained" style={{height:30, marginTop:20,marginRight:10}}>
                           업로드
                        </Button>
                        <Button variant="contained" style={{height:30, marginTop:20}}>
                            전체 보기
                        </Button>
                    </div>

                </div>
            </div >
            
            <div className={classes.buttons} style={{display:"flex"}}>
                <Button variant="contained" href="/mypage">
                    <span style={{fontWeight:550}}>취소</span>
                </Button>
                <Button variant="contained" color="primary" >
                    <span style={{color:"#fafafa",fontWeight:550}}>등록</span>
                </Button>
            </div>
        </div>
    )
}