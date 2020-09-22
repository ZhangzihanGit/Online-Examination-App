import React from 'react';
import { useParams } from 'react-router-dom';

const Subject = () => {
  const { code } = useParams();

  return (
    <>
      <h1>{code}</h1>
    </>
  )
};

export default Subject;