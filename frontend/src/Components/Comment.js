import React, { Component } from 'react';
import { Button } from '@material-ui/core';

class Comment extends Component {
    render() {
        const { classes } = this.props;
        return (
            <div>
                <h5>{this.props.nickname}</h5>
                <body1>{this.props.comment}</body1>
                <div style={{ display: "flex" }}>
                    <body2>{this.props.date}</body2>
                    <div style={{marginLeft:620, display:"flex"}}>
                        <Button variant="contained" style={{marginRight:10, width:50}}>
                            <img src="/Icon/commentGood.png" alt="icon" />
                            &ensp;{this.props.goodNum}
                        </Button>
                        <Button variant="contained"style={{marginRight:5, width:50}}>
                            <img src="/Icon/commentBad.png" alt="icon" />
                            &ensp;{this.props.badNum}
                        </Button>
                    </div>
                </div>
            </div>
        )
    }
}

export default Comment;