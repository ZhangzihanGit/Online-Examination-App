import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Form, Input, Button, Select } from 'antd';
import { createSubject } from '../../../actions/subject';
import styles from './index.module.less';

// const { Option } = Select;

const SubjectForm = () => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);

  const onFinish = values => {
    console.log(values);
    dispatch(createSubject({
      ...values,
      userId: identity.userId,
    }, `/dashboard/subjects`));
  }

  return (
    <div className={styles.formContainer}>
      <Form
        name="newSubjectForm"
        onFinish={onFinish}
      >
        <Form.Item
          label="Subject Code"
          name="showName"
          rules={[{
            required: true,
            message: "Please enter subject code",
          }]}
        >
          <Input placeholder="Enter the subject code" />
        </Form.Item>
        <Form.Item
          label="Subject Name"
          name="description"
          rules={[{
            required: true,
            message: "Please enter subject name",
          }]}
        >
          <Input placeholder="Enter the subject name" />
        </Form.Item>

        {/* TODO: let admin assign instructors and students for this subject */}
        {/* <Form.Item
          name="instructor"
          label="Instructor"
          rules={[
            {
              required: true,
              message: "Please assign at one instructor"
            },
          ]}
        >
          <Select
            placeholder="Assign an instructor"
            // onChange={onGenderChange}
            mode="multiple"
            allowClear
          >
            <Option value="male">male</Option>
            <Option value="female">female</Option>
            <Option value="other">other</Option>
          </Select>
        </Form.Item> */}

        <Form.Item>
          <Button type="primary" htmlType="submit" className={styles.submitButton}>
            Create Subject
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default SubjectForm;