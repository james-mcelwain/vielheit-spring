import {Button, Form, Input, Select} from 'antd'
import * as React from 'react'
import {store} from '../../../main'
import './EditorForm.scss'
import {AbstractionType} from '../../../domain/AbstractionType'
import {EditorState} from '../modules/editor'
import SyntheticEvent = React.SyntheticEvent
import {Abstraction} from '../../../domain/Abstraction'

const FormItem = Form.Item
const Option = Select.Option;

class AbstractionForm extends React.Component<{ editor: EditorState, form: any, submitting: boolean, submitAbstraction: (e: Abstraction) => any }, {}> {
  private currentAbstractionType: AbstractionType;

  public handleSelectChange(type: string) {
    const abstractionType = this.getAbstractionTypes().find((t: AbstractionType) => t.id.type === type)
    if (abstractionType) {
      this.currentAbstractionType = abstractionType
    }
  }

  public getAbstractionTypes(): Array<AbstractionType> {
    return (this.props.editor && this.props.editor.abstractionTypes || [])
  }

  public getOptions() {
    return this.getAbstractionTypes().map((t: any, i: number) => <Option key={i} value={t.id.type}>{t.id.type}</Option>)
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

    const { submitting } = this.props

    return (
      <Form onSubmit={this.handleSubmit.bind(this)} className="editor-form">

        <FormItem>
          {getFieldDecorator('type', {
            rules: [{ required: true, message: 'required' }],
            onChange: this.handleSelectChange.bind(this),
          })(
            <Select
              showSearch
              style={{ width: 200 }}
              placeholder="Select a type"
              optionFilterProp="children"
              notFoundContent="No types found!"
              onChange={this.handleSelectChange.bind(this)}
              filterOption={(input, option: any) => option.props.value.toLowerCase().indexOf(input.toLowerCase()) >= 0}
            >
              {this.getOptions()}
            </Select>,
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
