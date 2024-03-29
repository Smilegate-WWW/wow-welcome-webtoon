import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CardActionArea from '@material-ui/core/CardActionArea';
import Button from '@material-ui/core/Button';

const styles = theme => ({
    card: {
        width: 400,
    },
})

class WebtoonPoster extends Component {
    render() {
        return (
            <img src={this.props.thumbnail} width="128" height="128"/>
        )
    }
}



class MyWebtoon extends Component {
    render() {
        const { classes } = this.props;
        return (
            <div>
                    <Card href="/webtoon" className={classes.card} variant="outlined">
                        <CardActionArea>
                            <CardContent>
                                <WebtoonPoster container justify="center" thumbnail={this.props.thumbnail} />
                                <Typography gutterBottom variant="body1" component="h4">
                                    {this.props.title}
                                </Typography>
                                <Typography>등록일&ensp;{this.props.created_date.slice(0,10)}</Typography>
                                <Typography>업데이트일&ensp;{this.props.last_updated.slice(0,10)}</Typography>
                                <div style={{ display: "flex", margin:3}}>
                                    <Button variant="contained" href={"/mypage/editRegister?idx="+this.props.idx} style={{marginRight:10}}>작품 정보 수정</Button>
                                    <Button variant="contained" href={"/mypage/editEpisode?idx="+this.props.idx}>회차 관리</Button>
                                </div>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                
            </div>
        );
    }
}

export default withStyles(styles)(MyWebtoon);
