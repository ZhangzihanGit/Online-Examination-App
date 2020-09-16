import React from 'react';
import { List, Card } from 'antd';

const Subjects = ({ list }) => {

  return (
    <List
      grid={{ gutter: 24, xxl: 4, xl: 3, lg: 2, md: 2, sm: 2, xs: 1 }}
      dataSource={list}
      renderItem={(item) => (
        <List.Item>
          <Card
            hoverable
            cover={<img alt={item.cover} src={item.cover} />}
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

export default Subjects;