import React, { useState } from 'react';
import { Form, Input, Button, Select, Divider } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';

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
    sm: { span: 16, offset: 8 },
  },
};

const createInitTwoOptionField = () => {

}

const CreateQuestion = ({ count, removeQuestion, questionField, question }) => {
  const [questionType, setQuestionType] = useState(null);

  console.log(question)

  const onFinish = values => {
    console.log('Received values of form:', values);
  };

  const onQuestionTypeChange = (value) => {
    if (value === "multipleChoice") {
      setQuestionType("multipleChoice");
    } else if (value === "shortAnswer") {
      setQuestionType("shortAnswer");
    }
  };

  return (
    <Form.Item name={question.name} key={`${question.fieldKey + 1}`}>
      <Divider
        orientation="left"
        key={`divier ${question.fieldKey + 1}`}
      >
        {`Question ${question.fieldKey + 1}`}
      </Divider>

      <Form.Item
        label="Question Type"
        name={[question.name, 'select']}
        fieldKey={[question.fieldKey, 'select']}
      // name={`questionType${question.fieldKey + 1}`}
      // key={`select ${question.fieldKey + 1}`}
      >
        <Select
          placeholder="Select a question type"
          onChange={onQuestionTypeChange}
          allowClear
        >
          <Option value="multipleChoice">Multiple Choice</Option>
          <Option value="shortAnswer">Short Answer</Option>
        </Select>
      </Form.Item>

      {questionType === "shortAnswer" && (
        <Form.Item
          // key={`shortAnswer ${question.fieldKey + 1}`}
          label="Description"
          // name={`questionDescription${question.fieldKey + 1}`}
          name={[question.name, 'shortAnswer']}
          fieldKey={[question.fieldKey, 'shortAnswer']}
        >
          <Input.TextArea placeholder="Question description..." />
        </Form.Item>
      )}

      {questionType === "multipleChoice" && (
        <>
          <Form.Item
            // key={`multipleChoice ${question.fieldKey + 1}`}
            label="Description"
            // name={`questionDescription${question.fieldKey + 1}`}
            name={[question.name, 'multipleChoice']}
            fieldKey={[question.fieldKey, 'multipleChoice']}
          >
            <Input.TextArea placeholder="Question description..." />
          </Form.Item>

          {/* <Form.Item
            // {...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
            // label={`Option ${index + 1}`}
            label="Option"
            required={false}
          // key={field.key}
          >
            <Form.Item
              // {...field}
              validateTrigger={['onChange', 'onBlur']}
              rules={[
                {
                  required: true,
                  message: "Please enter an option",
                },
              ]}
              noStyle
            >
              <Input placeholder="Enter option here..." style={{ width: '85%' }} />
            </Form.Item>
          </Form.Item> */}

          <Form.List name={`options${question.fieldKey + 1}`} key={`options ${question.fieldKey + 1}`}>
            {(fields, { add, remove }) => {
              return (
                <div>
                  {fields.map((field, index) => {
                    console.log(fields)
                    console.log(field)
                    console.log(index)
                    // if (index === 0 || index === 1) return;
                    return (
                      <Form.Item
                        // {...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
                        label={`Option ${index + 1}`}
                        required={false}
                        key={field.key}
                      >
                        <Form.Item
                          {...field}
                          validateTrigger={['onChange', 'onBlur']}
                          rules={[
                            {
                              required: true,
                              message: "Please enter an option",
                            },
                          ]}
                          noStyle
                        >
                          <Input placeholder="Enter option here..." style={{ width: '85%' }} />
                        </Form.Item>
                        {fields.length > 1 ? (
                          <MinusCircleOutlined
                            style={{ margin: '0 8px' }}
                            onClick={() => {
                              remove(field.name);
                            }}
                          />
                        ) : null}
                      </Form.Item>
                    )
                  })}
                  <Form.Item>
                    <Button
                      type="dashed"
                      onClick={() => {
                        add();
                      }}
                      style={{ width: '60%' }}
                    >
                      <PlusOutlined /> Add Option
                  </Button>
                  </Form.Item>
                </div>
              );
            }}
          </Form.List>
        </>
      )}

      <Button
        key={`removeQuestion ${question.fieldKey + 1}`}
        type="dashed"
        onClick={() => {
          removeQuestion(question.name);
        }}
      >
        Remove Question
      </Button>
    </Form.Item>
  )
};

export default CreateQuestion;