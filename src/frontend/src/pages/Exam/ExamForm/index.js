import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { Form, Input, Button, Divider, Space, message } from 'antd';
import { MinusOutlined, PlusOutlined } from '@ant-design/icons';
import { createExam, updateExam } from '../../../actions/subject';
import QuestionForm from '../QuestionForm';
import { findSubjectByCode } from '../../../utils/helpers';
import styles from './index.module.less';

const ExamForm = () => {
  const dispatch = useDispatch();
  const { code, examId } = useParams();
  const { identity } = useSelector(state => state.user);
  const { subjectList, examList } = useSelector(state => state.subject);
  const [initialData, setInitialData] = useState(null);

  useEffect(() => {
    if (examId && examList) {
      const exam = examList.find(e => e.examId === Number(examId));
      const { showName, description, questions } = exam;

      // de-transform the value from the server so that it can be set 
      // back to the form
      const deTransformedQuestions = [];
      questions.forEach(question => {
        const temp = {};
        if (question.questionType.toLowerCase() === "multiplechoice") {
          temp.questionType = "multipleChoice";
          // convert "["A","B","C"]" to ["A", "B", "C"]
          const options = JSON.parse(question.options);
          temp.option1 = options[0];
          temp.option2 = options[1];
          if (options.length > 2) {
            // put the rest of the options in the addtional <Select/> field
            const remainOptions = options.slice(2);
            temp.options = remainOptions.map((o, i) => ({ [`option${i + 3}`]: o }));
          } else {
            temp.options = undefined;
          }
        } else {
          temp.questionType = "shortAnswer";
        }

        temp.questionDescription = question.description;
        temp.questionMark = question.mark;
        temp.questionId = question.questionId;
        deTransformedQuestions.push(temp);
      });

      setInitialData({
        showName,
        description,
        questions: deTransformedQuestions,
      })
    }
  }, [examId, examList]);

  const onFinish = values => {
    if (!values.questions || values.questions.length === 0) {
      message.error('Please add at least one question!');
      return;
    }

    // transform the value from the form so that it complies with API
    const transformedQuestions = [];
    values.questions.forEach(({
      questionType, questionDescription, questionMark,
      options, option1, option2, questionId
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
        questionId
      });
    });

    const data = {
      ...values,
      questions: transformedQuestions,
      subjectId: findSubjectByCode(subjectList, code).id,
      userId: identity.userId,
      userType: identity.userType,
    };

    // if has examId, it means we are updating the exam, not creating a new one
    if (examId) {
      data.examId = Number(examId);
      dispatch(updateExam(data, `/dashboard/subjects/${code}`));
      return;
    }

    dispatch(createExam(data, `/dashboard/subjects/${code}`));
  };

  return (
    <div className={styles.formContainer}>
      {(!examId || (examId && initialData)) && (
        <Form
          name="newExamForm"
          onFinish={onFinish}
          initialValues={examId ? initialData : null}
        >
          <Divider orientation="left">Basic Info</Divider>
          <Form.Item
            label="Exam Title"
            name="showName"
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
            name="description"
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

                        {(examId && initialData) && (
                          <Form.Item
                            {...field}
                            label="Question Id"
                            name={[field.name, 'questionId']}
                            fieldKey={[field.fieldKey, 'questionId']}

                          >
                            <Input disabled />
                          </Form.Item>
                        )}

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
      )}
    </div>
  )
};

export default ExamForm;