import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Card, Form, Radio, Input, Button, Checkbox } from 'antd';
import { HighlightOutlined } from '@ant-design/icons';
import { splitOptions, findAnswerById } from '../../../utils/helpers';
import { SAVE_ANSWER } from '../../../constants/actions';
import styles from './index.module.less';

const AnswerCard = () => {
  const dispatch = useDispatch();

  const handleSaveMark = (e) => {
    console.log(e.target.value);
  }

  return (
    <div className={styles.cardContainer}>
      <Card title={"dasdsaasd"} className={styles.questionCard}>
        <Input.TextArea
          disabled
          autoSize={{ minRows: 5, maxRows: 10 }}
          value={"ahah"}
        />
      </Card>

      <Input
        className={styles.markInput}
        defaultValue="0"
        addonBefore={<span><HighlightOutlined /> Mark this question</span>}
        suffix={`/ ${10}`}
        onBlur={handleSaveMark}
      />
    </div>
  );
};

export default AnswerCard;