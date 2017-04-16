import {Button, Form, Input, Select} from 'antd'
import * as React from 'react'
import {store} from '../../../main'
import './EditorForm.scss'
import {AbstractionType} from '../../../domain/AbstractionType'
import SyntheticEvent = React.SyntheticEvent

const FormItem = Form.Item
const Option = Select.Option;

class AbstractionTypeForm extends React.Component<{ form: any, submitting: boolean, submitAbstractionType: (e: AbstractionType) => any }, {}> {
  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, resource: { description: string, type: string }) => {
      if (!err) {
        const user = store.getState().application.user
        if (user) {
          const abstractionType = {
            description: resource.description,
            id : {
              userId: user.id,
              type: resource.type,
            },
          }

          this.props.submitAbstractionType(abstractionType)
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

export default Form.create()(AbstractionTypeForm)
