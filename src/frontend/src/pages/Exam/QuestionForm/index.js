import React, { useState } from 'react';
import { Form, Input, Button, Select } from 'antd';
import { MinusCircleOutlined, PlusSquareOutlined } from '@ant-design/icons';

const { Option } = Select;

const QuestionForm = ({ field }) => {
  const [questionType, setQuestionType] = useState(null);

  const onQuestionTypeChange = (value) => {
    if (value === "multipleChoice") {
      setQuestionType("multipleChoice");
    } else if (value === "shortAnswer") {
      setQuestionType("shortAnswer");
    }
  };

  return (
    <>
      <Form.Item
        {...field}
        label="Question Type"
        name={[field.name, 'questionType']}
        fieldKey={[field.fieldKey, 'questionType']}
        rules={[{
          required: true,
          message: "Please select question type",
        }]}
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

      {questionType === "multipleChoice" && (
        <>
          <Form.Item
            {...field}
            label="option1"
            name={[field.name, 'option1']}
            fieldKey={[field.fieldKey, 'option1']}
            rules={[{ required: true, message: 'Please enter an option' }]}
          >
            <Input placeholder="Enter an option" />
          </Form.Item>
          <Form.Item
            {...field}
            key={`option2${field.key}`} // override key
            label="option2"
            name={[field.name, 'option2']}
            fieldKey={[field.fieldKey, 'option2']}
            rules={[{ required: true, message: 'Please enter an option' }]}
          >
            <Input placeholder="Enter an option" />
          </Form.Item>

          <Form.List
            {...field}
            key={`options${field.key}`}  // override key
            name={[field.name, 'options']}
            fieldKey={[field.fieldKey, 'options']}
          >
            {(optionFields, { add, remove }) => {
              return (
                <div>
                  {optionFields.map((optionField, index) => {
                    return (
                      <Form.Item
                        label={`option${index + 3}`}
                        key={`option${optionField.key}`}
                      >
                        <Form.Item
                          {...optionField}
                          key={`option${index + 3}`}
                          name={[optionField.name, `option${index + 3}`]}
                          fieldKey={[optionField.fieldKey, `option${index + 3}`]}
                          validateTrigger={['onChange', 'onBlur']}
                          rules={[
                            {
                              required: true,
                              message: "Please enter option or delete it",
                            },
                          ]}
                          noStyle
                        >
                          <Input placeholder="Enter option here..." style={{ width: '90%' }} />
                        </Form.Item>
                        <MinusCircleOutlined
                          style={{ margin: '0 8px' }}
                          onClick={() => {
                            remove(optionField.name);
                          }}
                        />
                      </Form.Item>
                    )
                  })}

                  <Form.Item>
                    <Button
                      type="ghost"
                      size="small"
                      onClick={() => {
                        add();
                      }}
                      style={{ width: '70%' }}
                    >
                      <PlusSquareOutlined /> Add Option
                    </Button>
                  </Form.Item>
                </div>
              )
            }}
          </Form.List>
        </>
      )}
    </>
  )
};

export default QuestionForm;