import { Alert, Button, Checkbox, Form, Icon, Input, Select } from 'antd'
import * as React from 'react'
import { Link } from 'react-router'
import Entry from '../../../domain/Entry'
import { store } from '../../../main'
import './EditorForm.scss'
import SyntheticEvent = React.SyntheticEvent

const FormItem = Form.Item
const Option = Select.Option;

const children: any[] = [];
for (let i = 10; i < 36; i++) {
  children.push(<Option key={i.toString(36) + i}>{i.toString(36) + i}</Option>);
}

function handleChange(value: any) {
  // pass
}

class EntryForm extends React.Component<{ form: any, submitting: boolean, submit: (entry: Entry) => void }, {}> {
  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, entry: Entry) => {
      if (!err) {
        entry.user = { userId: store.getState().user.id() }
        this.props.submit(entry)
      }
    })
  }

  public render() {
    const {
      getFieldDecorator,
    } = this.props.form

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit} className="editor-form">
        <FormItem>
          {getFieldDecorator('title', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="text" placeholder="Title"/>,
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('body', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="textarea" placeholder="Entry"/>,
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
  }
}

export default Form.create()(EntryForm)
