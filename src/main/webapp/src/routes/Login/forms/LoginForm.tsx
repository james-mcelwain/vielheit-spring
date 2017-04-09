import {SyntheticEvent} from '@types/react';
import { Alert, Button, Checkbox, Form, Icon, Input } from 'antd'
import * as React from 'react'
import { Link } from 'react-router'
import {LoginState, LoginUserRequest} from '../modules/login'
import './LoginForm.scss'

const FormItem = Form.Item

class LoginForm extends React.Component<{ loginState: LoginState, login: (req: LoginUserRequest) => void, form: any }, {}> {
  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, values: LoginUserRequest) => {
      if (!err) {
        this.props.login(values)
      }
    })
  }

  public render() {
    const {
      getFieldDecorator,
    } = this.props.form

    const {
      error,
      loggingIn,
    } = this.props.loginState

    return (
      <Form onSubmit={this.handleSubmit.bind(this)} className="login-form">
        <span>{error && <Alert message="Invalid email or password" type="error"/>}</span>
        <FormItem>
          {getFieldDecorator('emailAddress', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input addonBefore={<Icon type="user" />} placeholder="Email" />,
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input addonBefore={<Icon type="lock" />} type="password" placeholder="Password" />,
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
