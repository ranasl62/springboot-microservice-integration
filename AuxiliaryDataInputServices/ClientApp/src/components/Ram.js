import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import 'chartjs-adapter-date-fns'; 
import { Chart } from 'chart.js';
import DateAdapter from 'chartjs-adapter-date-fns';

Chart.register(DateAdapter);

const ramDataFromApi = [];
const addNewRamData = (ramData) => {
  if(ramDataFromApi.length == 20) {
    for(let i = 1; i <= 19; i++) {
      ramDataFromApi[i - 1] = ramDataFromApi[i];
    }
    ramDataFromApi[ramDataFromApi.length - 1] = ramData;
  }
  else ramDataFromApi[ramDataFromApi.length] = ramData;
}


const Ram = ({computerID}) => {
  const [labels, setLabels] = useState([]);
  const [data, setData] = useState([]);
  const [currentData, setCurrentData] = useState([]);

  const fetchData = async () => {
    const response = await fetch('http://localhost:8081/api/ram-data/' + computerID + '/get-current-data');
    const jsonData = await response.json();
  
    let dataFromApi = [];
    dataFromApi[dataFromApi.length] = jsonData.time;
    dataFromApi[dataFromApi.length] = jsonData.active;
    dataFromApi[dataFromApi.length] = jsonData.wired;
    dataFromApi[dataFromApi.length] = jsonData.throttled;
    dataFromApi[dataFromApi.length] = jsonData.compressor;
    dataFromApi[dataFromApi.length] = jsonData.inactive;
    dataFromApi[dataFromApi.length] = jsonData.purgeable;
    dataFromApi[dataFromApi.length] = jsonData.speculative;
    dataFromApi[dataFromApi.length] = jsonData.free;
    setCurrentData(jsonData);
    addNewRamData(dataFromApi);

    setData(ramDataFromApi);
    setLabels(['time', 'active', 'wired','throttled', 'compressor','inactive','purgeable','speculative','free']);
   
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(() => {
      fetchData();
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  const chartData = {
    labels: data.map(item => (item[0]*1000)),
    
    datasets: labels.slice(1).map((label, index) => ({
      label,
      data: data.map(item => item[index + 1]),
      backgroundColor: `hsla(${(index * 40) % 360}, 70%, 50%, 0.2)`,
      borderColor: `hsla(${(index * 40) % 360}, 70%, 50%)`,
      borderWidth: 1,
      fill: true,
    })),
  };

  const options = {
    scales: {
      x: {
        type: 'time',
        time: {
          parser: 'M/dd/yyyy, hh:mm:ss a',
          unit: 'second',
          displayFormats: {
            second: 'HH:mm:ss',
          },
        },
      },
      y: {
        beginAtZero: true,
      },
    },
  };
  

  return (
    <div>
      <h1>RAM USAGE</h1>
      <marquee>
        <p>
          Last update:  {`Active: ${currentData.active}, Wired: ${currentData.wired}, Throttled: ${currentData.throttled}, Compressor: ${currentData.compressor}, Inactive: ${currentData.inactive}, Purgeable: ${currentData.purgeable}, Speculative: ${currentData.speculative}, Free: ${currentData.free}`}
        </p>
      </marquee>
      <Line data={chartData} options={options} />
    </div>
  );
};

export default Ram;

