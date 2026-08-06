import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Audit() {

    const navigate = useNavigate();

    const [logs, setLogs] = useState([]);

    useEffect(() => {
        loadLogs();
    }, []);

    const loadLogs = async () => {

        try {

            const response = await API.get("/audit");

            setLogs(response.data);

        } catch (error) {

            console.error(error);

            alert("Unable to load audit logs.");

        }

    };

    return (

        <div style={{ padding: "30px" }}>

            <h2>Audit Logs</h2>

            <button
                onClick={() => navigate("/dashboard")}
            >
                Back to Dashboard
            </button>

            <br /><br />

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>User</th>
                        <th>Action</th>
                        <th>File</th>
                        <th>Date & Time</th>

                    </tr>

                </thead>

                <tbody>

                    {
                        logs.length === 0 ?

                        (
                            <tr>
                                <td colSpan="4">
                                    No audit logs found.
                                </td>
                            </tr>
                        )

                        :

                        logs.map(log => (

                            <tr key={log.id}>

                                <td>{log.userEmail}</td>

                                <td>{log.action}</td>

                                <td>{log.fileName}</td>

                                <td>{log.actionTime}</td>

                            </tr>

                        ))
                    }

                </tbody>

            </table>

        </div>

    );

}

export default Audit;