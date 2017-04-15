import { Alert, Button, Checkbox, Form, Icon, Input, Select } from 'antd'
import * as React from 'react'
import { Link } from 'react-router'
import Entry from '../../../domain/Entry'
import { store } from '../../../main'
import './EditorForm.scss'
import SyntheticEvent = React.SyntheticEvent
import EditorForm from './EditorForm'

const FormItem = Form.Item
const Option = Select.Option;

class TimeForm extends EditorForm<{ form: any, submitting: boolean, submit: (entry: Entry) => void }, {}> {
  public render() {
    const {
      getFieldDecorator,
    } = this.props.form

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit} className="editor-form">
        <FormItem>
          {getFieldDecorator('type', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="text" placeholder="Type"/>,
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('description', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="textarea" placeholder="Description"/>,
          )}
        </FormItem>
        <FormItem>
          <Button className="editor-form-button" disabled={submitting} type="primary" htmlType="submit">
            Submit
          </Button>
        </FormItem>
      </Form>
    )
  }
}

export default Form.create()(TimeForm)
