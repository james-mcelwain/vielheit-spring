import * as React from 'react'
import LoginForm from '../forms/LoginForm'
import {LoginState} from '../modules/login'
import './Login.scss'

export const Login = (props: { loginState: LoginState }) => (
  <div className="login-container">
    <LoginForm  {...props}/>
  </div>
)

export default Login
