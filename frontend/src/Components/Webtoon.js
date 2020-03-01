import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CardActionArea from '@material-ui/core/CardActionArea';
import Box from '@material-ui/core/Box';
import Rating from '@material-ui/lab/Rating';

const styles = theme => ({
    card: {
        width: 220,
    },
})

class Webtoonthumbnail extends Component {
    render() {
        return (
            <img src={this.props.thumbnail} width="128" height="128" />
        )
    }
}

class Webtoon extends Component {
    render() {
        const { classes } = this.props;
        return (
            <div>
                <a href={"/webtoon?idx="+this.props.idx} style={{ textDecoration: "none" }}>
                    <Card href={"/webtoon?idx="+this.props.idx} className={classes.card} variant="outlined">
                        <CardActionArea>
                            <CardContent>
                                <Webtoonthumbnail container justify="center" thumbnail={this.props.thumbnail} />
                                <Typography gutterBottom variant="body1" component="h4">
                                    {this.props.title}({this.props.artist})
                                </Typography>
                                <Box component="span" mb={0} borderColor="transparent">
                                    <Rating name="read-only" value={this.props.ep_rating_avg} readOnly />
                                </Box>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </a>
            </div>
        );
    }
}

export default withStyles(styles)(Webtoon);
