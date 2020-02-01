// 인증이 필요한 컴포넌트를 위한 전용 라우트
import React from 'react';
import { Route, Redirect } from 'react-router-dom';

function AuthRoute({ authenticated, component: Component, render, ...rest }) {
  return (
    console.log(authenticated),
    <Route
      {...rest}
      render={props =>
        authenticated ? (
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