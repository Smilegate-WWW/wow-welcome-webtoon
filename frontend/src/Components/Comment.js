import React, { Component } from 'react';
import { Button } from '@material-ui/core';
// 토큰 재발급
var ReToken = require("../AuthRoute");

class Comment extends Component {
    state = { goodNum: this.props.goodNum, badNum: this.props.badNum };

    handleGood = ({ target: { goodNum } }) => {
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

        fetch("/comments/" + this.props.cmt_idx + "/like", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    this.setState({ goodNum: result.data.cnt });
                }
                else if (result.code == 21) {
                    alert("[ERROR 21] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else if (result.code == 24) {
                    alert("자신이 단 댓글을 추천할 수 없습니다.")
                }
                else if (result.code == 44) {
                    ReToken.ReToken()
                }
                else if (result.code == 42) {
                    // 로그인 필요한 경우
                    if (!(localStorage.getItem("AUTHORIZATION"))) {
                        alert("로그인이 필요한 기능입니다, 로그인 페이지로 이동합니다.")
                        window.location.href = "/login";
                    }
                    else
                        alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else {
                    alert("잘못된 접근입니다, 관리자에게 문의하세요.")
                }
            })
            .catch(error => console.log('error', error));
    }

    handleBad = ({ target: { badNum } }) => {
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

        fetch("/comments/" + this.props.cmt_idx + "/dislike", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if (result.code == 0) {
                    this.setState({ badNum: result.data.cnt })
                }
                else if (result.code == 21) {
                    alert("[ERROR 21] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else if (result.code == 25) {
                    alert("자신이 단 댓글을 비추천할 수 없습니다.")
                }
                else if (result.code == 44) {
                    ReToken.ReToken()
                }
                else if (result.code == 42) {
                    // 로그인 필요한 경우
                    if (!(localStorage.getItem("AUTHORIZATION"))) {
                        alert("로그인이 필요한 기능입니다, 로그인 페이지로 이동합니다.")
                        window.location.href = "/login";
                    }
                    else
                        alert("[ERROR 42] 잘못된 접근입니다, 관리자에게 문의하세요.")
                }
                else {
                    alert("잘못된 접근입니다, 관리자에게 문의하세요.")
                }
            })
            .catch(error => console.log('error', error));
    }

    render() {
        const { goodNum, badNum } = this.state;

        return (
            <div>
                <h5>{this.props.nickname}</h5>
                <span style={{ fontSize: 15 }}>{this.props.comment}<br /></span>
                <div style={{ display: "flex" }}>
                    <span style={{ fontSize: 10, paddingTop: 20 }}>{this.props.date}</span>
                    <div style={{ marginLeft: 580, display: "flex" }}>
                        <Button variant="contained" onClick={this.handleGood} style={{ marginRight: 10, width: 50 }}>
                            <img src="/Icon/commentGood.png" alt="icon" />
                            &ensp;{goodNum}
                        </Button>
                        <Button variant="contained" onClick={this.handleBad} style={{ marginRight: 5, width: 50 }}>
                            <img src="/Icon/commentBad.png" alt="icon" />
                            &ensp;{badNum}
                        </Button>
                    </div>
                    <div style={{ display: "none" }}>{this.props.cmt_idx}</div>
                    <div style={{ display: "none" }}>{this.props.goodNum}</div>
                    <div style={{ display: "none" }}>{this.props.badNum}</div>
                </div>
            </div>
        )
    }
}

export default Comment;