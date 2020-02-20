import React from 'react';
import { withRouter } from 'react-router-dom';

import Button from '@material-ui/core/Button';


function LogoutButton() {

    const handleClick = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

        var raw = JSON.stringify({ "RefreshToken": localStorage.getItem("REFRESHTOKEN") });

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        var user_idx = localStorage.getItem("USER_IDX");
        fetch("/users/" + user_idx+ "/token", requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                localStorage.clear();
                window.location.reload();
            })
            .catch(error => console.log('error', error));

        
    }

    return (
        <div >
            <Button variant="contained" color="primary" onClick={handleClick}>
                <span style={{ color: "#fafafa", fontWeight: "bold" }}>로그아웃</span>
            </Button>
        </div>
    );
}

export default withRouter(LogoutButton);