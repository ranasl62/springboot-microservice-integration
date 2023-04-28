// import React, { useState,useEffect  } from 'react';
// import { Container, Typography, Box, Button, Stack } from '@mui/material';
// import BarChart from './BarChart';
import React, {useEffect, useState} from 'react';
import {Line} from 'react-chartjs-2';
import 'chartjs-adapter-date-fns'; // Add this line
//import 'chartjs-adapter-date-fns';
import {Chart} from 'chart.js';
import DateAdapter from 'chartjs-adapter-date-fns';

Chart.register(DateAdapter);

const networkDataFromApi = [];
const addNewNetworkData = (networkData) => {
    if (networkDataFromApi.length == 20) {
        for (let i = 1; i <= 19; i++) {
            networkDataFromApi[i - 1] = networkDataFromApi[i];
        }
        networkDataFromApi[networkDataFromApi.length - 1] = networkData;
    } else networkDataFromApi[networkDataFromApi.length] = networkData;
}


const Network = ({computerID}) => {
    const [labels, setLabels] = useState([]);
    const [data, setData] = useState([]);
    const [currentData, setCurrentData] = useState([]);

    const fetchData = async () => {
        const response = await fetch('http://localhost:8081/api/network-data/' + computerID + '/get-current-data');
        const jsonData = await response.json();
        let dataFromApi = [];
        dataFromApi[dataFromApi.length] = jsonData.time;
        dataFromApi[dataFromApi.length] = jsonData.received;
        dataFromApi[dataFromApi.length] = jsonData.sent;
        console.log(dataFromApi);
        addNewNetworkData(dataFromApi);
        setCurrentData(jsonData);
        setData(networkDataFromApi);
        setLabels(['time', 'received', 'sent']);

        // setData((prevData) => [...prevData, jsonData.data]);
        // setData(jsonData.data);
        // setLabels(jsonData.labels);
    };

    useEffect(() => {
        fetchData();
        const interval = setInterval(() => {
            fetchData();
        }, 1000);
        return () => clearInterval(interval);
    }, []);

    const chartData = {
        labels: data.map(item => (item[0] * 1000)),

        datasets: labels.slice(1).map((label, index) => ({
            label,
            data: data.map(item => item[index + 1]),
            backgroundColor: `hsla(${(index * 40) % 360}, 70%, 50%, 0.2)`,
            borderColor: `hsla(${(index * 40) % 360}, 70%, 50%)`,
            borderWidth: 1,
            fill: true,
        })),
    };
    console.log("=========================xxxxxxxx" + chartData.labels)

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
            <h1>NETWORK USAGE</h1>
            <marquee>
                <p>
                    Last update: {`Received: ${currentData.received}, Sent: ${currentData.sent}`}
                </p>
            </marquee>
            <Line data={chartData} options={options}/>
        </div>
    );
};
export default Network;