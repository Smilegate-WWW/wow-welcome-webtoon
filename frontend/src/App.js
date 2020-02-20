import React, { useState } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Home, Comment, Login, MyPage, Signup, Register, Webtoon,Episode,Upload,EditInfo,EditRegister} from './Pages';
import AuthRoute from './AuthRoute';

export default function App() {


  return (
    <div>
      <Switch>
        <Route 
          exact path="/" 
          render={props => (
            <Home {...props}/>
          )}
        />
        <AuthRoute
          exact path="/mypage"
          render={props => <MyPage {...props}/>}
        />
        <Route
          exact path="/login"
          render={props => (
            <Login/>
          )}
        />
        <Route 
          path="/mypage/comment" 
          render={props => (
            <Comment/>
          )}
        />
        <Route path="/login/signup" component={Signup} />
        <Route path="/mypage/register" component={Register} />
        <Route exact path="/webtoon" component={Webtoon}/>
        <Route path="/webtoon/episode" component={Episode}/>
        <Route path="/mypage/upload" component={Upload}/>
        <Route path="/mypage/editInfo" component={EditInfo}/>
        <Route path="/mypage/editRegister" component={EditRegister}/>
      </Switch>
    </div>
  );

}
