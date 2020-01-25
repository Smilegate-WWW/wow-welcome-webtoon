import React, {Component} from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import {grey} from '@material-ui/core/colors'

const styles=theme => ({
    card: {
        backgroundColor: grey[400],
        minWidth: 200,
        minHeight: 200
    },
    icon:{
        marginLeft:theme.spacing(10)
    }
})

class PlusWebtoon extends Component {
    render(){
        const {classes}=this.props;
        return (
            <div>
                <Card className={classes.card} >
                    <CardActionArea>
                    <CardContent>
                        <div className="icon">
                            <img src="/Icon/plusIcon.png"/>
                        </div>
                    </CardContent>
                    </CardActionArea>
                </Card>
            </div>
        );
    } 
}

export default withStyles(styles) (PlusWebtoon);
