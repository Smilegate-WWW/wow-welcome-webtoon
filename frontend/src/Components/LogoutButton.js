import React from 'react';
import { withRouter } from 'react-router-dom';

import Button from '@material-ui/core/Button';


function LogoutButton({ logout, history }) {

    const handleClick = () => {
        logout();
        history.push('/');
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