import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import 'chartjs-adapter-date-fns'; // Add this line
import { Chart } from 'chart.js';
import DateAdapter from 'chartjs-adapter-date-fns';
Chart.register(DateAdapter);
const diskDataFromApi = [];

const addNewDiskData = (diskData) => {
  if(diskDataFromApi.length == 20) {
    for(let i = 1; i <= 19; i++) {
      diskDataFromApi[i - 1] = diskDataFromApi[i];
    }
    diskDataFromApi[diskDataFromApi.length - 1] = diskData;
  }
  else diskDataFromApi[diskDataFromApi.length] = diskData;
}

const Disk = ({computerID}) => {
  const [labels, setLabels] = useState([]);
  const [data, setData] = useState([]);
  const [currentData, setCurrentData] = useState([]);


  const fetchData = async () => {
    const response = await fetch('http://localhost:8081/api/disk-data/' + computerID + '/get-current-data');
    const jsonData = await response.json();
    let dataFromApi = [];
    dataFromApi[dataFromApi.length] = jsonData.time;
    dataFromApi[dataFromApi.length] = jsonData.in;
    dataFromApi[dataFromApi.length] = jsonData.out;
    setCurrentData(jsonData);
    addNewDiskData(dataFromApi);
     setData(diskDataFromApi);
    setLabels(['time', 'in', 'out']);
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
  console.log("=========================xxxxxxxx"+chartData.labels)

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
      <h1>DISK USAGE</h1>
      <marquee>
        <p>
          Last update:  {`In: ${currentData.in}, Out: ${currentData.out}`}
        </p>
      </marquee>
      <Line data={chartData} options={options} />
    </div>
  );
};
export default Disk;