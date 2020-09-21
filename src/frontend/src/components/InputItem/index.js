import React from 'react';
import { Input, Form } from 'antd';

const InputItem = ({ name, rules, label, ...rest }) => {
  return (
    <Form.Item name={name} rules={rules} label={label}>
      <Input {...rest} />
    </Form.Item>
  )
};

export default InputItem;