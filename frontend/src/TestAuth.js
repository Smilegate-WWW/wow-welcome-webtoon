const users = [
    { id: 'kim', pw: '123'},
    { id: 'lee', pw: '456'},
    { id: 'par', pw: '789'}
  ]
  
  export default function signIn({userid, pw}) {
    const user = users.find(user => user.id === userid && user.pw == pw);
    if (user === undefined) throw new Error();
    return user;
  }