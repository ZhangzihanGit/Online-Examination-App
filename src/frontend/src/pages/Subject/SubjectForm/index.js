import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Form, Input, Button, Select } from 'antd';
import { createSubject } from '../../../actions/subject';
import { getAllInstructors, getAllStudents } from '../../../actions/user';
import styles from './index.module.less';

const { Option } = Select;

const SubjectForm = () => {
  const dispatch = useDispatch();
  const { identity, instructorList, studentList } = useSelector(state => state.user);
  // const { identity } = useSelector(state => state.user);

  const onFinish = values => {
    dispatch(createSubject({
      ...values,
      userId: identity.userId,
      sessionId: identity.sessionId,
    }, `/dashboard/subjects`));
  };

  const handleInstructorClick = () => {
    // only fetch data if Redux store does not have it
    if (!instructorList) {
      dispatch(getAllInstructors({
        userId: identity.userId,
        userType: identity.userType,
        sessionId: identity.sessionId,
      }));
    }
  };

  const handleStudentClick = () => {
    // only fetch data if Redux store does not have it
    if (!studentList) {
      dispatch(getAllStudents({
        userId: identity.userId,
        userType: identity.userType,
        sessionId: identity.sessionId,
      }));
    }
  };

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

        {/* let admin assign instructors and students for this subject */}
        <Form.Item
          name="instructors"
          label="Instructors"
          rules={[
            {
              required: true,
              message: "Please assign at least one instructor"
            },
          ]}
        >
          <Select
            placeholder="Assign instructors"
            onClick={handleInstructorClick}
            mode="multiple"
            allowClear
          >
            {instructorList && instructorList.map(i => (
              <Option key={i.userId} value={i.userId}>{i.showName}</Option>
            ))}
          </Select>
        </Form.Item>

        <Form.Item
          name="students"
          label="students"
          rules={[
            {
              required: true,
              message: "Please assign at least one student"
            },
          ]}
        >
          <Select
            placeholder="Assign students"
            onClick={handleStudentClick}
            mode="multiple"
            allowClear
          >
            {studentList && studentList.map(i => (
              <Option key={i.userId} value={i.userId}>{i.showName}</Option>
            ))}
          </Select>
        </Form.Item>

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