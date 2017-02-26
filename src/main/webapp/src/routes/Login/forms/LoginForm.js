import React, { Componen } from 'react'
import { Alert, Form, Icon, Input, Button, Checkbox } from 'antd'
import { Link } from 'react-router'
import './LoginForm.scss'

const FormItem = Form.Item

const LoginForm = Form.create()(React.createClass({
  handleSubmit(e) {
    e.preventDefault()
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.props.login(values)
      }
    })
  },
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
  },
}))

export default LoginForm
