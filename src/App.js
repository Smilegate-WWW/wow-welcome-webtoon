import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import {Home, CommentPage,LoginPage,MyPage,SignupPage} from './Pages';

class App extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Home}/>
        <Route exact path="/mypage" component={MyPage}/>
        <Route exact path="/login" component={LoginPage}/>
        <Route path="/mypage/comment" component={CommentPage}/>
        <Route path="/loginpage/signup" component={SignupPage}/>
      </div>
    );
  }
}

export default App;