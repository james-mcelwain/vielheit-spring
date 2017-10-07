import { Button, Form, Input, Select } from 'antd'
import * as React from 'react'

const Option = Select.Option

class RegisterForm extends React.Component<any, { passwordDirty: boolean }> {
  public render() {
    return (<div>form!</div>)
  }
}

export default Form.create()(RegisterForm)
