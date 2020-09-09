import React, { useState } from 'react';
import './App.css';

function App() {

  const [data, setData] = useState("");

  const handleClick = async () => {
    try {
      const resposne = await fetch('login');
      const msg = await resposne.text();
      console.log(msg);
      setData(msg);
    } catch {
      console.log('fetch data error');
    }
  }

  return (
    <div className="App">
      hello world from react

      <br />
      <button onClick={handleClick}>Get</button>
      <br />
      {`From server: ${data}`}
    </div>
  );
}

export default App;
