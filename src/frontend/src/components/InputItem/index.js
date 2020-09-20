import React, { useState, useEffect } from 'react';
import { Input, Form } from 'antd';
import styles from './index.module.less';

const InputItem = ({ name, rules, label, ...rest }) => {
  return (
    <Form.Item name={name} rules={rules} label={label}>
      <Input {...rest} />
    </Form.Item>
  )
};

export default InputItem;