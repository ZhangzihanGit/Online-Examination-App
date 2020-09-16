import React, { useState } from 'react';
import { Link, useRouteMatch, } from 'react-router-dom';
import { List, Card } from 'antd';

const SubjectList = ({ list }) => {
  const [imgLoading, setImgLoading] = useState(true);
  const { url } = useRouteMatch();

  return (
    <List
      size="small"
      // loading
      grid={{ gutter: 24, xxl: 4, xl: 3, lg: 2, md: 2, sm: 2, xs: 1 }}
      dataSource={list}
      renderItem={(item) => (
        <List.Item>
          <Link to={`${url}/${item.code}`}>
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
                title={item.code}
                description={item.name}
              />
            </Card>
          </Link>
        </List.Item>
      )}
    />
  )
};

export default SubjectList;