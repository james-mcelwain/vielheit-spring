import {Button, Form, Input, Select} from 'antd'
import * as React from 'react'
import {store} from '../../../main'
import './EditorForm.scss'
import {AbstractionType} from '../../../domain/AbstractionType'
import {EditorModule} from '../modules/editor'
import SyntheticEvent = React.SyntheticEvent
import AbstractionTypesRemoteSelect from '../inputs/AbstractionTypesRemoteSelect'
import {FormComponentProps} from 'antd/lib/form/Form'
import {EditorProps} from 'routes/Editor/containers/EditorContainer'

const FormItem = Form.Item
const Option = Select.Option

class AbstractionForm extends React.Component<EditorProps & EditorModule & FormComponentProps, {}> {
  private currentAbstractionType: AbstractionType

  public handleSelectChange(type: any[]) {
    const abstractionType = this.props.editor.abstractionTypes.find((t: AbstractionType) => t.type === type[0])
    if (abstractionType) {
      this.currentAbstractionType = abstractionType
    }
  }

  public handleSubmit(e: SyntheticEvent<any>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, resource: { description: string, name: string }) => {
      if (!err) {
        const user = store.getState().application.user
        if (user) {
          const abstractionType = {
            description: resource.description,
            name: resource.name,
            abstractionType: this.currentAbstractionType,
          }

          this.props.submitAbstraction(abstractionType)
        }
      }
    })
  }

  public render() {
    const {
      getFieldDecorator,
    } = this.props.form

    const { submitting } = this.props.editor

    return (
      <Form onSubmit={this.handleSubmit.bind(this)} className="editor-form">

        <FormItem>
          {getFieldDecorator('type', {
            rules: [{ required: true, message: 'required' }],
          })(
            <AbstractionTypesRemoteSelect
              handleSelectChange={this.handleSelectChange.bind(this)}
              getAbstractionTypes={this.props.getAbstractionTypes.bind(this)}
              editor={this.props.editor}
            />,
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('name', {
            rules: [{ required: true, message: 'required' }],
          })(
            <Input type="textarea" placeholder="Name"/>,
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

export default Form.create()(AbstractionForm)
