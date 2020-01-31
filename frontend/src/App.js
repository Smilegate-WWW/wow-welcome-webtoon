import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import {Home, Comment,Login,MyPage,Signup} from './Pages';

class App extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Home}/>
        <Route exact path="/mypage" component={MyPage}/>
        <Route exact path="/login" component={Login}/>
        <Route path="/mypage/comment" component={Comment}/>
        <Route path="/login/signup" component={Signup}/>
      </div>
    );
  }
}

export default App;