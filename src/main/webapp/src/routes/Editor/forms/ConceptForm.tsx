import * as React from 'react'
import { Alert, Form, Icon, Input, Button, Checkbox, Select } from 'antd'
import { Link } from 'react-router'
import { store } from '../../../main'
import './EditorForm.scss'
import Entry from "../../../domain/Entry"
import SyntheticEvent = React.SyntheticEvent


const FormItem = Form.Item
const Option = Select.Option;

class ConceptForm extends React.Component<{ form: any, submitting: boolean, submit: (entry: Entry) => void }, {}> {
  handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, entry: Entry) => {
      if (!err) {
        entry.user = { userId: store.getState().user.id() }
        this.props.submit(entry)
      }
    })
  }
  render() {
    const {
      getFieldDecorator
    } = this.props.form

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit} className="editor-form">
        <FormItem>
          {getFieldDecorator('name', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="text" placeholder="Concept"/>
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('description', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="textarea" placeholder="Description"/>
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

export default Form.create()(ConceptForm)
