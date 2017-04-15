import { Alert, Button, Checkbox, Form, Icon, Input, Select } from 'antd'
import * as React from 'react'
import { Link } from 'react-router'
import Entry from '../../../domain/Entry'
import { store } from '../../../main'
import './EditorForm.scss'
import SyntheticEvent = React.SyntheticEvent
import EditorForm from './EditorForm'
import {EntityType} from '../../../domain/EntityType'

const FormItem = Form.Item
const Option = Select.Option;

class EntityTypeForm extends React.Component<{ form: any, submitting: boolean, submitEntityType: (e: EntityType) => any }, {}> {
  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, resource: { description: string, type: string }) => {
      if (!err) {
        const user = store.getState().application.user
        if (user) {
          const entityType = {
            description: resource.description,
            id : {
              userId: user.id,
              type: resource.type,
            },
          }

          this.props.submitEntityType(entityType)
        }
      }
    })
  }

  public render() {
    const {
      getFieldDecorator,
    } = this.props.form

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit.bind(this)} className="editor-form">
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

export default Form.create()(EntityTypeForm)
