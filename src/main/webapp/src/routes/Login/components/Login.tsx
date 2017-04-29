import * as React from 'react'
import LoginForm from '../forms/LoginForm'
import './Login.scss'
import {LoginProps} from '../containers/LoginContainer'

export const Login = (props: LoginProps) => (
  <div className="login-container">
    <LoginForm  {...props}/>
  </div>
)

export default Login
