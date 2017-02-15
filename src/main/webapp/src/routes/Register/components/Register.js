import React from 'react'
import RegisterForm from '../forms/Register'
import './Register.scss'

export const Register = (props) => (
  <div className="register-container">
    <RegisterForm  {...props}/>
  </div>
)

Register.propTypes = {
  register     : React.PropTypes.func.isRequired,
}

export default Register
