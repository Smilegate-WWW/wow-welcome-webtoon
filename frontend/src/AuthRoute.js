// 인증이 필요한 컴포넌트를 위한 전용 라우트
import React from 'react';
import { Route, Redirect } from 'react-router-dom';

function postToken() {
  var myHeaders = new Headers();

  myHeaders.append("Content-Type", "application/json");
  myHeaders.append("Authorization", localStorage.getItem("AUTHORIZATION"));

  var raw = JSON.stringify({ "RefreshToken": localStorage.getItem("REFRESHTOKEN") });

  var requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow'
  };

  var user_idx = localStorage.getItem("USER_IDX")

  fetch("/users/" + user_idx + "/token", requestOptions)
    .then(response => {
      localStorage.setItem("AUTHORIZATION", response.headers.get('Authorization'))
      response.json().then(
        result => console.log(result)
      )
    })
    .catch(error => console.log('error', error))

}

function AuthRoute({ component: Component, render, ...rest }) {
  if (localStorage.getItem("AUTHORIZATION")) {
    var temp = localStorage.getItem("AUTHORIZATION")
    var jwt_decode = require('jwt-decode')
    var decodeToken = jwt_decode(temp.replace("bearer ", ""))
    // token 유효시간 체크
    if ((decodeToken.exp - Date.now()) < 1000 * 30) {
      postToken();
    }
  }

  return (
    <Route
      {...rest}
      render={props =>
        (localStorage.getItem("AUTHORIZATION")) ? (
          render ? render(props) : <Component {...props} />
        ) : (
            <Redirect
              to={{ pathname: '/login', state: { from: props.location } }}
            />
          )
      }
    />
  );
}

export default AuthRoute;