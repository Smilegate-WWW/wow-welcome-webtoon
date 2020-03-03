import React from 'react';
import Header from '../Components/Header';
//버튼
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
//텍스트 필드
import TextField from '@material-ui/core/TextField';
//색상
import { grey } from '@material-ui/core/colors';
//preview
import Preview from './Preview';
// 토큰 재발급
var ReToken = require("../AuthRoute");

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
        marginLeft: 10,
    },
    buttons: {
        marginTop: theme.spacing(1),
        paddingLeft: theme.spacing(85),
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    upload: {
        variant: "contained",
        height: 30,
        marginTop: 20,
        marginRight: 10
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
var episode_idx = getParameterByName('episode_idx');

export default function Upload() {
    const [title, setTitle] = React.useState("");
    const [comment, setComment] = React.useState("");
    const [thumbnail, setThumbnail] = React.useState("");
    const [script, setScript] = React.useState([]);
    const [thumbnailstr,setThumbnailstr]=React.useState("");
    var images = [];

    const classes = useStyles();

    const handleTitleChange = (e) => {
        setTitle(e.target.value)
    }

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    }

    const handleImageChange = (e) => {
        const file = e.target.files[0];

        let reader = new FileReader();
        reader.onloadend = () => {
            console.log("load end");
        };
        reader.readAsDataURL(file);
        if (file.length === 0) {
            alert("파일이 선택되지 않았습니다.");
        }
        else if (file.type != "image/jpeg") {
            alert("jpg 타입의 파일을 선택해주세요!")
        }
        else if (file.size > 1048576) {
            alert("파일의 크기가 너무 큽니다");
        }
        else {
            var img = new Image();

            img.src = window.URL.createObjectURL(file);
            img.onload = function () {
                if (img.width <= 690) {
                    images = script;
                    images.push(file);
                    setScript(images);
                    localStorage.setItem("SCRIPT",script)
                    alert(file.name + "이(가) 선택되었습니다 \n\n 선택된 이미지: " + images.length + "개");
                }
                else {
                    alert("파일 사이즈가 맞지 않습니다.")
                }
            }
        }
    }

    const handleThumbnailChange = (e) => {
        const file = e.target.files[0];

        let reader = new FileReader();
        reader.onloadend = () => {
            console.log("load end");
            setThumbnailstr(reader.result);
        };
        reader.readAsDataURL(file);
        if (file.length === 0) {
            alert("파일이 선택되지 않았습니다.");
        }
        else if (file.type != "image/jpeg") {
            alert("jpg 타입의 파일을 선택해주세요!")
        }
        else if (file.size > 1048576) {
            alert("파일의 크기가 너무 큽니다");
        }
        else {
            var img = new Image();

            img.src = window.URL.createObjectURL(file);
            img.onload = function () {
                if (img.height <= 330 && img.width <= 430) {
                    alert("파일이 선택되었습니다.")
                    setThumbnail(file);
                }
                else {
                    alert("파일 사이즈를 맞춰주세요")
                }
            }
        }
    }

    const hadleSubmit = () => {
        if (title === "" || comment === "" || script.length == 0) {
            alert("필요한 모든 정보를 입력해주세요")
        }
        else {
            var myHeaders = new Headers();
            myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

            var formdata = new FormData();
            formdata.append("thumbnail", thumbnail);
            for (var i = 0; i < script.length; i++) {
                formdata.append("manuscript", script[i]);
            }
            formdata.append("title", title);
            formdata.append("author_comment", comment);

            var requestOptions = {
                headers:myHeaders,
                method: 'POST',
                body: formdata,
                redirect: 'follow'
            };

            fetch("/myArticleDetail/" + idx, requestOptions)
                .then(response => response.json())
                .then(result => {
                    console.log(result)
                    if(result.code ==0 ){
                        alert("새로운 회차가 등록되었습니다")
                        window.location.href="/mypage/editEpisode?idx="+idx;
                    }
                    else if(result.code==44){
                        ReToken.ReToken()
                    }
                    else if(result.code==42){
                        alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                    }
                    else{
                        alert("잘못된 접근입니다, 관리자에게 문의하세요.")
                    }
                })
                .catch(error => console.log('error', error));
        }
    }

    const handlePreview = () => {
        if (script.length >= 1) {
            window.open("/mypage/upload/preview", "preview")
        }
    }

    return (
        <div>
            <Header />

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

            <div className={classes.body} style={{ borderTop: '1px solid grey', borderBottom: '1px solid grey' }}>
                <h4>회차 등록</h4>
                <div style={{ marginLeft: 5 }}>
                    <div style={{ display: "flex", height: 50, }}>
                        <h5 >자동 회차 No&emsp;&emsp;</h5>
                        <TextField
                            id="episodeNo"
                            variant="outlined"
                            size="small"
                            label={episode_idx}
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
                        <h5>회차 제목&emsp;&emsp;&emsp;</h5>
                        <TextField
                            id="title"
                            variant="outlined"
                            value={title}
                            onChange={handleTitleChange}
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>

                    <div style={{ display: "flex", height:110}}>
                        <h5>썸네일 이미지&emsp;</h5>
                        <div>
                            <input
                                accept=".jpg"
                                id="thumbnail"
                                type="file"
                                style={{ display: "none" }}
                                onChange={handleThumbnailChange}
                            />
                            <label htmlFor="thumbnail">
                                <Button variant="contained" component="span" style={{ height: 100, width: 100, marginLeft: 10,marginRight:5 }}>
                                    430 X 330
                                </Button>
                            <img src={thumbnailstr} alt="thumbnail" width="100" height="100"/>
                            </label>
                        </div>
                        <div style={{ marginTop: 80, }}>
                            <body1 className={classes.fontStyle}>
                                썸네일을 업로드 하지 않으면 자동 생성 됩니다.
                            </body1>
                        </div>
                    </div>

                    <div style={{ display: "flex" }}>
                        <h5>원고 등록&emsp;&emsp;&emsp;&emsp;</h5>
                        <div>
                            <input
                                accept=".jpg"
                                id="images"
                                type="file"
                                style={{ display: "none" }}
                                onChange={handleImageChange}
                            />
                            <label htmlFor="images">
                                <Button variant="contained" component="span" style={{ height: 30, marginTop: 20, marginRight: 10 }}>
                                    원고 등록
                                </Button>
                            </label>

                            <Button variant="contained" style={{ height: 30, marginTop: 20 }} onClick={handlePreview}>
                                전체 보기
                            </Button>

                            <body1 className={classes.fontStyle}>
                                <br />
                                가로 크기는 690px로 제한하며, 세로 크기는 제한 없습니다.
                            </body1>
                        </div>

                    </div>
                    <h5>작가의 말</h5>
                    <TextField
                        id="summary"
                        variant="outlined"
                        value={comment}
                        onChange={handleCommentChange}
                        size="small"
                        style={{ width: 800 }}
                    />
                </div>
            </div >

            <div className={classes.buttons} style={{ display: "flex" }}>
                <Button variant="contained" href="/mypage">
                    <span style={{ fontWeight: 550 }}>취소</span>
                </Button>
                <Button variant="contained" color="primary" onClick={hadleSubmit} >
                    <span style={{ color: "#fafafa", fontWeight: 550 }}>등록</span>
                </Button>
            </div>
        </div>
    )
}