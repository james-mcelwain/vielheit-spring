import * as React from 'react'
import { Form, Input, Button } from 'antd'
import { FormComponentProps } from "antd/lib/form/Form";

interface AbstractionFormProps {

}

type Props = FormComponentProps & AbstractionFormProps
class AbstractionForm extends React.Component<Props, {}> {
  public handleSubmit() {

  }

  public render() {
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
          <Button type="primary">Submit</Button>
        </Form.Item>
      </Form>
    )
  }
}

export default Form.create()(AbstractionForm)
