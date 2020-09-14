import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'antd';
import * as api from '../../api/test';

const Login = () => {

  const [data, setData] = useState("");

  const handleClick = async () => {

    try {
      const response = await api.getTest();
      console.log(response);
      setData(response.data);
    } catch (error) {
      console.log(error);
      setData(error);
    }
  }

  return (
    <>
      <div>
        Login Page

      <br />
        <button onClick={handleClick}>Get</button>
        <br />
        {`From server: ${data}`}
      </div>
      <Button type="primary">
        <Link to="/">Home</Link>
      </Button>
    </>
  );
}

export default Login;