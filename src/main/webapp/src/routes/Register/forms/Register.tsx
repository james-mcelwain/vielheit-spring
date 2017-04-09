import { Button, Cascader, Checkbox, Col, Form, Icon, Input, Row, Select, Tooltip } from 'antd'
import * as React from 'react'
import {RegisterUserRequest} from '../modules/register'
import SyntheticEvent = React.SyntheticEvent

const Option = Select.Option

class RegisterForm extends React.Component<any, { passwordDirty: boolean }> {
  public getInitialState() {
    return {
      passwordDirty: false,
    }
  }
  public isPristine() {
    const fields = ['firstName', 'lastName', 'emailAddress', 'password', 'confirm']
    const fieldVals = fields.map(this.props.form.getFieldValue)
    return !fieldVals.every((x) => x !== void 0)
  }
  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFieldsAndScroll((err: Error, values: RegisterUserRequest) => {
      if (!err) {
        this.props.register(values)
      }
    })
  }
  public handlePasswordBlur(e: any) {
    const value = e.target.value
    this.setState({ passwordDirty: this.state.passwordDirty || !!value })
  }
  public checkPassword(rule: any, value: any, callback: any) {
    const form = this.props.form
    if (value && value !== form.getFieldValue('password')) {
      callback('Two passwords that you enter is inconsistent!')
    } else {
      callback()
    }
  }
  public checkConfirm(rule: any, value: any, callback: any) {
    const form = this.props.form
    if (value && this.state.passwordDirty) {
      form.validateFields(['confirm'], { force: true })
    }
    callback()
  }

  public render() {
    const { getFieldDecorator } = this.props.form
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 14 },
    }

    const prefixSelector = getFieldDecorator('prefix', {
      initialValue: '86',
    })(
      <Select className="icp-selector">
        <Option value="86">+86</Option>
      </Select>,
    )
    return (
      <Form onSubmit={this.handleSubmit}>
      <Form.Item
          {...formItemLayout}
          label="First Name"
        >
          {getFieldDecorator('firstName', {
            rules: [{ required: true, message: 'Please input your first name!' }],
          })(
            <Input />,
          )}
      </Form.Item>
      <Form.Item
          {...formItemLayout}
          label="Last Name"
        >
          {getFieldDecorator('lastName', {
            rules: [{ required: true, message: 'Please input your last name!' }],
          })(
            <Input />,
          )}
        </Form.Item>
        <Form.Item
          {...formItemLayout}
          label="Email"
          hasFeedback
        >
          {getFieldDecorator('emailAddress', {
            rules: [{
              type: 'email', message: 'The input is not a valid email address!',
            }, {
              required: true, message: 'Please input your email!',
            }],
          })(
            <Input />,
          )}
        </Form.Item>
        <Form.Item
          {...formItemLayout}
          label="Password"
          hasFeedback
        >
          {getFieldDecorator('password', {
            rules: [{
              required: true, message: 'Please input your password!',
            }, {
              validator: this.checkConfirm,
            }],
          })(
            <Input type="password" onBlur={this.handlePasswordBlur} />,
          )}
        </Form.Item>
        <Form.Item
          {...formItemLayout}
          label="Confirm Password"
          hasFeedback
        >
          {getFieldDecorator('confirm', {
            rules: [{
              required: true, message: 'Please confirm your password!',
            }, {
              validator: this.checkPassword,
            }],
          })(
            <Input type="password" />,
          )}
        </Form.Item>
        <Form.Item>
          <Button disabled={this.isPristine()} type="primary" htmlType="submit" size="large">Register</Button>
        </Form.Item>
      </Form>
    )
  }
}

export default Form.create()(RegisterForm)
