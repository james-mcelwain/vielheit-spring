import React, { Component } from 'react'
import { Alert, Form, Icon, Input, Button, Checkbox } from 'antd'
import { Link } from 'react-router'
import './LoginForm.scss'
import {SyntheticEvent} from "@types/react";
import {LoginState, LoginUserRequest} from "../modules/login"

const FormItem = Form.Item

class LoginForm extends React.Component<{ loginState: LoginState, login: (req: LoginUserRequest) => void, form: any }, {}> {
  handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, values: LoginUserRequest) => {
      if (!err) {
        this.props.login(values)
      }
    })
  }
  render() {
    const {
      getFieldDecorator
    } = this.props.form

    const {
      error,
      loggingIn
    } = this.props.loginState

    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <span>{error && <Alert message="Invalid email or password" type="error"/>}</span>
        <FormItem>
          {getFieldDecorator('emailAddress', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input addonBefore={<Icon type="user" />} placeholder="Email" />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input addonBefore={<Icon type="lock" />} type="password" placeholder="Password" />
          )}
        </FormItem>
        <FormItem>
          <Button disabled={loggingIn} type="primary" htmlType="submit" className="login-form-button">
            Log in
          </Button>
          <Link to="/register"><i>register</i></Link>
        </FormItem>
      </Form>
    )
  }
}

export default Form.create()(LoginForm)
