import React, { Component } from 'react';
import { Button } from '@material-ui/core';

import Episode from '../Pages/Episode';

class Comment extends Component {
    render() {
        //var cmt_idx=1;

        const handleGood = () => {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
            myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

            var raw = "";

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/comments/"+this.props.cmt_idx+"/like", requestOptions)
                .then(response => response.text())
                .then(result => {
                    console.log(result)
                    Episode.Episode();
                })
                .catch(error => console.log('error', error));
        }

        const handleBad = () => {
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
            myHeaders.append("AUTHORIZATION", localStorage.getItem("AUTHORIZATION"));

            var raw = "";

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("/comments/"+this.props.cmt_idx+"/dislike", requestOptions)
                .then(response => response.text())
                .then(result => {
                    console.log(result)
                    Episode.Episode();
                })
                .catch(error => console.log('error', error));
        }


        return (
            <div>
                <h5>{this.props.nickname}</h5>
                <body1>{this.props.comment}</body1>
                <div style={{ display: "flex" }}>
                    <body2>{this.props.date}</body2>
                    <div style={{ marginLeft: 620, display: "flex" }}>
                        <Button variant="contained" onClick={handleGood} style={{ marginRight: 10, width: 50 }}>
                            <img src="/Icon/commentGood.png" alt="icon" />
                            &ensp;{this.props.goodNum}
                        </Button>
                        <Button variant="contained" onClick={handleBad} style={{ marginRight: 5, width: 50 }}>
                            <img src="/Icon/commentBad.png" alt="icon" />
                            &ensp;{this.props.badNum}
                        </Button>
                    </div>
        <div style={{display:"none"}}>{this.props.cmt_idx}</div>
                </div>
            </div>
        )
    }
}

export default Comment;