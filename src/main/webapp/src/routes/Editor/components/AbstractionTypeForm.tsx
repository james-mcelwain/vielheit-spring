import * as React from 'react'
import { Form, Input, Button } from 'antd'
import { FormComponentProps } from "antd/lib/form/Form";
import { AbstractionType } from '../modules/editor'

interface AbstractionTypeFormProps {
  submitAbstractionType: (type: AbstractionType) => void
}

type Props = FormComponentProps & AbstractionTypeFormProps
class AbstractionTypeForm extends React.Component<Props, {}> {
  public handleSubmit(e: React.SyntheticEvent<{}>) {
    e.preventDefault()
    this.props.form.validateFields((err: Error, values: AbstractionType) => {
      if (!err) {
        this.props.submitAbstractionType(values)
      }
    })
  }

  public render() {
    console.log(this.props)
    const {getFieldDecorator} = this.props.form

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
        <Form.Item {...formItemLayout} label="type">
          {getFieldDecorator('type', {
            rules: [{
              required: true,
              message: 'required'
            }]
          })(<Input placeholder="type"/>)}
        </Form.Item>
        <Form.Item {...formItemLayout} label="description">
          {getFieldDecorator('description', {
            rules: [{
              required: true,
              message: 'required'
            }]
          })(<Input placeholder="description"/>)}
        </Form.Item>
        <Form.Item {...tailFormItemLayout}>
          <Button type="primary" htmlType="submit">Submit</Button>
        </Form.Item>
      </Form>
    )
  }
}

export default Form.create()(AbstractionTypeForm)
