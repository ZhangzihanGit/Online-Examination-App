import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { Form, Input, Button, Divider, Space, message } from 'antd';
import { MinusOutlined, PlusOutlined } from '@ant-design/icons';
import { createExam } from '../../../actions/subject';
import QuestionForm from '../QuestionForm';
import { findSubjectByCode } from '../../../utils/helpers';
import styles from './index.module.less';

import { CREATE_EXAM } from '../../../constants/actions';

const ExamForm = () => {
  const dispatch = useDispatch();
  const { code } = useParams();
  console.log(code);
  const { identity } = useSelector(state => state.user);
  const { subjectList } = useSelector(state => state.subject);

  const onFinish = values => {
    console.log('Received values of form:', values);
    // dispatch({
    //   type: CREATE_EXAM,
    //   payload: values
    // });

    if (!values.questions || values.questions.length === 0) {
      message.error('Please add at least one question!');
      return;
    }

    const transformedQuestions = [];
    values.questions.forEach(({
      questionType, questionDescription, questionMark,
      options, option1, option2
    }) => {
      let newOptions = [];
      if (options) {
        options.forEach(option => newOptions = [...newOptions, ...Object.values(option)]);
      }
      if (option1 && option2) {
        newOptions = [...newOptions, option1, option2];
      }
      transformedQuestions.push({
        questionType,
        description: questionDescription,
        mark: Number(questionMark),
        options: newOptions,
      });
    });

    const data = {
      ...values,
      questions: transformedQuestions,
      subjectId: findSubjectByCode(subjectList, code).id,
      // userId: identity.userId,
      // userType: identity.userType,
    };
    dispatch(createExam(data));
  };

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

                      <Form.Item
                        {...field}
                        label="Mark"
                        name={[field.name, 'questionMark']}
                        fieldKey={[field.fieldKey, 'questionMark']}
                        rules={[
                          {
                            required: true,
                            message: "Please enter question mark",
                          }, {
                            type: "number",
                            min: 1,
                            max: 100,
                            transform(value) {
                              return Number(value);
                            },
                            message: "The mark must between 1 - 100"
                          }
                        ]}
                      >
                        <Input placeholder="Question mark..." />
                      </Form.Item>

                      <QuestionForm
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

export default ExamForm;