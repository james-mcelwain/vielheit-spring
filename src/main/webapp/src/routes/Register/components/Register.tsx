import React from 'react'
import RegisterForm from '../forms/Register'
import './Register.scss'
import {RegisterUserRequest} from "../modules/register"

export const Register = (props: any) => (
  <div className="register-container">
    <RegisterForm  {...props}/>
  </div>
)

export default Register
