import {Button, Form, Input, Select} from 'antd'
import * as React from 'react'
import {store} from '../../../main'
import './EditorForm.scss'
import {AbstractionType} from '../../../domain/AbstractionType'
import {EditorState} from '../modules/editor'
import SyntheticEvent = React.SyntheticEvent

const FormItem = Form.Item
const Option = Select.Option;

class AbstractionForm extends React.Component<{ editor: EditorState, form: any, submitting: boolean, submitAbstractionType: (e: AbstractionType) => any }, {}> {
  public handleSelectChange() {
    return null
  }

  public getOptions() {
    return (this.props.editor && this.props.editor.abstractionTypes || []).map((t: any) => <Option value={t.id.type}>{t.id.type}</Option>)
  }

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
            onChange: this.handleSelectChange,
          })(
            <Select
              showSearch
              style={{ width: 200 }}
              placeholder="Select a person"
              optionFilterProp="children"
              onChange={this.handleSelectChange}
              filterOption={(input, option: any) => option.props.value.toLowerCase().indexOf(input.toLowerCase()) >= 0}
            >
              {this.getOptions()}
            </Select>,
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
