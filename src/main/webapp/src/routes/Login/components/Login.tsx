import React from 'react'
import LoginForm from '../forms/LoginForm'
import './Login.scss'
import {LoginState} from "../modules/login"

export const Login = (props: { loginState: LoginState }) => (
  <div className="login-container">
    <LoginForm  {...props}/>
  </div>
)

export default Login
