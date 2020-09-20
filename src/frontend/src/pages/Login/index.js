import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { Form } from 'antd';
import { UserOutlined, LockTwoTone } from '@ant-design/icons';
import InputItem from '../../components/InputItem';
import SubmitButton from '../../components/SubmitButton';
import * as api from '../../api/test';

const Login = () => {
  const [form] = Form.useForm();
  const history = useHistory();

  const handleLogin = (values) => {
    console.log(values);

    // after login, redirect to dashboard
    history.push("/dashboard");
    // TODO: add real authentication
    // dispatch(login(values));
  }

  return (
    <>
      <Form
        form={form}
        onFinish={handleLogin}
      >
        <InputItem
          name="username"
          placeholder="Username"
          prefix={<UserOutlined style={{ color: '#1890ff' }} />}
          size="large"
          rules={[
            {
              required: true,
              message: 'Please enter your username'
            }
          ]}
        />
        <InputItem
          name="password"
          type="password"
          placeholder="Password"
          prefix={<LockTwoTone />}
          size="large"
          rules={[
            {
              required: true,
              message: 'Please enter your password'
            }
          ]}
        />
        <SubmitButton>Login</SubmitButton>
      </Form>
    </>
  );
}

export default Login;