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

export default function Upload() {
    const [title, setTitle] = React.useState("");
    const [comment, setComment] = React.useState("");
    const [thumbnail, setThumbnail] = React.useState("");
    const [script,setScript]=React.useState([]);
    var images=[];

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
            images=script;
            images.push(file);
            setScript(images);
            alert(file.name + "이(가) 선택되었습니다 \n\n 선택된 이미지: " + images.length + "개");
        }
    }

    const handleThumbnailChange = (e) => {
        const file = e.target.files[0];
        setThumbnail(file);
        let reader = new FileReader();
        reader.onloadend = () => {
            console.log("load end");
        };
        if(file!=null){
        reader.readAsDataURL(file);
        }
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
            alert(file.name + "이(가) 선택되었습니다");
        }
    }

    const hadleSubmit = () => {
        console.log(title,comment,script)
        if (title === "" || comment === "" || script.length == 0) {
            alert("필요한 모든 정보를 입력해주세요")
        }
        else {
            var episodeInfo = JSON.stringify({ "ep_no": null, "title": title, "auth_comment": comment })

            var formdata = new FormData();
            formdata.append("thumbnail", thumbnail);
            for (var i = 0; i < script.length; i++) {
                formdata.append("manuimages", script[i]);
            }
            formdata.append("episode", episodeInfo);

            var requestOptions = {
                method: 'POST',
                body: formdata,
                redirect: 'follow'
            };

            fetch("/myArticleDetail/", requestOptions)
                .then(response => response.json())
                .then(result => console.log(result))
                .catch(error => console.log('error', error));
        }
    }

    /*
    const handlePreview =()=>{
        if(script.length>=){
            window.open
        }
        var reader = new FileReader();

        for(var i=0 ; i < script.length ; i++){
            reader.readAsDataURL(script[i]);
            var tempImage = new Image();
            tempImage.src = reader.result;

        }
    }
    */
   
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
                            label="5" //임의 설정
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

                    <div style={{ display: "flex" }}>
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
                                <Button variant="contained" component="span" style={{ height: 100, width: 100, marginLeft: 10 }}>
                                    430 X 330
                                </Button>
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

                            <Button variant="contained" onClick={handlePreview} style={{ height: 30, marginTop: 20 }}>
                                전체 보기
                            </Button>
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