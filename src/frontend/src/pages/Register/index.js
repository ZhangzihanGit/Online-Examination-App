import React from 'react';
import { Form } from 'antd';
import InputItem from '../../components/InputItem';
import SubmitButton from '../../components/SubmitButton';
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