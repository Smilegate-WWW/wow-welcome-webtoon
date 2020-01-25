import React, {Component} from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CardActionArea from '@material-ui/core/CardActionArea';
import Box from '@material-ui/core/Box';
import Rating from '@material-ui/lab/Rating';

const styles=theme => ({
    card: {
        minWidth: 200,
    },
})

class WebtoonPoster extends Component{
    render(){
        return(
            <img src={this.props.poster}/>
        )
    }
}

class Webtoon extends Component {
    render(){
        const {classes}=this.props;
        return (
            <div>
                <Card className={classes.card} variant="outlined">
                    
                    <CardActionArea>
                    <CardContent>
                        <WebtoonPoster container justify="center" poster={this.props.poster}/>
                        <Typography gutterBottom variant="body1" component="h4">
                            {this.props.title}({this.props.artist})
                        </Typography>
                        <Box component="span" mb={0} borderColor="transparent">
                            <Rating name="read-only" value={this.props.rating} readOnly />
                        </Box>
                    </CardContent>
                    </CardActionArea>
                </Card>
            </div>
        );
    } 
}

export default withStyles(styles) (Webtoon);
