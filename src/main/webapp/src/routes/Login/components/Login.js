import React from 'react'
import LoginForm from '../forms/LoginForm'
import './Login.scss'

export const Login = (props) => (
  <div className="login-container">
    <LoginForm  {...props}/>
  </div>
)

Login.propTypes = {
  login     : React.PropTypes.func.isRequired,
}

export default Login
