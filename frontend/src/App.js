import React, { useState } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Home, Comment, Login, MyPage, Signup, Register, Webtoon} from './Pages';
import signIn from './TestAuth';
import AuthRoute from './AuthRoute';

export default function App() {
  //user 로그인된 사용자 정보 담은 변수
  const [user, setUser] = useState(null);
  // 인증 여부
  const authenticated = user != null;

  const login = ({ id, pw }) => setUser(signIn({ id, pw }));
  const logout = () => setUser(null);

  return (
    <div>
      <Switch>
        <Route 
          exact path="/" 
          render={props => (
            <Home authenticated={authenticated} login={login} logout={logout} {...props} />
          )}
        />
        <AuthRoute
          authenticated={authenticated}
          exact path="/mypage"
          render={props => <MyPage user={user} authenticated={authenticated} logout={logout} {...props} />}
        />
        <Route
          exact path="/login"
          render={props => (
            <Login authenticated={authenticated} login={login} {...props} />
          )}
        />
        <Route 
          path="/mypage/comment" 
          render={props => (
            <Comment authenticated={authenticated} logout={logout} {...props} />
          )}
        />
        <Route path="/login/signup" component={Signup} />
        <Route path="/mypage/register" component={Register} />
        <Route path="/webtoon" component={Webtoon}/>
      </Switch>
    </div>
  );

}
