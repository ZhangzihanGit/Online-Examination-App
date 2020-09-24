import React, { useEffect, useState } from 'react';
import { Form, Input, Button, Select, Row, Col, Divider, Space } from 'antd';
import { MinusOutlined, PlusOutlined } from '@ant-design/icons';
import CreateQuestion from '../CreateQuestion';
import styles from './index.module.less';
import QuestionSelector from '../QuestionSelector';

const { Option } = Select;

const formItemLayout = {
  labelCol: {
    sm: { span: 8 },
  },
  wrapperCol: {
    sm: { span: 16 },
  },
};
const formItemLayoutWithOutLabel = {
  wrapperCol: {
    xs: { span: 24, offset: 0 },
    sm: { span: 20, offset: 4 },
  },
};

const NewQuestionForm = () => {
  // const [questionCount, setQuestionCount] = useState(1);
  // const [questionCountArr, setquestionCountArr] = useState([]);

  const onFinish = values => {
    console.log('Received values of form:', values);
  };

  // useEffect(() => {
  //   setquestionCountArr([...questionCountArr, questionCount])
  // }, [questionCount]);

  return (
    <div className={styles.formContainer}>
      <Form
        name="newExamForm"
        onFinish={onFinish}
      >
        <Divider orientation="left">Basic Info</Divider>
        <Form.Item
          label="Exam Title"
          name="title"
          rules={[{
            required: true,
            message: "Please enter exam title",
          }]}
        >
          <Input
            placeholder="Enter the exam title..."
          />
        </Form.Item>

        <Form.Item
          label="Exam Description"
          name="examDescription"
          rules={[{
            required: true,
            message: "Please enter exam description",
          }]}
        >
          <Input.TextArea autoSize placeholder="Exam description..." />
        </Form.Item>

        <Form.List name={`questions`}>
          {(fields, { add, remove }) => {
            return (
              <div>
                {fields.map((field, index) => {
                  return (
                    <Space key={field.key} direction="vertical">
                      {/* In each question, has a divider and question type */}
                      <Divider key={field.key} orientation="left">{`Question ${index + 1}`}</Divider>

                      <Form.Item
                        {...field}
                        label="Description"
                        name={[field.name, 'questionDescription']}
                        fieldKey={[field.fieldKey, 'questionDescription']}
                        rules={[{
                          required: true,
                          message: "Please enter question description",
                        }]}
                      >
                        <Input.TextArea autoSize placeholder="Question description..." />
                      </Form.Item>

                      <QuestionSelector
                        key={field.key}
                        field={field}
                        questionCount={index + 1}
                      />

                      <Form.Item>
                        <Button
                          danger
                          type="dashed"
                          onClick={() => {
                            remove(field.name);
                          }}
                          block
                        >
                          <MinusOutlined /> Delete Question
                        </Button>
                      </Form.Item>
                    </Space>
                  )
                })}

                <Form.Item>
                  <Button
                    className={styles.addQuestionButton}
                    type="dashed"
                    onClick={() => {
                      add();
                    }}
                    block
                  >
                    <PlusOutlined /> Add Question
                  </Button>
                </Form.Item>
              </div>
            );
          }}
        </Form.List>

        <Form.Item>
          <Button type="primary" htmlType="submit" className={styles.submitButton}>
            Submit
          </Button>
        </Form.Item>
      </Form>
    </div>
  )
};

export default NewQuestionForm;