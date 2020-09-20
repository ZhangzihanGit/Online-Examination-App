import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Tabs, Form, Row, Typography } from 'antd';
import { UserOutlined, LockTwoTone } from '@ant-design/icons';
import InputItem from '../../components/InputItem';
import SubmitButton from '../../components/SubmitButton';
import * as api from '../../api/test';
import styles from './index.module.less';

const layout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 18,
  },
};

const Register = () => {
  const [form] = Form.useForm();

  const handleRegisterUser = (values) => {
    console.log(values);
    // TODO: add real register
    // console.log('register success');
    // dispatch(register(values));
  }

  return (
    <>
      <Form
        {...layout}
        className={styles.register}
        form={form}
        onFinish={handleRegisterUser}
      >
        <InputItem
          name="username"
          label="Username"
          placeholder="Please enter username"
          size="large"
          rules={[
            {
              required: true,
              message: 'Please enter username'
            }
          ]}
        />
        <InputItem
          name="password"
          label="Password"
          placeholder="Please enter password"
          size="large"
          rules={[
            {
              required: true,
              message: 'Please enter username'
            }
          ]}
        />
        <SubmitButton>Register</SubmitButton>
      </Form>
    </>
  )
};

export default Register;