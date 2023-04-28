import React, { useEffect, useState } from 'react'
import axios from "axios";

export default function Home() {

    const [inputData, setInputData] = useState([]);

    useEffect(() => {
        loadData();

    }, []);

    const loadData = async () => {
        const result = await axios.get("http://localhost:8380/datainput/messages");
        console.log(result.data);
        setInputData(result.data);
    }

    const onStart = async (e) => {
        e.preventDefault();
        await axios.post("http://localhost:8380/datainput/messages/start");
    };

    const view = async (e) => {
        e.preventDefault();
        await axios.get("http://localhost:8380/datainput/messages");
        window.location.reload(false);
    };

    const onStop = async (e) => {
        e.preventDefault();
        await axios.post("http://localhost:8380/datainput/messages/stop");
    };

    const onClear = async (e) => {
        e.preventDefault();
        await axios.post("http://localhost:8380/datainput/messages/clear");
        window.location.reload(false);
    };

    return (
        <div className='container'>

            <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                <form onSubmit={(e) => onStart(e)}>
                    <button type="button" onClick={(e) => view(e)} class="btn btn-success mx-2 mt-2">Dispay Data</button>
                    <button type="submit" class="btn btn-primary mx-2 mt-2">Start</button>
                    <button type="button" onClick={(e) => onStop(e)} class="btn btn-danger mx-2 mt-2">Stop</button>
                    <button type="button" onClick={(e) => onClear(e)} class="btn btn-warning mx-2 mt-2">Clear</button>
                </form>
            </div>


            <div className='py-4'>
                <table className="table border">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Topic name</th>
                            <th scope="col">Input Data</th>
                        </tr>
                    </thead>
                    <tbody>

                        {
                            inputData.map((inputd, index) => (
                                <tr>
                                    <th scope="row" key={index}>{index + 1}</th>
                                    <td>{inputd.topic}</td>
                                    <td>{inputd.message}</td>
                                </tr>
                            ))
                        }

                    </tbody>
                </table>
            </div>
        </div>
    )
}
