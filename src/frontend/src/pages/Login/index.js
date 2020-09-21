import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { Form } from 'antd';
import { UserOutlined, LockTwoTone } from '@ant-design/icons';
import { login } from '../../actions/user';
import InputItem from '../../components/InputItem';
import SubmitButton from '../../components/SubmitButton';

const Login = () => {
  const [form] = Form.useForm();
  const history = useHistory();
  const dispatch = useDispatch()

  const handleLogin = (values) => {
    // TODO: add real authentication
    dispatch(login(values));

    // after login, if success, redirect to dashboard
    history.push("/dashboard");
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