import React, {Component} from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import {grey} from '@material-ui/core/colors'

const styles=theme => ({
    card: {
        backgroundColor: grey[400],
        width: 220,
        maxHeight: 220,
        marginTop:theme.spacing(1),
        marginLeft:theme.spacing(1),
    },
    iconBox:{
        maxWidth:200,
        padding:theme.spacing(6,4.1)
    },
    icon:{
        marginLeft:theme.spacing(4)
    }
})

class PlusWebtoon extends Component {
    render(){
        const {classes}=this.props;
        return (
            <div>
                <Card className={classes.card} >
                    <CardActionArea href="/mypage/register">
                    <CardContent>
                        <div className={classes.iconBox} >
                            <img className={classes.icon} src="/Icon/plusIcon.png"/>
                            <h4>새로운 작품 등록</h4>
                        </div>
                    </CardContent>
                    </CardActionArea>
                </Card>
            </div>
        );
    } 
}

export default withStyles(styles) (PlusWebtoon);
