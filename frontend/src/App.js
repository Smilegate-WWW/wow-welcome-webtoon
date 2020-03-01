import React, { useState } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Home, Comment, Login, MyPage, Signup, Register, MyEpisode,Webtoon,Episode,Upload,EditInfo,EditRegister,EditEpisode,EditUpload,Preview} from './Pages';
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

        <AuthRoute 
          path="/mypage/comment" 
          render={props => (
            <Comment {...props}/>
          )}
        />
        <Route path="/login/signup" component={Signup} />
        <AuthRoute path="/mypage/register" render={props => <Register {...props} />}/>
        <Route exact path="/webtoon" component={Webtoon}/>
        <Route path="/webtoon/episode" component={Episode}/>
        <AuthRoute path="/mypage/upload" render={props => <Upload {...props}/>}/>
        <AuthRoute path="/mypage/editInfo" render={props => <EditInfo {...props}/>}/>
        <AuthRoute path="/mypage/editRegister" render={props => <EditRegister {...props}/>}/>
        <AuthRoute path="/mypage/editEpisode" render={props => <EditEpisode {...props}/>}/>
        <AuthRoute path="/mypage/editUpload" render={props => <EditUpload {...props}/>}/>
        <AuthRoute path="/mypage/upload/preview" render={props => <Preview {...props}/>}/>
        <AuthRoute path="/mypage/myEpisode" render={props => <MyEpisode {...props}/>}/>
      </Switch>
    </div>
  );

}
