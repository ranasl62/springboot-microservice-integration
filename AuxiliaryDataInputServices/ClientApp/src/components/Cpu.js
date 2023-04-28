import React, {useEffect, useState} from 'react';

import {Line} from 'react-chartjs-2';
import 'chartjs-adapter-date-fns'; // Add this line
import {Chart} from 'chart.js';
import DateAdapter from 'chartjs-adapter-date-fns';
import ComputerList from './ComputerList';

const cpuDataFromApi = [];
//var computerID = 3;

const addNewCpuData = (cpuData) => {
    if (cpuDataFromApi.length == 20) {
        for (let i = 1; i <= 19; i++) {
            cpuDataFromApi[i - 1] = cpuDataFromApi[i];
        }
        cpuDataFromApi[cpuDataFromApi.length - 1] = cpuData;
    } else cpuDataFromApi[cpuDataFromApi.length] = cpuData;
}

Chart.register(DateAdapter);

const Cpu = ({computerID}) => {
    const [labels, setLabels] = useState([]);
    const [data, setData] = useState([]);
    const [currentData, setCurrentData] = useState([]);

    const changeMetrics = () => {
        return (
            <div>
                <ComputerList/>
            </div>
        )
    }

    const fetchData = async () => {
        const response = await fetch('http://localhost:8081/api/cpu-data/' + computerID + '/get-current-data');
        const jsonData = await response.json();

        let dataFromApi = [];
        setCurrentData(jsonData);
        dataFromApi[dataFromApi.length] = jsonData.time;
        dataFromApi[dataFromApi.length] = jsonData.user;
        dataFromApi[dataFromApi.length] = jsonData.nice;
        dataFromApi[dataFromApi.length] = jsonData.system;
        addNewCpuData(dataFromApi);
        console.table(cpuDataFromApi);
        setData(cpuDataFromApi);
        setLabels(['time', 'user', 'nice', 'system']);
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
    const options = {
        scales: {
            x: {
                type: 'time',
                minUnit: 'second',
                time: {
                    parser: 'M/dd/yyyy, hh:mm:ss a',
                    unit: 'second',
                    displayFormats: {
                        second: 'HH:mm:ss',
                    },
                },
                ticks: {
                    stepSize: 1,
                },
            },
            y: {
                beginAtZero: true,
            },
        },
    };

    //console.log(chartData.labels);

    return (
        <div>
            <h1>CPU USAGE</h1>
            <marquee>
                <p>
                  Last update:  {`User: ${currentData.user}, Nice: ${currentData.nice}, System: ${currentData.system}`}
                </p>
            </marquee>
            <Line data={chartData} options={options}/>
            <button onClick={changeMetrics}></button>
        </div>
    );
};
export default Cpu;