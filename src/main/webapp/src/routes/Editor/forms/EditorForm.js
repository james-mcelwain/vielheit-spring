import React, { Componen } from 'react'
import { Alert, Form, Icon, Input, Button, Checkbox, Select } from 'antd'
import { Link } from 'react-router'
import { store } from '../../../main'
import './EditorForm.scss'


const FormItem = Form.Item
const Option = Select.Option;

const children = [];
for (let i = 10; i < 36; i++) {
  children.push(<Option key={i.toString(36) + i}>{i.toString(36) + i}</Option>);
}

function handleChange(value) {
  console.log(`selected ${value}`);
}


const EditorForm = Form.create()(React.createClass({
  handleSubmit(e) {
    e.preventDefault()
    this.props.form.validateFields((err, entry) => {
      if (!err) {
        entry.user = { userId: store.getState().user.id }
        this.props.submit(entry)
      }
    })
  },
  render() {
    const {
      getFieldDecorator
    } = this.props.form

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit} className="editor-form">
        <FormItem>
          {getFieldDecorator('title', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="text" placeholder="Title"/>
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('body', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="textarea" placeholder="Entry"/>
          )}
        </FormItem>
        <Select
          mode="multiple"
          style={{ width: '100%' }}
          placeholder="Please select"
          onChange={handleChange}
        >
          {children}
        </Select>
        <FormItem>
          <Button className="editor-form-button" disabled={submitting} type="primary" htmlType="submit">
            Submit
          </Button>
        </FormItem>
      </Form>
    )
  },
}))

export default EditorForm
