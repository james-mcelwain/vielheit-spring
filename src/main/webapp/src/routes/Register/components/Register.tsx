import * as React from 'react'
import RegisterForm from '../forms/Register'
import './Register.scss'

export const Register = (props: any) => (
  <div className="register-container">
    <RegisterForm  {...props}/>
  </div>
)

export default Register
