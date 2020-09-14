import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'antd';

const Home = () => {

  return (
    <>
      <div>Home Page</div>
      <Button type="primary">
        <Link to="/login">Login</Link>
      </Button>
    </>
  )
}

export default Home;