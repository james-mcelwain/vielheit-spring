import React, {Component, SyntheticEvent} from 'react';
import {Button, Form, Icon, Input,} from 'antd';
import {State} from "./State";
import {WrappedFormUtils} from "antd/lib/form/Form";

function hasErrors(fieldsError: any): boolean {
    return Object.keys(fieldsError).some(field => !!fieldsError[field]);
}

class HorizontalLoginForm extends Component<{ state: State, form: WrappedFormUtils }> {
    componentDidMount() {
        this.props.form.validateFields();
    }

    handleSubmit = (e: SyntheticEvent) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            const { username, password } = values
            if (!err) {
                this.props.state.logIn(username, password)
            }
        });
    }

    render() {
        const {
            getFieldDecorator, getFieldsError, getFieldError, isFieldTouched,
        } = this.props.form;

        // Only show error after a field is touched.
        const usernameError = isFieldTouched('username') && getFieldError('username');
        const passwordError = isFieldTouched('password') && getFieldError('password');
        return (
            <Form layout="inline" onSubmit={this.handleSubmit}>
                <Form.Item
                    validateStatus={usernameError ? 'error' : ''}
                    help={usernameError || ''}
                >
                    {getFieldDecorator('username', {
                        rules: [{required: true, message: 'Please input your username!'}],
                    })(
                        <Input prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>} placeholder="Username"/>
                    )}
                </Form.Item>
                <Form.Item
                    validateStatus={passwordError ? 'error' : ''}
                    help={passwordError || ''}
                >
                    {getFieldDecorator('password', {
                        rules: [{required: true, message: 'Please input your Password!'}],
                    })(
                        <Input prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>} type="password"
                               placeholder="Password"/>
                    )}
                </Form.Item>
                <Form.Item>
                    <Button
                        type="primary"
                        htmlType="submit"
                        disabled={hasErrors(getFieldsError())}
                    >
                        Log in
                    </Button>
                </Form.Item>
            </Form>
        );
    }
}

export default Form.create({name: 'horizontal_login'})(HorizontalLoginForm);
