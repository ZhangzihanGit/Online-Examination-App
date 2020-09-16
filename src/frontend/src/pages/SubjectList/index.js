import React, { useState } from 'react';
import { List, Card } from 'antd';

const SubjectList = ({ list }) => {
  const [imgLoading, setImgLoading] = useState(true);
  console.log(list)

  const handleSubjectClick = (item) => {
    console.log(item)
  }

  return (
    <List
      size="small"
      // loading
      grid={{ gutter: 24, xxl: 4, xl: 3, lg: 2, md: 2, sm: 2, xs: 1 }}
      dataSource={list}
      renderItem={(item) => (
        <List.Item onClick={() => handleSubjectClick(item)}>
          <Card
            hoverable
            loading={imgLoading}
            size="small"
            cover={
              <img
                alt={item.cover}
                src={item.cover}
                onLoad={() => setImgLoading(false)}
              />}
          >
            <Card.Meta
              title={<a href="#!">{item.code}</a>}
              description={item.name}
            />
          </Card>
        </List.Item>
      )}
    />
  )
};

export default SubjectList;