import * as React from 'react'
import { Form, Input, Button, Alert } from 'antd'
import { FormComponentProps } from 'antd/lib/form/Form'
import { AbstractionType } from '../modules/editor'

interface AbstractionTypeFormProps {
  model: AbstractionType
  submitAbstractionType: (type: AbstractionType) => void
}

interface AbstractionTypeFormState {
  error: null | Error
}

type Props = FormComponentProps & AbstractionTypeFormProps

class AbstractionTypeForm extends React.Component<Props, AbstractionTypeFormState> {
  public state: AbstractionTypeFormState = {
    error: null
  }

  public handleSubmit(e: React.SyntheticEvent<{}>) {
    e.preventDefault()
    this.props.form.validateFields(async (err: Error, values: AbstractionType) => {
      if (!err) {
        // update....
        if (this.props.model.id) {
          values.id = this.props.model.id
        }

        try {
          await this.props.submitAbstractionType(values)
        } catch(error) {
          this.setState({
            error,
          })
        }

      }
    })
  }

  public render() {
    const { getFieldDecorator } = this.props.form
    const { error } = this.state

    console.log(this.props.form)

    const formItemLayout = {
      labelCol: {
        xs: {span: 24},
        sm: {span: 6},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 14},
      },
    }

    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 24,
          offset: 0,
        },
        sm: {
          span: 14,
          offset: 6,
        },
      },
    }

    return (
      <Form onSubmit={this.handleSubmit.bind(this)}>
        {error && <Alert type="error" message={error.message}/>}
        <Form.Item {...formItemLayout} label="Type">
          {getFieldDecorator('type', {
            initialValue: this.props.model.type,
            rules: [{
              required: true,
              message: 'required',
            }],
          })(<Input placeholder="type"/>)}
        </Form.Item>
        <Form.Item {...formItemLayout} label="Description">
          {getFieldDecorator('description', {
            initialValue: this.props.model.description,
            rules: [{
              required: true,
              message: 'required',
            }],
          })(<Input placeholder="description"/>)}
        </Form.Item>
        <Form.Item {...tailFormItemLayout}>
          <Button type="primary" htmlType="submit">Submit</Button>
        </Form.Item>
      </Form>
    )
  }
}

export default Form.create<AbstractionTypeFormProps>()(AbstractionTypeForm)
