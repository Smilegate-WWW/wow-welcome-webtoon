import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import {Home, CommentPage,LoginPage,MyPage,SignupPage} from './Pages';

class App extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Home}/>
        <Route exact path="/MyPage" component={MyPage}/>
        <Route exact path="/LoginPage" component={LoginPage}/>
        <Route path="/MyPage/CommentPage" component={CommentPage}/>
        <Route path="/LoginPage/SignupPage" component={SignupPage}/>
      </div>
    );
  }
}

export default App;