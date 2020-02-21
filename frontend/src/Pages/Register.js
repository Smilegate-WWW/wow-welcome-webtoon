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
    buttons: {
        marginTop: theme.spacing(1),
        paddingLeft: theme.spacing(80),
        '& > *': {
            margin: theme.spacing(1),
        },
    },
}));



export default function Register() {
    const classes = useStyles();
    const [title, setTitle] = React.useState('');
    const [type, setType] = React.useState('');
    const [genre, setGenre] = React.useState({
        daily: false,
        gag: false,
        fantasy: false,
        action: false,
        drama: false,
        pure: false,
        emotion: false,
    });
    const [summary, setSummary] = React.useState('');
    const [plot, setPlot] = React.useState('');
    const [thumbnail,setThumbnail]=React.useState('');
    const genreArray = [genre.daily, genre.gag, genre.fantasy, genre.action, genre.drama, genre.pure, genre.emotion];

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
    }
    const handleTypeChange = event => {
        setType(event.target.value);
    };
    const handleGenreChange = name => event => {
        setGenre({ ...genre, [name]: event.target.checked });
    };
    const handleSummaryChange = (e) => {
        setSummary(e.target.value);
    }
    const handlePlotChange = (e) => {
        setPlot(e.target.value);
    }
    const handleThumbnailChange = (e) => {
        const file = e.target.files[0];
        setThumbnail(file);
        let reader = new FileReader();
        reader.onloadend = () => {
            console.log("load end");
        };
        reader.readAsDataURL(file);
        if(file.length ===0){
            alert("파일이 선택되지 않았습니다.");
        }
        else if(file.type != "image/jpeg"){
            alert("jpg 타입의 파일을 선택해주세요!")
        }
        else if(file.size > 1048576 ){
            alert("파일의 크기가 너무 큽니다");
        }
        else{
            alert(file.name+"이(가) 선택되었습니다");
        }
    }

    const handleCancleBtnClick = () => {
        alert("변경된 내용이 저장되지 않습니다.");
    }
    const handleSubmit = () => {
        let genreNum = 0
        for (var i = 0; i < genreArray.length; i++) {
            if (genreArray[i] == true) {
                genreNum += 1;
            }
        }

        if (title === '' || type === '' || summary === '' || plot === '') {
            alert("정보를 모두 입력해주세요!!");
        }
        else if (genreNum > 2) {
            alert("장르는 2개까지 선택해주세요.");
        }
        else {
            // 장르 2개 넘겨주기
            const genreTrue = [];
            let genre1 = null;
            let genre2 = null;
            var j = 0;
            for (var i = 0; i < genreArray.length; i++) {
                if (genreArray[i] == true) {
                    genreTrue[j] = i;
                    j++
                }
            }
            if (j == 0) {
                genre1 = null;
                genre2 = null;
            }
            else if (j == 1) {
                genre1 = genreTrue[0];
                genre2 = null;
            }
            else {
                genre1 = genreTrue[0];
                genre2 = genreTrue[1];
            }

            var myHeaders = new Headers();
            myHeaders.append("Content-Type","multipart/form-data");
            myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

            var webtoonInfo = JSON.stringify({"title":title,"toon_type":type,"genre1":genre1,"genre2":genre2,"summary":summary,"plot":plot});
            
            const fs=require("fs");

            fs.writeFile('./webtoon.json','webtoonInfo',function(err){ 
                if (err === null) { console.log('success'); } 
                else { console.log('fail'); } 
            });

            fs.readdir('./webtoon.json',function(err,filelist){ console.log(filelist); });

            var formdata = new FormData();
            formdata.append("thumbnail",thumbnail);
            formdata.append("webtoon", webtoonInfo);

            console.log(thumbnail);
            console.log(webtoonInfo);
            

            var requestOptions = {
              method: 'POST',
              headers: myHeaders,
              body: formdata,
              redirect: 'follow'
            };
            
            fetch("/myTitleDetail", requestOptions)
              .then(response => response.json())
              .then(result => console.log(result))
              .catch(error => console.log('error', error));

        }
    }
    return (
        <div>
            <Header/>

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
                            value={title}
                            onChange={handleTitleChange}
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex", height: 50, }}>
                        <h5 >형식&emsp;&emsp;&emsp;&emsp;</h5>
                        <RadioGroup aria-label="position" name="type" value={type} onChange={handleTypeChange} row>
                            <FormControlLabel
                                value="0"
                                control={<Radio color="primary" />}
                                label="에피소드"
                                labelPlacement="end"
                            />
                            <FormControlLabel
                                value="1"
                                control={<Radio color="primary" />}
                                label="옴니버스"
                                labelPlacement="end"
                            />
                            <FormControlLabel
                                value="2"
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
                                    checked={genre.daily}
                                    onChange={handleGenreChange('daily')}
                                    value="0"
                                    color="primary"
                                />
                            }
                            label="일상"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.gag}
                                    onChange={handleGenreChange('gag')}
                                    value="1"
                                    color="primary"
                                />
                            }
                            label="개그"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.fantasy}
                                    onChange={handleGenreChange('fantasy')}
                                    value="2"
                                    color="primary"
                                />
                            }
                            label="판타지"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.action}
                                    onChange={handleGenreChange('action')}
                                    value="3"
                                    color="primary"
                                />
                            }
                            label="액션"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.drama}
                                    onChange={handleGenreChange('drama')}
                                    value="4"
                                    color="primary"
                                />
                            }
                            label="드라마"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.pure}
                                    onChange={handleGenreChange('pure')}
                                    value="5"
                                    color="primary"
                                />
                            }
                            label="순정"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={genre.emotion}
                                    onChange={handleGenreChange('emotion')}
                                    value="6"
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
                            value={summary}
                            onChange={handleSummaryChange}
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5 >줄거리&emsp;&emsp;</h5>
                        <TextField
                            id="summary"
                            variant="outlined"
                            value={plot}
                            onChange={handlePlotChange}
                            size="small"
                            style={{ width: 800 }}
                        />
                    </div>
                    <div style={{ display: "flex" }}>
                        <h5 >썸네일&emsp;&emsp;</h5>
                        <div>
                            <input
                                accept=".jpg"
                                id="thumbnail"
                                type="file"
                                style={{ display: "none" }}
                                onChange={handleThumbnailChange}
                            />
                            <label htmlFor="thumbnail">
                                <Button variant="contained" component="span" style={{ height: 100, width: 100 }}>
                                    430 X 330
                                </Button>
                            </label>
                        </div>
                        <p style={{
                            fontSize: 12,
                            padding: 5,
                            marginTop: 80,
                            color: grey
                        }}> 파일용량 1MB 이하/ jpg만 업로드 가능</p>
                    </div>
                </div>
            </div>
            <div className={classes.buttons} style={{ display: "flex" }}>
                <Button variant="contained" href="/mypage" onClick={handleCancleBtnClick}>
                    <span style={{ fontWeight: 550 }}>취소</span>
                </Button>
                <Button variant="contained" color="primary" onClick={handleSubmit}>
                    <span style={{ color: "#fafafa", fontWeight: 550 }}>등록</span>
                </Button>
                <Button variant="contained" color="primary" href="/mypage/upload" onClick={handleSubmit}>
                    <span style={{ color: "#fafafa", fontWeight: 550 }}>등록 후 1회 올리기</span>
                </Button>
            </div>
        </div >
    )
}

